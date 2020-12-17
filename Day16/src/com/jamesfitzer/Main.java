package com.jamesfitzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        HashMap<String, ArrayList<String>> constraints = new HashMap<>();
        HashMap<String, Field> fields = new HashMap<>();

        ArrayList<Integer> myTicket = new ArrayList<>();
        ArrayList<ArrayList<Integer>> nearbyTickets = new ArrayList<>();

        try {
            Scanner s = new Scanner(new File("resources/input.txt"));

            while (s.hasNextLine()) {
                String inputLine = s.nextLine();
                if(inputLine.contains("your ticket")){
                    inputLine = s.nextLine();
                    int[] ticket = Arrays.stream(inputLine.split(",")).mapToInt(Integer::parseInt).toArray();
                    for(int num: ticket){
                        myTicket.add(num);
                    }
                }
                if(inputLine.contains("nearby tickets")){
                    while(s.hasNextLine()){
                        inputLine = s.nextLine();
                        int[] ticket = Arrays.stream(inputLine.split(",")).mapToInt(Integer::parseInt).toArray();
                        ArrayList<Integer> newTicket = new ArrayList<>();
                        for(int num: ticket) {
                            newTicket.add(num);
                        }
                        nearbyTickets.add(newTicket);
                    }
                }
                if(inputLine.contains(":")){
                    String field = inputLine.split(":")[0];

                    //field is self explanatory, it's the string before the colon
                    // limits is basically a set of strings that look like : "7-23"
                    // splitting on the or and trimming. We will parse rules by splitting on the "-" for lower/upper bound

                    String[] limits = Arrays.stream(inputLine.split(":")[1].split("or")).map(String::trim).toArray(String[]::new);

                    for(String limit: limits){
                        constraints.putIfAbsent(field,new ArrayList<String>(Arrays.asList(limits)));
                    }
                }

            }

            int fieldNumber = myTicket.size();
            for(String field: constraints.keySet()){
                //using the constraints i parsed, make my fields array
                fields.put(field, new Field(field, fieldNumber, constraints.get(field)));
            }

            System.out.println("Part One: " + part1(nearbyTickets, fields));
            //solve problem here.
            System.out.println("Part Two: first figure out all fields... "  );
            int notFiguredOut;
            do{
                figureOutFields(nearbyTickets, fields);
                notFiguredOut = 0;
                for(Field field: fields.values()){
                    if(field.getIndex() == -1){
                        notFiguredOut++;

                    }
                }
            } while (notFiguredOut > 0);
            System.out.println("Now map our ticket to fields\nMy Ticket:\n-----------");
            long result = 1L;
            for(Field field: fields.values()){
                System.out.println(field.getName() + ": " + myTicket.get(field.getIndex()));
                if(field.getName().startsWith("departure")){
                    result *= myTicket.get(field.getIndex());
                }
            }
            System.out.println("Part 2 result is: " + result);



        } catch (FileNotFoundException ex) {
            System.out.println("Cannot find file");
        }
    }

    public static int part1(ArrayList<ArrayList<Integer>> nearbyTickets, HashMap<String, Field> fields){
        int sum = 0;

        //find invalid values, and remove tickets for which no values are valid for any field.
        Iterator<ArrayList<Integer>> itr = nearbyTickets.iterator();

        while(itr.hasNext()){
            ArrayList<Integer> ticket = itr.next();
            //System.out.println("Checking ticket: " + ticket.toString());


            for(int value: ticket){
                boolean result = false;
                for(String field: fields.keySet()){
                    //System.out.println("Value : " + value + " valid for field " + fields.get(field).getName() + ": " + fields.get(field).isValueValid(value));
                    if(fields.get(field).isValueValid(value)){
                        result = true;

                    }
                }
                //System.out.println(result);
                if(!result){
                    sum += value;
                    itr.remove();
                }
            }
        }


        return sum;
    }

    public static void figureOutFields(ArrayList<ArrayList<Integer>> nearbyTickets, HashMap<String,Field> fields){
        //right now we have a bunch of fields with indexes set to -1, and a full array of possible fields.

        //step one is to eliminate indexes for all fields if any ticket's index falls out of the valid range for that field.

        for( Field field: fields.values()){
            Iterator<Integer> itr = field.getPossibleIndices().iterator();
            while(itr.hasNext()){
                int i = itr.next();
                for(ArrayList<Integer> ticket: nearbyTickets){
                    if(!field.isValueValid(ticket.get(i))){
                        //this index isnt valid for this field, so, strike it as possible index for this field
                        itr.remove();
                    }
                }
            }
            // at the end of this, if possible indices is size 1, that's the index for the field
            if(field.getPossibleIndices().size() == 1){
                field.setIndex(field.getPossibleIndices().get(0));
            }
        }

        //now we have to check for any field which has an possible index that nobody else has, it's clearly the index. Set index, and remove everything else from possible indices.
        //this feels hashmap-y... i'm gonna hashmap this

        HashMap<Integer, ArrayList<String>> uniqueAcrossFields = new HashMap<>();

        for(Field field: fields.values()){
            for(int index: field.getPossibleIndices()){
                uniqueAcrossFields.putIfAbsent(index, new ArrayList<String>());
                uniqueAcrossFields.get(index).add(field.getName());
            }
        }

        for(int index: uniqueAcrossFields.keySet()){
            if(uniqueAcrossFields.get(index).size() == 1){
                // this index has only one field associated with it
                String nameOfField = uniqueAcrossFields.get(index).get(0);
                fields.get(nameOfField).setIndex(index);
                Iterator<Integer> possibles = fields.get(nameOfField).getPossibleIndices().iterator();
                while(possibles.hasNext()){
                    if(possibles.next() != index){
                        possibles.remove();
                    }
                }

            }
        }

        //now we have to go through all the fields, and if an index is SET for another field, remove it from the possibles for this field, sooooo
        // dont do the check if we iterate across the field we're checking.

        for(Field field: fields.values()){
            //only need to check fields with more than 1 possible index
            if(field.getPossibleIndices().size() > 1){
                Iterator<Integer> iter = field.getPossibleIndices().iterator();
                while(iter.hasNext()){
                    //for each index, check all the fields to see if that index is present. If it is, remove it from OUR possibles.
                    int currentIndex = iter.next();
                    for(Field fieldToCheck: fields.values()){
                        if(!field.getName().equals(fieldToCheck.getName())){
                            //not the current field
                            if(fieldToCheck.getIndex() == currentIndex){
                                iter.remove();
                            }
                        }
                    }
                }
            }
        }

        // as a final step, zip through all the fields again and if they only have one possible index, set that to the index...

        for(Field field: fields.values()){
            if(field.getPossibleIndices().size() == 1){
                field.setIndex(field.getPossibleIndices().get(0));
            }
        }

    }

}

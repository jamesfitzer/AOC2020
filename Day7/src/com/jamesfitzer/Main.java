package com.jamesfitzer;

import javax.naming.PartialResultException;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        ArrayList<String> input = new ArrayList<>();
        Pattern parentP = Pattern.compile("^\\w+ \\w+");
        Pattern childP = Pattern.compile("\\d+ \\w+ \\w+");
        HashMap<String, LinkedList<String>> rules = new HashMap<>();

        try{
            Scanner s = new Scanner(new File("resources/day7input.txt"));

            while(s.hasNextLine()){
                String rule = s.nextLine();
                Matcher parentM = parentP.matcher(rule);
                parentM.find();
                String parent = parentM.group();

                Matcher children = childP.matcher(rule);
                LinkedList<String> child = new LinkedList<>();
                while(children.find()) {
                    child.add(children.group());
                }
                rules.put(parent, child);
            }
            System.out.println("Loaded rules");

            System.out.println("Count of bags for part 1: " + part1(rules, "shiny gold"));
            System.out.println("------------------------");
            System.out.println("Count of bags for part 2: " + part2(rules, "shiny gold"));


        } catch (FileNotFoundException ex) {
            System.out.println("Cannot find file");
        }









    }
    public static int part1(HashMap<String, LinkedList<String>> rules, String theBagToFind){
        int count = 0;

        for(String bag: rules.keySet()){
            if(canContain(rules, theBagToFind, bag)){
                count++;
            }
        }

        return count;
    }

    public static int part2(HashMap<String, LinkedList<String>> rules, String theBagToCountContents){

        return countContents(rules, theBagToCountContents) - 1;

    }

    public static int countContents(HashMap<String, LinkedList<String>> rules, String theBagToCountContents){
        int count = 1;
        for(String contents: rules.get(theBagToCountContents)) {
            int quantity = Integer.parseInt(contents.substring(0, contents.indexOf(' ')));
            String newBag = contents.substring(contents.indexOf(' ') + 1);
            count += quantity * countContents(rules, newBag);
        }
        return count;
    }

    public static boolean canContain(HashMap<String, LinkedList<String>> rules, String bagToFind, String bagToSearch){
        if(rules.get(bagToSearch).size() == 0){
            return false;
        } else {


            //check to see if bagtofind is in our contents
            for(String contents: rules.get(bagToSearch)){
                if(contents.contains(bagToFind)){
                    return true;
                }
            }

            //didnt find it at top level, so now need to check inner bags.
            boolean returnValue = false;


            for(String contents: rules.get(bagToSearch)){

                if(canContain(rules, bagToFind, contents.substring(2))){
                    returnValue = true;
                }
            }

            return returnValue;
        }
    }
}

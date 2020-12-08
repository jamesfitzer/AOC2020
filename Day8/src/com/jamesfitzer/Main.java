package com.jamesfitzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        ArrayList<String> input = new ArrayList<>();

        try {
            Scanner s = new Scanner(new File("resources/day8input.txt"));

            while (s.hasNextLine()) {
                input.add(s.nextLine());
            }

            System.out.println("Part 1: " + part1(input));
            System.out.println("Part 2: " + part2(input));


        } catch (FileNotFoundException ex) {
            System.out.println("Cannot find file");
        }
    }

    public static int part1(ArrayList<String> input){
        //loops occur in this program when we visit an instruction more than once. So...
        boolean[] visited = new boolean[input.size()];
        Arrays.fill(visited, Boolean.FALSE);
        int accumulator = 0;

        //as we hit an index and execute it, mark as visited
        int i = 0;
        try{
            while(!visited[i]){
                String line = input.get(i);

                String operation = line.split(" ")[0];
                int argument = Integer.parseInt(line.split(" ")[1]);

                if(operation.equals("acc")){
                    visited[i] = true;
                    accumulator += argument;
                    i++;
                } else if (operation.equals("jmp")){
                    visited[i] = true;
                    i += argument;
                } else if (operation.equals("nop")) {
                    visited[i] = true;
                    i++;
                }
            }
        } catch(IndexOutOfBoundsException e){
            System.out.println("Terminating");
        }


        return accumulator;
    }

    public static int part2(ArrayList<String> input){
        //get indexes of nop, jmp instructions
        ArrayList<Integer> indexesToChange = new ArrayList<>();
        for(int i = 0; i < input.size();i++){
            if(input.get(i).contains("jmp") || input.get(i).contains("nop")){
                indexesToChange.add(i);
            }
        }

        for(int index : indexesToChange){
            //for each one, make a new arrayList of the input, swap the instruction, and try the operation. If it's NOT -1, return the value.
            ArrayList<String> temp = new ArrayList(input);

            if(temp.get(index).contains("jmp")){
                temp.set(index, temp.get(index).replace("jmp","nop"));
            } else if(temp.get(index).contains("nop")){
                temp.set(index, temp.get(index).replace("nop","jmp"));
            }
            int results = runInstructions(temp);
            if(results != -1) {
                return results;
            }
        }

        return -1;
    }

    public static int runInstructions(ArrayList<String> input){
        //run instructions, return acc if we complete without loop. If we loop, return -1.
        ArrayList<Integer> indexVisited = new ArrayList<>();
        int i = 0;
        int accumulator = 0;

        try{
            while(indexVisited.size() < input.size()){
                String line = input.get(i);

                String operation = line.split(" ")[0];
                int argument = Integer.parseInt(line.split(" ")[1]);

                if(indexVisited.contains(i)){
                    return -1;
                }

                if(operation.equals("acc")){
                    indexVisited.add(i);
                    accumulator += argument;
                    i++;
                } else if (operation.equals("jmp")){
                    indexVisited.add(i);
                    i += argument;
                } else if (operation.equals("nop")) {
                    indexVisited.add(i);
                    i++;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Complete");
        }



        return accumulator;
    }

}

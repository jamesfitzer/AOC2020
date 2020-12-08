package com.jamesfitzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        //i think this is just making a list and checking for unique values.
        // so , read strings in , double newline delimited, to make a "Group"

        try {
            String fileContents = new String(Files.readAllBytes(Paths.get("resources/day6input.txt")));

            String[] entries = fileContents.split("\\n\\n");
            long total = 0;
                        for(String group: entries){
                            //System.out.println(group);
                            long count = group.replace("\n", "").chars().distinct().count();
                            total += count;
                            //System.out.println(count);
                            //System.out.println("---------------");
                        }
                        System.out.println("Sum of group counts for part 1: " + total);



            //part two, questions everyone  answered instead of anyone

            total = 0;
            for(String group: entries) {
                boolean[] presentInAll = new boolean[26];
                //initialize a boolean array for each letter, start with true
                Arrays.fill(presentInAll,true);
                String[] people = group.split("\n");
                for(String person: people) {
                    //                    System.out.println(person);
                    //                    System.out.println("------");
                    int[] characterCount = new int[26];
                    for(char character : person.toCharArray()){
                        characterCount[character - 'a']++;
                    }
                    for(int i=0;i< characterCount.length;i++){
                        //System.out.print("Letter " + ((char)(i + 97)) + ": " + characterCount[i]);
                        //System.out.println();
                        if(characterCount[i] == 0){ //if this character isnt present in one "person", then it cant be common to the whole group.
                            presentInAll[i] = false;
                        }
                    }
                }
                //System.out.print("Letters present in all: " );
                int count = 0;
                for(int i = 0; i < presentInAll.length; i++) {
                    if(presentInAll[i] == true){
                        count++;
                    }
                }
                //System.out.println(count);
                total += count;
            }

            System.out.println("Total part Two: " + total);



        } catch (IOException ex) {
            System.out.println("Cannot find file");
        }


    }
}

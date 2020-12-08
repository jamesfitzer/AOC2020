package com.jamesfitzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int partOneCount = 0;
        int partTwoCount = 0;
        try{
            Scanner s = new Scanner(new File("resources/day2input.txt"));


            while(s.hasNextLine()){
                String lineToCheck = s.nextLine();
                if(conformsToPolicy(lineToCheck)){
                    partOneCount++;
                }
                if(conformsToNewPolicy(lineToCheck)){
                    partTwoCount++;
                }
            }

        } catch (FileNotFoundException ex) {
            System.out.println("Cannot find file");
        }

        System.out.println("Count of passwords that are valid for part 1: " + partOneCount);
        System.out.println("Count of passwords that are valid for part 2: " + partTwoCount);
    }

    public static boolean conformsToPolicy(String input){
        String[] data = input.split(" ");
        String range = data[0];
        char charToCheck = data[1].charAt(0);
        String password = data[2];
        int lowerBound = Integer.parseInt(range.split("-")[0]);
        int upperBound = Integer.parseInt(range.split("-")[1]);

        int count = 0;
        for(int i=0;i< password.length();i++){
            if(password.charAt(i) == charToCheck){
                count++;
            }
        }
        if(lowerBound <= count && count <= upperBound){
            return true;
        } else {
            return false;
        }
    }

    public static boolean conformsToNewPolicy(String input){
        String[] data = input.split(" ");
        String range = data[0];
        char charToCheck = data[1].charAt(0);
        String password = data[2];
        int firstPosition = Integer.parseInt(range.split("-")[0]) - 1;
        int secondPosition = Integer.parseInt(range.split("-")[1]) - 1;

        if(password.charAt(firstPosition) == charToCheck){
            if(password.charAt(secondPosition) == charToCheck){
                return false;
            } else {
                return true;
            }
        } else {
            if(password.charAt(secondPosition) == charToCheck){
                return true;
            } else {
                return false;
            }
        }

    }

}

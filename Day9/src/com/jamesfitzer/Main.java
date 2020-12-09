package com.jamesfitzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ArrayList<Long> input = new ArrayList<>();

        try {
            Scanner s = new Scanner(new File("resources/input.txt"));

            while (s.hasNextLine()) {
                input.add(Long.parseLong(s.nextLine()));
            }

            Long invalidNumber = part1(input,25);
            System.out.println("First invalid number (part 1) " + invalidNumber);
            System.out.println("Weakness (part 2): " + part2(invalidNumber, input));


        } catch (FileNotFoundException ex) {
            System.out.println("Cannot find file");
        }
    }

    public static long part1(ArrayList<Long> input, int preambleLength){
        //return -1 if whole list is valid, return first number in list that isnt valid otherwise
        //start with first index after preamble length
        for(int i = preambleLength; i < input.size();i++){
            if(!isValid(input.get(i), input.subList(i-preambleLength, i)) ){
                return input.get(i);
            }
        }
        return (long) -1;
    }

    public static boolean isValid(Long number, List<Long> previousNumbers){
        //System.out.println("Checking " + number + " against " + previousNumbers.toString());
        for(int i = 0;i < previousNumbers.size() - 1; i++) {
            for(int j = i + 1; j < previousNumbers.size();j++){
                if(previousNumbers.get(i) + previousNumbers.get(j) == number){
                    return true;
                }
            }
        }
        return false;
    }

    public static long part2(long invalidNumber, ArrayList<Long> input){
        //find a contigious set of numbers that adds up to the invalid number
        //if exists, sum first and last number and return
        //if not, return -1

        for(int i = 0;i < input.size();i++){
            for(int j = i+1;j < input.size();j++){
                List<Long> range = input.subList(i,j);
                //System.out.println("Checking range " + range.toString() + " to see if sum equals " + invalidNumber);
                long sum = range.stream().mapToLong(Long::longValue).sum();
                //System.out.println("Sum is: " + sum);

                if(sum > invalidNumber){
                    //System.out.println("Sum: " + sum + " , went over. moving to next number");
                    break;
                } else if (sum == invalidNumber) {
                    //System.out.println("Found weakness!");
                    return Collections.max(range) + Collections.min(range);
                }
            }
        }



        return (long)-1;
    }
}

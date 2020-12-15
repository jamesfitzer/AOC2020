package com.jamesfitzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ArrayList<Integer> input = new ArrayList<>();

        input.add(2);
        input.add(1);
        input.add(10);
        input.add(11);
        input.add(0);
        input.add(6);

        System.out.println("Part 1: " + part1(input, 2020));
        System.out.println("Part 2: " + part1(input, 30000000));

    }

    public static int part1(ArrayList<Integer> input, int whichNumber){
        ArrayList<Integer> numbers = cloneList(input);
        HashMap<Integer, ArrayList<Integer>> indices = new HashMap<>();

        //load initial numbers
        for(int i=0;i<numbers.size();i++){
            if(!indices.containsKey(numbers.get(i))){
                indices.put(numbers.get(i), new ArrayList<Integer>(Arrays.asList(i)));
            } else {
                indices.get(numbers.get(i)).add(i);
            }
        }
        System.out.println("Numbers array: " + numbers.toString());
        System.out.println("Loaded the map: " + indices.toString());

        for(int i = i = numbers.size(); i < whichNumber;i++){
            int num = numbers.get(i-1);
            //System.out.println("Turn " + (i + 1) + " : ");
            //System.out.println("Previous num = " + num);

            //two scenarios. One, it has only been seen once (the last item added), or its been seen before
            // if this is the only occurrance:
            if(indices.get(num).size() == 1){
                //add 0 to our array and update indices
                //System.out.println("Putting 0 into array");
                numbers.add(0);
                if(!indices.containsKey(0)){
                    indices.put(0, new ArrayList<Integer>(Arrays.asList(numbers.size()-1)));
                } else {
                    indices.get(0).add(numbers.size()-1);
                }
            } else {
                //System.out.println("indexes to subtract: " + (indices.get(num).size()-2) + " and " + (indices.get(num).size()-1) );
                int newNum = indices.get(num).get(indices.get(num).size()-1)  - indices.get(num).get(indices.get(num).size()-2);
                //System.out.println("Putting " + newNum + " into array");
                numbers.add(newNum);
                if(!indices.containsKey(newNum)){
                    indices.put(newNum, new ArrayList<Integer>(Arrays.asList(numbers.size()-1)));
                } else {
                    indices.get(newNum).add(numbers.size()-1);
                }
            }
            //System.out.println("Array: " + numbers.toString());
            //System.out.println("Map: " + indices.toString());
        }

        return numbers.get(numbers.size()-1);
    }

    public static ArrayList<Integer> cloneList(ArrayList<Integer> input){
        ArrayList<Integer> results = new ArrayList<>();

        for(int i: input){
            results.add(i);
        }

        return results;
    }
}

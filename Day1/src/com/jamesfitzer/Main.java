package com.jamesfitzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try{
            Scanner s = new Scanner(new File("resources/day1input.txt"));
            List<Integer> inputList = new ArrayList<>();
            while(s.hasNextInt()){
                inputList.add(s.nextInt());
            }
            System.out.println("Part1 solution: " + Part1(inputList.stream().mapToInt(i -> i).toArray()));
            System.out.println("Part2 solution: " + Part2(inputList.stream().mapToInt(i -> i).toArray()));
        } catch (FileNotFoundException ex) {
            System.out.println("Cannot find file");
        }
    }

    public static int Part1(int[] data){
        int result = 0;
        for (int i = 0; i < data.length;i++) {
            for(int j = i + 1; j < data.length;j++){
                if(data[i] + data[j] == 2020) {
                    return data[i] * data[j];
                }
            }
        }
        return result;
    }

    public static int Part2(int[] data){
        int result = 0;
        for (int i = 0; i < data.length;i++) {
            for(int j = i + 1; j < data.length;j++){
                for(int k = j + 1; k < data.length;k++){
                    if(data[i] + data[j] + data[k] == 2020) {
                        return data[i] * data[j] * data[k];
                    }
                }
            }
        }
        return result;
    }
}

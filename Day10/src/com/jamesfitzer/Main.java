package com.jamesfitzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ArrayList<Integer> input = new ArrayList<>();

        try {
            Scanner s = new Scanner(new File("resources/input.txt"));

            while (s.hasNextLine()) {
                input.add(Integer.parseInt(s.nextLine()));
            }

            //add outlet:
            input.add(0);

            Collections.sort(input);


            //add device (3 higher than highest adapter)
            input.add(3 + input.get(input.size()-1));

            System.out.println(input.toString());

            System.out.println("Part One: " + part1(input));
            System.out.println("Part Two: " + part2(input));


        } catch (FileNotFoundException ex) {
            System.out.println("Cannot find file");
        }
    }

    public static int part1(ArrayList<Integer> input) {
        int differencesOfOne = 0;
        int differencesOfThree = 0;

        for(int i = 0; i < input.size() - 1;i++){
            if (input.get(i+1) - input.get(i) == 3){
                differencesOfThree++;
            } else if (input.get(i+1) - input.get(i) == 1){
                differencesOfOne++;
            }
        }


        return differencesOfOne * differencesOfThree;
    }

    public static long part2(ArrayList<Integer> input){
        long[] count = new long[input.size()];

        count[0] = 1;
        for (int i = 1; i < input.size(); i++) {
            for (int j = i - 3; j < i; j++) {
                if (j >= 0 && input.get(i) - input.get(j) <= 3) {
                    count[i] += count[j];
                }
            }
        }

        return count[count.length - 1];
    }
}

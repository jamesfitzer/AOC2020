package com.jamesfitzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ArrayList<String> input = new ArrayList<>();
        ArrayList<String> binaryInput = new ArrayList<>();

        try{
            Scanner s = new Scanner(new File("resources/day5input.txt"));

            while(s.hasNextLine()){
                String lineToCheck = s.nextLine();
                input.add(lineToCheck);
            }

        } catch (FileNotFoundException ex) {
            System.out.println("Cannot find file");
        }

        //replace F and L with 0; B and R with 1, cuz this is just binary.
        for(String line: input) {
            String newLine = line.replaceAll("[FL]", "0").replaceAll("[BR]", "1");
            binaryInput.add(newLine);
        }

        //sanity check, find highest seat id
        Collections.sort(binaryInput);
        // highest binary number will also be highest seat id i think
        //first seven digits row, last three column. Base 2
        int highestSeatIdRow = Integer.parseInt(binaryInput.get(binaryInput.size()-1).substring(0,7), 2);
        int highestSeatIdColumn = Integer.parseInt(binaryInput.get(binaryInput.size()-1).substring(7,10), 2);
        int highestSeatId = highestSeatIdRow * 8 + highestSeatIdColumn;

        System.out.println("Part 1, highest seat ID: " + highestSeatId);

        int previousSeat = -1; //starting point
        //seat will be surrounded by people, so the entire missing rows may be red herring.
        //in theory all we have to do is find the first "missing" seat with people sitting
        // at previous and next seats.

        for(String seat: binaryInput) {
            int row = Integer.parseInt(seat.substring(0, 7), 2);
            int column = Integer.parseInt(seat.substring(7, 10), 2);
            int seatId = row * 8 + column;
            if (previousSeat != -1 && seatId != previousSeat + 1) { //if we arent on the first one, and our current seat isnt one greater than the previous, we've found our seat
                // the missing one which would be one less..
                System.out.println("My seat: " + (seatId - 1));
            }
            previousSeat = seatId;
        }
    }
}

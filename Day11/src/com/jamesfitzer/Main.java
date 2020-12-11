package com.jamesfitzer;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ArrayList<char[]> input = new ArrayList<>();


        try {
            Scanner s = new Scanner(new File("resources/input.txt"));

            while (s.hasNextLine()) {
                String line = s.nextLine();
                input.add(line.toCharArray());
            }

            char[][] seatMap = new char[input.size()][input.get(0).length];
            for(int i = 0;i < seatMap.length;i++){
                for (int j = 0; j < seatMap[i].length;j++){
                    seatMap[i][j] = input.get(i)[j];
                }
            }
            //printMap(seatMap);
            System.out.println("Part 1: " + part1(seatMap));
            System.out.println("Part 2: " + part2(seatMap));


        } catch (FileNotFoundException ex) {
            System.out.println("Cannot find file");
        }
    }


    public static void printMap(char[][] seatMap){
        for(int i = 0;i < seatMap.length;i++){
            for (int j = 0; j < seatMap[i].length;j++){
                System.out.print(seatMap[i][j]);
            }
            System.out.println();
        }
    }

    public static int part1(char[][] seatMap){
            int changes;

            do{
                changes = 0;
                //make a temp copy of the seatmap for manipulation:
                char[][] tempMap = Arrays.stream(seatMap)
                    .map((char[] row) -> row.clone())
                    .toArray((int length) -> new char[length][]);

                for(int i = 0; i < seatMap.length;i++){
                    for(int j = 0; j < seatMap[0].length;j++){
                        // if empty seat and no occupied adjacent seats, its occupied
                        if(seatMap[i][j] == 'L' && adjacentOccupiedSeats(seatMap,i,j) == 0){
                            tempMap[i][j] = '#';
                            changes++;
                        }
                        // if seat is occupied and >= 4 seats adjacenet are also occupied, becomes empty
                        else if (seatMap[i][j] == '#' && adjacentOccupiedSeats(seatMap, i, j) >= 4){
                            tempMap[i][j] = 'L';
                            changes++;
                        }
                    }
                }
                seatMap = tempMap;
            } while(changes > 0);

        return occupiedSeatsTotal(seatMap);
    }

    public static int part2(char[][] seatMap){
        int changes;
        //this is mostly dupe code of part 1, could prolly be refactored.

        do{
            changes = 0;
            //make a temp copy of the seatmap for manipulation:
            char[][] tempMap = Arrays.stream(seatMap)
                .map((char[] row) -> row.clone())
                .toArray((int length) -> new char[length][]);

            for(int i = 0; i < seatMap.length;i++){
                for(int j = 0; j < seatMap[0].length;j++){
                    // if empty seat and no occupied visible seats, its occupied
                    if(seatMap[i][j] == 'L' && occupiedSeatsVisible(seatMap,i,j) == 0){
                        tempMap[i][j] = '#';
                        changes++;
                    }
                    // if seat is occupied and >= 5 visible are also occupied, becomes empty
                    else if (seatMap[i][j] == '#' && occupiedSeatsVisible(seatMap, i, j) >= 5){
                        tempMap[i][j] = 'L';
                        changes++;
                    }
                }
            }
            seatMap = tempMap;
        } while(changes > 0);

        return occupiedSeatsTotal(seatMap);
    }

    public static int adjacentOccupiedSeats(char[][] seatMap, int row, int col){
        int count = 0;


        //check each seat around the current seat and check for occupancy. First part of if is bounds check
        // second part is to exclude the point we're "at"
        for(int i = row - 1; i <= row + 1; i++){
            for(int j = col - 1; j <= col + 1; j++){
                if(i >= 0 && j >= 0 && i < seatMap.length && j < seatMap[0].length && (i != row || j != col)){
                    if(seatMap [i][j] == '#'){
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public static int occupiedSeatsVisible(char[][] seatMap, int row, int col){
        int count = 0;

        Point[] directions = {
            new Point(1, 0), //these are basically slopes of directions
            new Point(-1,0),
            new Point(0,1),
            new Point(0,-1),
            new Point(1,1),
            new Point(1,-1),
            new Point(-1,1),
            new Point(-1,-1)
        };

        for(Point direction: directions){
            //check the point in the direction we're looking at. If we dont find a "seat", then keep moving in that direction
            // this is proly super inefficient but its easy to read and works.
            Point pointToCheck = new Point(row + direction.x, col + direction.y);
            boolean foundSeat = false;

            //while we havent found a seat and also the one we're checking is in our bounds
            while(!foundSeat && (pointToCheck.x >= 0 && pointToCheck.x < seatMap.length) && (pointToCheck.y >= 0 && pointToCheck.y < seatMap[0].length) ){
                if(seatMap[pointToCheck.x][pointToCheck.y] == '#'){
                    count++;
                    foundSeat = true;
                } else if (seatMap[pointToCheck.x][pointToCheck.y] == 'L'){
                    foundSeat = true;
                } else {
                    pointToCheck.x += direction.x;
                    pointToCheck.y += direction.y;
                }
            }
        }

        return count;
    }

    public static int occupiedSeatsTotal(char[][] seatMap){
        int count = 0;
        for(int i = 0; i < seatMap.length;i++){
            for(int j = 0; j < seatMap[0].length;j++){
                if(seatMap[i][j] == '#'){
                    count++;
                }
            }
        }
        return count;
    }


}

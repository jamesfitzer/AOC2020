package com.jamesfitzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        ArrayList<String> input = new ArrayList<>();
        HashSet<String> active = new HashSet<>();


        try {
            Scanner s = new Scanner(new File("resources/input.txt"));

            while (s.hasNextLine()) {
                input.add(s.nextLine());
            }

            System.out.println("Part1: " + part1(input));
            System.out.println("Part2: " + part2(input));


        } catch (FileNotFoundException ex) {
            System.out.println("Cannot find file");
        }
    }

    public static int part1(ArrayList<String> input){
        HashSet<String> active = new HashSet<>();
        final int turns = 6;
        //load active cells. 0,0,0,0 should? be
        int width = 0;
        int length = 0;
        int x = 0;
        int y = 0;
        int z = 0;

        for(String s: input){
            for(char c: s.toCharArray()){
                System.out.print(c);
                if(c == '#'){

                    active.add(x + "," + y + "," + z);
                }
                x++;
            }
            y++;
            x = 0;
            width = s.length();
            length = y;
            System.out.println();
        }

        System.out.println(active.toString());


        //z layers: start with z = 0
        // 1 turn: z = -1,0,-1
        // 2 turns: z = -2,-1,-0,1,2
        //3 turns, z = ??
        // to make sure i have room i suppose, z layers starts at -turns, goes to +turns. I dont think it'll ever grow bigger than that
        // for x , y, size, each cycle you could go left/up by at most 1 i think so -turn-1
        // max would be the starting board size + how many turns.
        //pad this for safety?

        for(int i = 0; i < turns;i++){
            HashSet<String> newBoard = new HashSet<>();

            for(int xc = (-turns-1);xc < (width+turns+1);xc++){
                for(int yc = (-turns-1);yc < (length+turns+1);yc++){
                    for(int zc = (-turns-1);zc < (turns+1);zc++){
                        List list = Arrays.asList(Integer.toString(xc),Integer.toString(yc),Integer.toString(zc));
                        String point = String.join(",", list);

                        int neighbors = countNeighbors(point, active);

                        if(active.contains(point)){
                            if(neighbors == 2 || neighbors == 3){
                                newBoard.add(point);
                            }
                        } else {
                            if(neighbors == 3){
                                newBoard.add(point);
                            }
                        }
                    }
                }
            }
            active = newBoard;
        }
        return active.size();
    }

    public static int part2(ArrayList<String> input){
        HashSet<String> active = new HashSet<>();
        final int turns = 6;
        //load active cells. 0,0,0,0 should? be
        int width = 0;
        int length = 0;
        int x = 0;
        int y = 0;
        int z = 0;
        int w = 0;

        for(String s: input){
            for(char c: s.toCharArray()){
                System.out.print(c);
                if(c == '#'){

                    active.add(x + "," + y + "," + z + "," + w);
                }
                x++;
            }
            y++;
            x = 0;
            width = s.length();
            length = y;
            System.out.println();
        }

        System.out.println(active.toString());


        //z layers: start with z = 0
        // 1 turn: z = -1,0,-1
        // 2 turns: z = -2,-1,-0,1,2
        //3 turns, z = ??
        // to make sure i have room i suppose, z layers starts at -turns, goes to +turns. I dont think it'll ever grow bigger than that
        // for x , y, size, each cycle you could go left/up by at most 1 i think so -turn-1
        // max would be the starting board size + how many turns.
        //pad this for safety?
        // to add fourth dimension, same rules as for z apply to w

        for(int i = 0; i < turns;i++){
            HashSet<String> newBoard = new HashSet<>();

            for(int xc = (-turns-1);xc < (width+turns+1);xc++){
                for(int yc = (-turns-1);yc < (length+turns+1);yc++){
                    for(int zc = (-turns-1);zc < (turns+1);zc++){
                        for(int wc = (-turns-1);wc < (turns+1);wc++){
                            List list = Arrays.asList(Integer.toString(xc),Integer.toString(yc),Integer.toString(zc), Integer.toString(wc));
                            String point = String.join(",", list);

                            int neighbors = countNeighbors2(point, active);

                            if(active.contains(point)){
                                if(neighbors == 2 || neighbors == 3){
                                    newBoard.add(point);
                                }
                            } else {
                                if(neighbors == 3){
                                    newBoard.add(point);
                                }
                            }
                        }
                    }
                }
            }

            active = newBoard;

        }
        return active.size();
    }

    public static int countNeighbors( String cell, HashSet<String> active){
        int result = 0;

        String[] coords = cell.split("\\,");
        int x = Integer.parseInt(coords[0]);
        int y = Integer.parseInt(coords[1]);
        int z = Integer.parseInt(coords[2]);

        for(int xc = -1; xc < 2; xc++){
            for(int yc= -1; yc < 2; yc++){
                for( int zc = -1; zc < 2; zc++){
                    if(!(xc == 0 && yc == 0 && zc == 0)){
                        List<String> list = Arrays.asList(Integer.toString(x+xc),Integer.toString(y+yc),Integer.toString(z+zc));
                        if(active.contains(String.join(",",list))){
                            result++;
                        }
                    }
                }
            }
        }


        return result;
    }

    public static int countNeighbors2( String cell, HashSet<String> active){
        int result = 0;

        String[] coords = cell.split("\\,");
        int x = Integer.parseInt(coords[0]);
        int y = Integer.parseInt(coords[1]);
        int z = Integer.parseInt(coords[2]);
        int w = Integer.parseInt(coords[3]);

        for(int xc = -1; xc < 2; xc++){
            for(int yc= -1; yc < 2; yc++){
                for( int zc = -1; zc < 2; zc++){
                    for(int wc = -1; wc < 2; wc++){
                        if(!(xc == 0 && yc == 0 && zc == 0 && wc == 0)){
                            List<String> list = Arrays.asList(Integer.toString(x+xc),Integer.toString(y+yc),Integer.toString(z+zc), Integer.toString(w+wc));
                            if(active.contains(String.join(",",list))){
                                result++;
                            }
                        }
                    }
                }
            }
        }


        return result;
    }


}

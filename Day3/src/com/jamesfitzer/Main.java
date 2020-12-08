package com.jamesfitzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ArrayList<String> input = new ArrayList<>();

        try{
            Scanner s = new Scanner(new File("resources/day3input.txt"));


            while(s.hasNextLine()){
                String lineToCheck = s.nextLine();
                input.add(lineToCheck);
            }

        } catch (FileNotFoundException ex) {
            System.out.println("Cannot find file");
        }

        System.out.println("Part 1: " + Toboggan(input, 3, 1));

        ArrayList<Integer> treeCounts = new ArrayList<>();
        treeCounts.add(Toboggan(input, 1, 1));
        treeCounts.add(Toboggan(input, 3, 1));
        treeCounts.add(Toboggan(input, 5, 1));
        treeCounts.add(Toboggan(input, 7, 1));
        treeCounts.add(Toboggan(input, 1, 2));

        long total = 1;

        for(int i = 0;i < treeCounts.size(); i++)
        {
            total *= treeCounts.get(i);
        }

        System.out.println("Part 2: " + total);
    }

    public static int Toboggan(ArrayList<String> forest, int xMove, int downMove){
        // always moving down, so downMove is how much, yMove is posie for right, negative for left.
        int trees = 0;

        int xPos = 0;
        int yPos = 0;

        while(yPos < forest.size() - 1){
            //roll off the edge right side
            if(xPos + xMove > (forest.get(yPos).length() - 1)) {
                xPos = xPos + xMove - forest.get(yPos).length();

            } else if (xPos + xMove < 0) {
                //roll off the edge left side
                xPos = forest.get(yPos).length() - (xPos + xMove);
            } else {
                xPos += xMove;
            }
            yPos += downMove;
            //System.out.println("xPos: " + xPos + "yPos: " + yPos);
            if(forest.get(yPos).charAt(xPos) == '#'){
                trees++;
            }

        }


        return trees;
    }
}

package com.jamesfitzer;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ArrayList<String> input = new ArrayList<>();

        try {
            Scanner s = new Scanner(new File("resources/input.txt"));

            while (s.hasNextLine()) {
                input.add(s.nextLine());
            }

            System.out.println("Part 1: " + part1(input));
            System.out.println("Part 2: " + part2(input));


        } catch (FileNotFoundException ex) {
            System.out.println("Cannot find file");
        }
    }

    public static double manhattanDistance(Point startPos,Point endPos){
        //start pos should always be 0,0 but i'm trying to anticipate part 2 changing that.
        return Math.abs(endPos.getX() - startPos.getX()) + Math.abs(endPos.getY() - startPos.getY());
    }

    public static double part1(ArrayList<String> input){

        char currentDir = 'E';
        Point currentPos = new Point(0,0);

        for(String instruction: input){
            char direction = instruction.charAt(0);
            int distance = Integer.parseInt(instruction.substring(1));
            if(direction =='L' || direction == 'R'){
                currentDir = turn1(currentDir, direction, distance);
            } else {
                if(direction == 'F'){
                    direction = currentDir;
                }

                switch(direction){
                    case 'N':
                        currentPos.y += distance;
                        break;
                    case 'S':
                        currentPos.y -= distance;
                        break;
                    case 'E':
                        currentPos.x += distance;
                        break;
                    case 'W':
                        currentPos.x -= distance;
                        break;
                }
            }
            //System.out.println("Moving : " + direction + " for " + distance + " distance. Ship facing : " + currentDir);
            //System.out.println("Current position of point: East / West " + currentPos.getX() + ", North / South " + currentPos.getY());
        }
        return manhattanDistance(new Point(0,0), currentPos);

    }

    public static double part2(ArrayList<String> input){
        Point ship = new Point(0,0);
        Point waypoint = new Point(10,1);

        for(String instruction: input){
            char direction = instruction.charAt(0);
            int distance = Integer.parseInt(instruction.substring(1));

            if(direction == 'L' || direction == 'R'){
                waypoint = turn2(waypoint, direction, distance);
            } else if(direction == 'F'){
                for(int i = 0; i < distance; i++){
                    ship.setLocation(ship.getX() + waypoint.getX(), ship.getY() + waypoint.getY());
                }
            } else {
                switch(direction){
                    case 'N':
                        waypoint.y += distance;
                        break;
                    case 'S':
                        waypoint.y -= distance;
                        break;
                    case 'E':
                        waypoint.x += distance;
                        break;
                    case 'W':
                        waypoint.x -= distance;
                        break;
                }
            }
            System.out.println("Ship position: " + ship.toString());
        }


        return manhattanDistance(new Point(0,0), ship);
    }

    public static Point turn2(Point currentWaypoint, char direction, int turnValue){
        if(turnValue >= 360){
            turnValue -= 360;
        }
        int turns = turnValue / 90;

        for(int i = 0; i < turns;i++){
            if(direction == 'R'){
                currentWaypoint.setLocation(currentWaypoint.getY(), currentWaypoint.getX());
                currentWaypoint.setLocation(currentWaypoint.getX(), currentWaypoint.getY() * -1);
            } else {
                currentWaypoint.setLocation(currentWaypoint.getY(), currentWaypoint.getX());
                currentWaypoint.setLocation(currentWaypoint.getX() * -1, currentWaypoint.getY());
            }
        }

        return currentWaypoint;
    }

    public static char turn1(char currentDir, char direction, int turnValue){
        String dirs = "NESW";
        char newDir = currentDir;
        if(direction != 0){
            int currentDirIndex = dirs.indexOf(currentDir);
            if(turnValue >= 360){
                turnValue -= 360;
            }
            int turns = turnValue / 90;

            for(int i = 0;i < turns; i ++){
                if(direction == 'L' && currentDirIndex == 0){
                    currentDirIndex = dirs.length()-1;
                } else if (direction == 'R' && currentDirIndex == 3){
                    currentDirIndex = 0;
                } else {
                    if(direction == 'L'){
                        currentDirIndex--;
                    } else if (direction == 'R'){
                        currentDirIndex++;
                    }
                }

            }
            newDir = dirs.charAt(currentDirIndex);
        }


        return newDir;
    }
}

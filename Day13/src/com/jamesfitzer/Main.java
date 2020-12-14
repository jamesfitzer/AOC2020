package com.jamesfitzer;

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

            int minimumTime = Integer.parseInt(input.get(0));
            String[] busses = input.get(1).split(",");
            ArrayList<Integer> bussesInService = new ArrayList<>();
            for (String bus : busses){
                if(!bus.equals("x")){
                    bussesInService.add(Integer.parseInt(bus));
                }
            }

            System.out.println("Minimum time: " + minimumTime);
            System.out.println("Busses in service : " + bussesInService.toString());
            System.out.println("Part 1: " + part1(minimumTime, bussesInService));
            System.out.println("Part 2: " + part2(busses));




            //solve problem here.


        } catch (FileNotFoundException ex) {
            System.out.println("Cannot find file");
        }
    }

    public static int part1(int minimumTime, ArrayList<Integer> bussesInService){
        int currentDepartureTime = Integer.MAX_VALUE;
        int currentBus = bussesInService.get(0);

        for (int bus: bussesInService){
            int earliestDepartureTime = (int)(Math.floor(minimumTime / bus) + 1) * bus;
            //System.out.println("Earliest departure time for bus: " + bus + " is : " + earliestDepartureTime);
            if(earliestDepartureTime < currentDepartureTime){
                currentDepartureTime = earliestDepartureTime;
                currentBus = bus;
            }
        }
        System.out.println("Best case, bus: " + currentBus + " with a departure time of : " + currentDepartureTime);

        return currentBus * (currentDepartureTime - minimumTime);
    }

    public static long part2(String[] busses){
        ArrayList<Long> ids = new ArrayList<>();
        for(String bus: busses){
            ids.add(bus.equals("x") ? -1 : Long.parseLong(bus));
        }

        long lcm = -1, time = -1;
        int index = 0;
        while (true) {
            final long id = ids.get(index);
            if (id == -1) {
                index++;
                continue;
            }

            if (lcm == -1) {
                lcm = id;
                time = id - index;
                index++;
                continue;
            }

            if ((time + index) % id == 0) {
                if (++index >= ids.size()) {

                    return time;
                }

                lcm *= id;
                continue;
            }

            time += lcm;
        }
    }
}

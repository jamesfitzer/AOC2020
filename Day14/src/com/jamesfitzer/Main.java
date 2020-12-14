package com.jamesfitzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ArrayList<String> input = new ArrayList<>();

        try {
            Scanner s = new Scanner(new File("resources/input.txt"));

            while (s.hasNextLine()) {
                input.add(s.nextLine());
            }
            //System.out.println("Part 1: " );
            //part1(input);
            System.out.println("Part 2: ");
            part2(input);
            //solve problem here.


        } catch (FileNotFoundException ex) {
            System.out.println("Cannot find file");
        }
    }

    public static void part1(ArrayList<String> input){
        char[] mask = new char[36];
        HashMap<Integer, Long> memory = new HashMap<>();

        for(String line: input){
            String[] pieces = line.split("=");
            if(pieces[0].trim().equals("mask")){
                mask = pieces[1].trim().toCharArray();
                //System.out.println("Mask:              " + new String(mask));
            } else {
                int index = Integer.parseInt(line.substring(pieces[0].indexOf('[')+1,pieces[0].indexOf(']')));
                int value = Integer.parseInt(pieces[1].trim());

                //convert decimal value to binary and stuff in char array.

                char[] binary = String.format("%36s", Integer.toBinaryString(value)).replace(' ', '0').toCharArray();

                //System.out.println("Binary:            " + new String(binary));
                //apply mask:
                for(int i = 0; i < mask.length;i++){
                    if(mask[i] != 'X'){
                        binary[i] = mask[i];
                    }
                }
                //System.out.println("Binary after mask: " + new String(binary));
                //System.out.println("-------");
                memory.put(index,Long.parseLong(new String(binary), 2));

            }
        }

        //System.out.println("Values by address: " );
//        for(int i: memory.keySet()){
//            System.out.println("Address: " + i + " , value: " + memory.get(i));
//        }
        System.out.println("Sum of all nonzero values: " + memory.values().stream().reduce(0L, Long::sum));
    }

    public static void part2(ArrayList<String> input){
        char[] mask = new char[36];
        HashMap<Long, Long> memory = new HashMap<>();

        for(String line: input){
            String[] pieces = line.split("=");
            if(pieces[0].trim().equals("mask")){
                mask = pieces[1].trim().toCharArray();
                //System.out.println("Mask:              " + new String(mask));
            } else {
                Long index = Long.parseLong(line.substring(pieces[0].indexOf('[')+1,pieces[0].indexOf(']')));
                Long value = Long.parseLong(pieces[1].trim());

                //convert address value  value to binary and stuff in char array.

                char[] binary = String.format("%36s", Long.toBinaryString(index)).replace(' ', '0').toCharArray();
                ArrayList<Long> addressesToModify = new ArrayList<>();

                //System.out.println("Address in Binary:            " + new String(binary));
                //System.out.println("Mask in binary:               " + new String(mask));
                //apply mask, first apply 1s
                for(int i = 0; i < mask.length;i++){
                    if(mask[i] == '1'){
                        binary[i] = '1';
                    } else if (mask[i] == 'X'){
                        binary[i] = 'X';
                    }
                }
                //System.out.println("Address in Binary:            " + new String(binary));
                generateAddresses(addressesToModify, new String(binary), "");
//                System.out.println("Generated Addresses\n---------------");
//                for(int address: addressesToModify){
//                    System.out.println("                              " + address);
//                }
                for(Long address: addressesToModify){
                    memory.put(address,value);
                }
            }
        }
//        System.out.println("Values by address: " );
//        for(Long i: memory.keySet()){
//            System.out.println("Address: " + i + " , value: " + memory.get(i));
//        }
        System.out.println("Sum of all nonzero values: " + memory.values().stream().reduce(0L, Long::sum));
    }

    public static void generateAddresses(ArrayList<Long> addresses, String mask, String address){
        char[] validValues = {'0','1'};

        if(mask.length() == 0){
            //nothing left in mask, add address to addresses
            addresses.add(Long.parseLong(address, 2));
        } else {
            if(mask.charAt(0) == 'X'){
                //wildcard
                for(char v: validValues){
                    generateAddresses(addresses, mask.substring(1), address + v);
                }
            } else {
                if(mask.charAt(0) == '1'){
                    generateAddresses(addresses,mask.substring(1), address + '1');
                } else {
                    generateAddresses(addresses,mask.substring(1), address + mask.charAt(0));
                }

            }
        }
    }
}

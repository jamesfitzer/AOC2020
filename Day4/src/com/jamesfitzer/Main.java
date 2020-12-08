package com.jamesfitzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        try {
            String fileContents = new String(Files.readAllBytes(Paths.get("resources/day4input.txt")));

            String[] entries = fileContents.split("\\n\\n");

            String test = "eyr:2029 byr:1931 hcl:z cid:128\n"
                + "ecl:amb hgt:150cm iyr:2015 pid:148714704";


            int validPassports = 0;
            for(String entry: entries) {
                if(isValid(entry) ){
                    validPassports++;
                }
            }

            int validPassportsPartTwo = 0;
            for(String entry: entries) {
                if(isValidPartTwo(entry)) {
                    validPassportsPartTwo++;
                }
            }

            System.out.println("Valid passports Part 1: " + validPassports);
            System.out.println("Valid passports Part 2: " + validPassportsPartTwo);

        } catch (IOException ex) {
            System.out.println("Cannot find file");
        }
    }

    public static boolean isValid(String passport){
        final ArrayList<String> requiredFields = new ArrayList<>();
        requiredFields.add("byr");
        requiredFields.add("iyr");
        requiredFields.add("eyr");
        requiredFields.add("hgt");
        requiredFields.add("hcl");
        requiredFields.add("ecl");
        requiredFields.add("pid");

        boolean isValid = true;

        for(String field: requiredFields) {
            if(!passport.contains(field)){
                isValid = false;
            }
        }

        return isValid;
    }

    public static boolean isValidPartTwo(String passport){
        boolean isValid = true;
        if(!isValid(passport)){
            return false;
        }
        System.out.println("----------");
        System.out.println(passport);
        String[] fields = passport.split("\\s");
        for(int i = 0; i < fields.length;i++){
            String[] splitString = fields[i].split(":");
            String theField = splitString[0];
            String contents = splitString[1];
            System.out.println(theField + ": ");

            switch(theField){
                case "byr":
                    if(Integer.parseInt(contents) < 1920 ||Integer.parseInt(contents) > 2002 ) {
                        isValid = false;
                        System.out.println("INVALID");
                    }
                    break;
                case "iyr":
                    if(Integer.parseInt(contents) < 2010 ||Integer.parseInt(contents) > 2020 ){
                        isValid = false;
                        System.out.println("INVALID");
                    }
                    break;
                case "eyr":
                    if(Integer.parseInt(contents) < 2020 ||Integer.parseInt(contents) > 2030 ){
                        isValid = false;
                        System.out.println("INVALID");
                    }
                    break;
                case "hgt":
                    try{
                        String numContents = contents.replaceAll("\\D", "");
                        int numericalValue = Integer.parseInt(numContents);
                        if(contents.contains("in")){
                            if(numericalValue < 59 || numericalValue > 76){
                                isValid = false;
                                System.out.println("INVALID");
                            }
                        } else if (contents.contains("cm")) {
                            if(numericalValue < 150 || numericalValue > 193){
                                isValid = false;
                                System.out.println("INVALID");
                            }
                        } else {
                            isValid = false;
                            System.out.println("INVALID");
                        }
                    }catch (NumberFormatException e){
                        isValid = false;
                        System.out.println("INVALID");
                    }
                    break;
                case "hcl":
                    if(contents.charAt(0) != '#' ){
                        isValid = false;
                        System.out.println("INVALID");
                    }
                    if(contents.length() != 7){
                        isValid = false;
                        System.out.println("INVALID");
                    }
                    for(int k = 1; k < contents.length();k++){
                        if(Character.isDigit(contents.charAt(k))){
                            //nothing to do here.
                        } else if(Character.isLetter(contents.charAt(k))){
                            String validCharacters = "abcdef";
                            if(!validCharacters.contains(Character.toString(contents.charAt(k)))){
                                isValid = false;
                                System.out.println("INVALID");
                            }
                        } else {
                            isValid = false;
                            System.out.println("INVALID");
                        }
                    }
                    break;
                case "ecl":
                    ArrayList<String> validEyeColors = new ArrayList<>();
                    validEyeColors.add("amb");
                    validEyeColors.add("blu");
                    validEyeColors.add("brn");
                    validEyeColors.add("gry");
                    validEyeColors.add("grn");
                    validEyeColors.add("hzl");
                    validEyeColors.add("oth");

                    if(!validEyeColors.contains(contents) ){
                        isValid = false;
                        System.out.println("INVALID");
                    }
                    break;
                case "pid":
                    if(contents.length() != 9){
                        isValid = false;
                        System.out.println("INVALID");
                    }
                    for(int j = 0;j < contents.length();j++){
                        if(!Character.isDigit(contents.charAt(j))){
                            isValid = false;
                            System.out.println("INVALID");
                        }
                    }
                    break;
            }
        }
        System.out.println("Is valid: " + isValid);
        return isValid;
    }

}

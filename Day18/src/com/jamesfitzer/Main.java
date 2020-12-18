package com.jamesfitzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class Main {

    public static void main(String[] args) {
        ArrayList<String> input = new ArrayList<>();

        try {
            Scanner s = new Scanner(new File("resources/input.txt"));

            while (s.hasNextLine()) {
                input.add(s.nextLine());
            }

            //solve problem here.
            System.out.println("Part 1: " + part1(input));
            System.out.println("Part 2: " + part2(input));


        } catch (FileNotFoundException ex) {
            System.out.println("Cannot find file");
        }
    }

    public static long part1(ArrayList<String> input){
        long result = 0;

        for(String problem: input){
            result += evaluateExpression(problem, 1);
        }
        return result;
    }

    public static long part2(ArrayList<String> input){
        long result = 0;

        for(String problem: input){
            result += evaluateExpression(problem, 2);
        }
        return result;
    }

    public static long evaluateExpression(String mathProblem, int part){
        long result = 0;

        mathProblem = new String(mathProblem.replace(" ",""));
        //System.out.println("Math problem: " + mathProblem);

        Stack<Integer> openingStack = new Stack<>();
        int i = 0;

        while(mathProblem.contains("(") || mathProblem.contains(")")){
            if(mathProblem.charAt(i) == '('){
                //System.out.println("Found open parenthesis at: " + i);
                openingStack.push(i);
            }
            if(mathProblem.charAt(i+1) == ')') {
                //System.out.println("Found close parenthesis at: " + (i+1));
                int open = openingStack.pop();
                // stuff between i+1 and "open" is content of inner parenthesis
                String innerString = mathProblem.substring(open+1, i+1);
                //System.out.println("Stuff between parens: " + innerString);
                long innerResult = 0;
                if(part==1){
                    innerResult = mathLeftToRight(innerString);
                } else{
                    innerResult = mathReversedPrecedence(innerString);
                }

                //System.out.println("doing math on above results in : " + innerResult);
                // replace original string with this result.
                mathProblem = new String(mathProblem.replace(("(" + innerString + ")"), String.valueOf(innerResult)));
                //System.out.println("After replacement, math problem is now: " + mathProblem);

                i = open + String.valueOf(innerResult).length() - 1;
                //System.out.println("Resetting index to :" + i);
                //System.out.println("Math problem lenth: " + mathProblem.length());
            } else{
                i++;
            }


        }
        //System.out.println(openingStack.toString());
        //System.out.println(mathProblem);
        if(part == 1){
            result = mathLeftToRight(mathProblem);
        } else {
            result = mathReversedPrecedence(mathProblem);
        }


        return result;
    }

    public static long mathReversedPrecedence(String mathProblem){
        //must have no parens at this point
        mathProblem = new String(mathProblem.replace(" ",""));

        if (mathProblem.contains("(") || mathProblem.contains(")")){
            throw new NumberFormatException("Must not have parenthesis in the string sent to solver");
        } else {
            // first do all addition in line
            while(mathProblem.contains("+")){
                ArrayList<String> elements  = new ArrayList<>(Arrays.asList(mathProblem.split("(?<=[-+*/])|(?=[-+*/])")));


                for(int i = 0; i < elements.size();i++){
                    //if we find addition, do the sum, then remove the element before and after, and replace current element with sum. Maybe. I dunno. this problem sucks
                    // bail afterwards
                    if(elements.get(i).equals("+")){
                        long sum = Long.parseLong(elements.get(i-1)) + Long.parseLong(elements.get(i+1));
                        elements.set(i, new String(String.valueOf(sum)));
                        elements.remove(i+1);
                        elements.remove(i-1);
                        break;
                    }
                }

                StringBuffer sb = new StringBuffer();
                for(String s: elements){
                    sb.append(s);
                }
                mathProblem = new String(sb.toString());


            }

            //now use our original function
            return mathLeftToRight(mathProblem);

        }

    }

    public static long mathLeftToRight (String mathProblem){
        //must have no parens at this point

        if (mathProblem.contains("(") || mathProblem.contains(")")){
            throw new NumberFormatException("Must not have parenthesis in the string sent to solver");
        } else {
            Stack<Long> numberStack = new Stack<>();
            Stack<String> operatorStack = new Stack<>();

            String[] elements = mathProblem.split("(?<=[-+*/])|(?=[-+*/])");

            for(int i = elements.length - 1; i >= 0; i--){

                if(isNumber(elements[i])){
                    numberStack.push((Long.parseLong(elements[i])));
                } else {
                    operatorStack.push(elements[i]);
                }
            }

            long result = numberStack.pop();

            while(numberStack.size() > 0){

                String operator = operatorStack.pop();
                if(operator.equals("+")){
                    result += numberStack.pop();
                } else if (operator.equals("*")){
                    result *= numberStack.pop();
                }

            }


            return result;
        }

    }

    public static boolean isNumber(String str) {
        try{
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException ex){
            return false;
        }
    }
}

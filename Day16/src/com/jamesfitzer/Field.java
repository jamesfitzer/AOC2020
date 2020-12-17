package com.jamesfitzer;

import java.util.ArrayList;
import javafx.util.Pair;
import sun.security.krb5.internal.Ticket;

public class Field {
    private String name;
    private ArrayList<Integer> possibleIndices;
    private int index;
    private ArrayList<Pair<Integer, Integer>> constraints;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Integer> getPossibleIndices() {
        return possibleIndices;
    }

    public void setPossibleIndices( int ticketFields) {

        for(int i = 0; i < ticketFields;i++){
            this.possibleIndices.add(i);
        }
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    private void addConstraints(ArrayList<String> constraints){
        for(String constraint: constraints){
            int lowerBound = Integer.parseInt(constraint.split("-")[0]);
            int upperBound = Integer.parseInt(constraint.split("-")[1]);

            this.constraints.add(new Pair(lowerBound, upperBound));
        }
    }

    public boolean isValueValid(int value){
        boolean result = false;
        //System.out.println("Checking " + value + " against " + constraints.toString() + " for field: " + this.name);

        for(Pair<Integer, Integer> constraint: this.constraints){
            if(constraint.getKey() <= value && value <= constraint.getValue()){
                //System.out.println(value + " is between " + constraint.getKey() + " and " + constraint.getValue());
                result = true;
            } else {
                //System.out.println(value + " is NOT between " + constraint.getKey() + " and " + constraint.getValue());
            }
        }
        //System.out.println("Returning " + result);
        return result;
    }

    public String toString(){
        return new String("Name: " + this.name + "\nIndex: " + this.index + "\nPossible Indices: " + this.possibleIndices.toString() + "\nConstraints: " + this.constraints.toString());
    }

    public Field(String name, int ticketFields, ArrayList<String> constraints) {
        this.index = -1;  // -1 means not determined yet.
        this.name = name;
        this.possibleIndices = new ArrayList<Integer>();
        this.constraints = new ArrayList<>();

        setPossibleIndices(ticketFields);
        addConstraints(constraints);
    }
}

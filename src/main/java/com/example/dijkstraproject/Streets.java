package com.example.dijkstraproject;

public class Streets {
    String street;
    double x;
    double y;

    public Streets(String street, double x, double y) {
        this.street = street;
        this.x = x;
        this.y = y;
    }

    public Double[]getCoo(String str){
        if (str.equalsIgnoreCase(this.street)){
            return new Double[]{this.x,this.y};
        }
        else return null;
    }




}

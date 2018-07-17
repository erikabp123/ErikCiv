package com.erikpoerksen.erikciv.GameLogic.Helpers;

public class HelperMethods {

    public static int calculateShortestDirectDistance(Position from, Position to){
        int distanceX = Math.abs(to.getX()-from.getX());
        int distanceY = Math.abs(to.getY()-from.getY());
        if(distanceY > distanceX){
            return distanceY;
        }
        return  distanceX;
    }

    public static int roundedIntegerDivision(int numerator, int denominator){
        return (numerator*10/denominator + 5)/10;
    }





}

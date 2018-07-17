package com.erikpoerksen.erikciv.GameLogic.Helpers;

import java.util.Arrays;

public class Position {

    private int x;
    private int y;

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    @Override
    public boolean equals(Object o){
        if (o == this) {
            return true;
        }
        if (!(o instanceof Position)) {
            return false;
        }
        Position position = (Position) o;
        return position.x == x && position.y == y;
    }

    @Override
    public int hashCode(){
        return Arrays.hashCode(new Object[] {
                x,
                y,
        });
    }

    @Override
    public String toString(){
        return "(" + getX() + "," + getY() +")";
    }


}

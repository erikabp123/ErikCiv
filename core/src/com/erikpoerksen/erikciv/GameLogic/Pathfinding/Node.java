package com.erikpoerksen.erikciv.GameLogic.Pathfinding;

import com.erikpoerksen.erikciv.GameLogic.Helpers.Position;
import com.erikpoerksen.erikciv.GameLogic.Helpers.TerrainTypes;
import com.erikpoerksen.erikciv.GameLogic.Implementations.WorldImpl;
import com.erikpoerksen.erikciv.GameLogic.Structure.World;

import java.util.ArrayList;

public class Node implements Comparable<Node>{

    private Node origin;
    private int cost;
    private Position location;

    public Node(Node origin, int cost, Position location){
        this.origin = origin;
        this.cost = cost;
        this.location = location;
    }

    @Override
    public String toString(){
        return "NODE(" + location.getX() + "," + location.getY() +")";
    }


    public Node getOrigin() {
        return origin;
    }

    public void setOrigin(Node origin) {
        this.origin = origin;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Position getLocation() {
        return location;
    }

    @Override
    public int compareTo(Node o) {
        return ((Integer) (this.cost)).compareTo(o.cost);
    }
}

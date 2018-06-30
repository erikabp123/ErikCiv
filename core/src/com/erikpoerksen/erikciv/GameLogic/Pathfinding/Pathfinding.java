package com.erikpoerksen.erikciv.GameLogic.Pathfinding;

import com.erikpoerksen.erikciv.GameLogic.Helpers.HelperMethods;
import com.erikpoerksen.erikciv.GameLogic.Helpers.Position;
import com.erikpoerksen.erikciv.GameLogic.Helpers.TerrainTypes;
import com.erikpoerksen.erikciv.GameLogic.Implementations.WorldImpl;
import com.erikpoerksen.erikciv.GameLogic.Structure.Game;

import java.util.ArrayList;
import java.util.Collections;

public class Pathfinding {

    private Game game;
    private ArrayList<Node> queue;
    private ArrayList<Node> processed;
    private Position from;
    private Position to;
    private ArrayList<Node> path;

    public Pathfinding(Position from, Position to, Game game){
        this.game = game;
        this.to = to;
        this.from = from;
        this.queue = new ArrayList<>();
        this.processed = new ArrayList<>();
        this.path = new ArrayList<>();
        int directDistance = HelperMethods.calculateShortestDirectDistance(from, to);
        Node startingNode = new Node(null, directDistance, from);
        queue.add(startingNode);
        processQueue();
        extractPath();
        Collections.reverse(path);
    }

    public ArrayList<Node> getPath(){
        return path;
    }

    private void extractPath(){
        for(Node node : processed){
            if(isGoalNode(node)){
                path.add(node);
                addOriginNodes(node.getOrigin());
            }
        }
    }

    private void addOriginNodes(Node node){
        if(node == null){
            return;
        }
        path.add(node);
        addOriginNodes(node.getOrigin());
    }


    private void processQueue(){
        Node topNode = queue.get(0);
        ArrayList<Node> neighbors = findValidNeighboringNodes(topNode);
        queue.remove(topNode);
        processed.add(topNode);
        removeAlreadyProcessed(neighbors);
        for(Node node : neighbors){
            if(!listContainsNode(queue, node)){
                queue.add(node);
            }
        }
        if(reachedGoal()){
            return;
        }
        if(queue.isEmpty()){
            return;
        }
        Collections.sort(queue);
        processQueue();
    }

    private boolean reachedGoal(){
        for(Node node : processed){
            if(isGoalNode(node)){
                return true;
            }
        }
        return false;
    }

    private boolean isGoalNode(Node node){
        return node.getLocation().equals(to);
    }

    private void removeAlreadyProcessed(ArrayList<Node> neighbors){
        neighbors.removeIf(n -> listContainsNode(processed, n));

//        for(Node node : neighbors){
//            if(listContainsNode(processed, node)){
//                neighbors.remove(node);
//            }
//        }
    }

    private boolean listContainsNode(ArrayList<Node> nodes, Node node){
        for(Node listNode : nodes){
            if(listNode.getLocation().equals(node.getLocation())){
                return true;
            }
        }
        return false;
    }


    private ArrayList<Node> findValidNeighboringNodes(Node origin){
        Position location = origin.getLocation();

        ArrayList<Position> locations = new ArrayList<Position>();
        Position nw = new Position(location.getX()-1, location.getY()-1);
        Position n = new Position(location.getX()-1, location.getY());
        Position ne = new Position(location.getX()-1, location.getY()+1);
        Position e = new Position(location.getX(), location.getY()+1);
        Position se = new Position(location.getX()+1, location.getY()+1);
        Position s = new Position(location.getX()+1, location.getY());
        Position sw = new Position(location.getX()+1, location.getY()-1);
        Position w = new Position(location.getX(), location.getY()-1);

        //all 8 directions
        locations.add(nw);
        locations.add(n);
        locations.add(ne);
        locations.add(e);
        locations.add(se);
        locations.add(s);
        locations.add(sw);
        locations.add(w);

        //remove invalid destinations
        removeLocationsOutsideWorld(locations);
        removeLocationsWithInvalidTerrain(locations);
        removeLocationsWithEnemies(locations);

        return convertAllLocationsToNodes(locations, origin);
    }

    private void removeLocationsOutsideWorld(ArrayList<Position> locations){
        locations.removeIf(n -> n.getX() < 0);
        locations.removeIf(n -> n.getY() < 0);
        locations.removeIf(n -> n.getX() >= WorldImpl.rows);
        locations.removeIf(n -> n.getY() >= WorldImpl.columns);




//        for(Position location : locations){
//            if(location.getX() < 0 || location.getX() >= WorldImpl.rows){
//                locations.remove(location);
//            } else if(location.getY() < 0 || location.getY() >= WorldImpl.columns){
//                locations.remove(location);
//            }
//        }
    }

    private void removeLocationsWithInvalidTerrain(ArrayList<Position> locations){
        locations.removeIf(p -> game.getTerrainAtPosition(p) == TerrainTypes.OCEAN);
        locations.removeIf(p -> game.getTerrainAtPosition(p) == TerrainTypes.MOUNTAIN);

//        for(Position location : locations){
//            if(game.getTerrainAtPosition(location) == TerrainTypes.OCEAN){
//                locations.remove(location);
//            }
//            else if(game.getTerrainAtPosition(location) == TerrainTypes.MOUNTAIN){
//                locations.remove(location);
//            }
//        }
    }

    private void removeLocationsWithEnemies(ArrayList<Position> locations){
        locations.removeIf(p -> game.getUnitAtPosition(p) != null &&
                game.getUnitAtPosition(p).getOwner() != game.getPlayerInTurn());

//        for(Position location : locations){
//            Unit unitAtLocation = game.getUnitAtPosition(location);
//            if(unitAtLocation != null && unitAtLocation.getOwner() != game.getPlayerInTurn()){
//                locations.remove(location);
//            }
//        }
    }

    private Node convertLocationToNode(Position location, Node origin){
        int cost = HelperMethods.calculateShortestDirectDistance(location, to) + 1;
        return new Node(origin, cost, location);
    }

    private ArrayList<Node> convertAllLocationsToNodes(ArrayList<Position> locations, Node origin){
        ArrayList<Node> nodes = new ArrayList<Node>();
        for(Position location : locations){
            nodes.add(convertLocationToNode(location, origin));
        }
        return nodes;
    }







}

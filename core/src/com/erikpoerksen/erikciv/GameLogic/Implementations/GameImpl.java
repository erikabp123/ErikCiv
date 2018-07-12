package com.erikpoerksen.erikciv.GameLogic.Implementations;

import com.erikpoerksen.erikciv.GameLogic.Helpers.HelperMethods;
import com.erikpoerksen.erikciv.GameLogic.Helpers.PlayerTypes;
import com.erikpoerksen.erikciv.GameLogic.Helpers.Position;
import com.erikpoerksen.erikciv.GameLogic.Helpers.TerrainTypes;
import com.erikpoerksen.erikciv.GameLogic.Pathfinding.Node;
import com.erikpoerksen.erikciv.GameLogic.Pathfinding.Pathfinding;
import com.erikpoerksen.erikciv.GameLogic.Structure.*;

import java.util.ArrayList;
import java.util.Collections;

public class GameImpl implements Game {

    World world;
    ArrayList<Player> players;

    public GameImpl(String[][] worldString){
        players = new ArrayList<Player>();
        players.add(new PlayerImpl(PlayerTypes.BLUE));
        players.add(new PlayerImpl(PlayerTypes.RED));
        world = new WorldImpl(worldString, players);
    }


    @Override
    public boolean moveUnit(Position from, Position to) {
        Unit unitBeingMoved = getUnitAtPosition(from);
        if(unitBeingMoved == null || unitBeingMoved.getOwner() != getPlayerInTurn()){
            return false;
        }
        if(getTerrainAtPosition(to) == TerrainTypes.OCEAN || getTerrainAtPosition(to) == TerrainTypes.MOUNTAIN){
            return false;
        }
        Unit atPosition = getUnitAtPosition(to);
        if(atPosition != null && atPosition.getOwner() == getPlayerInTurn()){
            return false;
        }
        ArrayList<Node> path = new Pathfinding(from, to, this).getPath();
        if(path == null || path.isEmpty()){
            return false;
        }
        if(path.size() > unitBeingMoved.getRemainingMoveCount()){
            return false;
        }
        for(Node node : path){
            Position pathFrom = node.getOrigin().getLocation();
            Position pathTo = node.getLocation();
            System.out.println("Moving from: " + pathFrom + " to " + pathTo);
            if(getUnitAtPosition(pathTo) != null){
                System.out.println("Unit at " + pathTo + "... fighting!" );
                if(attackEnemy(pathFrom, pathTo)){
                    world.moveUnit(pathFrom, pathTo);
                }
            } else {
                world.moveUnit(pathFrom, pathTo);
            }
        }
        return true;
    }

    public boolean attackEnemy(Position attacker, Position defender){
        Unit attackingUnit = getUnitAtPosition(attacker);
        Unit defendingUnit = getUnitAtPosition(defender);

        defendingUnit.sustainDamage(attackingUnit.getAttack());
        attackingUnit.move(attackingUnit.getRemainingMoveCount()); //set move count to 0

        if(defendingUnit.getCurrentHealth() > 0){
            return false;
        }

        world.removeUnit(defender);
        return true;
    }


    @Override
    public Player getPlayerInTurn() {
        return players.get(0);
    }

    @Override
    public City getCityAtPosition(Position position) {
        return world.getCityAtPosition(position);
    }

    @Override
    public Unit getUnitAtPosition(Position position) {
        return world.getUnitAtPosition(position);
    }

    @Override
    public TerrainTypes getTerrainAtPosition(Position position) {
        return world.getTerrainAtPosition(position);
    }

    @Override
    public void endTurn() {
        Collections.rotate(players, -1);
        ArrayList<Unit> units = world.getAllUnits();
        for(Unit unit : units){
            if(unit.getOwner() == getPlayerInTurn()){
                unit.resetMoveCount();
            }
        }
    }



}

package com.erikpoerksen.erikciv.GameLogic.Implementations;

import com.erikpoerksen.erikciv.GameLogic.Helpers.HelperMethods;
import com.erikpoerksen.erikciv.GameLogic.Helpers.PlayerTypes;
import com.erikpoerksen.erikciv.GameLogic.Helpers.Position;
import com.erikpoerksen.erikciv.GameLogic.Helpers.TerrainTypes;
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
        Unit unit = world.getUnitAtPosition(from);
        if(unit == null || unit.getOwner() != getPlayerInTurn() || unit.getRemainingMoveCount() < 1){
            return false;
        }
        if(getTerrainAtPosition(to) == TerrainTypes.OCEAN){
            return false;
        }
        int directDistance = HelperMethods.calculateShortestDirectDistance(from, to);
        if(directDistance == 1){
            world.moveUnit(from, to);
            return true;
        }
        return false;
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
    }
}

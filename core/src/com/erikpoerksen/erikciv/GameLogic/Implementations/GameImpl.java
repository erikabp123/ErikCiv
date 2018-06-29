package com.erikpoerksen.erikciv.GameLogic.Implementations;

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

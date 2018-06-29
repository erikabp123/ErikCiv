package com.erikpoerksen.erikciv.GameLogic.Structure;

import com.erikpoerksen.erikciv.GameLogic.Helpers.Position;
import com.erikpoerksen.erikciv.GameLogic.Helpers.TerrainTypes;

public interface Game {

    boolean moveUnit(Position from, Position to);

    Player getPlayerInTurn();

    City getCityAtPosition(Position position);

    Unit getUnitAtPosition(Position position);

    TerrainTypes getTerrainAtPosition(Position position);

    void endTurn();

}

package com.erikpoerksen.blackjack.GameLogic.Structure;

import com.erikpoerksen.blackjack.GameLogic.Helpers.Position;
import com.erikpoerksen.blackjack.GameLogic.Helpers.TerrainTypes;

public interface World {

    Unit getUnitAtPosition(Position position);

    TerrainTypes getTerrainAtPosition(Position position);

    City getCityAtPosition(Position position);

    void moveUnit(Position from, Position to);


}

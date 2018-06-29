package com.erikpoerksen.erikciv.GameLogic.Structure;

import com.erikpoerksen.erikciv.GameLogic.Helpers.Position;
import com.erikpoerksen.erikciv.GameLogic.Helpers.TerrainTypes;

public interface World {

    Unit getUnitAtPosition(Position position);

    TerrainTypes getTerrainAtPosition(Position position);

    City getCityAtPosition(Position position);

    void moveUnit(Position from, Position to);


}

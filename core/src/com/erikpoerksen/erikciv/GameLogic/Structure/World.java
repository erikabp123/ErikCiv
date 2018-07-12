package com.erikpoerksen.erikciv.GameLogic.Structure;

import com.erikpoerksen.erikciv.GameLogic.Helpers.Position;
import com.erikpoerksen.erikciv.GameLogic.Helpers.TerrainTypes;

import java.util.ArrayList;

public interface World {

    Unit getUnitAtPosition(Position position);

    TerrainTypes getTerrainAtPosition(Position position);

    City getCityAtPosition(Position position);

    void moveUnit(Position from, Position to);

    ArrayList<Unit> getAllUnits();

    void removeUnit(Position position);


}

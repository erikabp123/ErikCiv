package com.erikpoerksen.erikciv.GameLogic.Structure;

import com.erikpoerksen.erikciv.GameLogic.Helpers.Position;
import com.erikpoerksen.erikciv.GameLogic.Helpers.TerrainTypes;
import com.erikpoerksen.erikciv.GameLogic.Helpers.UnitTypes;

import java.util.ArrayList;
import java.util.HashMap;

public interface World {

    Unit[] getUnitsAtPosition(Position position);

    TerrainTypes getTerrainAtPosition(Position position);

    City getCityAtPosition(Position position);

    void moveUnit(Position from, Position to);

    ArrayList<Unit> getAllUnits();

    void removeUnit(Position position);

    void placeCityAtPosition(Position position, Player owner);

    ArrayList<City> getAllCities();

    HashMap<Position, City> getAllCitiesWithPositions();

    void placeUnitAtPosition(Position position, Player owner, UnitTypes type);

    void setUnitAsTransitional(Position position);

    void setUnitAsOccupying(Position position);

}

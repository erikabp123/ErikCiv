package com.erikpoerksen.erikciv.GameLogic.Implementations;

import com.erikpoerksen.erikciv.GameLogic.Helpers.*;
import com.erikpoerksen.erikciv.GameLogic.Structure.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class WorldImpl implements World {

    private final int rows = GameConstants.X_LENGTH;
    private final int columns = GameConstants.Y_LENGTH;
    private ArrayList<Player> players;

    Tile[][] world;

    public WorldImpl(String[][] worldString, ArrayList<Player> players){
        this.players = players;
        world = parseWorldString(worldString);
    }

    private Tile[][] parseWorldString(String[][] worldString){
        Tile[][] parsed = new Tile[rows][columns];
        for(int row = 0; row<rows; row++){

            String terrainLayer = worldString[0][row];
            String cityLayer = worldString[1][row];
            String unitLayer = worldString[2][row];

            String[] terrain = splitString(terrainLayer);
            String[] cities = splitString(cityLayer);
            String[] units = splitString(unitLayer);

            for(int column = 0; column<columns; column++){
                Tile tile = new Tile();

                tile.setTerrain(processTerrainLetters(terrain[column]));
                tile.setCity(processCityLetters(cities[column]));
                tile.setOccupyingUnit(processUnitLetters(units[column]));

                parsed[row][column] = tile;
            }
        }
        return parsed;
    }

    private String[] splitString(String string){
        return string.split(Pattern.quote("."));
    }

    private TerrainTypes processTerrainLetters(String letter){
        if(letter.equals("M")){
            return TerrainTypes.MOUNTAIN;
        }
        if(letter.equals("P")){
            return TerrainTypes.PLAINS;
        }
        if(letter.equals("F")){
            return TerrainTypes.FOREST;
        }
        if(letter.equals("H")){
            return TerrainTypes.HILLS;
        }
        if(letter.equals("O")){
            return TerrainTypes.OCEAN;
        }
        return null; // shouldn't happen, string should be validated much earlier
    }

    private Player getPlayerByColor(PlayerTypes type){
        for(Player player : players){
            if(player.getColor() == type){
                return player;
            }
        }
        return null;
    }

    private City processCityLetters(String letters){
        if(letters.equals("-")){
            return null;
        }

        String color = letters.substring(0,1);
        Player owner = null;

        if(color.equals("B")){
            owner = getPlayerByColor(PlayerTypes.BLUE);
        } else if(color.equals("R")){
            owner = getPlayerByColor(PlayerTypes.RED);
        }

        String type = letters.substring(1, 2);

        if(type.equals("C")){
            return new CityImpl(owner);
        }

        return null; // shouldn't happen, string should be validated much earlier
    }

    private Unit processUnitLetters(String letters){
        if(letters.equals("-")){
            return null;
        }

        String color = letters.substring(0,1);
        Player owner = null;

        if(color.equals("B")){
            owner = getPlayerByColor(PlayerTypes.BLUE);
        } else if(color.equals("R")){
            owner = getPlayerByColor(PlayerTypes.RED);
        }

        String type = letters.substring(1, 2);

        if(type.equals("A")){
            return new UnitImpl(owner, UnitTypes.ARCHER);
        }
        if(type.equals("L")){
            return new UnitImpl(owner, UnitTypes.LEGION);
        }
        if(type.equals("W")){
            return new UnitImpl(owner, UnitTypes.WORKER);
        }

        return null; // shouldn't happen, string should be validated much earlier
    }

    @Override
    public Unit[] getUnitsAtPosition(Position position) {
        int x = position.getX();
        int y = position.getY();
        Unit[] units = new Unit[2];
        units[0] = world[x][y].getOccupyingUnit();
        units[1] = world[x][y].getTransitionalUnit();
        return units;
    }

    @Override
    public TerrainTypes getTerrainAtPosition(Position position) {
        int x = position.getX();
        int y = position.getY();
        return world[x][y].getTerrain();
    }

    @Override
    public City getCityAtPosition(Position position) {
        int x = position.getX();
        int y = position.getY();
        return world[x][y].getCity();
    }

    @Override
    public void moveUnit(Position from, Position to) {
        Unit toBeMoved = getUnitsAtPosition(from)[1];
        int distance = HelperMethods.calculateShortestDirectDistance(from, to);
        toBeMoved.move(distance);
        world[to.getX()][to.getY()].setTransitionalUnit(toBeMoved);
        world[from.getX()][from.getY()].setTransitionalUnit(null);
    }

    @Override
    public ArrayList<Unit> getAllUnits(){
        ArrayList<Unit> units = new ArrayList<>();
        for(int row = 0; row<rows; row++){
            for(int column = 0; column<columns; column++){
                Position curPos = new Position(row, column);
                Unit unit = getUnitsAtPosition(curPos)[0];
                if(unit != null){
                    units.add(unit);
                }
            }
        }
        return units;
    }

    @Override
    public void removeUnit(Position position) {
        world[position.getX()][position.getY()].setOccupyingUnit(null);
    }

    @Override
    public void placeCityAtPosition(Position position, Player owner) {
        world[position.getX()][position.getY()].setCity(new CityImpl(owner));
    }

    @Override
    public void placeUnitAtPosition(Position position, Player owner, UnitTypes type) {
        world[position.getX()][position.getY()].setOccupyingUnit(new UnitImpl(owner, type));
    }

    @Override
    public ArrayList<City> getAllCities() {
        ArrayList<City> cities = new ArrayList<>();
        for(int row = 0; row<rows; row++){
            for(int column = 0; column<columns; column++){
                Position curPos = new Position(row, column);
                City city = getCityAtPosition(curPos);
                if(city != null){
                    cities.add(city);
                }
            }
        }
        return cities;
    }

    @Override
    public HashMap<Position, City> getAllCitiesWithPositions(){
        HashMap<Position, City> cities = new HashMap<>();
        for(int row = 0; row<rows; row++){
            for(int column = 0; column<columns; column++){
                Position curPos = new Position(row, column);
                City city = getCityAtPosition(curPos);
                if(city != null){
                    cities.put(curPos, city);
                }
            }
        }
        return cities;
    }

    public void setUnitAsTransitional(Position position){
        Tile tile = world[position.getX()][position.getY()];
        tile.setTransitionalUnit(tile.getOccupyingUnit());
        tile.setOccupyingUnit(null);
    }

    public void setUnitAsOccupying(Position position){
        Tile tile = world[position.getX()][position.getY()];
        tile.setOccupyingUnit(tile.getTransitionalUnit());
        tile.setTransitionalUnit(null);
    }

    private class Tile {

        private TerrainTypes terrain;
        private Unit occupyingUnit;
        private City city;
        private Unit transitionalUnit;

        public void setTerrain(TerrainTypes contents){
            this.terrain = contents;
        }

        public void setOccupyingUnit(Unit contents){
            this.occupyingUnit = contents;
        }

        public void setTransitionalUnit(Unit contents){
            this.transitionalUnit = contents;
        }

        public void setCity(City contents){
            this.city = contents;
        }

        public TerrainTypes getTerrain(){
            return terrain;
        }

        public City getCity(){
            return city;
        }

        public Unit getOccupyingUnit(){
            return occupyingUnit;
        }

        public Unit getTransitionalUnit(){
            return transitionalUnit;
        }



    }

}

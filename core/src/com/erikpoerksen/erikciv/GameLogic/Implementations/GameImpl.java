package com.erikpoerksen.erikciv.GameLogic.Implementations;

import com.erikpoerksen.erikciv.GameLogic.Helpers.*;
import com.erikpoerksen.erikciv.GameLogic.Pathfinding.Node;
import com.erikpoerksen.erikciv.GameLogic.Pathfinding.Pathfinding;
import com.erikpoerksen.erikciv.GameLogic.Structure.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class GameImpl implements Game {

    World world;
    ArrayList<Player> players;
    GameMessages messages;
    int age;
    int turnCount;

    public GameImpl(String[][] worldString){
        this.players = new ArrayList<Player>();
        this.players.add(new PlayerImpl(PlayerTypes.BLUE));
        this.players.add(new PlayerImpl(PlayerTypes.RED));
        this.world = new WorldImpl(worldString, players);
        this.messages = new GameMessages();
        this.age = GameConstants.STARTING_AGE;
        this.turnCount = 0;
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
        Unit unitAtPosition = getUnitAtPosition(to);
        if(unitAtPosition != null){
            return false;
        }
        ArrayList<Node> path = new Pathfinding(from, to, this).getPath();
        if(path == null || path.isEmpty()){
            return false;
        }
        if(path.size() > unitBeingMoved.getRemainingMoveCount()){
            return false;
        }
        world.setUnitAsTransitional(from);
        for(Node node : path){
            Position pathFrom = node.getOrigin().getLocation();
            Position pathTo = node.getLocation();
            System.out.println("Moving from: " + pathFrom + " to " + pathTo);
            world.moveUnit(pathFrom, pathTo);
        }
        world.setUnitAsOccupying(to);
        return true;
    }

    @Override
    public boolean attackEnemy(Position attacker, Position defender){
        Unit attackingUnit = getUnitAtPosition(attacker);
        Unit defendingUnit = getUnitAtPosition(defender);

        if(defendingUnit == null || attackingUnit == null){
            return false;
        }
        if(attackingUnit.getOwner() != getPlayerInTurn()){
            return false;
        }
        if(defendingUnit.getOwner() == attackingUnit.getOwner()){
            return false;
        }
        if(HelperMethods.calculateShortestDirectDistance(attacker, defender) > 1 || attackingUnit.getRemainingMoveCount() < 1){
            return false;
        }

        defendingUnit.sustainDamage(attackingUnit.getAttack());
        attackingUnit.sustainDamage(defendingUnit.getAttack());

        boolean attackerDied = (attackingUnit.getCurrentHealth() <= 0);
        boolean defenderDied = (defendingUnit.getCurrentHealth() <= 0);

        if(attackerDied){
            world.removeUnit(attacker); // attacker died
        }
        if(defenderDied){
            world.removeUnit(defender);
        }
        if(!attackerDied && defenderDied){
            world.setUnitAsTransitional(attacker);
            world.moveUnit(attacker, defender);
        }
        attackingUnit.move(attackingUnit.getRemainingMoveCount()); //set move count to 0 for attacker
        return true;
    }

    @Override
    public boolean performSpecialActionAt(Position unit, Position target){
        Unit performer = getUnitAtPosition(unit);
        if(performer == null){
            return false;
        }
        if(performer.getOwner() !=  getPlayerInTurn()){
            return false;
        }
        if(performer.getRemainingMoveCount() == 0){
            return false;
        }
        if(HelperMethods.calculateShortestDirectDistance(unit, target) > 1){
            return false;
        }
        if(performer.getType() == UnitTypes.WORKER && getCityAtPosition(unit) != null){
            return false;
        }
        Unit targetUnit = getUnitAtPosition(target);
        if(performer.getType() == UnitTypes.ARCHER && (targetUnit == null || targetUnit.getOwner() == performer.getOwner())){
            return false;
        }
        performer.performSpecialAction(target, this);
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
        return world.getUnitsAtPosition(position)[0];
    }

    @Override
    public TerrainTypes getTerrainAtPosition(Position position) {
        return world.getTerrainAtPosition(position);
    }

    @Override
    public void endTurn() {
        turnCount++;
        if(turnCount == players.size() - 1){
            age += GameConstants.SPEED;
            turnCount = 0;
        }
        Collections.rotate(players, -1);
        if(age == GameConstants.STARTING_AGE){
            return; // if it's the first round, no player should be getting an unfair advantage
        }
        startOfTurnPreparations();
    }

    private void startOfTurnPreparations(){
        ArrayList<Unit> units = world.getAllUnits();
        for(Unit unit : units){
            if(unit.getOwner() == getPlayerInTurn()){
                unit.resetMoveCount();
            }
        }

        HashMap<Position, City> cities = world.getAllCitiesWithPositions();
        for(Position position : cities.keySet()){
            City city = cities.get(position);
            if(city.getOwner() == getPlayerInTurn()){
                city.increaseProductionCount(1);
                UnitTypes cityProduction = city.getProduction();
                if(world.getUnitsAtPosition(position)[0] != null){
                    continue; // If there is already a unit on the city tile, you can't spawn a new one
                }
                boolean productionCompleted = city.finishProduction();
                if(productionCompleted){
                    world.placeUnitAtPosition(position, getPlayerInTurn(), cityProduction);
                }
            }
        }
    }

    @Override
    public Position findCityPosition(City toBeFound){
        HashMap<Position, City> cities = world.getAllCitiesWithPositions();
        for(Position position : cities.keySet()){
            // we want to see if they are the EXACT same city object, not if they are equal
            if(cities.get(position) == toBeFound){
                return position;
            }
        }
        return null; // city doesn't exist in our world
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public boolean placeCityAtPosition(Position position, Player owner) {
        world.placeCityAtPosition(position, owner);
        return true;
    }


}

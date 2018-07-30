package com.erikpoerksen.erikciv.GameLogic.Implementations;

import com.erikpoerksen.erikciv.GameLogic.Helpers.HelperMethods;
import com.erikpoerksen.erikciv.GameLogic.Helpers.Position;
import com.erikpoerksen.erikciv.GameLogic.Helpers.UnitTypes;
import com.erikpoerksen.erikciv.GameLogic.Structure.Game;
import com.erikpoerksen.erikciv.GameLogic.Structure.Player;
import com.erikpoerksen.erikciv.GameLogic.Structure.Unit;
import com.erikpoerksen.erikciv.GameLogic.Structure.World;

public class UnitImpl implements Unit {

    private UnitTypes type;
    private Player owner;
    private int remainingMoveCount;
    private int defaultMoveCount;
    private int attack;
    private int defense;
    private int maxHealth;
    private int currentHealth;

    public UnitImpl(Player owner, UnitTypes type){
        this.owner = owner;
        this.type = type;
        if(type == UnitTypes.WORKER){
            this.remainingMoveCount = this.defaultMoveCount = 2;
            this.attack = 1;
            this.defense = 0;
            this.currentHealth = this.maxHealth = 5;
        } else if(type == UnitTypes.ARCHER) {
            this.remainingMoveCount = this.defaultMoveCount = 4;
            this.attack = 4;
            this.defense = 2;
            this.currentHealth = this.maxHealth = 8;
        } else if(type == UnitTypes.LEGION){
            this.remainingMoveCount = this.defaultMoveCount = 4;
            this.attack = 3;
            this.defense = 5;
            this.currentHealth = this.maxHealth = 10;
        }
    }




    @Override
    public void performSpecialAction(Position targetPosition, Game game) {
        if(getType() == UnitTypes.WORKER){
            game.placeCityAtPosition(targetPosition, game.getPlayerInTurn());
            game.getWorld().removeUnit(targetPosition);
            return;
        }
        if(getType() == UnitTypes.LEGION){
            // add legion fortification later
        } if(getType() == UnitTypes.ARCHER){
            Unit enemy = game.getUnitAtPosition(targetPosition);
            enemy.sustainDamage(getAttack());
            if(enemy.getCurrentHealth() <= 0){
                game.getWorld().removeUnit(targetPosition);
            }
            remainingMoveCount = 0;
        }
    }

    @Override
    public UnitTypes getType() {
        return type;
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public int getRemainingMoveCount(){
        return remainingMoveCount;
    }

    @Override
    public void move(int distance) {
        this.remainingMoveCount -= distance;
    }

    @Override
    public void resetMoveCount() {
        remainingMoveCount = defaultMoveCount;
    }

    @Override
    public int getDefaultMoveCount() {
        return defaultMoveCount;
    }

    @Override
    public int getMaxHealth() {
        return maxHealth;
    }

    @Override
    public int getCurrentHealth() {
        return currentHealth;
    }

    @Override
    public int getDefense() {
        return defense;
    }

    @Override
    public int getAttack() {
        return attack;
    }

    @Override
    public void sustainDamage(int rawAmount) {
        int damage = (HelperMethods.roundedIntegerDivision(rawAmount, defense) + 2);
        currentHealth -= damage;
    }


}

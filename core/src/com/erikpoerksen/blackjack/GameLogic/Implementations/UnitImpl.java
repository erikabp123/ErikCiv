package com.erikpoerksen.blackjack.GameLogic.Implementations;

import com.erikpoerksen.blackjack.GameLogic.Helpers.UnitTypes;
import com.erikpoerksen.blackjack.GameLogic.Structure.Player;
import com.erikpoerksen.blackjack.GameLogic.Structure.Unit;

public class UnitImpl implements Unit {

    UnitTypes type;
    Player owner;
    int remainingMoveCount;

    public UnitImpl(Player owner, UnitTypes type){
        this.owner = owner;
        this.type = type;
        remainingMoveCount = 2;
    }


    @Override
    public void performSpecialAction() {
        // does nothing as of alpha
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


}

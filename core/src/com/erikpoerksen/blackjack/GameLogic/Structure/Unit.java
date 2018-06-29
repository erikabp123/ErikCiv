package com.erikpoerksen.blackjack.GameLogic.Structure;

import com.erikpoerksen.blackjack.GameLogic.Helpers.UnitTypes;

public interface Unit {

    void performSpecialAction();

    UnitTypes getType();

    Player getOwner();

    int getRemainingMoveCount();

    void move(int distance);

}

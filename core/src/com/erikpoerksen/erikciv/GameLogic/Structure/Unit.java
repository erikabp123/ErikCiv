package com.erikpoerksen.erikciv.GameLogic.Structure;

import com.erikpoerksen.erikciv.GameLogic.Helpers.Position;
import com.erikpoerksen.erikciv.GameLogic.Helpers.UnitTypes;

public interface Unit {

    void performSpecialAction(Position targetPosition, Game game);

    UnitTypes getType();

    Player getOwner();

    int getRemainingMoveCount();

    void move(int distance);

    void resetMoveCount();

    int getDefaultMoveCount();

    int getMaxHealth();

    int getCurrentHealth();

    int getDefense();

    int getAttack();

    void sustainDamage(int rawAmount);

}

package com.erikpoerksen.erikciv.GameLogic.Structure;

import com.erikpoerksen.erikciv.GameLogic.Helpers.UnitTypes;

public interface City {

    Player getOwner();

    UnitTypes getProduction();

    void setProduction(UnitTypes production);

    void annexCity(Player newOwner);

    void increaseProductionCount(int amount);

    boolean finishProduction();
}

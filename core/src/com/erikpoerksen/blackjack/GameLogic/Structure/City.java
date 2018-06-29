package com.erikpoerksen.blackjack.GameLogic.Structure;

import com.erikpoerksen.blackjack.GameLogic.Helpers.UnitTypes;

public interface City {

    Player getOwner();

    UnitTypes getProduction();

    void setProduction(UnitTypes production);

    void annexCity(Player newOwner);
}

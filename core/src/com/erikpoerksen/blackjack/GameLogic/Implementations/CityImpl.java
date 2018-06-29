package com.erikpoerksen.blackjack.GameLogic.Implementations;

import com.erikpoerksen.blackjack.GameLogic.Helpers.UnitTypes;
import com.erikpoerksen.blackjack.GameLogic.Structure.City;
import com.erikpoerksen.blackjack.GameLogic.Structure.Player;

public class CityImpl implements City {

    private Player owner;
    private UnitTypes production;

    public CityImpl(Player owner){
        this.owner = owner;
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public UnitTypes getProduction() {
        return production;
    }

    @Override
    public void setProduction(UnitTypes production) {
        this.production = production;
    }

    @Override
    public void annexCity(Player newOwner) {
        this.owner = newOwner;
    }
}

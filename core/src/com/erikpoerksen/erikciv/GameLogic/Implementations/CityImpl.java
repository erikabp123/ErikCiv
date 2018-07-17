package com.erikpoerksen.erikciv.GameLogic.Implementations;

import com.erikpoerksen.erikciv.GameLogic.Helpers.UnitTypes;
import com.erikpoerksen.erikciv.GameLogic.Structure.City;
import com.erikpoerksen.erikciv.GameLogic.Structure.Player;

public class CityImpl implements City {

    private Player owner;
    private UnitTypes production;
    private int productionCount;

    public CityImpl(Player owner){
        this.owner = owner;
        production = null;
        productionCount = 0;
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

    @Override
    public void increaseProductionCount(int amount) {
        productionCount += amount;
    }

    @Override
    public boolean finishProduction() {
        int cost = getProduction().getValue();
        if(cost > productionCount){
            return false;
        }
        productionCount -= cost;
        setProduction(null); // stop producing stuff
        return true;
    }
}

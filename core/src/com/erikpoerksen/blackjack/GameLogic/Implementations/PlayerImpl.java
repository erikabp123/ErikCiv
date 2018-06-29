package com.erikpoerksen.blackjack.GameLogic.Implementations;

import com.erikpoerksen.blackjack.GameLogic.Helpers.PlayerTypes;
import com.erikpoerksen.blackjack.GameLogic.Structure.Player;

public class PlayerImpl implements Player {

    private int woodCount;
    private int foodCount;
    private int oreCount;
    private PlayerTypes color;

    public PlayerImpl(PlayerTypes color){
        this.color = color;
        this.woodCount = 50;
        this.foodCount = 100;
        this.oreCount = 20;
    }

    @Override
    public PlayerTypes getColor() {
        return color;
    }

    @Override
    public int getWoodCount() {
        return woodCount;
    }

    @Override
    public int getFoodCount() {
        return foodCount;
    }

    @Override
    public int getOreCount() {
        return oreCount;
    }
}

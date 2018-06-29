package com.erikpoerksen.erikciv.GameLogic.Structure;

import com.erikpoerksen.erikciv.GameLogic.Helpers.PlayerTypes;

public interface Player {

    PlayerTypes getColor();

    int getWoodCount();

    int getFoodCount();

    int getOreCount();

}

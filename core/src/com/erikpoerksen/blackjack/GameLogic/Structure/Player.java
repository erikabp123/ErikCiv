package com.erikpoerksen.blackjack.GameLogic.Structure;

import com.erikpoerksen.blackjack.GameLogic.Helpers.PlayerTypes;

public interface Player {

    PlayerTypes getColor();

    int getWoodCount();

    int getFoodCount();

    int getOreCount();

}

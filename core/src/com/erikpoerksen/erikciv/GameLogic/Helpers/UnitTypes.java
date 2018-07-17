package com.erikpoerksen.erikciv.GameLogic.Helpers;

public enum UnitTypes {
    ARCHER(GameConstants.archerCost), LEGION(GameConstants.legionCost), WORKER(GameConstants.workerCost);

    private final int value;
    UnitTypes(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

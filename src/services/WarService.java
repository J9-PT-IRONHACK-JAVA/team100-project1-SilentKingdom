package services;

import model.Army;

public class WarService {
    private Army light;
    private Army dark;

    public WarService(Army light, Army dark) {
        this.light = light;
        this.dark = dark;
    }

    public void startRandom() {
        // Start war (LOOP) picking random combatants while any of the armies is defeated, continue
    }

    public void startPlayerVsPlayer() {
        // Start war asking the players to choose combatants
    }

    public void startPlayerVsBot() {
        // Start war asking the players to choose combatants
    }

    public void stop() {
        // Stop war -- maybe one of the armies surrenders?
    }
}

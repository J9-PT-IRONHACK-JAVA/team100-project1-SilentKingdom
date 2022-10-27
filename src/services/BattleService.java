package services;

import model.Combatant;

public class BattleService {
    private Combatant lightSoldier;
    private Combatant darkSoldier;


    public BattleService(Combatant lightSoldier, Combatant darkSoldier) {
        this.lightSoldier = lightSoldier;
        this.darkSoldier = darkSoldier;
    }

    public Combatant fight() {
        // Both combatants attack while both are alive
        // Returns winner?
        return null;
    }

}

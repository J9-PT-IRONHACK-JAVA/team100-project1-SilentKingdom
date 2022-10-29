package model;

import java.util.ArrayList;

public interface ArmyMethods {
    public Combatant pickRandomCombatant();
    public Combatant pickCombatantByName();

    public void addCombatant(Combatant combatants);
    public void removeCombatant(Combatant combatant);

    public String getArmyStatus();
    public String getArmyVisual();
}

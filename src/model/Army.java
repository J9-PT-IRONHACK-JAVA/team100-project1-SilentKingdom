package model;

import java.util.ArrayList;

public class Army implements ArmyMethods{
    private String name;
    private final ArrayList<Combatant> combatants;
    private final int initialSize;

    public static final int MIN_ARMY_SIZE = 1;
    public static final int MAX_ARMY_SIZE = 10;


    @Override
    public Combatant pickRandomCombatant() {
        return combatants.get(0);
    }

    @Override
    public Combatant pickCombatantByName() {
        return null;
    }

    @Override
    public void addCombatant(Combatant combatant, int num_of_clones){
        combatants.add(combatant);
    }

    @Override
    public void removeCombatant(Combatant combatant) {

    }

    @Override
    public String getArmyStatus() {
        return null;
    }

    @Override
    public String getArmyVisual() {
        return null;
    }

    public Army(String name, int initialSize) {
        this.name = name;
        this.initialSize = initialSize;
        this.combatants = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public ArrayList<Combatant> getCombatants() {
        return combatants;
    }

    public int getSize() {
        return combatants.size();
    }

    public int getInitialSize() {
        return initialSize;
    }



    @Override
    public String toString() {
        return "Army{" +
                "name='" + name + '\'' +
                ", combatants:\n"
                +
                '}';
    }
}

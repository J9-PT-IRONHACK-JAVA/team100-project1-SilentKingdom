package model;

import repository.RepositoryCsv;

import java.util.ArrayList;

public class Army {
    private String name;
    private final ArrayList<Combatant> combatants;
    private final int initialSize;
    public static final int MIN_ARMY_SIZE = 1;
    public static final int MAX_ARMY_SIZE = 10;
    private final RepositoryCsv repo;

    // TODO army.remove() method to remove army calling repo.deleteArmy()
    //  In case user wants to change armies during game



    public Combatant pickRandomCombatant() {
        return combatants.get(0);
    }


    public Combatant pickCombatantByName() {
        return null;
    }


    public void addCombatant(Combatant combatant) throws Exception {
        combatant.setArmyName(name);
        combatants.add(combatant);
        repo.saveCombatant(combatant);
    }

    public void removeCombatant(Combatant combatant) {

    }

    public String getArmyStatus() {
        return null;
    }

    public String getArmyVisual() {
        return null;
    }

    public Army(String name, RepositoryCsv repo) {
        this.name = name;
        this.initialSize = 0;
        this.combatants = new ArrayList<>();
        this.repo = repo;
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

package model;

import java.util.ArrayList;

public class Army implements ArmyMethods{
    private String name;
    private String side;
    private final ArrayList<Combatant> combatants;
    private final int initialSize;


    @Override
    public Combatant pickRandomCombatant() {
        return combatants.get(0);
    }

    @Override
    public Combatant pickCombatantByName() {
        return null;
    }

    @Override
    public void addCombatant(Combatant combatant){
        combatant.setArmyName(name);
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

    public Army(String name, String side, int initialSize) {
        this.name = name;
        this.side = side;
        this.initialSize = initialSize;
        this.combatants = new ArrayList<>();
    }

    public Army(String name, String side) {
        this.name = name;
        this.side = side;
        this.initialSize = -1;
        this.combatants = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
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
                ", side='" + side + '\'' +
                ", combatants:\n"
                +
                '}';
    }
}

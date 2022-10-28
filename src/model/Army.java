package model;

import java.util.HashMap;

public class Army {
    private String name;
    private String side;
    private HashMap<Integer,Combatant> combatants;
    private int aliveCount;

    public Army(String name, String side) {
        this.name = name;
        this.side = side;
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

    public HashMap<Integer, Combatant> getCombatants() {
        return combatants;
    }

    public void setCombatants(HashMap<Integer, Combatant> combatants) {
        this.combatants = combatants;
    }

    public int getAliveCount() {
        return aliveCount;
    }

    public void setAliveCount(int aliveCount) {
        this.aliveCount = aliveCount;
    }
}

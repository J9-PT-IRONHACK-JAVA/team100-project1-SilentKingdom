package model;

import repository.RepositoryCsv;

public class Warrior extends Combatant{
    private int stamina;
    private int strength;

    @Override
    public void weakAttack() {

    }

    @Override
    public void heavyAttack() {

    }

    public Warrior(String name, int hp, boolean isAlive, int stamina, int strength, RepositoryCsv repo)
            throws Exception {
        super(name, hp, isAlive, repo);
        this.stamina = stamina;
        this.strength = strength;
    }

    public Warrior(int id, String name, int hp, boolean isAlive, int stamina, int strength) {
        super(id, name, hp, isAlive);
        this.stamina = stamina;
        this.strength = strength;
    }

    public Warrior(String name, RepositoryCsv repo) throws Exception {
        // TO DO Random logic for attributes
        super(name, -1, true, repo);
        this.stamina = -1;
        this.strength = -1;
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    @Override
    public String toString() {
        return "Warrior{" +
                "stamina=" + stamina +
                ", strength=" + strength +
                "} " + super.toString();
    }
}

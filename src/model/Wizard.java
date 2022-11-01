package model;

import repository.RepositoryCsv;

public class Wizard extends Combatant{
    private int mana;
    private int intelligence;


    @Override
    public void weakAttack() {

    }

    @Override
    public void heavyAttack() {

    }

    public Wizard(String name, int hp, boolean isAlive, int mana, int intelligence, RepositoryCsv repo)
            throws Exception {
        super(name, hp, isAlive, repo);
        this.mana = mana;
        this.intelligence = intelligence;
    }

    public Wizard(int id, String name, int hp, boolean isAlive, int mana, int intelligence) {
        super(id, name, hp, isAlive);
        this.mana = mana;
        this.intelligence = intelligence;
    }

    public Wizard(String name, RepositoryCsv repo) throws Exception {
        // TO DO Random logic for attributes
        super(name, -1, true, repo);
        this.mana = -1;
        this.intelligence = -1;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    @Override
    public String toString() {
        return "Wizard{" +
                "mana=" + mana +
                ", intelligence=" + intelligence +
                "} " + super.toString();
    }
}

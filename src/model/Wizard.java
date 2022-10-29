package model;

public class Wizard extends Combatant{
    private int mana;
    private int intelligence;


    @Override
    public void weakAttack() {

    }

    @Override
    public void heavyAttack() {

    }

    public Wizard(String name, int hp, boolean isAlive, int mana, int intelligence) {
        super(name, hp, isAlive);
        this.mana = mana;
        this.intelligence = intelligence;
    }

    public Wizard(String name){
        // TO DO Random logic for attributes
        super(name, -1, true);
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

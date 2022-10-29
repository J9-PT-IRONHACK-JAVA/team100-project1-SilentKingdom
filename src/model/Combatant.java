package model;

import repository.ArmyCsv;
import repository.ArmyRepository;

import java.util.Random;

public abstract class Combatant implements Attacker{
    private int id;
    private String name;
    private int hp;
    private boolean isAlive;

    public Combatant(String name, int hp, boolean isAlive) {
        this.id = new Random().nextInt(); // ArmyCsv.getNextId();
        this.name = name;
        this.hp = hp;
        this.isAlive = isAlive;
    }

    public Combatant(){}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    @Override
    public String toString() {
        return "Combatant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", hp=" + hp +
                ", isAlive=" + isAlive +
                '}';
    }
}

package model;

import jdk.dynalink.linker.support.CompositeTypeBasedGuardingDynamicLinker;
import repository.ArmyCsv;
import repository.ArmyRepository;
import repository.CombatantCsv;

import java.io.FileNotFoundException;
import java.util.Random;

public abstract class Combatant implements Attacker{
    private int id;
    private String name;
    private int hp;
    private boolean isAlive;
    private String armyName;

    // When creating new combatant
    public Combatant(String name, int hp, boolean isAlive, CombatantCsv repo) throws Exception {
        this.id = repo.getLastId() + 1;
        this.name = name;
        this.hp = hp;
        this.isAlive = isAlive;
        repo.save(this);
    }

    // When retrieving combatant from repository
    public Combatant(int id, String name, int hp, boolean isAlive) {
        this.id = id;
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

    public String getArmyName() {
        return armyName;
    }

    public void setArmyName(String armyName) {
        this.armyName = armyName;
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

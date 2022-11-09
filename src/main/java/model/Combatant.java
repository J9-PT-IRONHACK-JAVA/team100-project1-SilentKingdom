package model;

import repository.RepositoryCsv;

public abstract class Combatant implements Attacker{
    private final int id;
    private String name;
    private int hp;
    private boolean isAlive;
    private String armyName;

    // TODO combatant.Remove method to call repo.deleteCombatant() to remove combatants from repository
    //  Should also remove id at least (so there are no issues with id's)

    public void takeDamage(int damage){
        hp = Math.max(0, hp-damage);
        if (hp == 0) {
            setAlive(false);
        }
    }

    // When creating new combatant
    public Combatant(String name, int hp, boolean isAlive, RepositoryCsv repo) throws Exception {
        this.id = repo.getLastId() + 1;
        this.name = name;
        this.hp = hp;
        this.isAlive = isAlive;
        repo.saveCombatant(this);
    }

    // When retrieving combatant from repository
    public Combatant(int id, String name, int hp, boolean isAlive) {
        this.id = id;
        this.name = name;
        this.hp = hp;
        this.isAlive = isAlive;
    }


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

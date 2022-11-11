package model;

import repository.RepositoryCsv;
import utils.Colors;

public abstract class Combatant implements Attacker{
    private final int id;
    private String name;
    private int hp;
    private boolean isAlive;
    private String armyName;

    public void takeDamage(int damage){
        int realDamage = Math.min(damage, hp);
        String txt = Colors.WHITE_BRIGHT + "%s takes %s damage... ".formatted(getName(), realDamage);
        hp -= Math.min(damage, hp);

        if (hp == 0) {
            setAlive(false);
            System.out.printf(txt + "%s is dead!!\n\n" + Colors.RESET, getName());
        } else {
            System.out.printf(txt + "remaining HP = %s\n" + Colors.RESET, hp);
        }
    }

    // When creating new combatant
    public Combatant(String name, int hp, boolean isAlive, RepositoryCsv repo) throws Exception {
        this.id = repo.getMaxId() + 1;
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

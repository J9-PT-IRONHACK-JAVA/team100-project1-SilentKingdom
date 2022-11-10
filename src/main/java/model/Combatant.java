package model;

import repository.RepositoryCsv;
import utils.ConsoleColors;

public abstract class Combatant implements Attacker{
    private final int id;
    private String name;
    private int hp;
    private boolean isAlive;
    private String armyName;

    public void takeDamage(int damage){
        hp = Math.max(0, hp-damage);
        System.out.printf("%s takes %s damage... ", getName(), Math.min(damage, hp));

        if (hp == 0) {
            setAlive(false);
            System.out.printf("%s is dead!!\n\n", getName());
        }
        System.out.printf("remaining HP = %s\n", hp);
    }

    public Combatant copy(RepositoryCsv repo) throws Exception {
        if (this instanceof Warrior) {
            var warriorCopy = new Warrior(
                    getName(), getHp(), true,
                    ((Warrior) this).getStamina(),
                    ((Warrior) this).getStrength(), repo
            );
            repo.saveCombatant(warriorCopy);
            return warriorCopy;
        } else if (this instanceof Wizard) {
            var wizardCopy = new Warrior(
                    getName(), getHp(), true,
                    ((Wizard) this).getMana(),
                    ((Wizard) this).getIntelligence(), repo
            );
            repo.saveCombatant(wizardCopy);
            return wizardCopy;
        }
        return null;
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

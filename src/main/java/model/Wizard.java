package model;

import net.datafaker.Faker;
import repository.RepositoryCsv;

public class Wizard extends Combatant {
    private int mana;
    private int intelligence;

    public void attack(Combatant target) {
        if (mana >= 5) {
            fireball(target);
        } else {
            staffHit(target);
        }
    }

    public void staffHit(Combatant target) {
        target.takeDamage(2);
        mana++;
    }

    public void fireball(Combatant target) {
        target.takeDamage(intelligence);
        mana -= 5;
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

    public static Wizard createRandom(RepositoryCsv repo) throws Exception {
        var faker = new Faker();
        return new Wizard(
                faker.harryPotter().character(),
                faker.number().numberBetween(50, 100),
                true,
                faker.number().numberBetween(10, 50),
                faker.number().numberBetween(1, 50),
                repo
        );
    }

}

package model;

import net.datafaker.Faker;
import repository.RepositoryCsv;

public class Warrior extends Combatant{
    private int stamina;
    private int strength;

    public void attack(Combatant target){
        if (stamina >= 5) {
            heavyAttack(target);
        } else {
            weakAttack(target);
        }
    }

    public void weakAttack(Combatant target) {
        target.takeDamage(strength/2);
        stamina++;
    }

    public void heavyAttack(Combatant target) {
        target.takeDamage(strength);
        stamina -= 5;
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

    // STATIC METHODS

    public static Warrior createRandom(RepositoryCsv repo) throws Exception {
        var faker = new Faker();
        return new Warrior(
                faker.military().marinesRank() + " " + faker.elderScrolls().race(),
                faker.number().numberBetween(100, 200),
                true,
                faker.number().numberBetween(10, 50),
                faker.number().numberBetween(1, 10),
                repo
        );
    }
}
package model;

import net.datafaker.Faker;
import repository.RepositoryCsv;
import utils.Tools;

import java.util.*;

public class Army {
    private String name;
    private final HashMap<Integer,Combatant> combatants;
    private final RepositoryCsv repo;
    private boolean isBot;

    // TODO army.remove() method to remove army calling repo.deleteArmy()
    //  In case user wants to change armies during game


    public Combatant pickRandomCombatant() {
        Random rand = new Random();
        var combatantsList = new ArrayList<>(combatants.values());
        return combatantsList.get(rand.nextInt(combatants.keySet().size()));
    }

    public Combatant pickCombatantByName(String name) {
        for (Combatant combatant : combatants.values()) {
            if (name.equals(combatant.getName())) {
                return combatant;
            }
        }
        return null;
    }

    public void addCombatant(Combatant combatant) throws Exception {
        combatant.setArmyName(name);
        combatants.put(combatant.getId(), combatant);
        repo.saveCombatant(combatant);
    }

    public void removeCombatant(Combatant combatant) {
        combatants.remove(combatant.getId());
    }

    public Army(String name, RepositoryCsv repo) {
        this.name = name;
        this.combatants = new HashMap<>();
        this.repo = repo;
        this.isBot = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Combatant> getCombatants() {
        return new ArrayList<>(combatants.values());
    }

    public int getSize() {
        return combatants.size();
    }

    public boolean isBot() {
        return isBot;
    }

    public void setBot(boolean bot) {
        isBot = bot;
    }

    @Override
    public String toString() {
        return "Army{" +
                "name='" + name + '\'' +
                ", combatants:\n"
                + combatants.toString().replace("},", "}\n,") +
                '}';
    }

    public static Army createRandom(int size, RepositoryCsv repo) throws Exception {
        var faker = new Faker();
        String randomArmyName = Tools.generateRandomArmyName();

        var army = new Army(randomArmyName, repo);

        // Calculate random number of Wizards in army
        int wizardNum = faker.number().numberBetween(size/4, size/2);
        int warriorNum = size - wizardNum;

        // Create random Wizards and add to army until max number
        while (army.getCombatants().size() < wizardNum) {
            var randomWizard = Wizard.createRandom(repo);
            army.addCombatant(randomWizard);
        }
        // Create random Warriors until army size
        while (army.getCombatants().size() < size) {
            var randomWarrior = Warrior.createRandom(repo);
            army.addCombatant(randomWarrior);
        }

        return army;
    }
}

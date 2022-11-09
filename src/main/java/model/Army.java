package model;

import repository.RepositoryCsv;

import java.util.ArrayList;

import net.datafaker.Faker;
import utils.Tools;

import java.util.*;

public class Army {
    private String name;
    public static final int MIN_ARMY_SIZE = 1;
    public static final int MAX_ARMY_SIZE = 10;
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

    public Combatant pickCombatantByIndex(String id){
        return combatants.get(Integer.parseInt(id));
    }

    public void addCombatant(Combatant combatant) throws Exception {
        if (getSize() >= MAX_ARMY_SIZE) {
            System.out.printf("Cannot add more combatants, Army size is already %s\n", MAX_ARMY_SIZE);
            return;
        }
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

    public void printStatus(){
        var combatants = this.getCombatants();
        for (int i = 0; i < combatants.size(); i++) {
            System.out.println(i+1 + ") " + Tools.combatantStatus(combatants.get(i)));
        }
        System.out.println();
    }

}

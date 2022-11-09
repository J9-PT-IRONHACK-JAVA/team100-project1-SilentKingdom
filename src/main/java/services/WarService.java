package services;

import model.Army;

import model.Combatant;
import repository.RepositoryCsv;
import utils.ConsoleColors;
import utils.ConsolePrints;
import utils.Tools;

import java.io.Console;
import java.util.ArrayList;

import static utils.ConsoleColors.printWithColor;

public class WarService {

    private final ArrayList<Combatant> graveyard;
    private final Army lightArmy;
    private final Army darkArmy;
    private boolean isOver;


    private final RepositoryCsv repo;

    private final InputService inputService = new InputService();

    public WarService(Army light, Army dark, RepositoryCsv repo) {
        this.graveyard = new ArrayList<>();
        this.lightArmy = light;
        this.darkArmy = dark;
        this.isOver = false;
        this.repo = repo;
    }

    public void battle(Combatant lightCombatant, Combatant darkCombatant){
        // TODO Print battle will start with combatants stats
        System.out.println(lightCombatant);
        System.out.println(darkCombatant);

        while (lightCombatant.isAlive() && darkCombatant.isAlive()) {
            // TODO print attacks (can be done inside attack method)
            lightCombatant.attack(darkCombatant);
            darkCombatant.attack(lightCombatant);
        }

        if (!lightCombatant.isAlive()) {
            lightArmy.removeCombatant(lightCombatant);
            graveyard.add(lightCombatant);
            System.out.printf("Light Combatant DEFEATED: %s\n\n", lightCombatant.getName());
        }
        if (!darkCombatant.isAlive()) {
            darkArmy.removeCombatant(darkCombatant);
            graveyard.add(lightCombatant);
            System.out.printf("DARK Combatant DEFEATED: %s\n\n", darkCombatant.getName());
        }

        // TODO Print battle will ended with winner status (or draw)
    }

    public Combatant getNextCombatant(Army army) {
        //Random
        if (army.isBot()) return army.pickRandomCombatant();

        //Player
        String nextCombatantID = inputService.askNextCombatant(army);
        return army.pickCombatantByIndex(nextCombatantID);
    }


    public Army start() throws Exception {
        ConsolePrints.printWarBegins();

        // Start war (LOOP) picking random combatants while any of the armies is defeated, continue
        int n = 0;
        while (!isOver) {
            // TODO Here we should print the war status
            n++;
            System.out.printf("\nBATTLE # %s\n", n);
            System.out.printf(
                    "Armies Stats => %s (%s) VS %s (%s)\n",
                    lightArmy.getName(), lightArmy.getSize(),
                    darkArmy.getName(), darkArmy.getSize()
            );

            var lightCombatant = getNextCombatant(lightArmy);
            var darkCombatant = getNextCombatant(darkArmy);

            battle(lightCombatant, darkCombatant);

            // Update combatants in repository
            repo.saveCombatant(lightCombatant);
            repo.saveCombatant(darkCombatant);

            System.out.println(new String(new char[100]).replace("\0", "="));

            isOver = lightArmy.getSize() == 0 || darkArmy.getSize() == 0;
        }

        // Return the winner army
        if (lightArmy.getSize() > 0) {
            return lightArmy;
        } else if (darkArmy.getSize() > 0) {
            return darkArmy;
        }

        // In case of draw return null
        return null;
    }

    public void stop(){
        setOver(true);
    }

    public ArrayList<Combatant> getGraveyard() {
        return graveyard;
    }

    public Army getLightArmy() {
        return lightArmy;
    }

    public Army getDarkArmy() {
        return darkArmy;
    }

    public boolean isOver() {
        return isOver;
    }

    public void setOver(boolean over) {
        isOver = over;
    }
}

package services;

import model.Army;

import model.Combatant;
import repository.RepositoryCsv;
import utils.ConsoleColors;
import utils.ConsolePrints;

import java.util.ArrayList;

import static utils.Tools.*;

public class WarService {

    private final ArrayList<Combatant> graveyard;
    private final Army lightArmy;
    private final Army darkArmy;
    private boolean isOver;
    private final RepositoryCsv repo;
    private final InputService inputService;

    public WarService(Army lightArmy, Army darkArmy, RepositoryCsv repo, InputService inputSVC) {
        this.lightArmy = lightArmy;
        this.darkArmy = darkArmy;
        this.graveyard = new ArrayList<>();
        this.isOver = false;
        this.repo = repo;
        this.inputService = inputSVC;
    }

    public void battle(Combatant lightCombatant, Combatant darkCombatant){
        ConsolePrints.selectedCombatants(lightCombatant, darkCombatant);

        while (lightCombatant.isAlive() && darkCombatant.isAlive()) {
            lightCombatant.attack(darkCombatant);
            darkCombatant.attack(lightCombatant);
            sleep(GameService.ATTACK_SLEEP);
        }

        if (!lightCombatant.isAlive()) {
            lightArmy.removeCombatant(lightCombatant);
            graveyard.add(lightCombatant);
            ConsolePrints.battleResult(darkCombatant, lightCombatant, false);
        }
        if (!darkCombatant.isAlive()) {
            darkArmy.removeCombatant(darkCombatant);
            graveyard.add(lightCombatant);
            ConsolePrints.battleResult(lightCombatant, darkCombatant, true);
        }
    }

    public Combatant getNextCombatant(Army army) {
        Combatant combatant;

        if (army.isBot()) {
            combatant = army.pickRandomCombatant();
        } else {
            String nextCombatantID = inputService.askNextCombatant(army);
            combatant = army.pickCombatantByIndex(nextCombatantID);
        }
        return combatant;
    }


    public Army start() throws Exception {
        ConsolePrints.warBegins();

        // Start war (LOOP) picking random combatants while any of the armies is defeated, continue
        int n = 0;
        while (!isOver) {
            n++;
            ConsolePrints.clearConsole(ConsolePrints.battleHeader(n, warStatus()));

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

    public ArrayList<Combatant> getGraveyard() {
        return graveyard;
    }

    public Army getLightArmy() {
        return lightArmy;
    }

    public Army getDarkArmy() {
        return darkArmy;
    }

    private String warStatus(){
        return ConsoleColors.YELLOW_BOLD + "Armies Stats => %s (%s) VS %s (%s)\n".formatted(
                lightArmy.getName(), lightArmy.getSize(),
                darkArmy.getName(), darkArmy.getSize())
                + ConsoleColors.RESET;
    }
}

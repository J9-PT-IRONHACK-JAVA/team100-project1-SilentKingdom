package services;

import model.Army;

import model.Combatant;
import repository.RepositoryCsv;
import utils.Colors;
import utils.Prints;

import java.util.ArrayList;

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

    public void battle(Combatant lightCombatant, Combatant darkCombatant, int n) throws Exception {
        printBattleHeader(lightCombatant, darkCombatant, n);
        System.out.println(Colors.WHITE_BRIGHT +"\n LET'S FIGHT!!!" + Colors.RESET);

        var skip = false;

        while (lightCombatant.isAlive() && darkCombatant.isAlive()) {
            skip = inputService.askContinue(skip, true);
            printBattleHeader(lightCombatant, darkCombatant, n);
            lightCombatant.attack(darkCombatant);
            repo.saveCombatant(darkCombatant);

            skip = inputService.askContinue(skip, true);
            printBattleHeader(lightCombatant, darkCombatant, n);
            darkCombatant.attack(lightCombatant);
            repo.saveCombatant(lightCombatant);
        }

        printBattleHeader(lightCombatant, darkCombatant, n);

        if (!lightCombatant.isAlive()) {
            processDefeated(lightArmy, darkCombatant, lightCombatant, false);
        }
        if (!darkCombatant.isAlive()) {
            processDefeated(darkArmy, lightCombatant, darkCombatant, true);
        }

        inputService.askContinue(false, false);
    }

    private void processDefeated(Army loserArmy, Combatant winner, Combatant defeated, Boolean lightWins) {
        loserArmy.removeCombatant(defeated);
        graveyard.add(defeated);
        Prints.battleResult(winner, defeated, lightWins);
    }

    private Combatant getNextCombatant(Army army, String color) {
        Combatant combatant;

        if (army.isBot()) {
            combatant = army.pickRandomCombatant();
        } else {
            String nextCombatantID = inputService.askNextCombatant(army, color);
            combatant = army.pickCombatantByIndex(nextCombatantID);
        }
        return combatant;
    }


    public void start() throws Exception {
        Prints.warBegins();
        inputService.askContinue(false, false);
        // Start war (LOOP) picking random combatants while any of the armies is defeated, continue
        int n = 0;
        while (!isOver) {
            n++;
            Prints.clearConsole(Prints.battleHeader(n, getWarStatus()));

            var lightCombatant = getNextCombatant(lightArmy, Colors.YELLOW_BOLD);
            var darkCombatant = getNextCombatant(darkArmy, Colors.PURPLE_BOLD);

            battle(lightCombatant, darkCombatant, n);

            System.out.println(new String(new char[100]).replace("\0", "="));

            isOver = lightArmy.getSize() == 0 || darkArmy.getSize() == 0;
        }

        // Return the winner army
        if (lightArmy.getSize() > 0) {
            Prints.winner(lightArmy, Colors.YELLOW_BOLD, getGraveyard());
        } else if (darkArmy.getSize() > 0) {
            Prints.winner(darkArmy, Colors.PURPLE_BOLD, getGraveyard());
        } else {
            Prints.winner(null, Colors.CYAN_BOLD, getGraveyard());
        }
    }

    public ArrayList<Combatant> getGraveyard() {
        return graveyard;
    }


    private String getWarStatus() {
        String lightStatus = getArmyStatus(lightArmy, Colors.YELLOW_BOLD);
        String darkStatus = getArmyStatus(darkArmy, Colors.PURPLE_BOLD);

        return "\n" + lightStatus + "\n" + darkStatus + "\n";
    }

    private String getArmyStatus(Army army, String color){
        try {
            var combatants = repo.getArmyCombatants(army);
            String armyGrid = Prints.getArmyGrid(combatants);
            String armyTitle = "%s (%s) => ".formatted(army.getName(), army.getSize());
            return  color + armyTitle + Colors.RESET + armyGrid + "\n";
        } catch (Exception e) {
            return "ERROR: couldn't get army status from repository";
        }

    }

    private void printBattleHeader(Combatant light, Combatant dark, int n){
        Prints.clearConsole(
                Prints.battleHeader(n, getWarStatus())
        );
        System.out.println(Prints.combatantsStatus(light, dark));
    }
}

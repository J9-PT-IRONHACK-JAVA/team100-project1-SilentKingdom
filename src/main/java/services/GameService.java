package services;

import model.Army;
import model.Combatant;
import repository.RepositoryCsv;
import utils.Prints;

import java.io.IOException;

import static services.InputService.*;
import static utils.Tools.*;

public class GameService {
    public static final int ATTACK_SLEEP = 1;
    private RepositoryCsv repo;
    private InputService inputSVC;

    public GameService(){
        RepositoryCsv repositoryCsv;
        try {
            repositoryCsv = new RepositoryCsv();
            this.repo = repositoryCsv;
            this.inputSVC = new InputService(repo);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }


    public void start() throws Exception {
        Prints.gameWelcome();
        inputSVC.askContinue(false, false);

        while (true) {
            // War preparation
            Army lightArmy = createArmy();
            Army darkArmy = createArmy();

            var war = new WarService(lightArmy, darkArmy, repo, inputSVC);

            // War begins
            war.start();
            inputSVC.askContinue(false, false);

            // Play again?
            String answer = inputSVC.askPlayAgain();
            if (!answer.equals(YES)) {
                Prints.exitGame();
                inputSVC.close();
                System.exit(0);
            }

            // reset armies
            repo.deleteArmy(lightArmy);
            repo.deleteArmy(darkArmy);
            inputSVC.setArmiesCreated(0);
        }
    }

    private Army createArmy() throws Exception {
        boolean isOk;
        while (true) {
            String armyMode = inputSVC.askWhoIsArmyControlledBy();
            String creationType = inputSVC.askTypeArmyCreation();

            Army army;
            switch (creationType) {
                case IMPORT -> {
                    String armyCsv = inputSVC.askWhichArmyImport();
                    String armyName = inputSVC.askArmyName();
                    army = repo.importArmy(armyCsv, armyName);
                }
                case HANDMADE -> {
                    String armyName = inputSVC.askArmyName();
                    int armySize = Integer.parseInt(inputSVC.askArmySize());
                    army = createHandmadeArmy(armySize, armyName);
                }
                default -> {
                    int armySize = Integer.parseInt(inputSVC.askArmySize());
                    army = Army.createRandom(armySize, repo);
                }
            }

            army.setBot(armyMode.equals(BOT));
            String answer = inputSVC.okWithThisArmy(army);
            isOk = answer.equals(YES);
            if (!isOk) {
                repo.deleteArmy(army);
                continue;
            }
            if (creationType.equals(RANDOM) || creationType.equals(HANDMADE)) {
                inputSVC.askExportArmy(army, repo);
            }
            inputSVC.setArmiesCreated(inputSVC.getArmiesCreated() + 1);
            return army;
        }
    }

    private Army createHandmadeArmy(int armySize, String armyName) throws Exception {
        var army = new Army(armyName, repo);

        while (army.getSize() < armySize) {
            Combatant combatant;
            String answer = inputSVC.askCombatantCreateType();

            if (answer.equals(IMPORT)) {
                String combatantName = inputSVC.askWhichCombatantImport();
                combatant = repo.importCombatant(combatantName);
            } else {
                String combatantName = inputSVC.askWhichCombatantImport();
                combatant = repo.importCombatant(combatantName);
                //combatant = inputSVC.askCreateCombatant();
            }
            army.addCombatant(combatant);
        }
        return army;
    }

}

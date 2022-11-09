package services;

import model.Army;
import repository.RepositoryCsv;
import utils.ConsolePrints;

import java.io.IOException;
import java.util.ArrayList;

import static services.InputService.*;

public class GameService {

    private RepositoryCsv repo;
    private final InputService inputSVC;

    public GameService(){
        this.inputSVC = new InputService();
        RepositoryCsv repositoryCsv;
        try {
            repositoryCsv = new RepositoryCsv();
            this.repo = repositoryCsv;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }


    public void start() throws Exception {
        ConsolePrints.gameWelcome();

        while (true) {
            // War preparation
            ConsolePrints.letsSetArmies();

            Army lightArmy = createArmy();
            Army darkArmy = createArmy();

            var war = new WarService(lightArmy, darkArmy, repo);

            // War begins
            war.start();

            // Play again?
            String answer = inputSVC.askPlayAgain();
            if (!answer.equals(YES)) {
                ConsolePrints.exitGame();
                System.exit(0);
            }

            // reset armies
            repo.deleteArmy(lightArmy);
            repo.deleteArmy(darkArmy);
        }
    }

    private Army createArmy() throws Exception {
        boolean isOk;
        while (true) {
            ConsolePrints.startCreateArmy(repo.getDistinctArmies().length + 1);

            String armyMode = inputSVC.askWhoIsArmyControlledBy();
            String creationType = inputSVC.askTypeArmyCreation();

            Army army;
            switch (creationType) {
                case IMPORT -> {
                    String armyName = inputSVC.askArmyName();
                    String armyCsv = inputSVC.askWichArmyImport(repo);
                    army = repo.importArmy(armyCsv, armyName);
                }
                case HANDMADE -> {
                    String armyName = inputSVC.askArmyName();
                    army = inputSVC.createHandmadeArmy(armyName);
                    inputSVC.askExportArmy(army, repo);
                }
                default -> {
                    int armySize = Integer.parseInt(inputSVC.askArmySize());
                    army = Army.createRandom(armySize, repo);
                    inputSVC.askExportArmy(army, repo);
                }
            }

            army.setBot(armyMode.equals(PLAYER));
            String answer = inputSVC.okWithThisArmy(army);
            isOk = answer.equals(YES);
            if (!isOk) repo.deleteArmy(army);
            if (isOk) return army;
        }
    }

}

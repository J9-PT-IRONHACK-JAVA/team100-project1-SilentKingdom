package services;

import model.Army;
import repository.RepositoryCsv;
import utils.ConsolePrints;

public class GameService {
    private static final String RANDOM_MODE = "random";
    private static final String PVP_MODE = "PVP";
    private static final String PVB_MODE = "PVB";

    private static final String BACK = "BACK";


    private InputService inputService;

    public void startGame() {

        ConsolePrints.printGameWelcome();

        var gameMode = inputService.chooseGameMode();

        runGameMode(gameMode);

    }

        // Standard input and out (interactive part)
        // Choose how to instantiate armies: Import or Introduce size, attributes?

        // Import / Instantiate army's (Ask for Army's names, choose side, etc)

        // Choose war modality? Random, PlayerVsPlayer, PlayerVsBot?

        // Start war modality instantiate and start WarService


    public void runGameMode(String gameMode){
        if (gameMode == "1"){
//            botVSBotMode();

        } else if (gameMode == "2") {
            ConsolePrints.printPlayerVsBotSelection();

        } else if (gameMode == "3") {
            ConsolePrints.printPlayerVsPlayerSelection();

        } else{
            exitGame();
        }

    }


    public void botVSBotMode(RepositoryCsv repo) throws Exception {

        ConsolePrints.printBotVsBotSelection();

        var lightArmyName = inputService.askArmyName();
        var darkArmyName = inputService.askArmyName();

        ConsolePrints.printLetsChooseArmySize();
        var armySize = inputService.askNumberOfCombatants();

        ConsolePrints.printConstructionOfRandomArmy(lightArmyName);
        var lightArmy = Army.createRandom(armySize, repo);
        inputService.okWithCreatedArmy();

        ConsolePrints.printConstructionOfRandomArmy(darkArmyName);
        var darkArmy = Army.createRandom(armySize, repo);
        inputService.okWithCreatedArmy();


    } // finishes with the calling of createRandomArmies()

    public void exitGame(){
        ConsolePrints.printExitGame();
        System.exit(0);
    }

}

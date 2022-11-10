package services;

import model.Army;
import model.Combatant;
import model.Warrior;
import repository.RepositoryCsv;
import utils.ConsolePrints;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static utils.ConsoleColors.*;

public class GameService {
    private static final String RANDOM_MODE = "random";
    private static final String PVP_MODE = "PVP";
    private static final String PVB_MODE = "PVB";

    private static final String BACK = "BACK";


    private static final RepositoryCsv repositoryCsv;

    static {
        try {
            repositoryCsv = new RepositoryCsv();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static final String resetColor = "\u001B[0m";


    public static void printGrid(RepositoryCsv repo) throws Exception {

        var lightArmy = repo.getArmyCombatants(Army.createRandom(10, repositoryCsv));
        var darkArmy = repo.getArmyCombatants(Army.createRandom(9, repositoryCsv));

        System.out.println(RED_BACKGROUND+BLACK_BOLD+"\t"+"\t"+"\t"+"\t"+"\t"+ "Army 1" +"\t"+"\t"+"\t"+"\t"+"\t"+"\t"+resetColor);

        var gridPossWar = "\t" + Arrays.toString(new String[]{BLACK_BOLD + 'X' + resetColor});
        var gridPossWiz = "\t" + Arrays.toString(new String[]{BLACK_BOLD + 'O' + resetColor});

        setPositionAndColor(lightArmy, gridPossWar, gridPossWiz);

        System.out.println("\n" + "\n" + CYAN_BACKGROUND + BLACK_BOLD +"\t"+"\t"+"\t"+"\t"+"\t"+"Army 2"+"\t"+"\t"+"\t"+"\t"+"\t"+"\t"+resetColor);

        setPositionAndColor(darkArmy, gridPossWar, gridPossWiz);
    }

    //TODO hacer un string y imprimirlo al final,
    private static void setPositionAndColor(ArrayList<Combatant> darkArmy, String gridPossWar, String gridPossWiz) {
        for (Combatant combatant : darkArmy) {
            if (combatant.getClass() == Warrior.class) {
                if (combatant.isAlive() && combatant.getHp() >= 60) {
                    System.out.print(GREEN_BACKGROUND + gridPossWar);
                }else if (combatant.isAlive() && combatant.getHp() >= 40) {
                System.out.print(YELLOW_BACKGROUND + gridPossWiz);
                } else {
                    System.out.print(RED_BACKGROUND + gridPossWar);
                }
            } else {
                if (combatant.isAlive() && combatant.getHp() >= 60) {
                    System.out.print(GREEN_BACKGROUND + gridPossWiz);
                } else if (combatant.isAlive() && combatant.getHp() >= 40) {
                    System.out.print(YELLOW_BACKGROUND + gridPossWiz);
                } else {
                    System.out.print(RED_BACKGROUND + gridPossWiz);
                }
            }
        }
    }


    private final InputService inputService = new InputService();
/*
    public void startGame() throws Exception {

        ConsolePrints.printGameWelcome();

        // LIGHTARMY CREATION
        ConsolePrints.printLetsCreateLightArmy();

        var whoIsLightArmyControlledBy= inputService.askWhoIsArmyControlledBy();
        var typeLightArmyCreation = inputService.askTypeArmyCreation();
        Army lightArmy = createArmy(whoIsLightArmyControlledBy, typeLightArmyCreation);

        ConsolePrints.printNewArmyStatus(lightArmy.getName());
        lightArmy.printStatus();
        inputService.okWithThisArmy();

        // DARKARMY CREATION
        ConsolePrints.printLetsCreateDarkArmy();

        var whoIsDarkArmyControlledBy= inputService.askWhoIsArmyControlledBy();
        var typeDarkArmyCreation = inputService.askTypeArmyCreation();
        Army darkArmy = createArmy(whoIsDarkArmyControlledBy, typeDarkArmyCreation);

        ConsolePrints.printNewArmyStatus(darkArmy.getName());
        darkArmy.printStatus();
        inputService.okWithThisArmy();

        WarService warService = new WarService(lightArmy, darkArmy, repositoryCsv);

        warService.start();

    }

        // Standard input and out (interactive part)
        // Choose how to instantiate armies: Import or Introduce size, attributes?

        // Import / Instantiate army's (Ask for Army's names, choose side, etc)

        // Choose war modality? Random, PlayerVsPlayer, PlayerVsBot?

        // Start war modality instantiate and start WarService
    private Army createArmy(String whoIsControlledBy, String typeOfArmyCreation) throws Exception {

        Army army = null;

        switch (typeOfArmyCreation) {
            // import army
            case "1" -> {
                var armyName = inputService.askArmyName();
                var armyToImportFileName = inputService.armyToImportFileName();
                army = repositoryCsv.importArmy(armyToImportFileName, armyName);
            }

            case "2" -> {
                // create random army
                var armySize = inputService.askNumberOfCombatants();
                army = Army.createRandom(armySize, repositoryCsv);
                ConsolePrints.printConstructionOfRandomArmy();
                break;
            }

            case "3" -> {
                // create manual army
                var armyName = inputService.askArmyName();
                var armySize = inputService.askNumberOfCombatants();
                army = new Army(armyName, repositoryCsv);
                while (army.getCombatants().size() < armySize) {
                    System.out.println("Please select a combatant to add to " + armyName);
                    // TODO function that displays all combatants from repo to select and returns selected combatant object
//                army.addCombatant(combatant);
                }
                break;
            }
        }

        assert army != null;
        army.setBot(!whoIsControlledBy.equals("1"));

        return army;
    }*/

    public void exitGame(){
        ConsolePrints.printExitGame();
        System.exit(0);
    }

}

package services;
import model.Army;
import model.Warrior;
import model.Wizard;

import java.nio.file.LinkPermission;
import java.util.Scanner;

import model.Army;
import model.Combatant;
import model.Warrior;
import model.Wizard;
import utils.ConsoleColors;
import utils.Tools;

import java.util.Random;
import java.util.Scanner;

import static utils.ConsoleColors.printWithColor;

public class InputService {

    private static String EXIT_STRING = """
            ====================            
            EXIT - exit game
            ====================""";

    private static String BACK_STRING = """
            ====================            
            BACK - go back
            ====================""";

    private final Scanner prompt;

    public InputService() {
        this.prompt = new Scanner(System.in);
    }


    // Each of these methods will ask for the needed attributes,
    // store them in variables and instantiate the new object with them

    public String askWhoIsArmyControlledBy(){
        String input;
        do {
            var whoIsArmyControlledBy = buildMenu(
                    "First of all, tell me who will be this army lead by?",
                    "PLAYER - the player will decide the combatants to fight every battle",
                    "BOT - the machine will decide the combatants to fight every battle");

            System.out.println(whoIsArmyControlledBy);
            showBackMenuOption();
            showExitMenuOption();

            input = prompt.nextLine().trim().toLowerCase();
            switch (input) {
                case "1", "2", "exit", "back" -> {
                    if(input.equals("exit")){
                        input = "0";
                    }
                    if(input.equals("back")){
                        input = "-1";
                    }
                    return input;
                }
                default -> printWithColor("Command not recognized!", ConsoleColors.RED);
            }

        }while (true);
    }
    public String askTypeArmyCreation() {
        String input;
        do {
            var typeArmyCreation = buildMenu(
                    "How do you wish to create this army?",
                    "Import army from personal .csv file",
                    "Create a random army",
                    "Make a customized handmade army");

            System.out.println(typeArmyCreation);
            showBackMenuOption();
            showExitMenuOption();

            input = prompt.nextLine().trim().toLowerCase();
            switch (input) {
                case "1", "2", "3", "exit", "back" -> {
                    if(input.equals("exit")){
                        input = "0";
                    }
                    if(input.equals("back")){
                        input = "-1";
                    }
                    return input;
                }
                default -> printWithColor("Command not recognized!", ConsoleColors.RED);
            }

        }while (true);
    }
    public String askArmyName() {
        var chooseArmyName = buildMenu("Please tell us how do you wish to call this army:");
        System.out.println(chooseArmyName);
        showBackMenuOption();
        showExitMenuOption();

        var armyName = prompt.nextLine();
        return armyName;
    }
    public String armyToImportFileName() {
        var chooseArmyName = buildMenu("Please write down the name of the .csv file where the army to import is.");
        System.out.println(chooseArmyName);
        showBackMenuOption();
        showExitMenuOption();

        var fileName = prompt.nextLine();
        return fileName;
    }
    public int askNumberOfCombatants() {

        String input;
        int armySize = 0;
        do {
            var chooseNumberOfCombatants = buildMenu("Please write down the number of combatants that will compose the army:");
            System.out.println(chooseNumberOfCombatants);
            showBackMenuOption();
            showExitMenuOption();

            input = prompt.nextLine().trim().toLowerCase();

            if (input.equals("back")) {
                return -1;
            }
            if (input.equals("exit")) {
                return 0;
            }

            armySize = checkValidArmySize(input);

            if (armySize == -1) {
                printWithColor("Please try a valid size (between 1 and 10)", ConsoleColors.RED);
            }

        } while (armySize <= 0);

        return armySize;
    }
    private int checkValidArmySize(String input) {

        var armySize = 0;
        if (input.matches("^\\d+$")) {
            armySize = Integer.parseInt(input);
            if (armySize <= Army.MAX_ARMY_SIZE && armySize >= Army.MIN_ARMY_SIZE) {
                return armySize;
            }
        }
        return -1;
    }
    public String okWithThisArmy() {
        String input;
        do {
            var okWithCreatedArmy = buildMenu(
                    "Are you OK with this army?",
                    "Yes!",
                    "No, build random army again",
                    "No, go back to number of combatants selection",
                    "No, go back to game mode selection");
            System.out.println(okWithCreatedArmy);
            showBackMenuOption();
            showExitMenuOption();

            input = prompt.nextLine().trim().toLowerCase();
            switch (input) {
                case "1", "2", "3", "4" -> {
                    return input;
                }
                default -> printWithColor("Command not recognized!", ConsoleColors.RED);
            }

        }while (true);

    }

    public static String buildMenu(String title, String ... options) {
        StringBuilder menu = new StringBuilder(String.format("%s\n", title));

        for (int i = 0; i < options.length; i++) {
            menu.append(i+1).append(") ").append(options[i]).append("\n");
        }

        return menu.toString();
    }
    public static void showExitMenuOption(){
        System.out.println(EXIT_STRING);
    };
    public static void showBackMenuOption(){
        System.out.println(BACK_STRING);
    };
    public String askNextCombatant(Army army){
        String nextCombatant;

        do {
            var askNextCombatant = buildMenu("Please choose the next combatant to fight for: " + army.getName());
            System.out.println(askNextCombatant);
            army.printStatus();
            showBackMenuOption();
            showExitMenuOption();

            nextCombatant = prompt.nextLine().trim().toLowerCase();

            switch (nextCombatant) {
                case "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "exit", "back" -> {
                    if(nextCombatant.equals("exit")){
                        nextCombatant = "0";
                    }
                    if(nextCombatant.equals("back")){
                        nextCombatant = "-1";
                    }

                    var indexOutOfBounds = false;
                    try{
                        army.pickCombatantByIndex(nextCombatant);
                    }catch(Exception e){
                        indexOutOfBounds = true;
                    }
                    if(!(indexOutOfBounds==false)){
                        printWithColor("There is no " + nextCombatant + "th combatant. Please enter a valid number.", ConsoleColors.RED);
                        nextCombatant = Tools.OUT_OF_BOUND;
                    }

                    return nextCombatant;
                }
                default -> printWithColor("Command not recognized!", ConsoleColors.RED);
            }

        } while (true);
    }







    public String chooseGameMode() {
        String input;
        do {
            var chooseGameMode = buildMenu(
                    "Please choose a game mode:",
                    "Bot VS Bot (random)",
                    "Player VS Bot", "Player VS Player");
            System.out.println(chooseGameMode);
            showExitMenuOption();

            input = prompt.nextLine().trim().toLowerCase();
            switch (input) {
                case "1", "2", "3", "exit" -> {
                    return input;
                }
                default -> printWithColor("Command not recognized!", ConsoleColors.RED);
            }
        } while (!input.equals("exit"));

        return input;
    }


}

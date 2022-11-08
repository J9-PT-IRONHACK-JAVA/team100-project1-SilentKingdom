package services;
import model.Army;
import model.Warrior;
import model.Wizard;

import java.nio.file.LinkPermission;
import java.util.*;

import model.Army;
import model.Combatant;
import model.Warrior;
import model.Wizard;
import utils.ConsoleColors;
import utils.Tools;

import java.util.Scanner;

import static utils.ConsoleColors.printWithColor;

public class InputService {

    public static final String EXIT = "exit";
    public static final String BACK = "back";
    public static final String START = "start";
    public static final String PLAYER = "player";
    public static final String BOT = "bot";
    public static final String IMPORT = "import";
    public static final String RANDOM = "random";
    private static final String HANDMADE = "handmade";
    private static final String EXIT_STRING = """
            ====================            
            EXIT - exit game
            ====================""";

    private static final String BACK_STRING = """
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

        String title = "First of all, tell me who will be this army lead by?";
        String[] options = {
                "PLAYER - the player will decide the combatants to fight every battle",
                "BOT - the machine will decide the combatants to fight every battle",
        };

        var whoIsArmyControlledBy = buildMenu(title, options);

        do {
            System.out.println(whoIsArmyControlledBy);
            showBackMenuOption();
            showExitMenuOption();

            input = prompt.nextLine().trim().toLowerCase();
            switch (input) {
                case "1" -> { return PLAYER; }
                case "2" -> { return BOT; }
                case EXIT, BACK -> { return input;}
                default -> printWithColor("Command not recognized!", ConsoleColors.RED);
            }

        }while (true);
    }
    public String askTypeArmyCreation() {
        String input;
        String title = "How do you wish to create this army?";
        String[] options = {
                "Import army from personal .csv file",
                "Create a random army",
                "Make a customized handmade army"
        };

        var typeArmyCreation = buildMenu(title, options);

        do {
            System.out.println(typeArmyCreation);
            showBackMenuOption();
            showExitMenuOption();

            input = prompt.nextLine().trim().toLowerCase();
            switch (input) {
                case "1" -> { return IMPORT; }
                case "2" -> { return RANDOM; }
                case "3" -> { return HANDMADE; }
                case EXIT, BACK -> { return input;}
                default -> printWithColor("Command not recognized!", ConsoleColors.RED);
            }

        }while (true);
    }
    public String askArmyName() {
        var chooseArmyName = buildMenu("Please tell us how do you wish to call this army:");
        System.out.println(chooseArmyName);
        showBackMenuOption();
        showExitMenuOption();

        return prompt.nextLine();
    }
    public String armyToImportFileName() {
        // TODO use listArmiesImport method from repository to list and let the user choose
        var chooseArmyName = buildMenu("Please write down the name of the .csv file where the army to import is.");
        System.out.println(chooseArmyName);
        showBackMenuOption();
        showExitMenuOption();

        return prompt.nextLine();
    }
    public String askNumberOfCombatants() {
        String input;
        var title = "Please write down the number of combatants that will compose the army:";
        do {
            System.out.println(title);
            showBackMenuOption();
            showExitMenuOption();

            input = prompt.nextLine().trim().toLowerCase();

            if (input.equals(BACK) || input.equals(EXIT) || isValidArmySize(input)) {
                return input;
            }

            printWithColor("Please try a valid size (between 1 and 10)", ConsoleColors.RED);
        } while (true);
    }
    private boolean isValidArmySize(String input) {
        var armySizeInt = 0;
        if (input.matches("^\\d+$")) {
            armySizeInt = Integer.parseInt(input);
            return armySizeInt <= Army.MAX_ARMY_SIZE && armySizeInt >= Army.MIN_ARMY_SIZE;
        }
        return false;
    }
    public String okWithThisArmy() {
        String input;
        String title = "How do you wish to create this army?";
        String[] options = {
                "Yes!",
                "No, build random army again",
                "No, go back to number of combatants selection",
                "No, go back to game mode selection"
        };

        var okWithCreatedArmy = buildMenu(title, options);

        do {
            System.out.println(okWithCreatedArmy);
            showBackMenuOption();
            showExitMenuOption();

            input = prompt.nextLine().trim().toLowerCase();
            switch (input) {
                case "1", EXIT, BACK -> { return input; }
                case "2" -> { return RANDOM; }
                case "3" -> { return HANDMADE; }
                case "4" -> { return START; }
                default -> printWithColor("Command not recognized!", ConsoleColors.RED);
            }

        } while (true);
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


    private static ArrayList<String> getListStrNumbers(int n){
        var list = new ArrayList<String>();
        for (int i = 0; i < n; i++) {
            list.add(String.valueOf(i+1));
        }
        return list;
    }

    class Option {
        public String text;
        public String key;

        public Option(String text, String key) {
            this.text = text;
            this.key = key;
        }
    }
}

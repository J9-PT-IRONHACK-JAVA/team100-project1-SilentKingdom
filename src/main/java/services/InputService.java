package services;
import model.Army;
import model.Warrior;
import model.Wizard;
import java.util.Scanner;

import model.Army;
import model.Combatant;
import model.Warrior;
import model.Wizard;
import utils.ConsoleColors;

import java.util.Random;
import java.util.Scanner;

public class InputService {

    private static String EXIT_STRING = """
            =========================             
                    EXIT - exit game
                    =========================
            """;
    private final Scanner prompt;

    public InputService() {
        this.prompt = new Scanner(System.in);
    }


    // Each of these methods will ask for the needed attributes,
    // store them in variables and instantiate the new object with them

    public String chooseGameMode() {
        String input;
        do {
            var chooseGameMode = """
                    Please choose a game mode:
                    1) Bot VS Bot (random)
                    2) Player VS Bot
                    3) Player VS Player         
                                        
                    =========================             
                    EXIT - exit game
                    =========================
                    """;
            System.out.println(chooseGameMode);
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

    public static String buildMenu(String title, String ... options) {
        StringBuilder menu = new StringBuilder(String.format("%s\n", title));

        for (int i = 0; i < options.length; i++) {
            menu.append(i+1).append(") ").append(options[i]).append("\n");
        }

        menu.append("""
=========================             
    EXIT - exit game
=========================
                """);

        return menu.toString();
    }

    //BOT VS BOT MODE

    public String askArmyName() {
        System.out.println("Please choose a name for the army");
        var armyName = prompt.nextLine();
        return armyName;
    }

    public int askNumberOfCombatants() {

        String input;
        int armySize = 0;
        do {
            System.out.println("""
                    Please write down the number of combatants that will compose the army:
                                        
                    =====================================
                    BACK - go back to Game Mode Selection
                    =====================================
                    """);

            input = prompt.nextLine().trim().toLowerCase();

            if (input.equals("BACK") || input.equals("back")) {
                return -1;
            }

            armySize = checkValidArmySize(input);

            if (armySize == -1) {
                System.out.println("Please try a valid size");
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

    public String okWithCreatedArmy() {
        var okWithCreatedArmies = """
                Are you OK with the created armies?
                1) Yes!
                2) No, build army again
                3) No, go back to number of combatants selection
                4) No, go back to game mode selection        
                """;
        System.out.println(okWithCreatedArmies);
        String input;
        input = prompt.nextLine().trim().toLowerCase();
        switch (input) {
            case "1", "2", "3", "4" -> {
                return input;
            }
            default -> printWithColor("Command not recognized!", ConsoleColors.RED);
        }

        return null;
    }



    public static void printWithColor(String text, String color) {
        System.out.println(color + text + ConsoleColors.RESET);
    }

}

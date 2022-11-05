package services;

import Utils.ConsoleColors;
import model.Army;
import model.Combatant;
import model.Warrior;
import model.Wizard;

import java.util.Random;
import java.util.Scanner;

public class InputService {
    private Scanner prompt;

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


        private void createRandomArmies (String lightSideArmyName, String darkSideArmyName,int armySize){
            System.out.println("Construction of " + lightSideArmyName + "army:");
            var lightSideArmy = createRandomArmy(lightSideArmyName, armySize);

            System.out.println("Construction of " + darkSideArmyName + "army:");
            var darkSideArmy = createRandomArmy(darkSideArmyName, armySize);

            System.out.println("LightSide Army:");
            lightSideArmy.getArmyStatus();

            System.out.println("DarkSide Army:");
            darkSideArmy.getArmyStatus();

            var okWithCreatedArmies = """
                    Are you OK with the created armies?
                    1) Yes, let's go to WAR!
                    2) No, build new armies again
                    3) No, go back to game mode selection        
                    """;
            System.out.println(okWithCreatedArmies);
            String input;
            input = prompt.nextLine().trim().toLowerCase();
            switch (input) {
                case "1" -> startWar();
                case "2" -> createRandomArmies(lightSideArmyName, darkSideArmyName, armySize);
                case "3" -> chooseGameMode();
                default -> printWithColor("Command not recognized!", ConsoleColors.RED);
            }
        } // finishes with the calling of startWar()

        // Methods for createRandomArmies()
        private Warrior createRandomWarrior (String warriorName){
            Warrior warrior = new Warrior(warriorName);
            return warrior;
        }
        private Wizard createRandomWizard (String wizardName){
            Wizard wizard = new Wizard(wizardName);
            return wizard;
        }
        private Army createRandomArmy (String name,int initialSize){

            Army army = new Army(name, initialSize);
            while (army.getCombatants().size() < initialSize) {
                Combatant newCombatant;
                if (new Random().nextBoolean()) {
                    System.out.println("NEW WARRIOR. What do you wish to call your new warrior?");
                    var warriorName = prompt.nextLine();
                    newCombatant = createRandomWarrior(warriorName);
                } else {
                    System.out.println("NEW WIZARD. What do you wish to call your new wizard?");
                    var wizardName = prompt.nextLine();
                    newCombatant = createRandomWizard(wizardName);
                }
                army.addCombatant(newCombatant, 0);
            }
            return army;
        }


        //PLAYER VS BOT MODE
//    public void playerVsBotMode() {
//
//        System.out.println("""
//                You have entered the Player VS Bot gaming mode.
//
//                """);
//
//        String input;
//        int armySize = 0;
//        do {
//            System.out.println("""
//                    Please write down the number of combatants that will compose your army:
//
//                    =====================================
//                    BACK - go back to Game Mode Selection
//                    =====================================
//                    """);
//
//            input = prompt.nextLine().trim().toLowerCase();
//
//            if (input.matches("^\\d+$")) {
//                armySize = Integer.parseInt(input);
//                if (armySize <= Army.MAX_ARMY_SIZE && armySize >= Army.MIN_ARMY_SIZE) {
//                    break;
//                }
//            }
//            System.out.println("Please try a valid size");
//
//        } while (input.equals("back"));
//
//
//        String UserArmyName = null;
//        String BotArmyName = null;
//
//        System.out.println("Please choose a name for each army");
//        System.out.println(" Your Army name:");
//        UserArmyName = prompt.nextLine();
//        System.out.println(" Bot Army name:");
//        BotArmyName = prompt.nextLine();
//        createArmiesPlayerVsBot(UserArmyName, BotArmyName, armySize);
//
//
//        switch (input) {
//            case "1": {
//                System.out.println("Please choose a name for your lightside army");
//                System.out.println(" Your Army name:");
//                UserArmyName = prompt.nextLine();
//                BotArmyName = "BotArmy";
//                createArmiesPlayerVsBot(UserArmyName, BotArmyName, armySize);
//            }
//            case "2": {
//                System.out.println("Please choose a name for your darkside army");
//                System.out.println("DarkSide Army name:");
//                BotArmyName = prompt.nextLine();
//                UserArmyName = "LightSide default";
//                createArmiesPlayerVsBot(UserArmyName, BotArmyName, armySize);
//            }
//            default: printWithColor("Command not recognized!", ConsoleColors.RED);
//        }
//
//
//
//
//    } // finishes with the calling of createArmies()

//    private void createArmiesPlayerVsBot(String lightSideArmyName, String darkSideArmyName, int armySize) {
//
//        System.out.println("Construction of armies...\n");
//
//        Army lightSideArmy = null;
//        Army darkSideArmy = null;
//
//        if(lightSideArmyName.equals("LightSide default")){
//
//            System.out.println("Construction of " + darkSideArmyName + "army:");
//            darkSideArmy = createArmy(darkSideArmyName, armySize);
//
//            System.out.println("Construction of " + lightSideArmyName + "army:");
//            lightSideArmy = createRandomArmy(lightSideArmyName,  armySize);
//
//        }else{
//            System.out.println("Construction of " + lightSideArmyName + "army:");
//            lightSideArmy = createArmy(lightSideArmyName, armySize);
//
//            System.out.println("Construction of " + darkSideArmyName + "army:");
//            darkSideArmy = createRandomArmy(darkSideArmyName,  armySize);
//        }
//
//        System.out.println("LightSide Army stats:");
//        lightSideArmy.getArmyStatus();
//
//        System.out.println("DarkSide Army stats:");
//        darkSideArmy.getArmyStatus();
//
//        var okWithCreatedArmies = """
//                Are you OK with the created armies?
//                1) Yes, let's go to WAR!
//                2) No, build new armies again
//                3) No, go back to game mode selection
//                """;
//        System.out.println(okWithCreatedArmies);
//        String input;
//        input = prompt.nextLine().trim().toLowerCase();
//        switch (input) {
//            case "1" -> startWar();
//            case "2" -> createArmiesPlayerVsBot(lightSideArmyName, darkSideArmyName, armySize);
//            case "3" -> chooseGameMode();
//            default -> printWithColor("Command not recognized!", ConsoleColors.RED);
//        }
//    } // finishes with the calling of startWar()


        private Warrior createWarrior (String name,int hp, boolean isAlive, int stamina, int strength){
            return null;
        }

        private Wizard createWizard (String name,int hp, boolean isAlive, int mana, int intelligence){
            return null;
        }

        private Army createArmy () {
            // LOOP until army size is reached
            // Create and append combatants to the army

            return null;
        }


        private void addCombatants (Army army, Combatant combatant,int num_of_clones)
        { // method to add a combatant multiple times, used in PlayervsPlayer  branch
            army.addCombatant(combatant, num_of_clones);

        }


        private void startWar () {
        }

        public static void printWithColor (String text, String color){
            System.out.println(color + text + ConsoleColors.RESET);
        }


        private void start () {
            // Start asking to creat a new army (Manual or Random)
            // For each army ask to create warrior or wizzard, as many times as army's size
            // Finish when 2 armies with all combatants have been defined

            // CLOSE scanner when finished!
        }
    }

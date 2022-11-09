package services;
import model.Army;
import model.Warrior;
import model.Wizard;

import java.io.FileNotFoundException;
import java.nio.file.LinkPermission;
import java.util.*;

import model.Army;
import model.Combatant;
import model.Warrior;
import model.Wizard;
import repository.RepositoryCsv;
import utils.ConsoleColors;
import utils.ConsolePrints;
import utils.Tools;

import java.util.Scanner;

import static utils.ConsoleColors.printWithColor;

public class InputService {

    public static final String EXIT = "exit";
    public static final String BACK = "back";
    public static final String START = "start";
    public static final String PLAYER = "player";
    public static final String BOT = "bot";
    public static final String YES = "yes";
    public static final String NO = "no";
    public static final String IMPORT = "import";
    public static final String RANDOM = "random";
    public static final String HANDMADE = "handmade";
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

    public void close(){
        this.prompt.close();
    }

    // Each of these methods will ask for the needed attributes,
    // store them in variables and instantiate the new object with them

    public String askWhoIsArmyControlledBy(){
        String title = "First of all, tell me who will be this army lead by?";
        String[] options = {
                option(PLAYER,"PLAYER - the player will decide the combatants to fight every battle"),
                option(BOT,"BOT - the machine will decide the combatants to fight every battle"),
        };

        return askMenu(title, false, options);
    }
    public String askTypeArmyCreation() {
        String title = "How do you wish to create this army?";
        String[] options = {
                option(IMPORT,"Import army from personal .csv file"),
                option(RANDOM,"Create a random army"),
                option(HANDMADE,"Make a customized handmade army")
        };
        return askMenu(title, false, options);
    }
    public String askArmyName() {
        String title = "Please tell us how do you wish to call this army:";
        return askMenu(title, false);
    }

    public String askWichArmyImport(RepositoryCsv repo) throws FileNotFoundException {
        String title = "Please write down the name of the .csv file where the army to import is.";

        var armiesImport = repo.listArmiesImport();
        var options = new ArrayList<String>();
        for (String armyCsvName : armiesImport.keySet()) {
            options.add(option(armyCsvName, armiesImport.get(armyCsvName)));
        };
        return askMenu(title, false, options.toArray(new String[]{}));
    }

    public String askArmySize() {
        String input;
        var title = "Please write down the number of combatants that will compose the army:";
        var menu = buildMenu(title, false);
        do {
            System.out.println(menu);
            input = getInput();
            if (isValidArmySize(input)) {
                return input;
            }
            printWithColor("Please try a valid size (between 1 and 10)", ConsoleColors.RED);
        } while (true);
    }

    public String okWithThisArmy(Army army) {
        ConsolePrints.newArmyStatus(army.getName());
        army.printStatus();
        String title = "Are you ok with this army? ";
        String[] options = {
                option(YES,"Yes!"),
                option(START,"No, go back to army mode selection")
        };
        return askMenu(title, false, options);
    }

    public String askPlayAgain() {
        String title = "Do you want to play again?";
        String[] options = {
                option(YES,"Yes!"),
                option(EXIT,"No, enough blood for today")
        };
        return askMenu(title, false, options);
    }

    public void askExportArmy(Army army, RepositoryCsv repo) throws FileNotFoundException {
        String title = "Would you like to export this army for future games?";
        String[] options = {
                option(YES,"Of course!"),
                option(NO,"No, I don't like it that much")
        };

        String answer = askMenu(title, false, options);
        if (answer.equals(YES)){
            String fileName = askArmyExportFileName(repo);
            try {
                repo.exportArmy(fileName, army);
            } catch (Exception e) {
                System.err.println("ERROR: couldn't export army");
            }
        }
    }

    private String askArmyExportFileName(RepositoryCsv repo) throws FileNotFoundException {
        String title = "Please provide a valid file name (without '.csv')";
        String input;
        String menu = buildMenu(title, false);
        do {
            System.out.println(menu);
            input = getInput().trim();
            if (isValidArmyFileName(input, repo)) {
                return input;
            }
            printWithColor("Please enter a valid file name", ConsoleColors.RED);
        } while (true);
    }

    private boolean askOverwriteFile(String fileName){
        String title = "Army %s file already exists, do you want to overwrite it?". formatted(fileName);
        String[] options = {
                option(YES,"Yes!"),
                option(NO,"No")
        };
        return askMenu(title, false, options).equals(YES);
    }

    public String askNextCombatant(Army army){
        String title = "Please choose the next combatant to fight for: ";
        var combatants = army.getCombatants();
        var options = new ArrayList<String>();
        for (Combatant combatant : combatants) {
            options.add(option(String.valueOf(combatant.getId()), Tools.combatantStatus(combatant)));
        }

        return askMenu(title, false, options.toArray(new String[]{}));
    }

    /**
     * Builds a menu with the given title and (optional) options, and returns only a valid user answer
     * @param title the menu title or introduction
     * @param showBack if true will show a 'back' option
     * @param options array of different options to let the user choose between them
     * @return users valid answer as a response key
     */
    private String askMenu(String title, boolean showBack, String... options){
        var menu = buildMenu(title, showBack, options);
        var optionsMap = getOptionsMap(options);
        do {
            System.out.println(menu);
            String input = getInput();

            if (optionsMap.containsKey(input)) return optionsMap.get(input);
            if (options.length == 0) return input;
            printWithColor("Command not recognized!", ConsoleColors.RED);
        }while (true);
    }

    private String getInput(){
        String input = prompt.nextLine().trim();
        if (input.equals(EXIT)) {
            ConsolePrints.exitGame();
            System.exit(0);
        }
        return input;
    }

    private boolean isValidArmyFileName(String fileName, RepositoryCsv repo) throws FileNotFoundException {
        if (!fileName.matches("^\\w+$")) return false;
        fileName += ".csv";

        var armiesFiles = repo.listArmiesImport().keySet();
        if (armiesFiles.contains(fileName)) {
            return askOverwriteFile(fileName);
        }
        return true;
    }

    // ======== STATIC METHODS ==================
    private static ArrayList<String> getListStrNumbers(int n){
        var list = new ArrayList<String>();
        for (int i = 0; i < n; i++) {
            list.add(String.valueOf(i+1));
        }
        return list;
    }

    private static boolean isValidArmySize(String input) {
        var armySizeInt = 0;
        if (input.matches("^\\d+$")) {
            armySizeInt = Integer.parseInt(input);
            return armySizeInt <= Army.MAX_ARMY_SIZE && armySizeInt >= Army.MIN_ARMY_SIZE;
        }
        return false;
    }

    private static String buildMenu(String title, boolean showBack, String ... options) {
        StringBuilder menu = new StringBuilder(String.format("%s\n", title));

        for (int i = 0; i < options.length; i++) {
            menu.append(i+1).append(") ").append(options[i].split("\\|")[1]).append("\n");
        }
        if (showBack) menu.append(BACK_STRING);
        menu.append(EXIT_STRING);
        return menu.toString();
    }

    /**
     * Given an array of options to be printed to the user,
     * generates a map from the user's input to the option keys to return
     * @param options String array of
     * @return map of input answers to option keys
     */
    private static HashMap<String,String> getOptionsMap(String[] options) {
        var optionsMap = new HashMap<String,String>();
        for (int i = 0; i < options.length; i++) {
            optionsMap.put(String.valueOf(i+1), options[i].split("\\|")[0]);
        }
        optionsMap.put(BACK, BACK);
        return optionsMap;
    }

    private static String option(String key, String text){return ("%s|"+ text).formatted(key);}



}

package services;
import model.Army;

import java.io.FileNotFoundException;
import java.rmi.ServerError;
import java.util.*;

import model.Combatant;
import model.Warrior;
import model.Wizard;
import repository.RepositoryCsv;
import utils.Colors;
import utils.Prints;
import utils.Tools;

import java.util.Scanner;

import static utils.Colors.printWithColor;
import static utils.Prints.clearConsole;
import static utils.Prints.warPreparation;

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
    private static final String BACK_STRING = """
            ====================            
            BACK - go back
            ====================""";

    private final Scanner prompt;
    private final RepositoryCsv repo;

    private int armiesCreated;

    public InputService(RepositoryCsv repo) {
        this.prompt = new Scanner(System.in);
        this.repo = repo;
    }

    public void close(){
        this.prompt.close();
    }

    public boolean askContinue(boolean skip, boolean addSkipOption, String ... prints) {
        if (skip) return true;
        System.out.println(String.join("\n", prints));
        String strToPrint = Colors.RESET + "Press any key... ";
        if (addSkipOption) strToPrint += "('skip' to finish battle)";
        for (char c : strToPrint.toCharArray()) {
            try {Thread.sleep(20);} catch (Exception ignore) {}
            System.out.print(c);
        }
        System.out.println();
        String input = getInput();
        return input.equals("skip");
    }

    // Each of these methods will ask for the needed attributes,
    // store them in variables and instantiate the new object with them

    public String askWhoIsArmyControlledBy(){
        clearConsole(warPreparation( armiesCreated + 1));
        String title = "First of all, tell me who will be this army lead by?";
        String[] options = {
                option(PLAYER,"PLAYER - the player will decide the combatants to fight every battle"),
                option(BOT,"BOT - the machine will decide the combatants to fight every battle"),
        };

        return askMenu(title, false, options);
    }
    public String askTypeArmyCreation() {
        clearConsole(warPreparation( armiesCreated + 1));
        String title = "How do you wish to create this army?";
        String[] options = {
                option(IMPORT,"IMPORT - Import army from personal .csv file"),
                option(RANDOM,"RANDOM - Create a random army"),
                option(HANDMADE,"HANDMADE - Make a customized handmade army")
        };
        return askMenu(title, false, options);
    }
    public String askArmyName() {
        clearConsole(warPreparation( armiesCreated + 1));
        String title = "Please tell us how do you wish to call this army:";
        return askMenu(title, false);
    }

    public String askWhichArmyImport() throws FileNotFoundException {
        clearConsole(warPreparation( armiesCreated + 1));
        String title = "Please select which army file you want to import:";

        var armiesImport = repo.listArmiesImport();
        var options = new ArrayList<String>();
        for (String armyCsvName : armiesImport.keySet()) {
            options.add(option(armyCsvName, armiesImport.get(armyCsvName)));
        };
        return askMenu(title, false, options.toArray(new String[]{}));
    }

    public String  askWhichCombatantImport() throws FileNotFoundException {
        clearConsole(warPreparation( armiesCreated + 1));
        String title = "Please select from which template you want to create the next combatant:";

        var combatantTemplates = repo.listWarriorTemplates();
        combatantTemplates.putAll(repo.listWizardTemplates());

        var options = new ArrayList<String>();
        for (String combatantName : combatantTemplates.keySet()) {
            options.add(option(combatantName, combatantTemplates.get(combatantName)));
        };
        return askMenu(title, false, options.toArray(new String[]{}));
    }

    public String askArmySize() {
        clearConsole(warPreparation( armiesCreated + 1));
        String input;
        var title = "Please write down the number of combatants that will compose the army:";
        var menu = buildMenu(title, false);
        do {
            printWithColor(menu, Colors.WHITE_BRIGHT);
            input = getInput();
            if (isValidNum(input, Army.MIN_ARMY_SIZE, Army.MAX_ARMY_SIZE)) {
                return input;
            }
            printWithColor("Please try a valid size (between 1 and 10)", Colors.RED);
        } while (true);
    }

    public String okWithThisArmy(Army army) {
        clearConsole(warPreparation( armiesCreated + 1));
        Prints.newArmyStatus(army.getName());
        army.printStatus();
        String title = "Are you ok with this army? ";
        String[] options = {
                option(YES,"Yes!"),
                option(START,"No, go back to army mode selection")
        };
        return askMenu(title, false, options);
    }

    public String askPlayAgain() {
        Prints.clearConsole("");
        String title = "Do you want to play again?";
        String[] options = {
                option(YES,"Yes!"),
                option(EXIT,"No, enough blood for today")
        };
        return askMenu(title, false, options);
    }

    public void askExportCombatant(Combatant combatant) {
        clearConsole(warPreparation( armiesCreated + 1));
        System.out.println(Tools.combatantStatus(combatant) + "\n");
        String title = "Would you like to export this combatant as a template?";
        String[] options = {
                option(YES,"Sure!"),
                option(NO,"Not now")
        };

        String answer = askMenu(title, false, options);
        try {
            if (answer.equals(YES)) repo.exportCombatant(combatant);
        } catch (Exception e) {
            System.err.println("ERROR: could not export combatant");
        }
    }

    public void askExportArmy(Army army) {
        clearConsole(warPreparation( armiesCreated + 1));
        String title = "Would you like to export this army for future games?";
        String[] options = {
                option(YES,"Of course!"),
                option(NO,"No, I don't like it that much")
        };

        String answer = askMenu(title, false, options);
        if (answer.equals(YES)){
            String fileName = askArmyExportFileName();
            try {
                repo.exportArmy(fileName, army);
            } catch (Exception e) {
                System.err.println("ERROR: couldn't export army");
            }
        }
    }

    private String askArmyExportFileName() {
        clearConsole(warPreparation(armiesCreated + 1));
        String title = "Please provide a valid file name (without '.csv')";
        String input;
        String menu = buildMenu(title, false);
        do {
            printWithColor(menu, Colors.WHITE_BRIGHT);
            input = getInput().trim();
            if (isValidArmyFileName(input)) {
                return input + ".csv";
            }
            printWithColor("Please enter a valid file name", Colors.RED);
        } while (true);
    }

    private boolean askOverwriteFile(String fileName){
        clearConsole(warPreparation( armiesCreated + 1));
        String title = "Army %s file already exists, do you want to overwrite it?". formatted(fileName);
        String[] options = {
                option(YES,"Yes!"),
                option(NO,"No")
        };
        return askMenu(title, false, options).equals(YES);
    }

    public String askCombatantCreateMode(int size, Army army){
        clearConsole(warPreparation( armiesCreated + 1));
        System.out.printf("(%s remaining combatants)\n", size - army.getSize());
        army.printStatus();
        String title = "How do you want to create the next combatant?";
        String[] options = {
                option(IMPORT,"Import it from template"),
                option(HANDMADE,"Create it manually"),
        };
        return askMenu(title, false, options);
    }

    public String askCombatantType(Army army){
        clearConsole(warPreparation( armiesCreated + 1));
        army.printStatus();
        String title = "What type of combatant do you want to create?";
        String[] options = {
                option(RepositoryCsv.WARRIOR_TYPE,"WARRIOR (X)"),
                option(RepositoryCsv.WIZARD_TYPE,"WIZARD (O)"),
        };
        return askMenu(title, false, options);
    }

    public Combatant askCombatantAttributes(String type, Army army) throws Exception {
        clearConsole(warPreparation( armiesCreated + 1));
        army.printStatus();
        System.out.println(Colors.WHITE_BRIGHT + "Please, set your %s attributes:\n".formatted(type.toLowerCase()));
        String name = askCombatantName();

        if (type.equals(RepositoryCsv.WARRIOR_TYPE)) {
            int hp = askNumber("* HP (%s-%s):", 100, 200);
            int stamina = askNumber("* STAMINA (%s-%s):", 10, 50);
            int strength = askNumber("* STRENGTH (%s-%s):", 1, 10);
            return new Warrior(name, hp, true, stamina, strength, repo);
        } else {
            int hp = askNumber("* HP (%s-%s):", 50, 100);
            int mana = askNumber("* MANA (%s-%s):", 10, 50);
            int intelligence = askNumber("* INTELLIGENCE (%s-%s):", 1, 50);
            return new Wizard(name, hp, true, mana, intelligence, repo);
        }
    }
    public int askNumber(String title, int min, int max) {
        do {
            System.out.println(Colors.WHITE_BRIGHT + title.formatted(min, max));
            String input = getInput();
            if (isValidNum(input, min, max)) {
                return Integer.parseInt(input);
            }
            printWithColor("Please introduce a valid number (between %s and %s)".formatted(min, max), Colors.RED);
        } while (true);
    }

    public String askCombatantName() {
        String input;
        do {
            System.out.println(Colors.WHITE_BRIGHT +"* NAME:");
            input = getInput().trim();
            if (input.matches("^(\\w|\\s|-)+$")) {
                return input;
            }
            printWithColor("Please enter a valid combatant name", Colors.RED);
        } while (true);
    }

    public Combatant askCreateHandmadeCombatant(Army army) {
        String type = askCombatantType(army);
        try {
            if (type.equals(RepositoryCsv.WARRIOR_TYPE)) {
                return askCombatantAttributes(RepositoryCsv.WARRIOR_TYPE, army);
            } else {
                return askCombatantAttributes(RepositoryCsv.WIZARD_TYPE, army);
            }
        } catch (Exception e) {
            System.err.println("ERROR: could not create combatant, try importing one");
        }
        return null;
    }

    public String askNextCombatant(Army army, String color){
        String title = "Please choose the next combatant to fight for %s%s: ".formatted(color, army.getName())
                + Colors.WHITE_BRIGHT;
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
            printWithColor(menu, Colors.WHITE_BRIGHT);
            String input = getInput();

            if (optionsMap.containsKey(input)) return optionsMap.get(input);
            if (options.length == 0) return input;
            printWithColor("Command not recognized!", Colors.RED);
        }while (true);
    }

    private String getInput(){
        String input = prompt.nextLine().trim();
        if (input.equals(EXIT)) {
            Prints.exitGame();
            System.exit(0);
        }
        return input;
    }

    private boolean isValidArmyFileName(String fileName) {
        if (!fileName.matches("^\\w+$")) return false;
        fileName += ".csv";

        try {
            var armiesFiles = repo.listArmiesImport().keySet();
            if (armiesFiles.contains(fileName)) {
                return askOverwriteFile(fileName);
            }
        } catch (Exception e) {
            System.err.printf("ERROR: could not find directory %s\n", RepositoryCsv.ARMY_CATALOG_PATH);
        }
        return true;
    }

    public int getArmiesCreated() {
        return armiesCreated;
    }

    public void setArmiesCreated(int armiesCreated) {
        this.armiesCreated = armiesCreated;
    }

    // ======== STATIC METHODS ==================

    private static boolean isValidNum(String input, int min, int max) {
        var armySizeInt = 0;
        if (input.matches("^\\d+$")) {
            armySizeInt = Integer.parseInt(input);
            return armySizeInt <= max && armySizeInt >= min;
        }
        return false;
    }

    private static String buildMenu(String title, boolean showBack, String ... options) {
        StringBuilder menu = new StringBuilder(String.format("%s\n", title));
        if (options.length > 0) {
            menu.append("\n");
            for (int i = 0; i < options.length; i++) {
                menu.append("\s\s").append(i + 1).append(") ").append(options[i].split("\\|")[1]).append("\n");
            }
        }
        if (showBack) menu.append(BACK_STRING);
        return menu + Colors.RESET + "\n('exit' = finish game)\n";
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

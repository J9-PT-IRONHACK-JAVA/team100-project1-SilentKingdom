package utils;

import model.Combatant;
import services.InputService;

public class ConsolePrints {

    public static void gameWelcome(){
        clearConsole("");
        String text = ConsoleColors.CYAN +
                 """
                 _    _      _                            _            \s
                | |  | |    | |                          | |           \s
                | |  | | ___| | ___ ___  _ __ ___   ___  | |_ ___      \s
                | |/\\| |/ _ | |/ __/ _ \\| '_ ` _ \\ / _ \\ | __/ _ \\     \s
                \\  /\\  |  __| | (_| (_) | | | | | |  __/ | || (_) _ _ _\s
                 \\/  \\/ \\___|_|\\___\\___/|_| |_| |_|\\___|  \\__\\___(_(_(_)
                                                                       \s
                                                                       \s
                                                 ======================================================================       
                                                   _____ _ _            _     _  ___                 _                \s
                                                  / ____(_| |          | |   | |/ (_)               | |               \s
                                                 | (___  _| | ___ _ __ | |_  | ' / _ _ __   __ _  __| | ___  _ __ ___ \s
                                                  \\___ \\| | |/ _ | '_ \\| __| |  < | | '_ \\ / _` |/ _` |/ _ \\| '_ ` _ \\\s
                                                  ____) | | |  __| | | | |_  | . \\| | | | | (_| | (_| | (_) | | | | | |
                                                 |_____/|_|_|\\___|_| |_|\\__| |_|\\_|_|_| |_|\\__, |\\__,_|\\___/|_| |_| |_|
                                                                                            __/ |                     \s
                                                                                           |___/                      \s                        
                                                 ======================================================================
               
                """;
        printSlow(text, 100);
    }

    public static void ConstructionOfRandomArmy() {
        System.out.println("Construction of random army...");
    }

    public static void newArmyStatus(String armyName) {
        System.out.println(armyName + " (NEW ARMY!):");
    }

    public static void exitGame() {
        clearConsole("");
        System.out.println("""
                Ok, see you soon!

                """);
    }


    public static void warBegins() {
        clearConsole("");
        String text = ConsoleColors.RED +
               """
                                                                                         )                       (                      (       ) (                      \s
                                                                                *   ) ( /(       (  (      (     )\\ )     (      (      )\\ ) ( /( )\\ )                   \s
                                                                              ` )  /( )\\())(     )\\))(   ' )\\   (()/(   ( )\\ (   )\\ )  (()/( )\\()(()/(                   \s
                                                                               ( )(_)((_)\\ )\\   ((_)()\\ ((((_)(  /(_))  )((_))\\ (()/(   /(_)((_)\\ /(_))                  \s
                                                                              (_(_()) _((_((_)  _(())\\_)()\\ _ )\\(_))   ((_)_((_) /(_))_(_))  _((_(_))                    \s
                                                             ___ ___ ___ ___  |_   _|| || | __| \\ \\((_)/ (_)_\\(_| _ \\   | _ | __(_)) __|_ _|| \\| / __|   ___ ___ ___ ___ \s
                                                            |___|___|___|___|   | |  | __ | _|   \\ \\/\\/ / / _ \\ |   /   | _ | _|  | (_ || | | .` \\__ \\  |___|___|___|___|\s
                                                            |___|___|___|___|   |_|  |_||_|___|   \\_/\\_/ /_/ \\_\\|_|_\\   |___|___|  \\___|___||_|\\_|___/  |___|___|___|___|\s
                                          
                """ + ConsoleColors.RESET;
        printSlow(text, 100);
    }

    public static String battleHeader(int n, String warStats){
        return ConsoleColors.BLACK_BACKGROUND + ConsoleColors.RED_BOLD +
                "BATTLE # %s".formatted(n) + ConsoleColors.RESET +"\n" +
                ConsoleColors.CYAN_BOLD + warStats + ConsoleColors.RESET;
    }

    public static void printSlow(String text, int s) {
        for (String line: text.split("\\n")) {
            Tools.sleep(s);
            System.out.println(line);
        }
    }

    public static void clearConsole(String header) {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println(header);
    }

    public static String warPreparation(int n) {
        // Font name: small
        return  ConsoleColors.PURPLE +
                """         
                    _   ___ __  __ ___ ___ ___   ___ ___ _____ _   _ ___\s
                   /_\\ | _ \\  \\/  |_ _| __/ __| / __| __|_   _| | | | _ \\
                  / _ \\|   / |\\/| || || _|\\__ \\ \\__ \\ _|  | | | |_| |  _/
                 /_/ \\_\\_|_\\_|  |_|___|___|___/ |___/___| |_|  \\___/|_| \s
                                                                        \s
                """ + ConsoleColors.RESET +
                ConsoleColors.BLACK_BACKGROUND + ConsoleColors.YELLOW_BOLD +
                "ARMY #%s CREATION".formatted(n) + ConsoleColors.RESET +"\n"
                ;
    }

    public static void selectedCombatants(Combatant light, Combatant dark) {
        String text = ConsoleColors.WHITE_BRIGHT + "The following combatants will fight!\n" +
                ConsoleColors.YELLOW_BOLD + light.getName() + " - " + Tools.combatantStatus(light) + "\n" +
                ConsoleColors.PURPLE_BOLD + dark.getName() + " - " + Tools.combatantStatus(dark) + "\n" +
                ConsoleColors.WHITE_BRIGHT + "Fight until death!!";
        System.out.println(text);
    }

    public static void battleResult(Combatant winner, Combatant defeated, boolean light) {
        String color = (light) ? ConsoleColors.YELLOW_BOLD : ConsoleColors.PURPLE_BOLD;
        ConsoleColors.printWithColor("%s WINS the battle!".formatted(winner.getName()), color);
        ConsoleColors.printWithColor(
                "%s combatant DEFEATED: %s\n\n".formatted(
                        defeated.getArmyName(),
                        defeated.getName()
                ), color);
    }
}


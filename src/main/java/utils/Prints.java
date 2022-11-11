package utils;

import model.Army;
import model.Combatant;
import model.Warrior;


import java.io.IOException;
import java.util.ArrayList;

public class Prints {

    public static void gameWelcome(){
        clearConsole("");
        String text = Colors.CYAN +
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
        System.out.println(Colors.CYAN +
                """
                          
                          ______ __                   __            ____                           \s
                         /_  __// /_   ____ _ ____   / /__ _____   / __/____   _____               \s
                          / /  / __ \\ / __ `// __ \\ / //_// ___/  / /_ / __ \\ / ___/               \s
                         / /  / / / // /_/ // / / // ,<  (__  )  / __// /_/ // /                   \s
                        /_/  /_/ /_/ \\__,_//_/ /_//_/|_|/____/  /_/   \\____//_/                    \s
                                    __               _                                             \s
                            ____   / /____ _ __  __ (_)____   ____ _                               \s
                           / __ \\ / // __ `// / / // // __ \\ / __ `/                               \s
                          / /_/ // // /_/ // /_/ // // / / // /_/ /_                               \s
                         / .___//_/ \\__,_/ \\__, //_//_/ /_/ \\__, /( )                              \s
                        /_/               /____/           /____/ |/                              __
                           _____ ___   ___     __  __ ____   __  __   _____ ____   ____   ____   / /
                          / ___// _ \\ / _ \\   / / / // __ \\ / / / /  / ___// __ \\ / __ \\ / __ \\ / /\s
                         (__  )/  __//  __/  / /_/ // /_/ // /_/ /  (__  )/ /_/ // /_/ // / / //_/ \s
                        /____/ \\___/ \\___/   \\__, / \\____/ \\__,_/  /____/ \\____/ \\____//_/ /_/(_)  \s
                                            /____/                                                 \s
                        """ + Colors.RESET);
    }


    public static void warBegins() {
        clearConsole("");
        String text = Colors.RED +
               """
                                             
                                             )                       (                      (       ) (                      \s
                                    *   ) ( /(       (  (      (     )\\ )     (      (      )\\ ) ( /( )\\ )                   \s
                                  ` )  /( )\\())(     )\\))(   ' )\\   (()/(   ( )\\ (   )\\ )  (()/( )\\()(()/(                   \s
                                   ( )(_)((_)\\ )\\   ((_)()\\ ((((_)(  /(_))  )((_))\\ (()/(   /(_)((_)\\ /(_))                  \s
                                  (_(_()) _((_((_)  _(())\\_)()\\ _ )\\(_))   ((_)_((_) /(_))_(_))  _((_(_))                    \s
                 ___ ___ ___ ___  |_   _|| || | __| \\ \\((_)/ (_)_\\(_| _ \\   | _ | __(_)) __|_ _|| \\| / __|   ___ ___ ___ ___ \s
                |___|___|___|___|   | |  | __ | _|   \\ \\/\\/ / / _ \\ |   /   | _ | _|  | (_ || | | .` \\__ \\  |___|___|___|___|\s
                |___|___|___|___|   |_|  |_||_|___|   \\_/\\_/ /_/ \\_\\|_|_\\   |___|___|  \\___|___||_|\\_|___/  |___|___|___|___|\s
                                          
                """ + Colors.RESET;
        printSlow(text, 100);
    }

    public static String battleHeader(int n, String warStatus){
        String title = Colors.RED_BOLD + """
                
                __      __   _     ___\s
                \\ \\    / /  /_\\   | _ \\
                 \\ \\/\\/ /  / _ \\  |   /
                  \\_/\\_/  /_/ \\_\\ |_|_\\
                                """;
        return  title + "\n" + Colors.RESET
                + warStatus + Colors.BLACK_BACKGROUND + Colors.RED_BOLD
                + "BATTLE # %s".formatted(n) + " ".repeat(50) + Colors.RESET +"\n";
    }

    public static void printSlow(String text, int s) {
        for (String line: text.split("\\n")) {
            Tools.sleep(s);
            System.out.println(line);
        }
    }

    public static void clearConsole(String header) {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else {
                System.out.print("\033\143");
                System.out.flush();
            }
        } catch (IOException | InterruptedException ex) {exitGame();}

        System.out.println(header);
    }

    public static String warPreparation(int n) {
        // Font name: small
        String title = Colors.GREEN +
                """  
                           
                    _   ___ __  __ ___ ___ ___   ___ ___ _____ _   _ ___\s
                   /_\\ | _ \\  \\/  |_ _| __/ __| / __| __|_   _| | | | _ \\
                  / _ \\|   / |\\/| || || _|\\__ \\ \\__ \\ _|  | | | |_| |  _/
                 /_/ \\_\\_|_\\_|  |_|___|___|___/ |___/___| |_|  \\___/|_| \s
                                                                        \s
                """ + Colors.RESET;

        String subtitle = Colors.BLACK_BACKGROUND +
                ((n == 1)? Colors.YELLOW_BOLD : Colors.PURPLE_BOLD) +
                "ARMY #%s CREATION".formatted(n) + Colors.RESET +"\n";

        return  title + subtitle;
    }

    public static String combatantsStatus(Combatant light, Combatant dark) {
        return Colors.YELLOW_BOLD  + " * " + Tools.combatantStatus(light) + "\n" +
                Colors.PURPLE_BOLD + " * " + Tools.combatantStatus(dark) + "\n" +
                Colors.RESET;
    }

    public static void battleResult(Combatant winner, Combatant defeated, boolean light) {
        String color = (light) ? Colors.YELLOW_BOLD : Colors.PURPLE_BOLD;
        Colors.printWithColor("\n%s WINS the battle!".formatted(winner.getName()), color);
        Colors.printWithColor(
                "%s combatant DEFEATED: %s\n".formatted(
                        defeated.getArmyName(),
                        defeated.getName()
                ), color);
    }

    public static String getArmyGrid(ArrayList<Combatant> army) {
        StringBuilder finalGameBoard = new StringBuilder();
        for (Combatant combatant : army) {
            finalGameBoard.append(" ").append(getCombatantGrid(combatant));
        }
        return finalGameBoard.append(Colors.RESET).toString();
    }

    private static String getCombatantGrid(Combatant combatant) {
        var warriorIcon = " X ";
        var wizardIcon = " O ";

        String combatantIcon = ((combatant instanceof Warrior) ? warriorIcon : wizardIcon)
               + Colors.RESET;

        if (combatant.isAlive() && combatant.getHp() >= 50) {
            return Colors.GREEN_BACKGROUND + Colors.WHITE_BOLD + combatantIcon;
        }else if (combatant.isAlive() && combatant.getHp() >= 20) {
            return Colors.YELLOW_BACKGROUND + Colors.WHITE_BOLD + combatantIcon;
        } else if (combatant.isAlive()) {
            return Colors.RED_BACKGROUND + Colors.WHITE_BOLD + combatantIcon;
        } else {
            return Colors.BLACK_BACKGROUND + Colors.WHITE_BOLD + combatantIcon;
        }
    }

    public static void winner(Army army, String color, ArrayList<Combatant> graveyard){
        clearConsole("");
        String header = """
                
                __      __  ___   _  _   _  _   ___   ___     ___   ___              \s
                \\ \\    / / |_ _| | \\| | | \\| | | __| | _ \\   |_ _| / __|             \s
                 \\ \\/\\/ /   | |  | .` | | .` | | _|  |   /    | |  \\__ \\    _   _   _\s
                  \\_/\\_/   |___| |_|\\_| |_|\\_| |___| |_|_\\   |___| |___/   (_) (_) (_)
                """ + "\n";


        String winnerStats = (army != null) ? army.getName().toUpperCase() + "\n\n"
                + army.getStatus() : "NO ONE, IT'S A DRAW!";

        String winnerStr = color + header + winnerStats + Colors.RESET + "\n";
        String graveyardStr = getGraveyard(graveyard);

        printSlow(winnerStr + graveyardStr, 100);
    }

    public static String getGraveyard(ArrayList<Combatant> graveyard) {
        StringBuilder text = new StringBuilder("\n GRAVEYARD \n\n" + Colors.RESET);
        for (Combatant combatant: graveyard) {
            text.append(" - ").append(Tools.combatantStatus(combatant)).append("\n");
        }
        return (text + "\n" + " R. I. P. " + "\n\n");
    }
}


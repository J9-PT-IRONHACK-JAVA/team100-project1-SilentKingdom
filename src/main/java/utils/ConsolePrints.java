package utils;

public class ConsolePrints {


    public static void gameWelcome(){
        System.out.println("""
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
                """);
    }
    public static void BotVsBotSelection(){
        System.out.println("""
                You have entered the Bot VS Bot gaming mode.

                """);
    }
    public static void PlayerVsBotSelection() {
        System.out.println("""
                You have entered the Player VS Bot gaming mode.

                """);
    }
    public static void PlayerVsPlayerSelection() {
        System.out.println("""
                You have entered the Player VS Player gaming mode.

                """);
    }
    public static void LetsChooseArmySize() {
        System.out.println("Let's choose the number of Combatants that will " +
                "compose both armies");
    }
    public static void LetsChooseArmySize(String armyName) {
        System.out.println("Let's choose the number of Combatants for " + armyName);
    }
    public static void ConstructionOfRandomArmy() {
        System.out.println("Construction of random army...");
    }
    public static void letsSetArmies() {
        System.out.println("Let's first set the armies that will fight!");
    }
    public static void startCreateArmy(int n) {
        System.out.printf("ARMY #%s CREATION\n\n", n);
    }

    public static void newArmyStatus(String armyName) {
        System.out.println(armyName + " (NEW ARMY!):");
    }

    public static void exitGame() {
        System.out.println("""
                Ok, see you soon!

                """);
    }


    public static void warBegins() {
        System.out.println("""
                                                                                         )                       (                      (       ) (                      \s
                                                                                *   ) ( /(       (  (      (     )\\ )     (      (      )\\ ) ( /( )\\ )                   \s
                                                                              ` )  /( )\\())(     )\\))(   ' )\\   (()/(   ( )\\ (   )\\ )  (()/( )\\()(()/(                   \s
                                                                               ( )(_)((_)\\ )\\   ((_)()\\ ((((_)(  /(_))  )((_))\\ (()/(   /(_)((_)\\ /(_))                  \s
                                                                              (_(_()) _((_((_)  _(())\\_)()\\ _ )\\(_))   ((_)_((_) /(_))_(_))  _((_(_))                    \s
                                                             ___ ___ ___ ___  |_   _|| || | __| \\ \\((_)/ (_)_\\(_| _ \\   | _ | __(_)) __|_ _|| \\| / __|   ___ ___ ___ ___ \s
                                                            |___|___|___|___|   | |  | __ | _|   \\ \\/\\/ / / _ \\ |   /   | _ | _|  | (_ || | | .` \\__ \\  |___|___|___|___|\s
                                                            |___|___|___|___|   |_|  |_||_|___|   \\_/\\_/ /_/ \\_\\|_|_\\   |___|___|  \\___|___||_|\\_|___/  |___|___|___|___|\s
                                          
                """);
    }
}

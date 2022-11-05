package Utils;

public class ConsolePrints {


    public static void printGameWelcome(){
        System.out.println("""
                Welcome to...
                ===============
                                    
                (here goes silent kingdom ascii)
                                    
                ===============
                """);
    }
    public static void printBotVsBotSelection(){
        System.out.println("""
                You have entered the Bot VS Bot gaming mode.

                """);
    }
    public static void printPlayerVsBotSelection() {
        System.out.println("""
                You have entered the Player VS Bot gaming mode.

                """);
    }
    public static void printPlayerVsPlayerSelection() {
        System.out.println("""
                You have entered the Player VS Player gaming mode.

                """);
    }
    public static void printLetsChooseArmySize() {
        System.out.println("Let's choose the number of Combatants that will " +
                "compose both armies");
    }
    public static void printLetsChooseArmySize(String armyName) {
        System.out.println("Let's choose the number of Combatants for " + armyName);
    }
    public static void printConstructionOfRandomArmy(String armyName) {
        System.out.println("Construction of " + armyName + " ...\n");
    }

    public static void printArmyStatus() {
        System.out.println("Construction of armies...\n");
    }

    public static void printExitGame() {
        System.out.println("""
                Ok, see you soon!

                """);
    }



}

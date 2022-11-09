import repository.RepositoryCsv;
import services.GameService;
import services.InputService;
import services.WarService;


public class Main {
    public static void main(String[] args) throws Exception {

        var repo = new RepositoryCsv();
        var inputSVC = new InputService();

        var armyName = inputSVC.askArmyName();

        inputSVC.askArmySize();

        inputSVC.askWhoIsArmyControlledBy();

        //var armyCSVName = inputSVC.armyToImportFileName();
        var army = repo.importArmy("lotrLightArmy.csv", armyName);
        var combatant = army.pickCombatantByIndex(inputSVC.askNextCombatant(army));
        System.out.println(combatant);


//        var lightArmy =  repo.importArmy("lotrLightArmy.csv", "Heroes Army");
//        inputService.askNextCombatant(lightArmy);

//        WarService warService = new WarService(lightArmy, lightArmy, repo);
//        lightArmy.setBot(false);
//        System.out.println(warService.getNextCombatant(lightArmy));

//        var repo = new RepositoryCsv();
//
//
//        var lightArmy =  repo.importArmy("lotrLightArmy.csv", "Heroes Army");
//
//        lightArmy.printStatus();



        // =========== DEMO Repository ============ (Uncomment for testing)

        /*
        // Initialize CSV repository
        var repo = new RepositoryCsv();

        // Import army from imports/armies --> Check storage/combatants.csv
        var lightArmy =  repo.importArmy("lotrLightArmy.csv", "Heroes Army");

        // Create random army of size 10
        var darkArmy = Army.createRandom(11, repo);

        // Create warrior and export it to imports/templates
        var warrior = new Warrior("Boromir", 120, false, 25, 7, repo);
        repo.exportCombatant(warrior);

        // When adding combatant to army, it gets saved --> Check storage/combatants.csv
        lightArmy.addCombatant(warrior);

        // Pick combatant, change status and save --> Check storage/combatants.csv
        var darkWarrior = darkArmy.pickRandomCombatant();
        darkWarrior.setAlive(false);
        repo.deleteCombatant(darkWarrior);
        repo.deleteArmy(darkArmy);
        repo.saveArmy(darkArmy);

        // Try to import a 3rd army --> Not possible, returns null
        var anotherArmy = repo.importArmy("lotrLightArmy.csv","Heroes Army");
        System.out.println(anotherArmy);

        // Export current army to csv --> saved to imports/armies/
        repo.exportArmy("exportedArmy.csv", darkArmy);

        // Export another combatant to imports/templates/
        repo.exportCombatant(darkWarrior);

        // If export existing combatant, replaces
        warrior.setStamina(30);
        repo.exportCombatant(warrior);

        // Import new combatant from imports/templates/ and saved it when added to army
        var warrior2 = repo.importCombatant("Troll");
        darkArmy.addCombatant(warrior2);


        // =========== DEMO InputService =============
        var inputService = new InputService();

        var armySize = inputService.askNumberOfCombatants();

        if (armySize == -1) {
            System.exit(0);
        } else {
            System.out.printf("Army size = %s\n", armySize);
            System.exit(0);
        }
        // =========== DEMO War =============

        // Initialize War Service
        var war = new WarService(lightArmy, darkArmy, repo);

        // Start simulator
        var winner = war.start();

        System.out.printf("\nTHE WINNER IS:\n %s\n\n",winner);

        System.out.println(String.join(war.getGraveyard().toString().replace("},","}\n")));
        */
    }
}
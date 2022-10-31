import model.Warrior;
import repository.RepositoryCsv;

public class Main {
    public static void main(String[] args) throws Exception {

        // =========== DEMO Repository ============

        // Initialize CSV repository
        var repo = new RepositoryCsv();

        // Import armies from imports/armies --> Check storage/combatants.csv
        var lightArmy =  repo.importArmy("lotrLightArmy.csv", "Heroes Army");
        var darkArmy = repo.importArmy("lotrDarkArmy.csv", "Sauron's Army");

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
    }
}
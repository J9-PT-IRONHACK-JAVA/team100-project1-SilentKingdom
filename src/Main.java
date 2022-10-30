import model.Warrior;
import repository.ArmyCsv;
import repository.CombatantCsv;

public class Main {
    public static void main(String[] args) throws Exception {

        var repo = new CombatantCsv();

        var lightArmyCsv = new ArmyCsv("ops/imports/armies/lotrLightArmy.csv");
        var lightArmy = lightArmyCsv.load("Heroes Army", "light", repo);

        var darkArmyCsv = new ArmyCsv("ops/imports/armies/lotrDarkArmy.csv");
        var darkArmy = darkArmyCsv.load("Sauron's Army", "dark", repo);

        var warrior = new Warrior("Boromir", 120, false, 25, 7, repo);
        lightArmy.addCombatant(warrior);
        repo.save(warrior);

        var darkWarrior = darkArmy.pickRandomCombatant();
        darkWarrior.setAlive(false);
        repo.saveAll(darkArmy);

        System.out.println(lightArmy);
        System.out.println(darkArmy);
        System.out.println(lightArmy.pickRandomCombatant());




    }
}
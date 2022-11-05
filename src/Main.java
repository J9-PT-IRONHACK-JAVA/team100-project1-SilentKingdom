
import services.InputService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

//        var lightArmyCsv = new ArmyCsv("ops/imports/armies/lotrLightArmy.csv");
//
//        var lightArmy = lightArmyCsv.get("Heroes Army", "light");
//
//        System.out.println(lightArmy);
//        System.out.println(lightArmy.pickRandomCombatant());
//

        var inputService = new InputService();

        inputService.askNumberOfCombatants();


    }
}
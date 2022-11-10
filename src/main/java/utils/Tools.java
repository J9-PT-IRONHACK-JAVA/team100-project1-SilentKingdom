package utils;


import model.Army;
import model.Combatant;
import model.Warrior;
import model.Wizard;
import net.datafaker.Faker;
import org.apache.commons.text.CaseUtils;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Tools {

    public static String OUT_OF_BOUND = "OUT_OF_BOUND";


    // Given an array list of string arrays, overwrites file with csv structure
    public static void overwriteCsv(String filePath, ArrayList<String[]> rows, String[] headers) throws IOException {
        var writer = new FileWriter(filePath);
        writer.write(String.join(",", headers));
        for (String[] row : rows){
            writer.write('\n' + String.join(",",row));
        }
        writer.close();
    }

    public static int getIndex(String[] array, String value){
        for (int i = 0; i < array.length; i++) {
            if (value.equals(array[i])) return i;
        }
        return -1;
    }

    public static String generateRandomArmyName(){
        var faker = new Faker();

        String firstName = faker.elderScrolls().city();
        String lastName = CaseUtils.toCamelCase(faker.team().creature(), true);

        return (firstName + " " + lastName);
    }



    public static String combatantStatus(Combatant combatant){
        String id = String.valueOf(combatant.getId());
        String army = combatant.getArmyName();
        String name = combatant.getName();
        String hp = String.valueOf(combatant.getHp());
        String type;
        String stamina;
        String strength;
        String mana;
        String intelligence;

        var combatantStatus = "";
        if (combatant instanceof Warrior) {
            type = "warrior";
            stamina = String.valueOf(((Warrior) combatant).getStamina());
            strength = String.valueOf(((Warrior) combatant).getStrength());
            mana = "-1";
            intelligence = "-1";



            combatantStatus = (name + " (%s): " + "HP=" + hp + ", stamina=" + stamina +
                    ", strength=" + strength).formatted(type);
        } else {
            type = "wizard";
            stamina = "-1";
            strength = "-1";
            mana = String.valueOf(((Wizard) combatant).getMana());
            intelligence = String.valueOf(((Wizard) combatant).getIntelligence());

            combatantStatus = (name + " (%s): " + "HP=" + hp + ", mana=" + mana +
                    ", intelligence=" + intelligence).formatted(type);
        }

        return combatantStatus;
    }

    public static void sleep(int s){
        try {
            Thread.sleep(s);
        } catch (InterruptedException ignored) {}
    }

}

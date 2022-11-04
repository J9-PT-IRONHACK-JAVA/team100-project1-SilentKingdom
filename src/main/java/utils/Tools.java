package utils;

import model.Army;
import model.Combatant;
import model.Warrior;
import model.Wizard;
import net.datafaker.Faker;
import org.apache.commons.text.CaseUtils;
import repository.RepositoryCsv;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Tools {

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


}

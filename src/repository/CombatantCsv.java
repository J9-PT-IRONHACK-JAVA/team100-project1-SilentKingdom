package repository;

import model.Army;
import model.Combatant;
import model.Warrior;
import model.Wizard;
import utils.Tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class CombatantCsv {
    public static final String ID = "id";
    public static  final String ARMY = "army";
    public static  final String TYPE = "type";
    public static  final String NAME = "name";
    public static  final String HP = "hp";
    public static  final String IS_ALIVE = "is_alive";
    public static  final String STAMINA = "stamina";
    public static  final String STRENGTH = "strength";
    public static  final String MANA = "mana";
    public static  final String INTELLIGENCE = "intelligence";
    public static  final String WARRIOR_TYPE = "warrior";
    public static  final String WIZARD_TYPE = "wizard";

    public static  final String FILE_PATH ="ops/storage/combatants.csv";
    private final File targetFile;

    public static final String[] HEADERS = new String[]{
            ID,ARMY,TYPE,NAME,HP,IS_ALIVE,STAMINA,STRENGTH,MANA,INTELLIGENCE
    };

    public void saveAll(Army army) throws Exception {
        var rows = getAllRows();
        var newRows = new ArrayList<String[]>();
        for (Combatant combatant : army.getCombatants()) {
            newRows.add(mapCombatantToRow(combatant));
        }

        for (String[] newRow : newRows) {
            rows = replaceOrAppendRow(rows, newRow);
        }
        // Overwrite file
        Tools.overwriteCsv(FILE_PATH, rows, HEADERS);
    }

    public void save(Combatant combatant) throws Exception {
        var newRow = mapCombatantToRow(combatant);
        var id = newRow[0];
        var rows = getAllRows();

        // Replace existing row by new row if found
        rows = replaceOrAppendRow(rows, newRow);

        // Overwrite file
        Tools.overwriteCsv(FILE_PATH, rows, HEADERS);
    }


    public ArrayList<String[]> getAllRows() throws FileNotFoundException {
        var allRows = new ArrayList<String[]>();
        var reader = new Scanner(targetFile);

        // Skip header
        reader.nextLine();
        while (reader.hasNextLine()) {
            var row = reader.nextLine().split(",");
            allRows.add(row);
        }
        reader.close();
        return allRows;
    }

    public int getLastId() throws FileNotFoundException {
        var reader = new Scanner(targetFile);
        reader.nextLine();
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            if (!reader.hasNextLine()) {
                String id = line.split(",")[Tools.getIndex(CombatantCsv.HEADERS, CombatantCsv.ID)];
                return Integer.parseInt(id);
            }
        }
        return 0;
    }

    public CombatantCsv() throws IOException {
        this.targetFile = new File(FILE_PATH);
        var writer = new FileWriter(FILE_PATH);
        writer.write(String.join(",", HEADERS));
        writer.close();
    }

    public String[] getHeaders() {
        return HEADERS;
    }


    public static Combatant mapRowToCombatant(String[] row){
        var mapRow = new HashMap<String, String>();
        for (int i = 0; i < HEADERS.length; i++) {
            mapRow.put(HEADERS[i], row[i]);
        }

        int id = Integer.parseInt(mapRow.get(ID));
        String type = mapRow.get(TYPE);
        String name = mapRow.get(NAME);
        int hp = Integer.parseInt(mapRow.get(HP));
        boolean isAlive = Boolean.parseBoolean(mapRow.get(IS_ALIVE));
        int stamina = Integer.parseInt(mapRow.get(STAMINA));
        int strength = Integer.parseInt(mapRow.get(STRENGTH));
        int mana = Integer.parseInt(mapRow.get(MANA));
        int intelligence = Integer.parseInt(mapRow.get(INTELLIGENCE));

        if (type.equals(WARRIOR_TYPE)) {
            return new Warrior(id, name, hp, isAlive, stamina, strength);
        } else if (type.equals(WIZARD_TYPE)) {
            return new Wizard(id, name, hp, isAlive, mana, intelligence);
        } else {
            return null;
        }
    };

    public static String[] mapCombatantToRow(Combatant combatant) throws Exception {
        String id = String.valueOf(combatant.getId());
        String army = combatant.getArmyName();
        String name = combatant.getName();
        String hp = String.valueOf(combatant.getHp());
        String isAlive = String.valueOf(combatant.isAlive());
        String type;
        String stamina;
        String strength;
        String mana;
        String intelligence;
        if (combatant instanceof Warrior) {
            type = WARRIOR_TYPE;
            stamina = String.valueOf(((Warrior) combatant).getStamina());
            strength = String.valueOf(((Warrior) combatant).getStrength());
            mana = "-1";
            intelligence = "-1";
        } else if (combatant instanceof Wizard) {
            type = WIZARD_TYPE;
            stamina = "-1";
            strength = "-1";
            mana = String.valueOf(((Wizard) combatant).getMana());
            intelligence = String.valueOf(((Wizard) combatant).getIntelligence());
        } else {
            throw new Exception("No Combatant child class was passed");
        }

        return new String[]{id,army,type,name,hp,isAlive,stamina,strength,mana,intelligence};
    }

    public static ArrayList<String[]> replaceOrAppendRow(ArrayList<String[]> oldRows, String[] newRow) {
        var newRows = new ArrayList<String[]>();
        var found = false;
        var id = newRow[0];
        for (String[] row : oldRows) {
            if (id.equals(row[0])) {
                found = true;
                newRows.add(newRow);
            } else {
                newRows.add(row);
            }
        }

        // If not found, append
        if (!found) {
            newRows.add(newRow);
        }
        return newRows;
    }
}

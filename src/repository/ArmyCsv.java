package repository;

import model.Army;
import model.Combatant;
import model.Warrior;
import model.Wizard;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class ArmyCsv implements ArmyRepository {
    private final String ID = "id";
    private final String TYPE = "type";
    private final String NAME = "name";
    private final String HP = "hp";
    private final String IS_ALIVE = "is_alive";
    private final String STAMINA = "stamina";
    private final String STRENGTH = "strength";
    private final String MANA = "mana";
    private final String INTELLIGENCE = "intelligence";
    private final String WARRIOR_TYPE = "warrior";
    private final String WIZARD_TYPE = "wizard";

    private File targetFile;

    private final ArrayList<String> headers = new ArrayList<>(
            List.of(ID,TYPE,NAME,HP,IS_ALIVE,STAMINA,STRENGTH,MANA,INTELLIGENCE)
    );


    @Override
    public void save(Army army) {

    }

    @Override
    public int getNextId() throws FileNotFoundException {
        var reader = new Scanner(targetFile);


        return 0;
    }

    @Override
    public Army get(String name, String side) throws FileNotFoundException {
        var army = new Army(name, side);
        var reader = new Scanner(targetFile);


        // Skip header!
        reader.nextLine();

        while (reader.hasNextLine()) {
            var values = reader.nextLine().split(",");
            var combatant = mapRowToCombatant(values);
            army.addCombatant(combatant);
        }

        return army;
    }


    @Override
    public void delete(Army army) {

    }

    @Override
    public void update(Combatant combatant) {

    }

    @Override
    public void save(Combatant combatant) {

    }

    @Override
    public Combatant getCombatantById(String combatantID) {
        return null;
    }

    @Override
    public Combatant getCombatantByName(String name) {
        return null;
    }

    @Override
    public Combatant getRandomCombatant() {
        return null;
    }

    @Override
    public void deleteCombatantById(String combatantID) {

    }

    @Override
    public void deleteCombatantByName(String name) {

    }

    public Combatant mapRowToCombatant(String[] values){
        var row = new HashMap<String, String>();
        for (int i = 0; i < headers.size(); i++) {
            row.put(headers.get(i), values[i]);
        }

        int id = Integer.parseInt(row.get(ID));
        String type = row.get(TYPE);
        String name = row.get(NAME);
        int hp = Integer.parseInt(row.get(HP));
        boolean isAlive = Boolean.parseBoolean(row.get(IS_ALIVE));
        int stamina = Integer.parseInt(row.get(STAMINA));
        int strength = Integer.parseInt(row.get(STRENGTH));
        int mana = Integer.parseInt(row.get(MANA));
        int intelligence = Integer.parseInt(row.get(INTELLIGENCE));

        if (type.equals(WARRIOR_TYPE)) {
            return new Warrior(name, hp, isAlive, stamina, strength);
        } else if (type.equals(WIZARD_TYPE)) {
            return new Wizard(name, hp, isAlive, mana, intelligence);
        } else {
            return null;
        }
    };

    public ArmyCsv(String filePath) throws Exception {
        this.targetFile = new File(filePath);
        var reader = new Scanner(targetFile);
        var foundHeaders = new ArrayList<>(List.of(reader.nextLine().split(",")));
        reader.close();

        if (!foundHeaders.equals(headers)){
            throw new Exception(
                    "The headers found in source file are not correct, should be \n%s, ".formatted(headers.toString())
            );
        }
    }

    public File getTargetFile() {
        return targetFile;
    }

    public void setTargetFile(String filePath) {
        this.targetFile = new File(filePath);
    }
}

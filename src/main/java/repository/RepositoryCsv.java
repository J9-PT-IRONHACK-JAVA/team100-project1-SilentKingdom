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
import java.util.*;

public class RepositoryCsv implements Repository{
    public static final String ID = "id";
    public static final String ARMY = "army";
    public static final String TYPE = "type";
    public static final String NAME = "name";
    public static final String HP = "hp";
    public static final String IS_ALIVE = "is_alive";
    public static final String STAMINA = "stamina";
    public static final String STRENGTH = "strength";
    public static final String MANA = "mana";
    public static final String INTELLIGENCE = "intelligence";
    public static final String WARRIOR_TYPE = "warrior";
    public static final String WIZARD_TYPE = "wizard";

    public static final String REPO_PATH ="src/main/resources/data/storage/combatants.csv";
    public static final String ARMY_CATALOG_PATH = "data/imports/armies/";
    public static final String COMBATANT_CATALOG_PATH = "data/imports/templates/combatants.csv";
    private final File repoFile;

    public static final String[] HEADERS = new String[]{
            ID,ARMY,NAME,TYPE,HP,IS_ALIVE,STAMINA,STRENGTH,MANA,INTELLIGENCE
    };

    public static final String[] IMPORT_HEADERS = new String[]{
            NAME,TYPE,HP,STAMINA,STRENGTH,MANA,INTELLIGENCE
    };

    // ============  DYNAMIC METHODS  ============

    /**
     * Given a combatant name, searches for a configuration in combatants templates.
     * If a configuration is found creates a new Combatant from its attributes, with the next incremental ID
     * @return new alive Combatant based on configuration available in combatants templates
     * @throws Exception if no file is found in templates combatants path
     */
    @Override
    public Combatant importCombatant(String name) throws Exception {
        var reader = new Scanner(new File(COMBATANT_CATALOG_PATH));

        while (reader.hasNextLine()){
            var valuesList = new ArrayList<>(List.of(reader.nextLine().split(",")));
            if (valuesList.get(Tools.getIndex(IMPORT_HEADERS,NAME)).equals(name)) {
                var values = convertImportToStorageRow(valuesList);

                return mapRowToCombatant(values);
            }
        }
        reader.close();
        return null;
    }

    /**
     * Given a Combatant gets its attributes and saves its configuration into combatant templates for later use
     * If a configuration already exists with the same Combatant name, it is replaced
     * @throws Exception if no file is found in templates combatants path
     */
    @Override
    public void exportCombatant(Combatant combatant) throws Exception {
        var newRow = convertStorageToImportRow(combatant);
        var rows = getAllRows(COMBATANT_CATALOG_PATH);

        rows = replaceOrAppendRow(rows, newRow);

        Tools.overwriteCsv(COMBATANT_CATALOG_PATH, rows, IMPORT_HEADERS);
    }

    /**
     * Creates a new Army with the given name importing all the combatants from a CSV
     * When the combatants are created and added to the Army, they are saved into the repository
     * If there are alreayd 2 armies in the repository, returns null
     * @return new Army imported from csv file
     * @throws Exception if no file is found in armies imports
     */
    @Override
    public Army importArmy(String fileName, String name) throws Exception {
        var distArmies = getDistinctArmies();
        if (distArmies.length > 1) {
            System.out.println("There are already 2 armies created");
            return null;
        }

        var army = new Army(name, this);
        var catalogFile = new File(ARMY_CATALOG_PATH + fileName);
        var reader = new Scanner(catalogFile);

        // Skip header!
        reader.nextLine();
        while (reader.hasNextLine()) {
            var valuesList = new ArrayList<>(List.of(reader.nextLine().split(",")));

            var values = convertImportToStorageRow(valuesList);

            var combatant = mapRowToCombatant(values);
            if (combatant != null) army.addCombatant(combatant);
        }
        reader.close();
        return army;
    }

    /**
     * Exports all combatants from an Army into a csv file, that can be used later to be imported.
     * If file already exists, it is replaced.
     * @throws Exception if no file is found in import armies path
     */
    @Override
    public void exportArmy(String fileName, Army army) throws Exception {
        var rows = new ArrayList<String[]>();
        for (Combatant combatant : army.getCombatants()) {
            var rowArray = convertStorageToImportRow(combatant);
            rows.add(rowArray);
        }

        Tools.overwriteCsv(ARMY_CATALOG_PATH + fileName, rows, IMPORT_HEADERS);
    }

    /**
     * Stores the state of a given Combatant in the repository. If the Combatant already exists, it is updated.
     * If it does not exist, the combatant is inserted.
     * @throws Exception if no file is found in repository path
     */
    @Override
    public void saveCombatant(Combatant combatant) throws Exception {
        var newRow = mapCombatantToRow(combatant);
        var rows = getAllRows(REPO_PATH);

        // Replace existing row by new row if found
        rows = replaceOrAppendRow(rows, newRow);

        // Overwrite file
        Tools.overwriteCsv(REPO_PATH, rows, HEADERS);
    }

    @Override
    public void updateCombatant(Combatant combatant) throws Exception {
        var newRow = mapCombatantToRow(combatant);
        var rows = getAllRows(REPO_PATH);

        for (int i = 0; i < rows.size(); i++) {
            if (rows.get(i)[0].equals(newRow[0])){
                rows.set(i, newRow);
                break;
            }
        }

        // Overwrite file
        Tools.overwriteCsv(REPO_PATH, rows, HEADERS);
    }

    /**
     * Stores the state of all combatants from a given Army. All existing combatants are updated in the repository,
     * while non-existing ones are inserted.
     * @throws Exception if no file is found in repository path
     */
    @Override
    public void saveArmy(Army army) throws Exception {
        var rows = getAllRows(REPO_PATH);
        var newRows = new ArrayList<String[]>();
        for (Combatant combatant : army.getCombatants()) {
            newRows.add(mapCombatantToRow(combatant));
        }

        for (String[] newRow : newRows) {
            rows = replaceOrAppendRow(rows, newRow);
        }
        // Overwrite file
        Tools.overwriteCsv(REPO_PATH, rows, HEADERS);
    }

    /**
     * Searches in the repository a Combatant with the given ID and returns it if it's found.
     * @return Combatant if a row exists in repository with the given ID.
     * @throws Exception if no file is found in repository path
     */
    @Override
    public Combatant getCombatantById(int id) throws Exception {
        var reader = new Scanner(repoFile);
        while (reader.hasNextLine()){
            var values = reader.nextLine().split(",");
            if (values[Tools.getIndex(HEADERS,ID)].equals(String.valueOf(id))) {
                return mapRowToCombatant(values);
            }
        }
        reader.close();
        return null;
    }

    /**
     * Given an Army, return all it's combatants as they are stored in the repository
     * @return all combatants from an Army
     * @throws Exception if no file is found in repository path
     */
    @Override
    public ArrayList<Combatant> getArmyCombatants(Army army) throws Exception {
        var combatants = new ArrayList<Combatant>();
        var reader = new Scanner(repoFile);
        while (reader.hasNextLine()){
            var values = reader.nextLine().split(",");
            if (values[Tools.getIndex(HEADERS,ARMY)].equals(army.getName())) {
                combatants.add(mapRowToCombatant(values));
            }
        }
        reader.close();
        return combatants;
    }

    /**
     * DELETES a combatant row from the repository if there's a row with the given ID
     * @throws Exception if no file is found in repository path
     */
    @Override
    public void deleteCombatant(Combatant combatant) throws Exception {
        String id = String.valueOf(combatant.getId());
        var rows = getAllRows(REPO_PATH);
        rows.removeIf(row -> row[Tools.getIndex(HEADERS, ID)].equals(id));
        // Overwrite file
        Tools.overwriteCsv(REPO_PATH, rows, HEADERS);
    }

    /**
     * DELETES all rows from a repository with the Army name provided
     * @throws Exception if no file is found in repository path
     */
    @Override
    public void deleteArmy(Army army) throws Exception {
        var rows = getAllRows(REPO_PATH);
        rows.removeIf(row -> row[Tools.getIndex(HEADERS, ARMY)].equals(army.getName()));
        // Overwrite file
        Tools.overwriteCsv(REPO_PATH, rows, HEADERS);
    }

    /** Searches for the last row in the repository and returns its ID
     * @return the ID biggest value in the file
     * @throws FileNotFoundException if no file is found in repository path
     */
    @Override
    public int getLastId() throws FileNotFoundException {
        var reader = new Scanner(repoFile);
        reader.nextLine();
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            if (!reader.hasNextLine()) {
                String id = line.split(",")[Tools.getIndex(HEADERS, ID)];
                return Integer.parseInt(id);
            }
        }
        return 0;
    }

    /** Creates a set with all army names unique values and returns and array from it
     * @return array of distinct army names
     * @throws FileNotFoundException if no file is found in repository path
     */
    @Override
    public String[] getDistinctArmies() throws FileNotFoundException {
        var rows = getAllRows(REPO_PATH);
        var distinctArmiesSet = new HashSet<String>();
        for (String[] row : rows) {
            distinctArmiesSet.add(row[Tools.getIndex(HEADERS,ARMY)]);
        }
        String[] distinctArmies = {};
        return distinctArmiesSet.toArray(distinctArmies);
    }


    // ============  CONSTRUCTOR & GETTERS  ============

    /**
     * Constructs a new CSV repository. Creates a new CSV file in the repository path and overwrites the existing ones.
     * The HEADERS are added to the file as the first line
     * @throws IOException if the path does not exist
     */
    public RepositoryCsv() throws IOException {
        this.repoFile = new File(REPO_PATH);
        var writer = new FileWriter(REPO_PATH);
        writer.write(String.join(",", HEADERS));
        writer.close();
    }


    // ============  STATIC METHODS  ============

    /**
     * Given a CSV file path, reads all lines in the file skipping the header (first line).
     * The lines are split by ',' to obtain rows (String[]), which are collected into an ArrayList
     * @return ArrayList with all rows (String[]) in repository file.
     * @throws FileNotFoundException if the path does not exist
     */
    private static ArrayList<String[]> getAllRows(String filePath) throws FileNotFoundException {
        var file = new File(filePath);
        var allRows = new ArrayList<String[]>();
        var reader = new Scanner(file);

        // Check if file is empty
        if (!reader.hasNextLine()) {
            return allRows;
        }
        // Skip header
        reader.nextLine();
        while (reader.hasNextLine()) {
            var row = reader.nextLine().split(",");
            allRows.add(row);
        }
        reader.close();
        return allRows;
    }

    /**
     * Maps all values from a row (String[]) to a Combatant object.
     * Depending on the value of type, it will instantiate a Warrior or a Wizard
     * @return a new Combatant created from the input row.
     */
    private static Combatant mapRowToCombatant(String[] row){
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
    }

    /**
     * Maps all properties from a Combatant into a row (String[]).
     * The properties will be ordered as in the HEADERS array so the row can be stored in the repository
     * Depending on the type of Combatant the values will be set accordingly.
     * @return an array of values based on Combatant's properties
     */
    private static String[] mapCombatantToRow(Combatant combatant){
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
        } else {
            type = WIZARD_TYPE;
            stamina = "-1";
            strength = "-1";
            mana = String.valueOf(((Wizard) combatant).getMana());
            intelligence = String.valueOf(((Wizard) combatant).getIntelligence());
        }

        String[] row = {id,army,name,type,hp,isAlive,stamina,strength,mana,intelligence};
        // Avoid null pointer exceptions
        for (int i = 0; i < row.length; i++) {
            if (row[i] == null) row[i] = "null";
        }
        return row;
    }

    /**
     * Given an array of rows with a unique ID in the first element and a new row, searches if the row first element
     * exists in the array. If exists, the new row replaces the old one in the exact same position. If it does not,
     * the new row is appended to the array.
     * @return an array of rows updated with the given row.
     */
    private static ArrayList<String[]> replaceOrAppendRow(ArrayList<String[]> oldRows, String[] newRow) {
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

    /**
     * Converts a row of values that matches the repository structure to the imports structure
     * @return a row with the converted structure
     */
    private String[] convertStorageToImportRow(Combatant combatant) {
        var rowList = new ArrayList<>(List.of(mapCombatantToRow(combatant)));
        rowList.remove(Tools.getIndex(HEADERS, IS_ALIVE));
        rowList.remove(Tools.getIndex(HEADERS, ARMY));
        rowList.remove(Tools.getIndex(HEADERS, ID));

        return rowList.toArray(new String[]{});
    }

    /**
     * Converts a row of values that matches the imports structure to the repository structure
     * @return a row with the converted structure
     */
    private String[] convertImportToStorageRow(ArrayList<String> valuesList) throws Exception {
        int indexId = Tools.getIndex(HEADERS, ID);
        int indexArmy = Tools.getIndex(HEADERS, ARMY);
        int indexIsAlive = Tools.getIndex(HEADERS, IS_ALIVE);
        valuesList.add(indexId, String.valueOf(getLastId() + 1));
        valuesList.add(indexArmy, "null");
        valuesList.add(indexIsAlive, "true");

        return valuesList.toArray(new String[]{});
    }

}

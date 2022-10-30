package repository;

import model.Army;
import model.Combatant;
import model.Warrior;
import model.Wizard;
import utils.Tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;


public class ArmyCsv implements ArmyRepository {
    private File targetFile;


    @Override
    public Army load(String name, String side, CombatantCsv repo) throws Exception {
        var army = new Army(name, side);
        var reader = new Scanner(targetFile);

        // Skip header!
        reader.nextLine();

        while (reader.hasNextLine()) {
            var valuesList = new ArrayList<>(List.of(reader.nextLine().split(",")));

            // Insert ID and Army columns in row structure
            int indexId = Tools.getIndex(CombatantCsv.HEADERS, CombatantCsv.ID);
            int indexArmy = Tools.getIndex(CombatantCsv.HEADERS, CombatantCsv.ARMY);
            valuesList.add(indexId, String.valueOf(repo.getLastId() + 1));
            valuesList.add(indexArmy, name);
            var values = valuesList.toArray(new String[]{});

            var combatant = CombatantCsv.mapRowToCombatant(values);


            army.addCombatant(combatant);

            repo.save(combatant);
        }

        reader.close();

        return army;
    }

    @Override
    public void export(Army army) {

    }

    @Override
    public void remove(Army army) {

    }

    public ArmyCsv(String filePath) throws Exception {
        this.targetFile = new File(filePath);
        var reader = new Scanner(targetFile);
        var foundHeaders = new ArrayList<>(List.of(reader.nextLine().split(",")));
        var requiredHeaders = CombatantCsv.HEADERS;
        reader.close();

        // Add ID and Army columns to match csv and check
        foundHeaders.add(Tools.getIndex(CombatantCsv.HEADERS, CombatantCsv.ID), CombatantCsv.ID);
        foundHeaders.add(Tools.getIndex(CombatantCsv.HEADERS, CombatantCsv.ARMY), CombatantCsv.ARMY);
        if (!Arrays.equals(foundHeaders.toArray(),requiredHeaders)){
            throw new Exception(
                    ("The headers found in source file are not correct, should be \n%s, ").formatted(
                            requiredHeaders.toString())
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

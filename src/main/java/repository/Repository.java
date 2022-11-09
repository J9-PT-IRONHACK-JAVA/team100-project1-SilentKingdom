package repository;

import model.Army;
import model.Combatant;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public interface Repository {
    Combatant importCombatant(String name) throws Exception;
    void exportCombatant(Combatant combatant) throws Exception;
    Army importArmy(String fileName, String name) throws Exception;
    void exportArmy(String fileName, Army army) throws Exception;
    void saveCombatant(Combatant combatant) throws Exception;
    void updateCombatant(Combatant combatant) throws Exception;
    void saveArmy(Army army) throws Exception;
    Combatant getCombatantById(int id) throws Exception;
    ArrayList<Combatant> getArmyCombatants(Army army) throws Exception;
    void deleteCombatant(Combatant combatant) throws Exception;
    void deleteArmy(Army army) throws Exception;
    int getLastId() throws FileNotFoundException;
    String[] getDistinctArmies() throws FileNotFoundException;
}

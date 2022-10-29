package repository;

import model.Army;
import model.Combatant;

import java.io.FileNotFoundException;

public interface ArmyRepository {
    public void save(Army army);



    public Army get(String name, String side) throws FileNotFoundException;

    public void delete(Army army);

    public void update(Combatant combatant);

    public void save(Combatant combatant);

    public Combatant getCombatantById(String combatantID);

    public Combatant getCombatantByName(String name);

    public Combatant getRandomCombatant();

    public void deleteCombatantById(String combatantID);

    public void deleteCombatantByName(String name);

    public int getNextId() throws FileNotFoundException;

}

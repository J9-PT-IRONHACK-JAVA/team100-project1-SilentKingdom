package repository;

import model.Army;
import model.Combatant;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface ArmyRepository {
    public void export(Army army);
    public void remove(Army army);
    public Army load(String name, String side, CombatantCsv repo) throws Exception;

}

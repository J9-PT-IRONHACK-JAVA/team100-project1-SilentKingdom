package repository;

import model.Army;

public interface ArmyRepository {
    public void save(Army army);

    public Army get();

    public void delete(Army army);

}

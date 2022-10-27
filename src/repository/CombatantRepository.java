package repository;

public interface CombatantRepository {
    public void update(Character character);

    public void save(Character character);

    public Character getById(String characterID);

    public Character getByName(String name);

    public Character getRandom();

    public void deleteById(String characterID);

    public void deleteByName(String name);
}

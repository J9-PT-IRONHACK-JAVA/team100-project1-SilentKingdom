package model;

public abstract class Combatant {
    public int health = 5;

    public abstract void TakeDamage(int damage);

    public abstract void Attack();

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
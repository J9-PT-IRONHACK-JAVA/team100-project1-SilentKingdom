package model;

public interface Attacker {
    public void Attack(Attacker target);
    public void heavyAttack(Attacker target);
    public void weakAttack(Attacker target);
    public void takeDamage(int damage);
}

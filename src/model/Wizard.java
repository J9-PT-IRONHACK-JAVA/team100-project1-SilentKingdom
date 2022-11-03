package model;

public class Wizard extends Combatant implements Attacker {

    public Wizard() {
    }

    @Override
    public void Attack(Attacker target) {
        //TODO implement attack selection
        if(x) {
            this.heavyAttack(target);
        } else {
            this.weakAttack(target);
        }
    }
    @Override
    public void takeDamage(int damage) {

        if(this.health > 0) {
            this.health -= damage;
        }

        if(this.health <= 0) {
            //TODO implement death?
        }
    }

    @Override
    public void heavyAttack(Attacker target) {
        var damage = 0;
        //TODO implement damage calculation
        target.takeDamage(damage);
    }

    @Override
    public void weakAttack(Attacker target) {
        var damage = 0;
        //TODO implement damage calculation
        target.takeDamage(damage);
    }

    @Override
    public void Attack() {

    }

    @Override
    public void TakeDamage(int damage) {

    }
}




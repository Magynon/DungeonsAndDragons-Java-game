package characters;

import shop.Inventory;
import visitor.Visitor;

public class Warrior extends Character{
    public Warrior(){
        inventory = new Inventory(100);
        strength = ((double)level / 2) * 100;
        charisma = ((double)level / 6) * 100;
        dexterity = ((double)level / 3) * 100;
        fire = true;
    }

    @Override
    void receiveDamage(int damage) {
        if(dexterity > charisma + 45){
            damage /= 2;
        }
        currentLife -= damage;
    }

    @Override
    int getDamage() {
        int damage = (int)(3/4 * strength + 1/8 * dexterity + 1/8 * charisma);
        if(strength > dexterity + 70*level){
            damage *= 2;
        }
        return damage;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public void accept(Visitor visitor) {

    }
}

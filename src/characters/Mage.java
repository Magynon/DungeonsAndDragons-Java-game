package characters;

import shop.Inventory;
import visitor.Visitor;

public class Mage extends Character{
    public Mage(){
        inventory = new Inventory(25);
        strength = ((double)level / 6) * 100;
        charisma = ((double)level / 2) * 100;
        dexterity = ((double)level / 3) * 100;
        ice = true;
        currentLife = maxLife;
        currentMana = maxMana;
    }

    void receiveDamage(int damage) {
        if(dexterity > strength + 30){
            damage /= 2;
        }
        currentLife -= damage;
    }

    @Override
    int getDamage() {
        int damage = (int)(3/4 * charisma + 1/8 * strength + 1/8 * dexterity);
        if(charisma > dexterity + 60*level){
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

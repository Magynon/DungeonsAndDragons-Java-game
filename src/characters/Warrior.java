package characters;

import shop.Inventory;
import shop.Potion;

public class Warrior extends Character{
    public Warrior(){
        inventory = new Inventory(100);
        strenght = ((double)level / 2) * 100;
        charisma = ((double)level / 6) * 100;
        dexterity = ((double)level / 3) * 100;
        fire = true;
    }

    @Override
    void receiveDamage(int damage) {
        currentLife -= damage;
    }

    @Override
    int getDamage() {
        return 0;
    }
}

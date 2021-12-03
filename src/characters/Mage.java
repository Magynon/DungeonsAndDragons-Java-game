package characters;

import shop.Inventory;
import shop.Potion;
import spells.Spell;

public class Mage extends Character{
    public Mage(){
        inventory = new Inventory(25);
        strenght = ((double)level / 6) * 100;
        charisma = ((double)level / 2) * 100;
        dexterity = ((double)level / 3) * 100;
        ice = true;
    }

    void receiveDamage(int damage) {

    }

    @Override
    int getDamage() {
        return 0;
    }
}

package characters;

import shop.Inventory;
import shop.Potion;

public class Rogue extends Character{
    public Rogue(){
        inventory = new Inventory(50);
        strenght = ((double)level / 3) * 100;
        charisma = ((double)level / 6) * 100;
        dexterity = ((double)level / 2) * 100;
        earth = true;
    }

    @Override
    void receiveDamage(int damage) {

    }

    @Override
    int getDamage() {
        return 0;
    }
}

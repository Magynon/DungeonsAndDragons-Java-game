package characters;

import shop.Inventory;

public class Rogue extends Character{
    public Rogue(){
        inventory = new Inventory(50);
        strength = ((double)level / 3) * 100;
        charisma = ((double)level / 6) * 100;
        dexterity = ((double)level / 2) * 100;
        earth = true;
    }

    @Override
    void receiveDamage(int damage) {
        if(strength > charisma + 40){
            damage /= 2;
        }
        currentLife -= damage;
    }

    @Override
    int getDamage() {
        int damage = (int)(3/4 * dexterity + 1/8 * strength + 1/8 * charisma);
        if(dexterity > strength + 50*level){
            damage *= 2;
        }
        return damage;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

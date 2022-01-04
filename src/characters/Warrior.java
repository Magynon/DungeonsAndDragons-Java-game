package characters;

import shop.Inventory;
import spells.Fire;

import java.util.ArrayList;
import java.util.Random;

public class Warrior extends Character{
    public Warrior(){
        inventory = new Inventory(100);
        updateTraitsWithLevel();
        fire = true;
        currentLife = maxLife;
        currentMana = maxMana;
        name = "Warrior";

        int abilities = new Random().nextInt(3) + 2;
        spellList = new ArrayList<>(abilities);

        for(int i = 0; i < abilities; i++){
            spellList.add(new Fire());
        }
    }

    @Override
    public void receiveDamage(int damage) {
        if(dexterity > charisma + 45){
            damage /= 2;
        }
        currentLife -= damage;
    }

    @Override
    public int getDamage() {
        int damage = (int) ((int)(3 * strength / 4 + dexterity / 8 + charisma / 8)/1.5);
        if(strength > dexterity + 70*level){
            damage *= 2;
        }
        return damage;
    }

    @Override
    public void updateTraitsWithLevel() {
        strength = ((double)level / 2) * 100;
        charisma = ((double)level / 6) * 100;
        dexterity = ((double)level / 3) * 100;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

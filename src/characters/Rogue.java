package characters;

import shop.Inventory;
import spells.Earth;
import visitor.Visitor;

import java.util.ArrayList;
import java.util.Random;

public class Rogue extends Character{
    public Rogue(){
        inventory = new Inventory(50);
        updateTraitsWithLevel();
        earth = true;
        currentLife = maxLife;
        currentMana = maxMana;
        name = "Rogue";

        int abilities = new Random().nextInt(3) + 2;
        spellList = new ArrayList<>(abilities);

        for(int i = 0; i < abilities; i++){
            spellList.add(new Earth());
        }
    }

    @Override
    public void receiveDamage(int damage) {
        if(strength > charisma + 40){
            damage /= 2;
        }
        currentLife -= damage;
    }

    @Override
    public int getDamage() {
        int damage = (int)(3 * dexterity / 4 + strength / 8 + charisma / 8)/2;
        if(dexterity > strength + 50*level){
            damage *= 2;
        }
        return damage;
    }

    @Override
    public void updateTraitsWithLevel() {
        strength = ((double)level / 3) * 100;
        charisma = ((double)level / 6) * 100;
        dexterity = ((double)level / 2) * 100;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public void accept(Visitor visitor) {

    }
}

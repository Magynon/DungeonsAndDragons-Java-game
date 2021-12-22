package characters;

import shop.Inventory;
import visitor.Visitor;

public class Rogue extends Character{
    public Rogue(){
        inventory = new Inventory(50);
        updateTraitsWithLevel();
        earth = true;
        currentLife = maxLife;
        currentMana = maxMana;
        name = "Rogue";
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

package characters;

import shop.Inventory;
import visitor.Visitor;

public class Mage extends Character{
    public Mage(){
        inventory = new Inventory(25);
        updateTraitsWithLevel();
        ice = true;
        currentLife = maxLife;
        currentMana = maxMana;
        name = "Mage";
    }

    public void updateTraitsWithLevel(){
        strength = ((double)level / 6) * 100;
        charisma = ((double)level / 2) * 100;
        dexterity = ((double)level / 3) * 100;
    }

    public void receiveDamage(int damage) {
        if(dexterity > strength + 30){
            damage /= 2;
        }
        currentLife -= damage;
    }

    @Override
    public int getDamage() {
        int damage = (int)(3 * charisma / 4 + strength / 8 + dexterity / 8)/2;
        if(charisma > dexterity + 60*level){
            damage *= 2;
        }
        return damage;
    }

    @Override
    public String toString() {
        return super.toString() + "damage: " + getDamage();
    }

    @Override
    public void accept(Visitor visitor) {

    }
}

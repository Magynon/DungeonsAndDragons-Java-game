package characters;

import spells.Earth;
import spells.Fire;
import spells.Ice;
import spells.Spell;

import java.util.List;

public abstract class Entity {
    private int maxLife, maxMana;
    protected int currentLife, currentMana;
    protected boolean ice = false, fire = false, earth = false;
    protected List<Spell> spellList;

    abstract void receiveDamage(int damage);
    abstract int getDamage();

    public void lifeRegeneration(int val){
        if(val > maxLife){
            currentLife = maxLife;
        }
        else{
            currentLife += val;
        }
    }

    public void manaRegeneration(int val){
        if(val > maxMana){
            currentMana = maxMana;
        }
        else{
            currentMana += val;
        }
    }

    public void useAbility(Spell spell, Entity enemy){
        if(currentMana >= spell.getMana()){
            // waste mana even if enemy is immune to spell
            currentMana -= spell.getMana();

            // check immunity
            if(spell instanceof Fire && fire)
                return;
            if(spell instanceof Earth && earth)
                return;
            if(spell instanceof Ice && ice)
                return;

            // if not immune, receive damage
            enemy.receiveDamage(spell.getDamage());
        }
    }
}

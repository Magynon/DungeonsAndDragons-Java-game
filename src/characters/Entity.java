package characters;

import spells.Earth;
import spells.Fire;
import spells.Ice;
import spells.Spell;

import java.util.List;

public abstract class Entity {
    protected int maxLife = 100, maxMana = 100;
    protected int currentLife, currentMana;
    protected boolean ice = false, fire = false, earth = false;
    protected List<Spell> spellList;

    abstract public void receiveDamage(int damage);
    abstract public int getDamage();

    public Spell getSpell(){
        if(spellList.size() == 0){
            return null;
        }
        return spellList.remove(0);
    }

    public void lifeRegeneration(int val){
        if(val + currentLife > maxLife){
            currentLife = maxLife;
        }
        else{
            currentLife += val;
        }
    }

    public void manaRegeneration(int val){
        if(val + currentMana > maxMana){
            currentMana = maxMana;
        }
        else{
            currentMana += val;
        }
    }

    public int getCurrentLife(){
        return currentLife;
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public void useAbility(Spell spell, Entity enemy){
        if(currentMana >= spell.getMana()){
            // waste mana even if enemy is immune to spell
            currentMana -= spell.getMana();

            // check immunity
            if(spell instanceof Fire && enemy.fire) {
                if(enemy instanceof Enemy){
                    System.out.println("Oops.. it seems the enemy is immune to fire!");
                }
                else{
                    System.out.println("Hurray! You are immune to fire!");
                }
                return;
            }
            if(spell instanceof Earth && enemy.earth){
                if(enemy instanceof Enemy){
                    System.out.println("Oops.. it seems the enemy is immune to earth!");
                }
                else{
                    System.out.println("Hurray! You are immune to earth!");
                }
                return;
            }

            if(spell instanceof Ice && enemy.ice){
                if(enemy instanceof Enemy){
                    System.out.println("Oops.. it seems the enemy is immune to ice!");
                }
                else{
                    System.out.println("Hurray! You are immune to ice!");
                }
                return;
            }

            // if not immune, receive damage
            enemy.receiveDamage(spell.getDamage());
            if(enemy instanceof Character){
                System.out.println("Enemy used the " + spell);
            }
            else{
                System.out.println("You used " + spell + " against the enemy.");
            }
        }
        else{
            if(enemy instanceof Enemy) {
                System.out.println("Not enough mana to use spell! Currently: " + currentMana);
                System.out.println("Try again (attack or use potion)!");
            }
            else{
                System.out.println("Turns out the enemy is out of mana. You're in luck, finish him!");
            }
        }
    }
}

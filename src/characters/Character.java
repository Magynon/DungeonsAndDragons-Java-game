package characters;

import shop.HealthPotion;
import shop.Inventory;
import shop.Potion;

abstract public class Character extends Entity{
    protected String name;
    protected int xp;
    protected int level = 1;
    protected double strength, charisma, dexterity;
    protected Inventory inventory;
    private int enemiesDefeated = 0;

    public void newEnemyDefeated(){
        enemiesDefeated++;
    }

    public int getEnemiesDefeated() {
        return enemiesDefeated;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public String getName() {
        return name;
    }

    public void buyPotion(Potion potion){
        inventory.addPotion(potion);
    }

    public void usePotion(int index){
        Potion potion = getInventory().getPotion(index);
        if(potion instanceof HealthPotion){
            lifeRegeneration(potion.getRegen());
        }
        else{
            manaRegeneration(potion.getRegen());
        }
        getInventory().removePotion(index);
    }

    abstract public void updateTraitsWithLevel();

    public void incLevel(){
        level++;
    }

    @Override
    public String toString() {
        return name +
                ": xp=" + xp +
                ", level=" + level +
                ", inventory=" + inventory +
                "\n";
    }
}

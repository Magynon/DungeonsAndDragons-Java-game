package characters;

import exceptions.InventoryFullOrNotEnoughMoneyException;
import shop.Inventory;
import shop.Potion;

abstract public class Character extends Entity{
    private String name;
    private int Ox, Oy;
    protected int xp;
    protected int level = 1;
    protected double strength, charisma, dexterity;
    protected Inventory inventory;

    public Inventory getInventory() {
        return inventory;
    }

    void buyPotion(Potion potion) throws InventoryFullOrNotEnoughMoneyException {
        inventory.addPotion(potion);
    }
}

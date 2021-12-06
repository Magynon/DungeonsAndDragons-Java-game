package characters;

import exceptions.InventoryFullOrNotEnoughMoneyException;
import grid.Cell;
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

    public void buyPotion(Potion potion) throws InventoryFullOrNotEnoughMoneyException {
        inventory.addPotion(potion);
    }

    public void setCurrentCoordinates(Cell currentCell){
        Ox = currentCell.getOx();
        Oy = currentCell.getOy();
    }

    @Override
    public String toString() {
        return "Character{" +
                "name='" + name + '\'' +
                ", Ox=" + Ox +
                ", Oy=" + Oy +
                ", xp=" + xp +
                ", level=" + level +
                ", strength=" + strength +
                ", charisma=" + charisma +
                ", dexterity=" + dexterity +
                ", inventory=" + inventory +
                "}\n";
    }
}

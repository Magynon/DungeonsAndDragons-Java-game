package exceptions;

public class InventoryFullOrNotEnoughMoneyException extends Exception{
    public InventoryFullOrNotEnoughMoneyException(){
        super("Inventory full or not enough money!");
    }
}

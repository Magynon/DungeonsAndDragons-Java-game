package shop;

import exceptions.InventoryFullOrNotEnoughMoneyException;
import java.util.ArrayList;
import java.util.List;

public class Inventory {
    List<Potion> potionList;
    final private int maxWeight;
    private int currentWeight;
    private int coinNumber;

    public Inventory(int maxWeight){
        potionList = new ArrayList<Potion>();
        this.maxWeight = maxWeight;
        currentWeight = 0;
        coinNumber = 0;
    }

    public void earnCoins(int coins){
        coinNumber += coins;
    }

    public void addPotion(Potion potion) throws InventoryFullOrNotEnoughMoneyException {
        if(currentWeight + potion.getWeight() <= maxWeight
                && coinNumber >= potion.getPrice()){
            potionList.add(potion);
            currentWeight += potion.getWeight();
            coinNumber -= potion.getPrice();
        }
        else{
            throw new InventoryFullOrNotEnoughMoneyException();
        }
    }

    public Potion removePotion(int index){
        currentWeight -= potionList.get(index).getWeight();
        return potionList.remove(index);
    }
}

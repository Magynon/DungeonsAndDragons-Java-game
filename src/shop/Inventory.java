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
        potionList = new ArrayList<>();
        this.maxWeight = maxWeight;
        currentWeight = 0;
        coinNumber = 50;
    }

    public void earnCoins(int coins){
        coinNumber += coins;
    }

    public void addPotion(Potion potion) {
        if(currentWeight + potion.getWeight() <= maxWeight
                && coinNumber >= potion.getPrice()){
            potionList.add(potion);
            currentWeight += potion.getWeight();
            coinNumber -= potion.getPrice();
        }
        else {
            System.out.println("Inventory full or not enough coins!");
        }
    }

    public void showPotions(){
        System.out.println("List of available potions:");
        for(int i = 0; i < potionList.size(); i++){
            System.out.println("\t" + (i+1) + " " + potionList.get(i));
        }
    }

    public int getPotionNumber(){
        return potionList.size();
    }

    public Potion getPotion(int index){
        return potionList.get(index);
    }

    public Potion removePotion(int index){
        currentWeight -= potionList.get(index).getWeight();
        return potionList.remove(index);
    }

    @Override
    public String toString() {
        return "Inventory:" +
                "\n\tpotionList: " + potionList +
                "\n\tmaxWeight: " + maxWeight +
                "\n\tcurrentWeight: " + currentWeight +
                "\n\tcoinNumber: " + coinNumber;
    }
}

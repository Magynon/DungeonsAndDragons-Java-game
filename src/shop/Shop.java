package shop;

import grid.CellElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Shop implements CellElement {
    List<Potion> potionList;

    public Shop(){

        Random rand = new Random();
        int capacity = rand.nextInt(3) + 2;
        potionList = new ArrayList<>(capacity);

        potionList.add(new HealthPotion(20, 1, 20));
        potionList.add(new ManaPotion(10, 5, 10));

        for(int i = 0; i < capacity-2; i++){
            if(rand.nextBoolean())
                potionList.add(new HealthPotion(20, 1, 20));
            else
                potionList.add(new ManaPotion(10, 5, 10));
        }
    }

    public Potion removePotion(int index){
        return potionList.remove(index);
    }

    public List<Potion> lookAround(boolean gui){
        if(gui){
            return potionList;
        }
        else{
            System.out.println("This shop has:");
            for(int i = 0; i < potionList.size(); i++){
                System.out.println("\t" + (i+1) + " " + potionList.get(i));
            }
            return null;
        }
    }

    public int getManaPotionIndex(){
        for(Potion i : potionList){
            if(i instanceof ManaPotion){
                return potionList.indexOf(i);
            }
        }
        return -1;
    }

    public int getHealthPotionIndex(){
        for(Potion i : potionList){
            if(i instanceof HealthPotion){
                return potionList.indexOf(i);
            }
        }
        return -1;
    }

    public boolean isEmpty(){
        return potionList.isEmpty();
    }

    @Override
    public char toCharacter() {
        return 'S';
    }

    @Override
    public String toString() {
        return "This shop has:" +
                potionList +
                "\n";
    }
}

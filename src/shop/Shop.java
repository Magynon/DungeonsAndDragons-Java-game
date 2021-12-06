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

        for(int i = 0; i < capacity; i++){
            if(rand.nextBoolean())
                potionList.add(new HealthPotion(20, 1, 100));
            else
                potionList.add(new ManaPotion(10, 5, 80));
        }
    }

    public Potion removePotion(int index){
        return potionList.remove(index);
    }

    @Override
    public char toCharacter() {
        return 'S';
    }

    @Override
    public String toString() {
        return "Shop{" +
                "potionList=" + potionList +
                "}\n";
    }
}

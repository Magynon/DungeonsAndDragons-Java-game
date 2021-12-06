package characters;

import grid.CellElement;
import spells.Earth;
import spells.Fire;
import spells.Ice;

import java.util.ArrayList;
import java.util.Random;

public class Enemy extends Entity implements CellElement {
    private int damage;

    public Enemy(){
        currentLife = new Random().nextInt(50) + 50;
        currentMana = new Random().nextInt(50) + 50;
        damage = (int) (currentLife*0.5);
        fire = new Random().nextBoolean();
        earth = !fire && new Random().nextBoolean();
        ice = !fire && !earth && new Random().nextBoolean();

        int abilities = new Random().nextInt(3) + 2;
        spellList = new ArrayList<>(abilities);
        for(int i = 0; i < abilities; i++){
            int ability = new Random().nextInt(4);
            switch(ability){
                case 0: spellList.add(new Earth());
                case 1: spellList.add(new Ice());
                case 2: spellList.add(new Fire());
            }
        }
    }

    @Override
    public char toCharacter() {
        return 'E';
    }

    @Override
    void receiveDamage(int damage) {
        boolean random = new Random().nextBoolean();
        if(random){
            damage /= 2;
        }
        currentLife -= damage;
        this.damage = (int) (currentLife*0.5);
    }

    @Override
    int getDamage() {
        boolean random = new Random().nextBoolean();
        if(random){
            damage *= 2;
        }
        return damage;
    }

    private int onDeathReturnCoins(){
        int random = new Random().nextInt(5);
        if(random != 1){
            return new Random().nextInt(20);
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Enemy{" +
                "damage=" + damage +
                "}\n";
    }
}

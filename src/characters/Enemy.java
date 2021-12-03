package characters;

import grid.CellElement;

public class Enemy extends Entity implements CellElement {
    private int life, mana;

    public Enemy(){

    }

    @Override
    public char toCharacter() {
        return 'E';
    }

    @Override
    void receiveDamage(int damage) {

    }

    @Override
    int getDamage() {
        return 0;
    }
}

package spells;

import characters.Entity;

public class Fire extends Spell{
    public Fire(){
        damage = 20;
        mana = 20;
    }

    @Override
    public void visit(Entity entity) {

    }

    @Override
    public String toString() {
        return "Fire";
    }
}

package spells;

import characters.Entity;

public class Fire extends Spell{
    public Fire(){
        damage = 10;
        mana = 20;
    }

    @Override
    public void visit(Entity entity) {

    }
}

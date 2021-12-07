package spells;

import characters.Entity;

public class Ice extends Spell{
    public Ice(){
        damage = 15;
        mana = 25;
    }

    @Override
    public void visit(Entity entity) {

    }
}

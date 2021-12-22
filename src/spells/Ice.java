package spells;

import characters.Entity;

public class Ice extends Spell{
    public Ice(){
        damage = 10;
        mana = 25;
    }

    @Override
    public void visit(Entity entity) {

    }

    @Override
    public String toString() {
        return "Ice";
    }
}

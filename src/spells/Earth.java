package spells;

import characters.Entity;

public class Earth extends Spell{
    public Earth(){
        damage = 5;
        mana = 10;
    }

    @Override
    public void visit(Entity entity) {

    }
}

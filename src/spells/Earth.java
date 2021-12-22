package spells;

import characters.Entity;

public class Earth extends Spell{
    public Earth(){
        damage = 15;
        mana = 10;
    }

    @Override
    public void visit(Entity entity) {

    }

    @Override
    public String toString() {
        return "Earth";
    }
}

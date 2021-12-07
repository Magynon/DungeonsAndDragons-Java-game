package spells;

import visitor.Visitor;

abstract public class Spell implements Visitor {
    protected int damage, mana;

    public int getDamage() {
        return damage;
    }

    public int getMana() {
        return mana;
    }
}

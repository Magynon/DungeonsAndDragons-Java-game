package spells;

public class Fire extends Spell{
    public Fire(){
        damage = 20;
        mana = 13;
    }

    @Override
    public String toString() {
        return "Fire";
    }
}

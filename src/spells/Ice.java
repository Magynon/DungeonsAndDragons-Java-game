package spells;

public class Ice extends Spell{
    public Ice(){
        damage = 10;
        mana = 15;
    }

    @Override
    public String toString() {
        return "Ice";
    }
}

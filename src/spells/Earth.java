package spells;

public class Earth extends Spell{
    public Earth(){
        damage = 15;
        mana = 10;
    }

    @Override
    public String toString() {
        return "Earth";
    }
}

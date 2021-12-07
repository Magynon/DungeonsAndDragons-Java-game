package characters;

public class CharacterFactory {
    public characters.Character createCharacter(String type){
        if(type == null || type.isEmpty())
            return null;
        if("Rogue".equals(type)){
            return new Rogue();
        }
        if("Warrior".equals(type)){
            return new Warrior();
        }
        if("Mage".equals(type)){
            return new Mage();
        }
        return null;
    }
}

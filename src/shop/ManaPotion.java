package shop;

public class ManaPotion implements Potion{
    int weight, price, regen;

    public ManaPotion(int price, int weight, int regen){
        this.price = price;
        this.weight = weight;
        this.regen = regen;
    }

    @Override
    public void usePotion() {

    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public int getRegen() {
        return regen;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "Mana Potion: " + weight + " grams, " + price + " coins, " + regen + " regens";
    }
}

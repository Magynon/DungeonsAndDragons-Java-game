package grid;

public class Empty implements CellElement{
    @Override
    public char toCharacter() {
        return 'N';
    }

    @Override
    public String toString() {
        return "Empty\n";
    }
}

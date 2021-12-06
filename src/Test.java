import characters.Rogue;
import exceptions.GridSizeOverflowException;
import grid.Grid;

public class Test {
    public static void main(String[] args) throws GridSizeOverflowException {
        characters.Character character = new Rogue();
        Grid grid = new Grid();
        grid = grid.genMap(10,4, character);
        System.out.println(grid.showAllGrid());

        System.out.println(grid + grid.getCurrentCell().getObj().toString() + character);
        grid.goEast();
        System.out.println(grid + grid.getCurrentCell().getObj().toString() + character);
        grid.goSouth();
        System.out.println(grid + grid.getCurrentCell().getObj().toString() + character);
        grid.goSouth();
        System.out.println(grid + grid.getCurrentCell().getObj().toString() + character);

        System.out.println("\nCurr cell: " + grid.getCurrentCell());

    }
}

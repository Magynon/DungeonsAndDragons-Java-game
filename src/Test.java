import exceptions.GridSizeOverflowException;
import grid.Grid;

public class Test {
    public static void main(String[] args) throws GridSizeOverflowException {
        Grid grid = new Grid();
        grid = grid.genMap(4,4);
        System.out.println(grid + "Curr cell: " + grid.getCurrentCell());
        grid.goEast();
        grid.goSouth();
        grid.goSouth();

        System.out.println("\nCurr cell: " + grid.getCurrentCell());

    }
}

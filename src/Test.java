import characters.CharacterFactory;
import exceptions.GridSizeOverflowException;
import grid.Grid;

import java.io.IOException;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws GridSizeOverflowException, IOException {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Choose mode:\n\t1 - Terminal\n\t2 - GUI");
        int mode = keyboard.nextInt();

        if(mode == 1) {
            CharacterFactory characterFactory = new CharacterFactory();
            characters.Character character = characterFactory.createCharacter("Rogue");
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
        else{
            System.out.println("GUI");
        }
    }
}

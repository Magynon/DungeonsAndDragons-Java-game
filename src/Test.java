import exceptions.GridSizeOverflowException;
import exceptions.InformationIncompleteException;
import exceptions.InvalidCommandException;
import exceptions.InventoryFullOrNotEnoughMoneyException;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException, InformationIncompleteException, InvalidCommandException {
        Game game = Game.getInstance();
        game.run();
    }
}

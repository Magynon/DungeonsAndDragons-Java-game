import exceptions.GridSizeOverflowException;
import exceptions.InformationIncompleteException;
import exceptions.InvalidCommandException;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws GridSizeOverflowException, IOException, InformationIncompleteException, InvalidCommandException {
        Game game = new Game();
        game.run();
    }
}

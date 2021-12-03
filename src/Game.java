import characters.*;
import exceptions.InformationIncompleteException;
import grid.Cell;
import setup.Account;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Game {
    List<Account> accountList;
    Map<Cell.CellType, List<String>> stories;

    public static void main(String[] args) throws InformationIncompleteException {
        Account account = new Account(
                new Account.Information.InformationBuilder()
                        .country("Romania")
                        .credentials("stefanmagirescu@yahoo.com", "qwerty")
                        .favoriteGames(new ArrayList<String>(Arrays.asList("minecraft", "slenderman")))
                        .name("Stefan Magirescu")
                        .build()
                ,
                new ArrayList<characters.Character>( Arrays.asList( new Rogue(), new Warrior() ) )
        );

        System.out.println(account);
    }

    public void run(){

    }

    public void showStory(){

    }
}

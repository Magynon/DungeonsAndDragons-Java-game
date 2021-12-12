import exceptions.GridSizeOverflowException;
import exceptions.InformationIncompleteException;
import exceptions.InvalidCommandException;
import grid.Cell;
import grid.CellElement;
import grid.Grid;
import json.JSONParser;
import setup.Account;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Game {
    List<Account> accountList;
    Map<Cell.CellType, List<String>> stories;

    public void run() throws IOException, InformationIncompleteException, InvalidCommandException, GridSizeOverflowException {
        Scanner keyboard = new Scanner(System.in);
        int i, input;

        // choose playing mode
        System.out.println("Choose mode:\n\t1 - Terminal\n\t2 - GUI");
        System.out.print("Enter choice: ");
        int mode = keyboard.nextInt();

        // TERMINAL MODE
        if(mode == 1) {
            // parse input
            parser();

            // choose account to play with
            System.out.println("Please choose an account from the following list:");
            for(i = 0; i < accountList.size(); i++){
                Account account = accountList.get(i);
                System.out.println("\t" + (i+1) + " " + account.getInformation().getName());
            }
            System.out.print("Enter choice: ");
            input = keyboard.nextInt();

            // validate input
            if(input > i){
                throw new InvalidCommandException();
            }
            Account account = accountList.get(input-1);

            // enter credentials
            System.out.print("Email: ");
            keyboard.nextLine();
            String email = keyboard.nextLine();
            System.out.print("Password: ");
            String password = keyboard.nextLine();

            // if credentials wrong, try again
            while(!email.equals(account.getInformation().getCredentials().getEmail())
                || !password.equals(account.getInformation().getCredentials().getPassword())){
                System.out.println("Wrong credentials, try again!");
                System.out.print("Email: ");
                email = keyboard.nextLine();
                System.out.print("Password: ");
                password = keyboard.nextLine();
            }

            System.out.println("Welcome, " + account.getInformation().getName() + "!");
            System.out.println("Choose the character to play with!");

            for(i = 0; i < account.getCharacters().size(); i++){
                System.out.println("\t" + (i+1) + " " + account.getCharacters().get(i).getName());
            }

            System.out.print("Enter choice: ");
            input = keyboard.nextInt();

            // validate input
            if(input > i){
                throw new InvalidCommandException();
            }

            // start game with account
            Grid grid = new Grid();
            grid = grid.genMap(10,4, account.getCharacters().get(input-1));
            System.out.println(grid.showAllGrid());
            System.out.println(grid + grid.getCurrentCell().getObj().toString() + grid.getCharacter());

            grid.goEast();
            System.out.println(grid + grid.getCurrentCell().getObj().toString() + grid.getCharacter());
            showStory(grid.getCurrentCell());

            grid.goSouth();
            System.out.println(grid + grid.getCurrentCell().getObj().toString() + grid.getCharacter());
            showStory(grid.getCurrentCell());

            grid.goSouth();
            System.out.println(grid + grid.getCurrentCell().getObj().toString() + grid.getCharacter());
            showStory(grid.getCurrentCell());

            System.out.println("\nCurr cell: " + grid.getCurrentCell());
        }

        // GUI MODE
        else if (mode == 2){
            System.out.println("GUI");
        }
        else{
            throw new InvalidCommandException();
        }

    }

    public void showStory(Cell element){
        System.out.println(stories.get(element.getType()).get(0));
    }

    private void parser() throws IOException, InformationIncompleteException {
        JSONParser parser = new JSONParser();
        accountList = parser.getAccountList();
        stories = parser.getStories();
    }
}

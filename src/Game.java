import characters.Character;
import exceptions.GridSizeOverflowException;
import exceptions.InformationIncompleteException;
import exceptions.InvalidCommandException;
import exceptions.InventoryFullOrNotEnoughMoneyException;
import grid.Cell;
import grid.Grid;
import json.JSONParser;
import setup.Account;
import shop.Shop;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Game {
    private static final Game game = new Game();
    private List<Account> accountList;
    private Map<Cell.CellType, List<String>> stories;
    private Scanner keyboard;

    private Game(){ }

    public static Game getInstance(){
        return game;
    }

    public void run() throws IOException, InformationIncompleteException, InvalidCommandException, GridSizeOverflowException, InventoryFullOrNotEnoughMoneyException {
        keyboard = new Scanner(System.in);
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
            keyboard.nextLine();

            // validate input
            if(input > i){
                throw new InvalidCommandException();
            }

            // start game with account
            Grid grid = Grid.getInstance();
            grid = grid.genMap(10,4, account.getCharacters().get(input-1));
            System.out.println(grid.showAllGrid());
            System.out.println(grid + grid.getCurrentCell().getObj().toString() + grid.getCharacter());

            while(true){
                System.out.print("Choose direction to move (N, S, E or W): ");
                String direction = keyboard.nextLine();
                nextMove(direction, grid);
            }
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

    public void nextMove(String direction, Grid grid) throws GridSizeOverflowException, InventoryFullOrNotEnoughMoneyException {
        switch(direction){
            case "N": grid.goNorth(); break;
            case "S": grid.goSouth(); break;
            case "E": grid.goEast(); break;
            case "W": grid.goWest(); break;
            default: System.out.println("Wrong direction: " + direction); return;
        }
        currentCellAction(grid);
    }

    public void currentCellAction(Grid grid) throws InventoryFullOrNotEnoughMoneyException {
//        if(!grid.getCurrentCell().visitedMoreThanOnce()){
//            showStory(grid.getCurrentCell());
//        }
        if(grid.getCurrentCell().getObj().toCharacter() == 'S'){
            System.out.println(grid + grid.getCurrentCell().getObj().toString() + grid.getCharacter());
            showStory(grid.getCurrentCell());
            System.out.println("Should you desire to take a look around the shop? (Y/n)");

            if(!keyboard.nextLine().equals("n")){
                Shop shop = (Shop) grid.getCurrentCell().getObj();
                Character character = grid.getCharacter();
                shop.lookAround();
                System.out.println("Do you still want to buy anything? (Y/n)");
                if(!keyboard.nextLine().equals("n")){
                    System.out.print("Choose the potion index: ");
                    int index = keyboard.nextInt() - 1;
                    character.buyPotion(shop.removePotion(index));
                }
                System.out.println("Exited store!");
            }
        }
        // TODO show enemy info
        if(grid.getCurrentCell().getObj().toCharacter() == 'E') {
            System.out.println(grid + grid.getCurrentCell().getObj().toString() + grid.getCharacter());
        }
    }

    private void parser() throws IOException, InformationIncompleteException {
        JSONParser parser = new JSONParser();
        accountList = parser.getAccountList();
        stories = parser.getStories();
    }
}

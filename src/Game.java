import characters.Character;
import characters.Enemy;
import exceptions.InformationIncompleteException;
import exceptions.InvalidCommandException;
import grid.Cell;
import grid.Grid;
import json.JSONParser;
import setup.Account;
import shop.Shop;
import spells.Spell;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private static final Game game = new Game();
    private List<Account> accountList;
    private Map<Cell.CellType, List<String>> stories;
    private Scanner keyboard;
    private boolean won = false, notDead = true;

    private Game(){ }

    public static Game getInstance(){
        return game;
    }

    public void runTest() throws InvalidCommandException, InformationIncompleteException, IOException {
        int i;

        // choose playing mode
        System.out.println("Choose mode:\n\t1 - Terminal\n\t2 - GUI");
        System.out.println("Enter choice: 1");

        // parse input
        parser();

        // choose account to play with
        System.out.println("Please choose an account from the following list:");
        for(i = 0; i < accountList.size(); i++){
            Account account = accountList.get(i);
            System.out.println("\t" + (i+1) + " " + account.getInformation().getName());
        }
        System.out.println("Enter choice: 3");
        Account account = accountList.get(2);

        // enter credentials
        System.out.println("Email: a");
        String email = "a";
        System.out.println("Password: a");
        String password = "a";

        // if credentials wrong, try again
        while(!email.equals(account.getInformation().getCredentials().getEmail())
                || !password.equals(account.getInformation().getCredentials().getPassword())){
            System.out.println("Wrong credentials, try again!");
            System.out.println("Email: ");
            System.out.println("Password: ");
        }

        System.out.println("Welcome, " + account.getInformation().getName() + "!");
        System.out.println("Choose the character to play with!");

        for(i = 0; i < account.getCharacters().size(); i++){
            System.out.println("\t" + (i+1) + " " + account.getCharacters().get(i).getName());
        }

        System.out.println("Enter choice: 1");

        System.out.println("Next, you will choose the width and height of the grid. Please be aware" +
                " that it must have at least 8 elements.");
        int width = 0;
        int height = 0;
        while(width*height < 8){
            System.out.print("Enter grid width: 5");
            width = 5;
            System.out.print("Enter grid height: 5");
            height = 5;
        }

        // start game with account
        Grid grid = Grid.getInstance();
        grid = grid.genMapTest(width, height, account.getCharacters().get(0));
        System.out.println(grid.showAllGrid());
        System.out.println(grid);

        System.out.println("Choose direction to move (N, S, E or W): E");
        nextMoveTest("E", grid);

        System.out.println("Choose direction to move (N, S, E or W): E");
        nextMoveTest("E", grid);

        // shop
        System.out.println("Choose direction to move (N, S, E or W): E");
        nextMoveTest("E", grid);

        System.out.println("Choose direction to move (N, S, E or W): E");
        nextMoveTest("E", grid);

        System.out.println("Choose direction to move (N, S, E or W): S");
        nextMoveTest("S", grid);

        System.out.println("Choose direction to move (N, S, E or W): S");
        nextMoveTest("S", grid);

        // enemy
        System.out.println("Choose direction to move (N, S, E or W): S");
        nextMoveTest("S", grid);

        if(notDead){
            // end
            System.out.println("Choose direction to move (N, S, E or W): S");
            nextMoveTest("S", grid);
        }

        System.out.println("Goodbye, " + account.getInformation().getName() + "!");
    }

    public void nextMoveTest(String direction, Grid grid){
        boolean goodMove = false;
        switch (direction) {
            case "N" -> goodMove = grid.goNorth();
            case "S" -> goodMove = grid.goSouth();
            case "E" -> goodMove = grid.goEast();
            case "W" -> goodMove = grid.goWest();
        }
        if(goodMove){
            currentCellActionTest(grid);
        }
        else{
            System.out.println("Wrong move, try again!");
        }
    }

    public void currentCellActionTest(Grid grid){
        System.out.print(grid);

        if(grid.getCurrentCell().getObj().toCharacter() == 'N' && !grid.getCurrentCell().visitedMoreThanOnce()){
            showStory(grid.getCurrentCell());
        }

        if(grid.getCurrentCell().getObj().toCharacter() == 'F'){
            System.out.println("Congratulations, you won!");
            won = true;
        }

        if(grid.getCurrentCell().getObj().toCharacter() == 'S'){
            showStory(grid.getCurrentCell());
            System.out.print("Should you desire to take a look around the shop? (Y/n) ");

            Shop shop = (Shop) grid.getCurrentCell().getObj();
            Character character = grid.getCharacter();

            shop.lookAround();
            System.out.println("Do you still want to buy anything? (Y/n) ");
            character.buyPotion(shop.removePotion(shop.getHealthPotionIndex()));
            System.out.println("Bought health potion!");

            shop.lookAround();
            System.out.println("Do you still want to buy anything? (Y/n) ");
            character.buyPotion(shop.removePotion(shop.getManaPotionIndex()));
            System.out.println("Bought mana potion!");

            System.out.println("Exited store!\n");
        }

        if(grid.getCurrentCell().getObj().toCharacter() == 'E' && !grid.getCurrentCell().visitedMoreThanOnce()) {
            Character character = grid.getCharacter();
            Enemy enemy = (Enemy) grid.getCurrentCell().getObj();

            showStory(grid.getCurrentCell());
            int index = 1;

            while(enemy.getCurrentLife() > 0 && character.getCurrentLife() > 0){
                boolean actionDone = false;

                while(!actionDone){
                    System.out.println("\nYOUR TURN! -------------------------------");
                    System.out.println("STATS:");
                    System.out.print("\tYOU: ");
                    System.out.println("life = " + character.getCurrentLife() + " mana = " + character.getCurrentMana());
                    System.out.print("\tENEMY: ");
                    System.out.println("life = " + enemy.getCurrentLife() + " mana = " + enemy.getCurrentMana());

                    System.out.println("""
                            There are 3 options:
                            \t1. attack!
                            \t2. use ability!
                            \t3. use potion!""");
                    System.out.println("Choose your move: ");

                    switch (index) {
                        case 0 -> {
                            System.out.println("You attacked the enemy!");
                            enemy.receiveDamage(character.getDamage());
                            actionDone = true;
                        }
                        case 1 -> {
                            Spell spell = character.getSpell();
                            if(spell == null){
                                System.out.println("No spells left! Try attacking or a potion.");
                                index = 2;
                                break;
                            }
                            if(spell.getMana() <= character.getCurrentMana()){
                                actionDone = true;
                            }
                            character.useAbility(spell, enemy);
                        }
                        case 2 -> {
                            if(character.getInventory().getPotionNumber() == 0){
                                System.out.println("No potions available!");
                                index = 0;
                            }
                            else{
                                character.getInventory().showPotions();
                                System.out.println("Choose potion: ");
                                System.out.println("Chose " + character.getInventory().getPotion(0));
                                character.usePotion(0);
                                actionDone = true;
                            }
                        }
                        default -> System.out.println("Invalid choice, try again!");
                    }
                }

                // enemy's turn
                if(enemy.getCurrentLife() > 0){
                    System.out.println("\nENEMY's TURN! -------------------------------");
                    System.out.println("STATS:");
                    System.out.print("\tYOU: ");
                    System.out.println("life = " + character.getCurrentLife() + " mana = " + character.getCurrentMana());
                    System.out.print("\tENEMY: ");
                    System.out.println("life = " + enemy.getCurrentLife() + " mana = " + enemy.getCurrentMana());

                    int chance = new Random().nextInt(4);
                    if(chance == 0){
                        Spell spell = enemy.getSpell();
                        if(spell == null){
                            System.out.println("Enemy attacked you!");
                            character.receiveDamage(enemy.getDamage());
                        }
                        else{
                            enemy.useAbility(spell, character);
                            if(enemy.getCurrentMana() < spell.getMana()){
                                System.out.println("Enemy attacked you!");
                                character.receiveDamage(enemy.getDamage());
                            }
                            else{
                                System.out.println("Enemy put a spell on you!");
                            }
                        }
                    }
                    else{
                        System.out.println("Enemy attacked you!");
                        character.receiveDamage(enemy.getDamage());
                    }
                }
            }
            if(enemy.getCurrentLife() <= 0){
                int earnedCoins = enemy.onDeathReturnCoins();
                character.getInventory().earnCoins(earnedCoins);
                System.out.println("Congratulations, the enemy was defeated" +
                        " and you won " + earnedCoins + " coins!");
                character.newEnemyDefeated();
            }
            else{
                System.out.println("You have been defeated. The future is black.");
                notDead = false;
            }
        }
    }

    public void run() throws IOException, InformationIncompleteException, InvalidCommandException{
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

            System.out.println("Next, you will choose the width and height of the grid. Please be aware" +
                    " that it must have at least 8 elements.");
            int width = 0;
            int height = 0;
            while(width*height < 8){
                System.out.print("Enter grid width: ");
                width = keyboard.nextInt();
                keyboard.nextLine();
                System.out.print("Enter grid height: ");
                height = keyboard.nextInt();
                keyboard.nextLine();
                if(width*height < 8){
                    System.out.println("Wrong sizes, please read the instructions above.");
                }
            }

            // start game with account
            Grid grid = Grid.getInstance();
            grid = grid.genMap(width,height, account.getCharacters().get(input-1));
            System.out.println(grid.showAllGrid());
            System.out.println(grid);

            while(!won && notDead){
                System.out.print("Choose direction to move (N, S, E or W): ");
                String direction = keyboard.nextLine();
                nextMove(direction, grid);
            }
            grid.getCharacter().incLevel();
            grid.getCharacter().updateTraitsWithLevel();

            System.out.println("Goodbye, " + account.getInformation().getName() + "!");
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
        int length = stories.get(element.getType()).size();
        int index = new Random().nextInt(length);
        System.out.println(stories.get(element.getType()).get(index));
    }

    public void nextMove(String direction, Grid grid) {
        boolean goodMove = false;
        switch (direction) {
            case "N" -> goodMove = grid.goNorth();
            case "S" -> goodMove = grid.goSouth();
            case "E" -> goodMove = grid.goEast();
            case "W" -> goodMove = grid.goWest();
        }
        if(goodMove){
            currentCellAction(grid);
        }
        else{
            System.out.println("Wrong move, try again!");
        }
    }

    public void currentCellAction(Grid grid) {
        System.out.print(grid);

        if(grid.getCurrentCell().getObj().toCharacter() == 'N' && !grid.getCurrentCell().visitedMoreThanOnce()){
            showStory(grid.getCurrentCell());
        }

        if(grid.getCurrentCell().getObj().toCharacter() == 'F'){
            System.out.println("Congratulations, you won!");
            won = true;
        }

        if(grid.getCurrentCell().getObj().toCharacter() == 'S'){
            showStory(grid.getCurrentCell());
            System.out.print("Should you desire to take a look around the shop? (Y/n) ");

            if(!keyboard.nextLine().equals("n")){
                Shop shop = (Shop) grid.getCurrentCell().getObj();
                Character character = grid.getCharacter();
                shop.lookAround();
                System.out.print("Do you still want to buy anything? (Y/n) ");
                while(!keyboard.nextLine().equals("n")){
                    System.out.print("Choose the potion index: ");
                    int index = keyboard.nextInt() - 1;
                    character.buyPotion(shop.removePotion(index));
                    System.out.println("Bought potion " + (index+1) + "!");

                    if(shop.isEmpty()){
                        break;
                    }

                    shop.lookAround();
                    System.out.print("Do you still want to buy anything? (Y/n) ");
                    keyboard.nextLine();
                }
                System.out.println("Exited store!\n");
            }
        }

        if(grid.getCurrentCell().getObj().toCharacter() == 'E' && !grid.getCurrentCell().visitedMoreThanOnce()) {
            Character character = grid.getCharacter();
            Enemy enemy = (Enemy) grid.getCurrentCell().getObj();

            showStory(grid.getCurrentCell());
            while(enemy.getCurrentLife() > 0 && character.getCurrentLife() > 0){
                boolean actionDone = false;

                while(!actionDone){
                    System.out.println("lnYOUR TURN! -------------------------------");
                    System.out.println("STATS:");
                    System.out.print("\tYOU: ");
                    System.out.println("life = " + character.getCurrentLife() + " mana = " + character.getCurrentMana());
                    System.out.print("\tENEMY: ");
                    System.out.println("life = " + enemy.getCurrentLife() + " mana = " + enemy.getCurrentMana());
                    System.out.println("""
                            There are 3 options:
                            \t1. attack!
                            \t2. use ability!
                            \t3. use potion!""");
                    System.out.print("Choose your move: ");
                    int index = keyboard.nextInt() - 1;
                    switch (index) {
                        case 0 -> {
                            enemy.receiveDamage(character.getDamage());
                            actionDone = true;
                        }
                        case 1 -> {
                            Spell spell = character.getSpell();
                            if(spell == null){
                                System.out.println("No spells left! Try attacking or a potion.");
                                break;
                            }
                            if(spell.getMana() <= character.getCurrentMana()){
                                actionDone = true;
                            }
                            character.useAbility(spell, enemy);
                        }
                        case 2 -> {
                            if(character.getInventory().getPotionNumber() == 0){
                                System.out.println("No potions available!");
                            }
                            else{
                                character.getInventory().showPotions();
                                System.out.print("Choose potion: ");
                                int potionIndex = keyboard.nextInt() - 1;
                                System.out.println("Chose " + character.getInventory().getPotion(potionIndex));
                                character.usePotion(potionIndex);
                                actionDone = true;
                            }
                        }
                        default -> System.out.println("Invalid choice, try again!");
                    }
                }

                // enemy's turn
                if(enemy.getCurrentLife() > 0){
                    System.out.println("\nENEMY's TURN! -------------------------------");
                    System.out.println("STATS:");
                    System.out.print("\tYOU: ");
                    System.out.println("life = " + character.getCurrentLife() + " mana = " + character.getCurrentMana());
                    System.out.print("\tENEMY: ");
                    System.out.println("life = " + enemy.getCurrentLife() + " mana = " + enemy.getCurrentMana());

                    int chance = new Random().nextInt(4);
                    if(chance == 0){
                        Spell spell = enemy.getSpell();
                        if(spell == null){
                            System.out.println("Enemy attacked you!");
                            character.receiveDamage(enemy.getDamage());
                        }
                        else{
                            enemy.useAbility(spell, character);
                            if(enemy.getCurrentMana() < spell.getMana()){
                                System.out.println("Enemy attacked you!");
                                character.receiveDamage(enemy.getDamage());
                            }
                            else{
                                System.out.println("Enemy put a spell on you!");
                            }
                        }
                    }
                    else{
                        System.out.println("Enemy attacked you!");
                        character.receiveDamage(enemy.getDamage());
                    }
                }
            }
            if(enemy.getCurrentLife() <= 0){
                int earnedCoins = enemy.onDeathReturnCoins();
                character.getInventory().earnCoins(earnedCoins);
                System.out.println("Congratulations, the enemy was defeated" +
                        " and you won " + earnedCoins + " coins!");
                character.newEnemyDefeated();
            }
            else{
                System.out.println("You have been defeated. The future is black.");
                notDead = false;
            }
            keyboard.nextLine();
        }
    }

    private void parser() throws IOException, InformationIncompleteException {
        JSONParser parser = new JSONParser();
        accountList = parser.getAccountList();
        stories = parser.getStories();
    }
}

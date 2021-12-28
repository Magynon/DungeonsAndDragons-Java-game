package GUI;

import characters.Character;
import characters.Enemy;
import grid.Cell;
import grid.Grid;
import shop.Shop;
import spells.Spell;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class GameBoardFrame extends JFrame{
    private final int gridWidth;
    private final int gridHeight;
    private final characters.Character character;
    private boolean won = false, notDead = true;
    private final Map<Cell.CellType, List<String>> stories;
    private Scanner keyboard;

    public GameBoardFrame(int gridWidth, int gridHeight, characters.Character character, Map<Cell.CellType, List<String>> stories){
        super("World Of Marcel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 800;
        int height = 700;
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.character = character;
        this.stories = stories;

        setMinimumSize(new Dimension(width, height));
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        runGUI();

        setVisible(true);
        pack();
        setLocationRelativeTo(null);
    }
    public void runGUI(){
        // start game with account
        Grid grid = Grid.getInstance();
        grid = grid.genMap(gridWidth, gridHeight, character);
        System.out.println(grid.showAllGrid());
        System.out.println(grid);

        while(!won && notDead){
            System.out.print("Choose direction to move (N, S, E or W): ");
            String direction = "TODO direction";
            nextMove(direction, grid);
        }
        grid.getCharacter().incLevel();
        grid.getCharacter().updateTraitsWithLevel();

        System.out.println("Goodbye!");
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
}

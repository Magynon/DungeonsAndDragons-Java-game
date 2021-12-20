package grid;

import characters.Character;
import characters.Enemy;
import exceptions.GridSizeOverflowException;
import shop.Shop;

import java.util.ArrayList;
import java.util.Random;

public class Grid extends ArrayList<ArrayList<Cell>> {
    private static Grid grid = new Grid();
    private int height, width;
    private Cell currentCell;
    private characters.Character character;

    private Grid(){
    }

    public static Grid getInstance(){
        return grid;
    }

    public Grid genMap(int width, int height, characters.Character character){
        this.character = character;
        this.width = width;
        this.height = height;
        Random rand = new Random();

        int enemies = rand.nextInt(width*height - 6) + 4;
        System.out.println(enemies + " enemies");
        int shops = rand.nextInt(width*height - enemies - 2) + 2;
        System.out.println(shops + " shops");

        // initialise cells
        for(int i = 0; i < height; i++){
            add(new ArrayList<>(width));
            for(int j = 0; j < width; j++){
                get(i).add(new Cell(j, i));
                get(i).get(j).setType(Cell.CellType.EMPTY);
                get(i).get(j).setObj(new Empty());
            }
        }
        currentCell = get(0).get(0);
        currentCell.visit();

        get(height-1).get(width-1).setType(Cell.CellType.FINISH);
        get(height-1).get(width-1).setObj(new Finish());

        // generate at least 4 enemies
        for(int i = 0; i < enemies; i++){
            int x = rand.nextInt(width);
            int y = rand.nextInt(height);

            while(get(y).get(x).getType() != Cell.CellType.EMPTY ||
                    (x == width-1 && y == height-1) || (x == 0 && y == 0)){
                x = rand.nextInt(width);
                y = rand.nextInt(height);
            }
            get(y).get(x).setType(Cell.CellType.ENEMY);
            get(y).get(x).setObj(new Enemy());
        }

        // generate at least 2 shops
        for(int i = 0; i < shops; i++){
            int x = rand.nextInt(width);
            int y = rand.nextInt(height);

            while(get(y).get(x).getType() != Cell.CellType.EMPTY ||
                    (x == width-1 && y == height-1) || (x == 0 && y == 0)){
                x = rand.nextInt(width);
                y = rand.nextInt(height);
            }
            get(y).get(x).setType(Cell.CellType.SHOP);
            get(y).get(x).setObj(new Shop());
        }
        return this;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public Character getCharacter() {
        return character;
    }

    // check if coins are available in cell, in case it is empty
    private void coinsAvailableIfEmpty(Cell cell){
        if(currentCell.getType() == Cell.CellType.EMPTY){
            int random = new Random().nextInt(5);
            if(random == 1){
                random = new Random().nextInt(20);
                character.getInventory().earnCoins(random);
            }
        }
    }

    public void goNorth() throws GridSizeOverflowException {
        if(currentCell.getOy() - 1 >= 0) {
            currentCell = get(currentCell.getOy() - 1).get(currentCell.getOx());
            currentCell.visit();
            currentCell.incrementTimesVisited();
            coinsAvailableIfEmpty(currentCell);
            character.setCurrentCoordinates(currentCell);
        }
        else
            throw new GridSizeOverflowException("Can't go north!");
    }

    public void goSouth() throws GridSizeOverflowException {
        int random;
        if(currentCell.getOy() + 1 < height) {
            currentCell = get(currentCell.getOy() + 1).get(currentCell.getOx());
            currentCell.visit();
            currentCell.incrementTimesVisited();
            coinsAvailableIfEmpty(currentCell);
            character.setCurrentCoordinates(currentCell);
        }
        else
            throw new GridSizeOverflowException("Can't go south!");
    }

    public void goEast() throws GridSizeOverflowException {
        if(currentCell.getOx() + 1 < width) {
            currentCell = get(currentCell.getOy()).get(currentCell.getOx() + 1);
            currentCell.visit();
            currentCell.incrementTimesVisited();
            coinsAvailableIfEmpty(currentCell);
            character.setCurrentCoordinates(currentCell);
        }
        else
            throw new GridSizeOverflowException("Can't go east!");
    }

    public void goWest() throws GridSizeOverflowException {
        if(currentCell.getOx() - 1 >= 0) {
            currentCell = get(currentCell.getOy()).get(currentCell.getOx() - 1);
            currentCell.visit();
            currentCell.incrementTimesVisited();
            coinsAvailableIfEmpty(currentCell);
            character.setCurrentCoordinates(currentCell);
        }
        else
            throw new GridSizeOverflowException("Can't go west!");
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                if(get(i).get(j).isVisited()){
                    out.append(get(i).get(j).getObj().toCharacter()).append(" ");
                }
                else{
                    out.append("? ");
                }
            }
            out.append("\n");
        }
        return out.toString();
    }
    public String showAllGrid(){
        StringBuilder out = new StringBuilder();
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                out.append(get(i).get(j).getObj().toCharacter()).append(" ");
            }
            out.append("\n");
        }
        return out.toString();
    }
}

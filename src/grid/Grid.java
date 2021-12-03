package grid;

import exceptions.GridSizeOverflowException;

import java.util.ArrayList;
import java.util.Random;

public class Grid extends ArrayList<ArrayList<Cell>> {
    private int height, width;
    private Cell currentCell = new Cell(0,0);
    private characters.Character character;

    public Grid(){
    }

    public Grid genMap(int width, int height){
        this.width = width;
        this.height = height;
        Random rand = new Random();

        int enemies = rand.nextInt(width*height - 6) + 4;
        System.out.println(enemies + " enemies");
        int shops = rand.nextInt(width*height - enemies - 2) + 2;
        System.out.println(shops + " shops");

        // initialise cells
        for(int i = 0; i < height; i++){
            add(new ArrayList<Cell>(width));
            for(int j = 0; j < width; j++){
                get(i).add(new Cell(j, i));
                get(i).get(j).setType(Cell.CellType.EMPTY);
            }
        }

        get(height-1).get(width-1).setType(Cell.CellType.FINISH);

        // generate at least 4 enemies
        for(int i = 0; i < enemies; i++){
            int x = rand.nextInt(width);
            int y = rand.nextInt(height);

            while(get(y).get(x).getType() != Cell.CellType.EMPTY || (x == width-1 && y == height-1)){
                x = rand.nextInt(width);
                y = rand.nextInt(height);
            }
            get(y).get(x).setType(Cell.CellType.ENEMY);
        }

        // generate at least 2 shops
        for(int i = 0; i < shops; i++){
            int x = rand.nextInt(width);
            int y = rand.nextInt(height);

            while(get(y).get(x).getType() != Cell.CellType.EMPTY || (x == width-1 && y == height-1)){
                x = rand.nextInt(width);
                y = rand.nextInt(height);
            }
            get(y).get(x).setType(Cell.CellType.SHOP);
        }
        return this;
    }

    public Cell getCurrentCell() {
        return currentCell;
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
            coinsAvailableIfEmpty(currentCell);
        }
        else
            throw new GridSizeOverflowException("Can't go north!");
    }

    public void goSouth() throws GridSizeOverflowException {
        int random;
        if(currentCell.getOy() + 1 < height) {
            currentCell = get(currentCell.getOy() + 1).get(currentCell.getOx());
            coinsAvailableIfEmpty(currentCell);
        }
        else
            throw new GridSizeOverflowException("Can't go south!");
    }

    public void goEast() throws GridSizeOverflowException {
        if(currentCell.getOx() + 1 < width) {
            currentCell = get(currentCell.getOy()).get(currentCell.getOx() + 1);
            coinsAvailableIfEmpty(currentCell);
        }
        else
            throw new GridSizeOverflowException("Can't go east!");
    }

    public void goWest() throws GridSizeOverflowException {
        if(currentCell.getOx() - 1 >= 0) {
            currentCell = get(currentCell.getOy()).get(currentCell.getOx() - 1);
            coinsAvailableIfEmpty(currentCell);
        }
        else
            throw new GridSizeOverflowException("Can't go west!");
    }
}

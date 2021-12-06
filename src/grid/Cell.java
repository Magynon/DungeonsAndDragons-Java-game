package grid;

public class Cell {
    private final int Ox;
    private final int Oy;
    private CellElement obj;
    private boolean visited;
    public enum CellType {
        EMPTY,
        ENEMY,
        SHOP,
        FINISH
    }
    CellType type;

    public Cell(int Ox, int Oy){
        this.Ox = Ox;
        this.Oy = Oy;
        visited = false;
    }

    public void setType(CellType type) {
        this.type = type;
    }

    public void visit(){
        visited = true;
    }

    public boolean isVisited(){
        return visited;
    }

    public CellType getType(){
        return type;
    }

    public int getOx() {
        return Ox;
    }

    public int getOy() {
        return Oy;
    }

    public CellElement getObj() {
        return obj;
    }

    public void setObj(CellElement obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return Ox +
                "," + Oy +
                "," + type + " ";
    }
}

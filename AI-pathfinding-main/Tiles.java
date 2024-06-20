import java.util.ArrayList;

public class Tiles {
    private int x, y, state;
    private String designation;
    private Tiles[][] tileset;

    public Tiles(int x, int y, String designation, Tiles[][] tileset) {
        this.x = x;
        this.y = y;
        this.designation = designation;
        this.tileset = tileset;
        this.state = 0; // Default state
    }

    public String returnDesign() {
        return this.designation;
    }

    public int returnstate() {
        return this.state;
    }

    public int returnx() {
        return this.x;
    }

    public int returny() {
        return this.y;
    }

    public void setstate(int i) {
        this.state = i;
    }

    public void setx(int number) {
        this.x = number;
    }

    public void sety(int number) {
        this.y = number;
    }

    public void setdesignation(String designation) {
        this.designation = designation;
    }

    // Static method to calculate distance between two tiles
    public static double distance(Tiles tile1, Tiles tile2) {
        return Math.sqrt(Math.pow(tile1.returnx() - tile2.returnx(), 2) + Math.pow(tile1.returny() - tile2.returny(), 2));
    }

    // Method to mark a tile as a wall
    public void setWall() {
        this.state = 9; // 9 represents a wall state
    }
}

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

    public void detecting_adjacenttile(Tiles targetTile, ArrayList<Tiles> tile_path, Tiles[][] tileset) {
        ArrayList<Tiles> adjacentTiles = new ArrayList<>();
        if (this.x > 0) adjacentTiles.add(tileset[this.y][this.x - 1]); // Left tile
        if (this.x < tileset[0].length - 1) adjacentTiles.add(tileset[this.y][this.x + 1]); // Right tile
        if (this.y > 0) adjacentTiles.add(tileset[this.y - 1][this.x]); // Up tile
        if (this.y < tileset.length - 1) adjacentTiles.add(tileset[this.y + 1][this.x]); // Down tile

        Tiles nextTile = null;
        for (Tiles adjTile : adjacentTiles) {
            if (adjTile != null && adjTile.returnstate() != 3 && adjTile.returnstate() != 9 && !tile_path.contains(adjTile)) {
                if (nextTile == null || Tiles.distance(targetTile, adjTile) < Tiles.distance(targetTile, nextTile)) {
                    nextTile = adjTile;
                }
            }
        }

        if (nextTile != null) {
            tile_path.add(nextTile);
            nextTile.setstate(3);  // Mark as part of the path
        }
    }
}

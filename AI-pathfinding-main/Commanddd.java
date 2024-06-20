import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Commanddd {

    public boolean add_effy(Tiles[][] tileset, int y, int x, int tilesizey, int tilesizex) {
        if (y < tilesizey) {
            add_effx(tileset, y, x, tilesizex);
            return add_effy(tileset, y + 1, x, tilesizey, tilesizex);
        } else {
            return true;
        }
    }

    public boolean add_effx(Tiles[][] tileset, int y, int x, int tilesizex) {
        if (x < tilesizex) {
            String desig = Integer.toString(x + 1) + Integer.toString(y + 1);
            tileset[y][x] = new Tiles(x, y, desig, tileset);
            return add_effx(tileset, y, x + 1, tilesizex);
        } else {
            return true;
        }
    }

    public boolean pathfind(ArrayList<Tiles> tile_path, Tiles[][] tileset, int targety, int targetx) {
        long startTime = System.nanoTime(); // Start time
        Runtime runtime = Runtime.getRuntime();
        long startMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        System.gc();
        Queue<Tiles> queue = new LinkedList<>();
        Tiles startTile = tile_path.get(0);
        queue.add(startTile);

        boolean[][] visited = new boolean[tileset.length][tileset[0].length];
        visited[startTile.returny()][startTile.returnx()] = true;

        Tiles[][] previousTile = new Tiles[tileset.length][tileset[0].length];

        while (!queue.isEmpty()) {
            Tiles currentTile = queue.poll();
            if (currentTile.returnx() == targetx - 1 && currentTile.returny() == targety - 1) {
                // Reconstruct path
                Tiles pathTile = currentTile;
                while (pathTile != null) {
                    tile_path.add(0, pathTile);
                    pathTile = previousTile[pathTile.returny()][pathTile.returnx()];
                }

                long endTime = System.nanoTime(); // End time
                long endMemory = runtime.totalMemory() - runtime.freeMemory(); // End memory

                long timeTaken = endTime - startTime;
                long memoryUsed = endMemory - startMemory;

                System.out.println("Pathfinding Time: " + timeTaken + " nanoseconds");
                System.out.println("Pathfinding Memory: " + memoryUsed + " bytes");

                return true;
            }

            // Add adjacent tiles to the queue
            addAdjacentTiles(queue, tileset, visited, previousTile, currentTile);
        }

        long endTime = System.nanoTime(); // End time
        long endMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long timeTaken = endTime - startTime;
        long memoryUsed = Math.abs(endMemory - startMemory); // Make the memory used absolute

        System.out.println("Pathfinding Time: " + timeTaken + " nanoseconds");
        System.out.println("Pathfinding Memory Used: " + memoryUsed + " bytes");

        return false;
    }

    private void addAdjacentTiles(Queue<Tiles> queue, Tiles[][] tileset, boolean[][] visited, Tiles[][] previousTile, Tiles currentTile) {
        int x = currentTile.returnx();
        int y = currentTile.returny();

        // Check adjacent tiles
        if (x > 0 && !visited[y][x - 1] && tileset[y][x - 1].returnstate() != 9) {
            queue.add(tileset[y][x - 1]);
            visited[y][x - 1] = true;
            previousTile[y][x - 1] = currentTile;
        }
        if (x < tileset[0].length - 1 && !visited[y][x + 1] && tileset[y][x + 1].returnstate() != 9) {
            queue.add(tileset[y][x + 1]);
            visited[y][x + 1] = true;
            previousTile[y][x + 1] = currentTile;
        }
        if (y > 0 && !visited[y - 1][x] && tileset[y - 1][x].returnstate() != 9) {
            queue.add(tileset[y - 1][x]);
            visited[y - 1][x] = true;
            previousTile[y - 1][x] = currentTile;
        }
        if (y < tileset.length - 1 && !visited[y + 1][x] && tileset[y + 1][x].returnstate() != 9) {
            queue.add(tileset[y + 1][x]);
            visited[y + 1][x] = true;
            previousTile[y + 1][x] = currentTile;
        }
    }

    public void print_path(ArrayList<Tiles> tile_path) {
        for (Tiles tile : tile_path) {
            System.out.println(tile.returnDesign());
        }
    }

    public boolean print_result(Tiles[][] tileset, int starty, int startx, int targety, int targetx) {
        ArrayList<Tiles> tile_path = new ArrayList<>();
        tile_path.add(tileset[starty][startx]);

        boolean path_found = pathfind(tile_path, tileset, targety, targetx);
        if (path_found) {
            print_path(tile_path);
            return true;
        } else {
            System.out.println("No path found.");
            return false;
        }
    }
}

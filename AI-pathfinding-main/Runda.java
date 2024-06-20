import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Runda extends JFrame {
    private static int gridSizeX;
    private static int gridSizeY;
    private static Tiles[][] grid;
    private static Commanddd command = new Commanddd();
    private static ArrayList<Tiles> tilePath = new ArrayList<>();
    private static int startX, startY, targetX, targetY;

    public Runda() {
        setTitle("Pathfinding Visualization");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        add(new GridPanel());
    }

    public static void main(String[] args) {
        // Prompt user for grid size, start and target coordinates
        try {
            gridSizeX = Integer.parseInt(JOptionPane.showInputDialog("Enter the grid width (x): "));
            gridSizeY = Integer.parseInt(JOptionPane.showInputDialog("Enter the grid height (y): "));
            startX = Integer.parseInt(JOptionPane.showInputDialog("Enter the start X coordinate: "));
            startY = Integer.parseInt(JOptionPane.showInputDialog("Enter the start Y coordinate: "));
            targetX = Integer.parseInt(JOptionPane.showInputDialog("Enter the target X coordinate: "));
            targetY = Integer.parseInt(JOptionPane.showInputDialog("Enter the target Y coordinate: "));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter integers only.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        grid = new Tiles[gridSizeY][gridSizeX];
        // Initialize tile set
        command.add_effy(grid, 0, 0, gridSizeY, gridSizeX);

        // Set walls in the grid1
        setWalls();

        // Set the start tile
        Tiles startTile = grid[startY][startX];
        startTile.setstate(3);  // Assuming 3 represents the starting state
        tilePath.add(startTile);

        // Pathfinding to the target
        boolean pathFound = command.pathfind(tilePath, grid, targetY, targetX);

        // Print the path if found
        if (pathFound) {
            System.out.println("Path found:");
            for (Tiles tile : tilePath) {
                if (tile.returnx() == startX && tile.returny() == startY) {
                    System.out.println(tile.returnDesign() + " start");
                } else if (tile.returnx() == targetX && tile.returny() == targetY) {
                    System.out.println(tile.returnDesign() + " target");
                } else {
                    System.out.println(tile.returnDesign());
                }
            }
        } else {
            System.out.println("No path found.");
        }

        // Display the visualization
        SwingUtilities.invokeLater(() -> {
            Runda runda = new Runda();
            runda.setVisible(true);
        });
    }

    private static void setWalls() {
        // Prompt user to specify wall positions
        while (true) {
            String inputX = JOptionPane.showInputDialog("Enter wall X coordinate (or type 'done' to finish): ");
            if (inputX.equalsIgnoreCase("done")) {
                break;
            }

            try {
                int x = Integer.parseInt(inputX);
                if (x < 0 || x >= gridSizeX) {
                    JOptionPane.showMessageDialog(null, "Invalid X coordinate. Please enter a valid coordinate.", "Error", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                String inputY = JOptionPane.showInputDialog("Enter wall Y coordinate (or type 'done' to finish): ");
                if (inputY.equalsIgnoreCase("done")) {
                    break;
                }

                try {
                    int y = Integer.parseInt(inputY);
                    if (y < 0 || y >= gridSizeY) {
                        JOptionPane.showMessageDialog(null, "Invalid Y coordinate. Please enter a valid coordinate.", "Error", JOptionPane.ERROR_MESSAGE);
                        continue;
                    }

                    // Set the tile at (y, x) as a wall
                    grid[y][x].setWall();

                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter integers only for Y coordinate.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter integers only for X coordinate.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class GridPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int cellWidth = getWidth() / gridSizeX;
            int cellHeight = getHeight() / gridSizeY;

            for (int y = 0; y < gridSizeY; y++) {
                for (int x = 0; x < gridSizeX; x++) {
                    Tiles tile = grid[y][x];
                    if (tile.returnstate() == 9) {
                        g.setColor(Color.BLACK);
                    } else if (tilePath.contains(tile)) {
                        g.setColor(Color.GREEN);
                    } else {
                        g.setColor(Color.WHITE);
                    }
                    g.fillRect(x * cellWidth, y * cellHeight, cellWidth, cellHeight);
                    g.setColor(Color.GRAY);
                    g.drawRect(x * cellWidth, y * cellHeight, cellWidth, cellHeight);
                }
            }
        }
    }
}

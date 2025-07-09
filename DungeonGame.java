public class DungeonGame{

      // Function to calculate the minimum initial health needed to reach the bottom-right corner
    public int calculateMinimumHP(int[][] dungeon) {
        // Dimensions of the dungeon
        int rows = dungeon.length;
        int cols = dungeon[0].length;
      
        // Dynamic programming table where each cell represents the minimum health needed
        int[][] minHealth = new int[rows + 1][cols + 1];
      
        // Initialize the dp table with high values except the border cells right and below the dungeon
        for (int[] row : minHealth) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
      
        // Initialization for the border cells where the hero can reach the princess with 1 health point
        minHealth[rows][cols - 1] = minHealth[rows - 1][cols] = 1;
      
        // Start from the bottom-right corner of the dungeon and move leftward and upward
        for (int i = rows - 1; i >= 0; --i) {
            for (int j = cols - 1; j >= 0; --j) {
                // The health at the current cell is the minimum health needed from the next step minus the current cell's effect
                // It should be at least 1 for the hero to be alive
                int healthNeeded = Math.min(minHealth[i + 1][j], minHealth[i][j + 1]) - dungeon[i][j];
                minHealth[i][j] = Math.max(1, healthNeeded);
            }
        }
      
        // The result is the minimum health needed at the starting cell
        return minHealth[0][0];
    }
}
}

/**
 * The Game class manages the simulation of the cellular automaton.
 * It initializes the board and runs the simulation for a specified number of generations.
 */
public class Game {
    private Board board;
    private int maxGen;

    /**
     * Constructs a Game with the specified parameters.
     *
     * @param rows  the number of rows in the board
     * @param cols  the number of columns in the board
     * @param seed  the seed for random number generation
     * @param range the range for random number generation
     * @param maxGen the maximum number of generations to run the game
     */
    public Game(int rows, int cols, int seed, int range, int maxGen) {
        board = new Board(rows, cols, seed, range);
        this.maxGen = maxGen;
    }

    /**
     * Runs the game for the specified number of generations or until the cells stabilize or die out.
     */
    public void runGame() {
        int i = 0;
        Board previousBoard = null;
        Board currentBoard = board.clone();

        while (i <= maxGen) {
            System.out.println("Generation " + i + ":");
            System.out.println(currentBoard.toString());

            // Clone the current board to keep track of the previous state
            Board newBoard = currentBoard.clone();
            currentBoard.nextGeneration();

            // Check if the new board is equal to the previous board
            if (previousBoard != null && currentBoard.equals(previousBoard)) {
                System.out.println("Cells have stabilized.");
                return;
            }

            // Check if all cells are dead
            if (currentBoard.isDead()) {
                System.out.println("Generation " + (i + 1) + ":");
                System.out.println(currentBoard.toString());
                System.out.println("All cells are dead.");
                return;
            }

            // Update previousBoard to the current state for the next iteration
            previousBoard = newBoard;
            i++;
        }
        System.out.println("The generation limitation was reached.");
    }
}

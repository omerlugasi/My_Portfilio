import java.io.Serializable;
import java.util.Random;

/**
 * The Board class represents a grid of cells for the simulation.
 * It contains methods to initialize the board, progress to the next generation,
 * and perform various checks and operations on the board.
 */
public class Board implements Cloneable {
   private Cell[][] board;
   private int rows;
   private int cols;
   private int range;
   private int seed;

   /**
    * Constructs a Board with the given number of rows, columns, seed, and range.
    *
    * @param rows  the number of rows in the board
    * @param cols  the number of columns in the board
    * @param seed  the seed for random number generation
    * @param range the range for random number generation
    */
   public Board(int rows, int cols, int seed, int range) {
      board = new Cell[rows][cols];
      Random rand = new Random(seed);
      for (int i = 0; i < rows; i++) {
         for (int j = 0; j < cols; j++) {
            int random_num = rand.nextInt(range);
            if (random_num % 2 == 0) {
               board[i][j] = new DeadCell();
            } else {
               board[i][j] = new HealthyCell();
            }
         }
      }
      this.rows = rows;
      this.cols = cols;
      this.range = range;
      this.seed = seed;
   }

   /**
    * Progresses the board to the next generation by applying the rules of the simulation.
    */
   public void nextGeneration() {
      Cell[][] newBoard = new Cell[rows][cols];
      for (int i = 0; i < board.length; i++) {
         for (int j = 0; j < board[i].length; j++) {
            int health_count = 0;
            int ill_count = 0;
            for (int k = i - 1; k <= i + 1; k++) {
               for (int l = j - 1; l <= j + 1; l++) {
                  if (k == i && l == j) {
                     continue;
                  }
                  if (inBounds(k, l)) {
                     if (board[k][l] instanceof HealthyCell) {
                        health_count++;
                     } else if (board[k][l] instanceof IllCell) {
                        ill_count++;
                     }
                  }
               }
            }
            newBoard[i][j] = board[i][j].nextGeneration(health_count, ill_count);
         }
      }
      board = newBoard;
   }

   /**
    * Checks if the given coordinates are within the bounds of the board.
    *
    * @param row the row index to check
    * @param col the column index to check
    * @return true if the coordinates are within bounds, false otherwise
    */
   public boolean inBounds(int row, int col) {
      return row >= 0 && row < board.length && col >= 0 && col < board[row].length;
   }

   /**
    * Returns a string representation of the board.
    *
    * @return the string representation of the board
    */
   @Override
   public String toString() {
      String representBoard = "";
      for (int i = 0; i < board.length; i++) {
         for (int j = 0; j < board[0].length; j++) {
            representBoard += board[i][j].toString();
            if (j < board[0].length - 1) {
               representBoard += " ";
            }
         }
         if (i < board.length - 1) {
            representBoard += "\n";
         }
      }
      return representBoard;
   }

   /**
    * Checks if this board is equal to another object.
    *
    * @param obj the object to compare with
    * @return true if the boards are equal, false otherwise
    */
   @Override
   public boolean equals(Object obj) {
      if (!(obj instanceof Board)) {
         return false;
      }
      Board other = (Board) obj;
      for (int i = 0; i < board.length; i++) {
         for (int j = 0; j < board[i].length; j++) {
            if (!board[i][j].equals(other.board[i][j])) {
               return false;
            }
         }
      }
      return true;
   }

   /**
    * Checks if all cells on the board are dead.
    *
    * @return true if all cells are dead, false otherwise
    */
   public boolean isDead() {
      for (int i = 0; i < board.length; i++) {
         for (int j = 0; j < board[i].length; j++) {
            if (!(board[i][j] instanceof DeadCell)) {
               return false;
            }
         }
      }
      return true;
   }

   /**
    * Creates and returns a copy of this board.
    *
    * @return the cloned board
    */
   @Override
   public Board clone() {
      Board newBoard = new Board(rows, cols, seed, range);
      for (int i = 0; i < board.length; i++) {
         for (int j = 0; j < board[i].length; j++) {
            newBoard.board[i][j] = board[i][j].clone();
         }
      }
      return newBoard;
   }
}

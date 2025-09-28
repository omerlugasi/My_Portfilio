/**
 * The Cell class represents a cell in the simulation.
 * It is an abstract class that provides the basic structure and common methods
 * for different types of cells.
 */
abstract public class Cell {
    public String type;

    /**
     * Determines the next generation of this cell based on the number of healthy and ill cells around it.
     *
     * @param health_num the number of healthy cells around this cell
     * @param ill_num the number of ill cells around this cell
     * @return the next generation of this cell
     */
    protected abstract Cell nextGeneration(int health_num, int ill_num);

    /**
     * Returns a string representation of the cell.
     *
     * @return the string representation of the cell
     */
    @Override
    public String toString(){
        return type;
    }

    /**
     * Checks if this cell is equal to another object.
     *
     * @param obj the object to compare with
     * @return true if the cells are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Cell)) {
            return false;
        }
        return obj.toString().equals(toString());
    }

    /**
     * Creates and returns a copy of this cell.
     *
     * @return the cloned cell
     * @throws RuntimeException if cloning is not supported
     */
    @Override
    public Cell clone() {
        try {
            return (Cell) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Cloning not supported", e);
        }
    }
}

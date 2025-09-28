/**
 * The HealthyCell class represents a healthy cell in the simulation.
 * It determines its next generation state based on the number of healthy and ill cells around it.
 */
public class HealthyCell extends Cell {

    /**
     * Constructs a HealthyCell with its type set to "H".
     */
    public HealthyCell() {
        super.type = "H";
    }

    /**
     * Determines the next generation of this cell based on the number of healthy and ill cells around it.
     * If there are fewer than 2 healthy cells, more than 3 healthy cells, or more than 3 ill cells around,
     * it becomes an IllCell; otherwise, it remains a HealthyCell.
     *
     * @param health_num the number of healthy cells around this cell
     * @param ill_num the number of ill cells around this cell
     * @return the next generation of this cell
     */
    @Override
    protected Cell nextGeneration(int health_num, int ill_num) {
        if ((health_num < 2) || (health_num > 3) || (ill_num > 3)) {
            return new IllCell();
        }
        return new HealthyCell();
    }

    /**
     * Creates and returns a copy of this HealthyCell.
     *
     * @return the cloned HealthyCell
     */
    @Override
    public HealthyCell clone() {
        return new HealthyCell();
    }
}

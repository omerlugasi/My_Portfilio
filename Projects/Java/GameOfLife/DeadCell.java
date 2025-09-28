/**
 * The DeadCell class represents a dead cell in the simulation.
 * It determines its next generation state based on the number of healthy and ill cells around it.
 */
public class DeadCell extends Cell {

    /**
     * Constructs a DeadCell with its type set to "-".
     */
    public DeadCell() {
        super.type = "-";
    }

    /**
     * Determines the next generation of this cell based on the number of healthy and ill cells around it.
     * If there are exactly 3 healthy cells around, it becomes a HealthyCell; otherwise, it remains a DeadCell.
     *
     * @param health_num the number of healthy cells around this cell
     * @param ill_num the number of ill cells around this cell
     * @return the next generation of this cell
     */
    @Override
    protected Cell nextGeneration(int health_num, int ill_num) {
        if (health_num == 3) {
            return new HealthyCell();
        }
        return new DeadCell();
    }

    /**
     * Creates and returns a copy of this DeadCell.
     *
     * @return the cloned DeadCell
     */
    @Override
    public DeadCell clone() {
        return new DeadCell();
    }
}

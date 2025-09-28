/**
 * The IllCell class represents an ill cell in the simulation.
 * It determines its next generation state based on the number of healthy and ill cells around it.
 */
public class IllCell extends Cell {

    /**
     * Constructs an IllCell with its type set to "S".
     */
    public IllCell() {
        super.type = "S";
    }

    /**
     * Determines the next generation of this cell based on the number of healthy and ill cells around it.
     * If there are fewer than 2 healthy cells, more than 3 healthy cells, or more than 2 ill cells around,
     * it becomes a DiyngCell; otherwise, it becomes a HealthyCell.
     *
     * @param health_num the number of healthy cells around this cell
     * @param ill_num the number of ill cells around this cell
     * @return the next generation of this cell
     */
    @Override
    protected Cell nextGeneration(int health_num, int ill_num) {
        if ((health_num < 2) || (health_num > 3) || (ill_num > 2)) {
            return new DiyngCell();
        }
        return new HealthyCell();
    }

    /**
     * Creates and returns a copy of this IllCell.
     *
     * @return the cloned IllCell
     */
    @Override
    public IllCell clone() {
        return new IllCell();
    }
}

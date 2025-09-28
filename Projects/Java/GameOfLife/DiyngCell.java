/**
 * The DiyngCell class represents a dying cell in the simulation.
 * It determines its next generation state based on the number of healthy and ill cells around it.
 */
public class DiyngCell extends Cell {

    /**
     * Constructs a DiyngCell with its type set to "D".
     */
    public DiyngCell() {
        super.type = "D";
    }

    /**
     * Determines the next generation of this cell based on the number of healthy and ill cells around it.
     * If there are not exactly 3 healthy cells or if there is more than 1 ill cell around, it becomes a DeadCell;
     * otherwise, it becomes a HealthyCell.
     *
     * @param health_num the number of healthy cells around this cell
     * @param ill_num the number of ill cells around this cell
     * @return the next generation of this cell
     */
    @Override
    protected Cell nextGeneration(int health_num, int ill_num) {
        if ((health_num != 3) || (ill_num > 1)) {
            return new DeadCell();
        }
        return new HealthyCell();
    }

    /**
     * Creates and returns a copy of this DiyngCell.
     *
     * @return the cloned DiyngCell
     */
    @Override
    public DiyngCell clone() {
        return new DiyngCell();
    }
}

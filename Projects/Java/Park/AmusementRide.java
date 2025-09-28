/**
 * @AmusementRide
 */

public class AmusementRide<E> {

    /**
     * AmusementRide attributes.
     */
    private final String rideName;
    private IsraeliQueue<E> queue = new IsraeliQueue<>();
    private final int rideMaxLimit;

    /**
     * AmusementRide constructor.
     */
    public AmusementRide(String rideName, int rideMaxLimit) {
        this.rideName = rideName;
        this.rideMaxLimit = rideMaxLimit;
    }

    /**
     * Start the ride.
     */
    public void startRide() {
        try {
            if (queue.isEmpty()){
                throw new Exception("Ride is empty");
            }
            System.out.println("Currently using the ride:");
           // queue.PrintQueue(Math.min(queue.size(), rideMaxLimit));
            if (queue.size() < rideMaxLimit) {
                while (!queue.isEmpty()) {
                    System.out.println(queue.remove().toString());
                }
            } else {
                for (int i = 0; i < rideMaxLimit; i++) {
                    System.out.println(queue.remove().toString());
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Add person to the ride's queue.
     */
    public void addPerson(E person, E friend) throws InvalidInputException {
        this.queue.add(person,friend);
    }
}

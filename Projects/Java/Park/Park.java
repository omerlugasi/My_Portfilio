/**
 * @Park
 */

public class Park {

    /**
     * Park attributes
     */
    private final String parkName;
    private AmusementRide [] amusementRides;
    private int rideCount;
    private final int ridesLimit = 5;

    /**
    * Park constructor
    */
    public Park(String parkName) {
        this.parkName = parkName;
        this.amusementRides = new AmusementRide[ridesLimit];
        this.rideCount = 0;

    }

    /**
     * Add amusement ride to the park.
     */
    public void add(AmusementRide ride) {
        if (!parkIsFull()) {
            amusementRides[rideCount] = ride;
            rideCount++;
        }
    }

    /**
     * Remove amusement ride from the park.
     */
    public void remove(AmusementRide ride) {
        for (int i = 0; i < rideCount; i++){
            if (amusementRides[i] == ride){
                System.arraycopy(amusementRides, i + 1, amusementRides, i, rideCount - i - 1);
                amusementRides[rideCount - 1] = null;
                rideCount--;
                return;
            }
        }
    }

    /**
     * Start each ride in the park.
     */
    public void startRides() {
        for (int i = 0; i < rideCount; i++) {
            amusementRides[i].startRide();
        }
    }

    /**
     * Add a person to a specific ride.
     */
    public void addPerson(AmusementRide ride, Person person) {
        try {
            for (int i = 0; i < rideCount; i++) {
                if (amusementRides[i] == ride){
                    amusementRides[i].addPerson(person, person.getFriend());
                }
            }
        } catch (Exception e) {
                System.out.println("exception " + e);
        }
    }

    /**
     * Check if the Park is full.
     */
    public boolean parkIsFull(){
        if (amusementRides.length <= rideCount){
            return true;
        }
        return false;
    }
}


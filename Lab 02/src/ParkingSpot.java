/**
 * Represents a Parking Spot in a Parking Lot
 *
 * @author Russell Harvey (rdh1896@rit.edu)
 */

public class ParkingSpot {

    /** Global variable representing an occupied spot. */
    private static String OCCUPIED_STR = "*";

    /** Number of the parking spot. */
    private int spot;

    /** The type of spot (HANDICAPPED, RESERVED, GENERAL). */
    private Permit.Type type;

    /** Vehicle parked in spot (null if none). */
    private Vehicle vehicle;

    /**
     * Creates a new parking spot. Takes in a number as a label and
     * gives it a type based on a Permit.Type input. Initially
     * The spot should have no vehicle, a spot, and a type.
     * @param spot spot number.
     * @param type type of spot.
     */
    public ParkingSpot (int spot, Permit.Type type) {
        this.spot = spot;
        this.type = type;
    }

    /**
     * Returns the spot value of a ParkingSpot.
     * @return this.spot
     */
    public int getSpot () {
        return this.spot;
    }

    /**
     * Returns the what type a ParkingSpot is.
     * @return this.type
     */
    public Permit.Type getType () {
        return this.type;
    }

    /**
     * Returns what vehicle is in the parking spot.
     * @return this.vehicle
     */
    public Vehicle getVehicle() {
        return this.vehicle;
    }

    /**
     * Occupies a ParkingSpot with a vehicle being put
     * into the parameters of the method.
     * @param vehicle vehicle being parked.
     */
    public void occupySpot (Vehicle vehicle) {
        this.vehicle = vehicle;
        this.vehicle.setParked(true);
    }

    /**
     * Used in the toString() method for ParkingSpot for proper
     * representation of the spot's type in toString().
     * @param type the spot's types
     * @return String
     */
    public String getTypeMarker(Permit.Type type) {
        if (type == Permit.Type.HANDICAPPED) {
            return "H";
        } else if (type == Permit.Type.RESERVED) {
            return "R";
        } else {
            return "G";
        }
    }

    /**
     * Returns information about a spot in a well formatted string.
     * @return String
     */
    @Override
    public String toString () {
        return spot + ":" + ((vehicle == null) ? getTypeMarker(type) : OCCUPIED_STR);
    }

    /** Vacates a spot if a vehicle is parked in it. */
    public void vacateSpot() {
        this.vehicle.setParked(false);
        this.vehicle = null;
    }

    /**
     * Used to test the ParkingSpot class by comparing the values
     * of a ParkingSpot to predefined parameters of what the ParkingSpot
     * should contain.
     * @param spotVar used to separate the tests from one another.
     * @param s ParkingSpot being verified.
     * @param spot what the ParkingSpot's spot should be.
     * @param type what the ParkingSpot's type should be.
     * @param vehicle what the ParkingSpot's vehicle should be.
     */
    private static void verifySpot​ (String spotVar, ParkingSpot s, int spot, Permit.Type type, Vehicle vehicle) {
        // Printing spotVar
        System.out.println("Verifying: " + spotVar);

        // Checking Spot and Type
        System.out.println("Checking spot equivalency: " + ((s.spot == spot) ? "OK" : "FAIL: Got " + s.spot));
        System.out.println("Checking type equivalency: " + ((s.type == type) ? "OK" : "FAIL: Got " + s.type));

        // Checking Vehicle
        if (s.vehicle == null) {
            System.out.println("Checking spot availability: " + ((s.vehicle == vehicle) ? "OK" :
                                "FAIL: Got " + s.vehicle.toString()));
        } else {
            System.out.println("Checking if spot is occupied: " + ((s.vehicle.equals(vehicle)) ? "OK" :
                                "FAIL: Got " + s.vehicle.toString()));
            System.out.println("Checking if vehicle is parked: " + ((s.vehicle.isParked() && vehicle.isParked()) ?
                                "OK" : "FAIL: Spot or Parameter not parked."));
        }

        // Displaying Parking Spot
        System.out.println("Permit -> " + s.toString());
    }

    /**
     * Uses the verifySpot() method to test the various features
     * in the ParkingSpot class. First creates a spot, then parks and
     * removes a car.
     * @param args
     */
    public static void main (String[] args) {
        // First Test - Verifying verifySpot
        ParkingSpot spot1 = new ParkingSpot(1, Permit.Type.HANDICAPPED);
        verifySpot​("First Test - Spot 1", spot1, 1, Permit.Type.HANDICAPPED, null);

        // Second Test - Parking A Car
        Vehicle dodgeChallenger = new Vehicle(1231);
        spot1.occupySpot(dodgeChallenger);
        verifySpot​("Second Test - Spot 1", spot1, 1, Permit.Type.HANDICAPPED, dodgeChallenger);

        //Third Test - Leaving the spot
        spot1.vacateSpot();
        verifySpot​("Third Test - Spot 1", spot1, 1, Permit.Type.HANDICAPPED, null);
    }
}

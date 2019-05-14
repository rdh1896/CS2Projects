import java.util.ArrayList;

/**
 * Used to Create and Manage a Parking Lot
 *
 * @author Russell Harvey (rdh1896)
 */

public class ParkingLot {
    /** Capacity of the parking lot. */
    private int capacity;

    /** Amount of general spots in the lot. */
    private int generalSpots;

    /** Amount of handicapped spots in the lot. */
    private int handicappedSpots;

    /** Amount of reserved spots in the lot */
    private int reservedSpots;

    /** Global value denoting an illegal spot. */
    static int ILLEGAL_SPOT = -1;

    /** A list of all the spots in a lot. */
    private ArrayList<ParkingSpot> lot;

    /** Number of parked vehicles in the lot. */
    private int parkedVehicles;

    /** Global variable stating the amount of spots in a line. */
    private static int SPOTS_PER_LINE = 10;

    /**
     * Creates a new parking lot. User can determine how many handicapped,
     * reserved, and general spots. The amount of total spots may not exceed
     * 100.
     * @param handicappedSpots amount of handicapped spots.
     * @param reservedSpots amount of reserved spots.
     * @param generalSpots amount of general spots.
     */
    public ParkingLot (int handicappedSpots, int reservedSpots, int generalSpots) {
        this.handicappedSpots = handicappedSpots;
        this.reservedSpots = reservedSpots;
        this.generalSpots = generalSpots;
        this.lot = new ArrayList<>();
        initializeSpots();
    }

    /**
     * Returns the capacity of the lot.
     * @return capacity
     */
    public int getCapacity () {
        return capacity;
    }

    /**
     * Returns how many vehicles are parked in the lot.
     * @return parkedVehicles
     */
    public int getNumParkedVehicles () {
        return parkedVehicles;
    }

    /**
     * Returns the ParkingSpot object at a given location.
     * @param spot location of spot being searched for.
     * @return location - a ParkingSpot object.
     */
    public ParkingSpot getSpot (int spot) {
        ParkingSpot location = lot.get(spot);
        return location;
    }

    /**
     * Used in the constructor to create the lot. Uses the inputted amount
     * of each kind of spot to create a lot with the correct specifications.
     * Also updates capacity to align with the size of the lot.
     */
    private void initializeSpots () {
        int sum = handicappedSpots + reservedSpots + generalSpots;

        for (int i = 0; i < handicappedSpots; i++){
            ParkingSpot spot = new ParkingSpot(i, Permit.Type.HANDICAPPED);
            this.lot.add(spot);
        }
        for (int i = handicappedSpots; i < handicappedSpots + reservedSpots; i++) {
            ParkingSpot spot = new ParkingSpot(i, Permit.Type.RESERVED);
            this.lot.add(spot);
        }
        for (int i = handicappedSpots + reservedSpots; i < sum; i++) {
            ParkingSpot spot = new ParkingSpot(i, Permit.Type.GENERAL);
            this.lot.add(spot);
        }

        this.capacity = sum;
    }

    /**
     * Returns if a spot is vacant or not.
     * @param spot spot being checked
     * @return boolean
     */
    public boolean isSpotVacant (int spot) {
        if (lot.get(spot).getVehicle() == null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns if a spot is valid or not.
     * @param spot spot being checked.
     * @return boolean
     */
    public boolean isSpotValid (int spot) {
        if (spot > (capacity - 1)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Parks a vehicle in a specific spot. Returns a boolean
     * based on if the parking job was successful.
     * @param vehicle vehicle being parked.
     * @param spot where the vehicle is being parked.
     * @return boolean
     */
    public boolean parkVehicle (Vehicle vehicle, int spot) {
        ParkingSpot location = lot.get(spot);
        if (verifySpot(spot)) {
            location.occupySpot(vehicle);
            parkedVehicles += 1;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes a vehicle from the lot.
     * @param vehicle vehicle being removed.
     * @return int - location of the parked vehicle or ILLEGAL_SPOT
     */
    public int removeVehicle(Vehicle vehicle) {
        for (int i = 0; i < capacity; i++) {
            ParkingSpot location = lot.get(i);
            if (location.getVehicle() == null) {
                assert true;
            } else {
                if (location.getVehicle().equals(vehicle)) {
                    location.vacateSpot();
                    parkedVehicles -= 1;
                    return i;
                } else {
                    assert true;
                }
            }
        }
        return ILLEGAL_SPOT;
    }

    /**
     * Takes isSpotValid() and isSpotVacant() and uses them to see if a spot
     * is suitable to be parked in.
     * @param spot spot being checked.
     * @return boolean
     */
    public boolean verifySpot (int spot) {
        if (isSpotValid(spot)) {
            if (isSpotVacant(spot)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Returns the information about a lot in a grid pattern
     * making it easy to read. Also states the amount of vacant
     * spots in the lot.
     * @return String
     */
    @Override
    public String toString () {
        int tracker = 0;
        int vacantSpots = 0;
        String lotChart = "";
        String spotStr = "";
        for (int i = 0; i < capacity; i++) {
            ParkingSpot location = lot.get(i);
            if (location.getVehicle() == null) {
                vacantSpots++;
            }
            if (i < SPOTS_PER_LINE) {
                spotStr = "0" + location.toString();
            } else {
                spotStr = location.toString();
            }
            if (tracker < SPOTS_PER_LINE) {
                lotChart += spotStr + " ";
                tracker++;
            } else {
                lotChart += "\n" + spotStr + " ";
                tracker = 1;
            }
        }
        return lotChart + "\n" + "Vacant Spots: " + vacantSpots;
    }

    /**
     * Method used to test the methods and attributes of the ParkingLot
     * object. Uses ternary operators in print statements to check the
     * attributes of a ParkingLot with predefined parameters that should
     * match.
     * @param lotVar used to separate different tests.
     * @param l lot being checked.
     * @param capacity capacity the lot is supposed to have.
     * @param parkedVehicles amount of parked vehicles the lot is supposed to have.
     * @param handicappedSpots amount of handicapped spots the lot is supposed to have.
     * @param reservedSpots amount of reserved spots the lot is supposed to have.
     * @param generalSpots amount of general spots the lot is supposed to have.
     */
    private static void verifyLot (String lotVar, ParkingLot l, int capacity, int parkedVehicles,
                                   int handicappedSpots, int reservedSpots, int generalSpots) {
        // Printing lotVar
        System.out.println("Verifying: " + lotVar);

        // Checking capacity and types
        System.out.println("Verifying Capacity: " + ((l.capacity == capacity) ? "OK" : "FAIL: Got " + l.capacity));
        System.out.println("Verifying Handicapped Spots: " + ((l.handicappedSpots == handicappedSpots) ? "OK"
                            : "FAIL: Got " + l.handicappedSpots));
        System.out.println("Verifying Reserved Spots: " + ((l.reservedSpots == reservedSpots) ? "OK"
                            : "FAIL: Got " + l.reservedSpots));
        System.out.println("Verifying Capacity: " + ((l.generalSpots == generalSpots) ? "OK" :
                            "FAIL: Got " + l.generalSpots));

        // Checking parked vehicles
        System.out.println("Verifying Parked Vehicles: " + ((l.parkedVehicles == parkedVehicles) ? "OK" :
                            "FAIL: Got " + l.parkedVehicles));

        // Printing Lot Layout
        System.out.println(l.toString());

        //Inserting A Empty Line
        System.out.println("");
    }

    /**
     * The main test function of the ParkingLot class. Conducts three tests;
     * creating a lot, parking two cars, then removing one car. Uses verifyLot
     * to ensure the tests were completed successfully.
     * @param args
     */
    public static void main (String[] args) {
        // First Test - Verifying Verify
        ParkingLot lotA = new ParkingLot(10, 10, 80);
        verifyLot("First Test - Lot A", lotA, 100, 0, 10,
                10, 80);

        //Second Test - Parking Two Cars in Lot A
        Vehicle dodgeChallenger = new Vehicle(1231);
        Vehicle scionXb = new Vehicle(1080);
        lotA.parkVehicle(dodgeChallenger, 2);
        lotA.parkVehicle(scionXb, 45);
        verifyLot("Second Test - Lot A (Check Layout for Parked Cars)", lotA, 100, 2,
                10, 10, 80);

        //Third Test - Removing A Car
        lotA.removeVehicle(scionXb);
        verifyLot("Third Test - Lot A (Check Layout for Parked Car)", lotA, 100, 1,
                10, 10, 80);
    }
}

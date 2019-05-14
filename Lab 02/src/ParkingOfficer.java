import java.util.ArrayList;

/**
 * Officer for RIT Parking
 *
 * @author Russell Harvey (rdh1896@rit.edu)
 */
public class ParkingOfficer {
    /** Lot the officer is patrolling. */
    private ParkingLot lot;

    /** Global value for the amount of time the officer will pause in patrolLot(). */
    private static int PAUSE_TIME = 1000;

    /** Tickets accrued by the officer. */
    private ArrayList<Ticket> tickets;

    /**
     * Creates a ParkingOfficer. Lot is set to null and an empty
     * array list for tickets to be held.
     */
    public ParkingOfficer () {
        this.lot = null;
        this.tickets = new ArrayList<>();
    }

    /**
     * Gets the fine a vehicle would have for parking in a specific spot or
     * returns no fine if the car is parked legally.
     * @param vehicle vehicle being checked for permit.
     * @param spot spot vehicle is being compared to.
     * @return Fine
     */
    public static Fine getFineVehicleSpot (Vehicle vehicle, ParkingSpot spot) {
        Permit.Type spotType = spot.getType();
        Permit.Type vehicleType = null;
        if (vehicle == null) {
            return Fine.NO_FINE;
        }
        if (vehicle.getPermit() == null) {
            assert true;
        } else {
            vehicleType = vehicle.getPermit().getType();
        }
        if (spotType == vehicleType) {
            return Fine.NO_FINE;
        } else {
            if (spotType == Permit.Type.HANDICAPPED) {
                return Fine.PARKING_HANDICAPPED;
            } else if ((spotType == Permit.Type.RESERVED)) {
                if (vehicleType == Permit.Type.HANDICAPPED) {
                    return Fine.NO_FINE;
                } else {
                    return Fine.PARKING_RESERVED;
                }
            } else {
                if ((vehicleType == Permit.Type.HANDICAPPED) || (vehicleType == Permit.Type.RESERVED)) {
                    return Fine.NO_FINE;
                } else {
                    return Fine.NO_PERMIT;
                }
            }
        }
    }

    /**
     * Returns all tickets an officer has issued.
     * @return tickets
     */
    public ArrayList<Ticket> getTickets () {
        return this.tickets;
    }

    /**
     * Issue a ticket to a vehicle in a given spot.
     * @param vehicle vehicle being ticketed.
     * @param spot spot vehicle is parked in.
     * @param fine fine the vehicle is being issued.
     */
    private void issueTicket (Vehicle vehicle, int spot, Fine fine) {
        Ticket ticket = new Ticket(vehicle.getPlate(), fine);
        tickets.add(ticket);
        vehicle.giveTicket(ticket);
        System.out.println("Issuing Ticket to: " + vehicle.toString() + " in spot " + spot + " for " + fine);
    }

    /**
     * Sets the officer to patrol the lot. Officer will check every vehicle
     * to make sure it is parked legally. If it is not, the officer will issue
     * a ticket. The officer pauses for a second between each spot.
     */
    public void patrolLot () {
        for (int i = 0; i < lot.getCapacity(); i++) {
            ParkingSpot officerCurrentLocation = lot.getSpot(i);
            Vehicle currentVehicle = officerCurrentLocation.getVehicle();
            Fine fine = getFineVehicleSpot(currentVehicle, officerCurrentLocation);
            if (fine == Fine.NO_FINE) {
                assert true;
            } else {
                issueTicket(currentVehicle, i, fine);
            }
            RITParking.pause(PAUSE_TIME);
        }
    }

    /**
     * Sets a parking lot for the officer to patrol.
     * @param lot lot officer is being assigned to.
     */
    public void setParkingLot (ParkingLot lot) {
        this.lot = lot;
    }

    /**
     * Test function used to verify that the attributes of a ParkingOfficer
     * are what they should be.
     * @param offVar used to differentiate tests.
     * @param o officer being checked.
     * @param lot lot officer is assigned to.
     */
    private static void verifyOfficer (String offVar, ParkingOfficer o, ParkingLot lot) {
        // Printing offVar
        System.out.println(offVar);

        // Verifying Lot
        System.out.println("Checking lot: " + ((o.lot == lot) ? "OK" : "FAIL: Got " + o.lot));

        // Checking Tickets
        System.out.println("Checking tickets: " + ((o.tickets.equals(new ArrayList<>())) ?
                            "No Tickets" : "Tickets: " + o.tickets));

        //Printing Space
        System.out.println("");
    }

    public static void main (String[] args) {
        /**
         * Main test function. Creates an officer, gives them a lot, and then issues
         * a ticket. Ticketing is verified through unqiue print statements as opposed
         * to verifyOfficer(). This is because even if tickets are equal their times will differentiate.
         */
        // Verifying Verify
        ParkingOfficer officerBurgess = new ParkingOfficer();
        ArrayList<Ticket> testTickets = new ArrayList<>();
        verifyOfficer("First Test - Officer Burgess", officerBurgess, null);

        // Give Him a Lot
        ParkingLot lotA = new ParkingLot(10, 10, 80);
        officerBurgess.setParkingLot(lotA);
        verifyOfficer("Second Test - Officer Burgess", officerBurgess, lotA);

        // Issue a Ticket (If a ticket is added to the object's tickets, it's successful)
        Vehicle dodgeChallenger = new Vehicle(1231);
        Permit permit = new Permit(1, Permit.Type.GENERAL);
        dodgeChallenger.setPermit(permit);
        lotA.parkVehicle(dodgeChallenger, 1);
        ParkingSpot spot1 = lotA.getSpot(1);
        ParkingSpot spot2 = lotA.getSpot(2);
        Fine fine = getFineVehicleSpot(dodgeChallenger, spot1);
        //Checking compatibility with null spots.
        Fine nullFine = getFineVehicleSpot(null, spot2);
        System.out.println("Showing that no fine is given for an empty spot: " + nullFine);
        officerBurgess.issueTicket(dodgeChallenger, 1, fine);
        verifyOfficer("Third Test - Officer Burgess", officerBurgess, lotA);
    }
}

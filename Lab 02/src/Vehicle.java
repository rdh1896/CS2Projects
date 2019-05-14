import java.util.ArrayList;

/**
 * Represents a vehicle in the parking simulator.
 *
 * @author Sean Strout @ RIT CS
 * @author Russell Harvey (rdh1896@rit.edu)
 */

public class Vehicle {

    /** States if the vehicle is parked or not. */
    private boolean parked;

    /** Tells us what permit the vehicle has, null if none. */
    private Permit permit;

    /** The plate number of the vehicle. */
    private int plate;

    /** Any tickets given to the vehicle. */
    private ArrayList<Ticket> tickets;

    /**
     * Create a new vehicle. Takes in a plate value as a parameter
     * and creates a new vehicle with stated plate, no permit, and
     * not parked. An empty list is created to store any tickets.
     *
     * @param plate Vehicle plate number
     */
    public Vehicle (int plate) {
        this.plate = plate;
        this.parked = false;
        this.tickets = new ArrayList<>();
    }

    /** Returns the plate number of a vehicle. */
    public int getPlate () {
        return this.plate;
    }

    /** Returns if the car is parked or not. */
    public boolean isParked () {
        return this.parked;
    }

    /** Returns if the car has a permit to park. */
    public boolean hasPermit () {
        if (this.permit == null) {
            return false;
        } else {
            return true;
        }
    }

    /** Returns the permit attached to the vehicle. */
    public Permit getPermit () {
        return this.permit;
    }

    /** Returns the list of tickets acquired by the vehicle */
    public ArrayList<Ticket> getTickets () {
        return this.tickets;
    }

    /** Returns information about the vehicle in the form of a formatted string. */
    @Override
    public String toString () {
        return "Vehicle{plate=" + this.plate + ", permit=" + ((this.permit == null) ? this.permit :
                this.permit.toString()) + ", parked=" + this.parked + ", tickets=" + this.tickets + "}";
    }

    /**
     * Sets the vehicle's parked value to a boolean given in the
     * parameter.
     * @param parked boolean
     */
    public void setParked (boolean parked) {
        this.parked = parked;
    }

    /**
     * Takes two vehicles and compares them by plate number. If they are
     * the same the method will return true.
     * @param other vehicle used for comparison.
     * @return boolean
     */
    @Override
    public boolean equals(Object other) {
        if(other instanceof Vehicle) {
            Vehicle temp = (Vehicle)other;
            return temp.plate == this.plate;
        } else {
            return false;
        }
    }

    /**
     * Sets the vehicle's permit.
     * @param permit permit being distributed.
     */
    public void setPermit (Permit permit) {

        this.permit = permit;

    }

    /**
     * Gives a parking ticket to the vehicle.
     * @param ticket ticket being issued.
     */
    public void giveTicket (Ticket ticket) {
        this.tickets.add(ticket);
    }

    /**
     * Main test function for the Vehicle class that tests
     * most operations and attributes within.
     * @param args
     */
    public static void main(String[] args) {
        // create a Vehicle, v1, whose plate number is 10, is unparked, with no
        // permit and no tickets (an empty list).
        Vehicle v1 = new Vehicle(10);

        // call the following operations on v1 and print each result:
        //      getPlate() -> 10
        //      isParked() -> false
        //      hasPermit() -> false
        //      getPermit() -> null
        //      getTickets() -> []
        System.out.println("v1 plate is 10? " + (10 == v1.getPlate() ? "OK" :
                "FAIL, got: " + v1.getPlate()));
        System.out.println("v1 is not parked? " + (!v1.isParked() ? "OK" : "FAIL"));
        System.out.println("v1 has no permit? " + (!v1.hasPermit() ? "OK" : "FAIL"));
        System.out.println("v1 permit is null? " + (v1.getPermit() == null ? "OK" :
                "FAIL, got: " + v1.getPermit()));
        System.out.println("v1 has no tickets? " + (v1.getTickets() != null && v1.getTickets().size() == 0 ?
                "OK" : "FAIL, got: " + v1.getTickets()));

        // check's Vehicle's toString(), you should get:
        //      Vehicle{plate=10, permit=null, parked=false, tickets=[]}
        System.out.println("v1 toString?: " +
            (v1.toString() != null && v1.toString().equals("Vehicle{plate=10, permit=null, parked=false, tickets=[]}") ?
                    "OK" : "FAIL, got: " + v1.toString()));

        // create a second Vehicle v2, whose plate number is 20, park it, test
        // it is parked and then its toString(), you should get:
        //      v2: Vehicle{plate=20, permit=null, parked=true, tickets=[]}
        Vehicle v2 = new Vehicle(20);
        v2.setParked(true);
        System.out.println("v2 toString?: " +
                (v2.toString() != null && v2.toString().equals("Vehicle{plate=20, permit=null, parked=true, tickets=[]}") ?
                        "OK" : "FAIL, got: " + v2.toString()));

        // create a third Vehicle v3, whose plate number is also 20 test it's
        // toString() is:
        //      v3: Vehicle{plate=20, permit=null, parked=false, tickets=[]}
        Vehicle v3 = new Vehicle(20);
        System.out.println("v3 toString?: " +
                (v3.toString() != null && v3.toString().equals("Vehicle{plate=20, permit=null, parked=false, tickets=[]}") ?
                        "OK" : "FAIL, got: " + v3.toString()));

        // verify that v2 and v3 are equal because they have the same plate number:
        System.out.println("v2 equals v3? " + (v2.equals(v3) ? "OK" : "FAIL"));

        // verify that v1 and v3 are not equal:
        System.out.println("v1 does not equal v3? " + (!v1.equals(v3) ? "OK" : "FAIL"));

        // verify that v1 is not equal to the string, "v1":
        System.out.println("v1 does not equal \"v1\"? " + (!v1.equals("v1") ? "OK" : "FAIL"));

        // issue a handicapped permit, p1, to v1 and make sure v1 has the permit
        Permit p1 = new Permit(1, Permit.Type.HANDICAPPED);
        v1.setPermit(p1);
        System.out.println("v1 with permit p1? " + (v1.getPermit() != null && v1.getPermit().equals(p1) ? "OK" : "FAIL"));

        // give a ticket, t2, to v2 for parking without a permit and verify it has it
        Ticket t1 = new Ticket(v2.getPlate(), Fine.NO_PERMIT);
        v2.giveTicket(t1);
        System.out.println("v2 with ticket t1? " + (v2.getTickets() != null &&
                v2.getTickets().get(0).equals(t1) ? "OK" : "FAIL"));

        // give another ticket, t3, to v2 and verify it has it as well
        Ticket t2 = new Ticket(v2.getPlate(), Fine.PARKING_HANDICAPPED);
        v2.giveTicket(t2);
        System.out.println("v2 with another ticket t2? " + (v2.getTickets() != null && v2.getTickets().get(1).equals(t2) ?
                "OK" : "FAIL"));
    }
}
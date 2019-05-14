package rit.stu.act2;

/**
 * Hostage Implementation for Predator
 *
 * @author Russell Harvey (rdh1896@rit.edu)
 */
public class Hostage implements Player {

    /** Hostage's id number. */
    private int id;

    /** Create a new hostage. */
    public Hostage (int id) {
        this.id = id;
    }

    /**
     * Declares if the Hostage is victorious.
     * @param player
     */
    public void victory (Player player) {
        System.out.println("Hostage #" + this.id + " yells, 'Victory over " + player + "!'");
    }

    /**
     * Declares if the Hostage is defeated.
     * @param player
     */
    public void defeat (Player player) {
        System.out.println("Hostage #" + this.id + " cries, 'Defeated by " + player + "!'");
    }

    /**
     * Hostage's toString() function.
     * @return hostage number.
     */
    public String toString () {
        return "Hostage #" + this.id;
    }
}

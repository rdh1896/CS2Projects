package rit.stu.act2;

/**
 * Soldier Implementation for Predator
 *
 * @author Russell Harvey (rdh1896@rit.edu)
 */
public class Soldier implements Player {

    /** Soldier's id number. */
    private int id;

    /** Creates a new soldier. */
    public Soldier (int id) {
        this.id = id;
    }

    /**
     * Declares if the Soldier is victorious.
     * @param player
     */
    public void victory (Player player) {
        System.out.println("Soldier #" + this.id + " yells, 'Sieg " + "\u00FC"  + "ber " + player + "!'");
    }

    /**
     * Declares if the Soldier is defeated.
     * @param player
     */
    public void defeat (Player player) {
        System.out.println("Soldier #" + this.id + " cries, 'Besiegt von " + player +"!'");
    }

    /**
     * Soldier's toString() function.
     * @return soldier's number.
     */
    public String toString () {
        return "Soldier #" + this.id;
    }

}

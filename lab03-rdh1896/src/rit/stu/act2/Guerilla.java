package rit.stu.act2;

/**
 * Guerilla Implementation for Predator
 *
 * @author Russell Harvey (rdh1896@rit.edu)
 */

public class Guerilla implements Player {

    /** Chance to beat a soldier. */
    public static int CHANCE_TO_BEAT_SOLDIER = 20;

    /** Guerilla id number. */
    private int id;

    /** Creates a new guerilla. */
    public Guerilla (int id) {
        this.id = id;
    }

    /**
     * Declares if the Guerilla is victorious.
     * @param player
     */
    public void victory (Player player) {
        System.out.println("Guerilla #" + this.id + " yells, 'Victoria sobre " + player + "!'");
    }

    /**
     * Declares if the Guerilla is defeated.
     * @param player
     */
    public void defeat (Player player) {
        System.out.println("Guerilla #" + this.id + " cries, 'Derrotado por " + player + "!'");
    }

    /**
     * Guerilla's toString() function.
     * @return guerilla's number.
     */
    public String toString () {
        return "Guerilla #" + this.id;
    }

}

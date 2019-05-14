package rit.stu.act2;

/**
 * Predator Implementation for Predator
 *
 * @author Russell Harvey (rdh1896@rit.edu)
 */

public class Predator implements Player {

    /** Chance to beat hostage. */
    public static final int CHANCE_TO_BEAT_HOSTAGE = 75;

    /** Chance to beat a soldier. */
    public static final int CHANCE_TO_BEAT_SOLDIER = 50;

    /** Creates a predator. */
    public Predator () { }

    /**
     * Declares if the Predator is victorious.
     * @param player
     */
    @Override
    public void victory (Player player) {
        System.out.println("The predator yells out in triumphant victory over " + player);
    }

    /**
     * Declares if the Predator is defeated.
     * @param player
     */
    @Override
    public void defeat (Player player) {
        System.out.println("The predator cries out in glorious defeat to " + player);
    }

    /**
     * Predator's toString() function.
     * @return predator.
     */
    @Override
    public String toString() {
        return "Predator";
    }

}

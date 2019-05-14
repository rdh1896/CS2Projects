package rit.stu.act2;

/**
 * Player Interface for Predator
 *
 * @author Russell Harvey (rdh1896@rit.edu)
 */

public interface Player {

    /**
     * Denotes if the player was defeated.
     * @param player
     */
    public void defeat (Player player);

    /**
     * Denotes if the player was victorious.
     * @param player
     */
    public void victory (Player player);

}

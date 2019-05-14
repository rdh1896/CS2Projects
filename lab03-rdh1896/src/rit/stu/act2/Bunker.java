package rit.stu.act2;
import rit.stu.act1.QueueNode;
import rit.cs.Queue;

/**
 * Bunker Implementation for Predator
 *
 * @author Russell Harvey (rdh1896@rit.edu)
 */

public class Bunker {

    /** Bunker queue. */
    private Queue<Soldier> bunker;

    /** Number of soldiers in the bunker. */
    private int numSoldiers;

    /** Creates a new bunker. */
    public Bunker (int numSoldiers) {
        this.numSoldiers = numSoldiers;
        this.bunker = new QueueNode<>();
    }

    /**
     * Checks if the bunker has soldiers in it.
     * @return boolean.
     */
    public boolean hasSoldiers () {
        return this.numSoldiers != 0;
    }

    /**
     * Gets the number of soldiers in the bunker.
     * @return number of soldiers.
     */
    public int getNumSoldiers () {
        return this.numSoldiers;
    }

    /**
     * Deploys a soldier from the bunker.
     * @return Soldier.
     */
    public Soldier deployNextSoldier () {
        this.numSoldiers--;
        return this.bunker.dequeue();
    }

    /**
     * Adds a soldier to the bunker.
     * @param soldier
     */
    public void fortifySoldiers (Soldier soldier) {
        this.numSoldiers++;
        this.bunker.enqueue(soldier);
    }
}

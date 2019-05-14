package rit.stu.act2;

import rit.cs.Queue;
import rit.cs.Stack;
import rit.stu.act1.QueueNode;
import rit.stu.act1.StackNode;

/**
 * EnemyBase Implementation for Predator
 *
 * @author Russell Harvey (rdh1896@rit.edu)
 */

public class EnemyBase {

    /** Queue of guerillas in the base. */
    private Queue<Guerilla> guerillas;

    /** Stack of hostages in the base. */
    private Stack<Hostage> hostages;

    /** Number of guerillas in the base. */
    private int numGuerillas;

    /** Number of hostages in the base. */
    private int numHostages;

    /** Creates a new enemy base. */
    public EnemyBase (int numHostages, int numGuerillas) {
        this.numHostages = numHostages;
        this.numGuerillas = numGuerillas;
        this.hostages = new StackNode<>();
        this.guerillas = new QueueNode<>();
    }

    /**
     * Adds a guerilla to the base.
     * @param guerilla
     */
    public void addGuerilla (Guerilla guerilla) {
        this.guerillas.enqueue(guerilla);
        this.numGuerillas++;
    }

    /**
     * Adds a hostage to the base.
     * @param hostage
     */
    public void addHostage (Hostage hostage) {
        this.hostages.push(hostage);
        this.numHostages++;
    }

    /**
     * Gets the first guerilla in line.
     * @return Guerilla.
     */
    public Guerilla getGuerilla () {
        this.numGuerillas--;
        return this.guerillas.dequeue();
    }

    /**
     * Gets the first hostage in the stack.
     * @return Hostage.
     */
    public Hostage getHostage () {
        this.numHostages--;
        return this.hostages.pop();
    }

    /**
     * Returns the number of guerillas in the base.
     * @return number of guerillas.
     */
    public int getNumGuerillas () {
        return this.numGuerillas;
    }

    /**
     * Returns the number of hostages in the base.
     * @return number of hostages in the base.
     */
    public int getNumHostages () {
        return this.numHostages;
    }

    /**
     * Soldier attempts to rescue a hostage.
     * @param soldier
     * @return Hostage.
     */
    public Hostage rescueHostage (Soldier soldier) {
        // Printing Soldier Enters Base
        System.out.println(soldier + " enters enemy base...");

        //Remove and Hold Hostage
        Hostage currentHostage = getHostage();

        // Checking for Guerillas
        if (getNumGuerillas() == 0) {
            return currentHostage;
        } else {
            Guerilla currentGuerilla = getGuerilla();
            int roll = Battlefield.nextInt(1, 100);
            System.out.println(soldier + " battles " + currentGuerilla + " who rolls a " + roll + ".");
            if (roll > currentGuerilla.CHANCE_TO_BEAT_SOLDIER) {
                soldier.victory(currentGuerilla);
                currentGuerilla.defeat(soldier);
                return currentHostage;
            } else {
                soldier.defeat(currentGuerilla);
                currentGuerilla.victory(soldier);
                this.addHostage(currentHostage);
                this.addGuerilla(currentGuerilla);
                return null;
            }
        }
    }
}

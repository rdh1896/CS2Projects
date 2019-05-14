package rit.stu.act2;

import java.util.Random;

/**
 * The main simulation class is run on the command line as:<br>
 * <br>
 * $ java Battlefield #_hostages #_soldiers #_guerillas
 *
 * @author Sean Strout @ RIT CS
 * @author Russell Harvey (rdh1896@rit.edu)
 */
public class Battlefield {
    /** the single instance of the random number generator */
    private final static Random rng = new Random();

    /** the seed the random number generator will use */
    private final static int SEED = 0;

    /** Bunker used in the simulation. */
    private Bunker bunker;

    /** Chopper used in the simulation. */
    private Chopper chopper;

    /** Enemy base used in the simulation. */
    private EnemyBase enemyBase;

    /** Predator used in the simulation. */
    private Predator predator;

    /**
     * Generate a random integer between min and max inclusive.  For example: <br>
     * <br>
     * <tt>Battlefield.nextInt(1, 5): A random number, 1-5</tt><br>
     * <br>
     *
     * @param min the smallest value allowed.
     * @param max the largest value allowed.
     * @return A random integer
     */
    public static int nextInt(int min, int max) {
        return rng.nextInt(max - min + 1) + min;
    }

    /**
     * Create the battlefield.  This method is responsible for seeding the
     * random number generator and initializing all the supporting classes
     * in the simulation: Chopper, EnemyBase, Bunker and Predator.
     *
     * @param numHostages number of hostages being held in enemy base at start
     * @param numSoldiers number of soldiers to rescue the hostages at start
     * @param numGuerillas number of guerillas in the enemy base at start
     */
    public Battlefield(int numHostages, int numSoldiers, int numGuerillas) {
        this.rng.setSeed(SEED);
        this.enemyBase = new EnemyBase(0, 0);
        this.bunker = new Bunker(0);
        for (int i = 1; i <= numHostages; i++) {
            Hostage hostage = new Hostage(i);
            this.enemyBase.addHostage(hostage);
        }
        for (int i = 1; i <= numSoldiers; i++) {
            Soldier soldier = new Soldier(i);
            this.bunker.fortifySoldiers(soldier);
        }
        for (int i = 1; i <= numGuerillas; i++) {
            Guerilla guerilla = new Guerilla(i);
            this.enemyBase.addGuerilla(guerilla);
        }
        this.predator = new Predator();
        this.chopper = new Chopper();
    }

    /**
     * Prints out the current statistics of the simulation in terms of the number
     * of hostages, soldiers, guerillas, and rescued people are.  The format
     * of the message is on a single line:<br>
     * <br>
     * Statistics: # hostages remain, # soldiers remain, # guerillas remain, # rescued<br>
     */
    private void printStatistics() {
        int numHostages = this.enemyBase.getNumHostages();
        int numGuerillas = this.enemyBase.getNumGuerillas();
        int numSoldiers = this.bunker.getNumSoldiers();
        int rescued = this.chopper.getNumRescued();
        System.out.println(numHostages + " hostages remain, " + numSoldiers + " soldiers remain, " + numGuerillas +
                            " guerillas remain, " + rescued + " rescued.");
    }

    /**
     * The main battle simulation loop runs here.  The simulation runs until either
     * all the hostages have been rescued, or there are no more soldiers left
     * to rescue hostages.
     */
    private void battle() {
        // Starting
        System.out.println("In an Arnold Schwarzenegger fashion... 'Get to the choppa!'");

        // Main Loop
        while (this.enemyBase.getNumHostages() != 0 && this.bunker.getNumSoldiers() != 0) {
            printStatistics();
            Soldier currSoldier = this.bunker.deployNextSoldier();
            Hostage rescuedHostage = this.enemyBase.rescueHostage(currSoldier);
            if (rescuedHostage == null) {
                assert true;
            } else {
                System.out.println(rescuedHostage + " rescued from enemy base by "
                                    + currSoldier + ".");
                int roll = nextInt(1, 100);
                System.out.println(currSoldier + " encounters the predator who rolls a " + roll + ".");
                if (roll > this.predator.CHANCE_TO_BEAT_SOLDIER) {
                    currSoldier.victory(predator);
                    predator.defeat(currSoldier);
                    this.bunker.fortifySoldiers(currSoldier);
                    this.chopper.boardPassenger(rescuedHostage);
                } else {
                    predator.victory(currSoldier);
                    currSoldier.defeat(predator);
                    int rollHostage = nextInt(1, 100);
                    System.out.println(rescuedHostage + " encounters the predator who rolls a "
                                        + rollHostage + ".");
                    if (rollHostage <= this.predator.CHANCE_TO_BEAT_HOSTAGE) {
                        this.predator.victory(rescuedHostage);
                        rescuedHostage.defeat(predator);
                    } else {
                        this.predator.defeat(rescuedHostage);
                        rescuedHostage.victory(predator);
                        this.chopper.boardPassenger(rescuedHostage);
                    }
                }
            }
        }

        // Boarding Soldiers
        int totalNumSoldiers = this.bunker.getNumSoldiers();
        for (int i = 0; i < totalNumSoldiers; i++) {
            Soldier currSoldier = this.bunker.deployNextSoldier();
            this.chopper.boardPassenger(currSoldier);
        }
        this.chopper.rescuePassengers();

        //Printing Stats
        printStatistics();
    }

    /**
     * The main method expects there to be three command line arguments:<br>
     * <br>
     *  1: the number of hostages (a positive integer)<br>
     *  2: the number of soldiers (a positive integer)<br>
     *  3: the number of guerillas (a positive integer)<br>
     * <br>
     * If all the arguments are supplied it will create the battle field
     * and then begin the battle.
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java Battlefield #_hostages #_soldiers #_guerillas");
        } else {
            Battlefield field = new Battlefield(
                    Integer.parseInt(args[0]),
                    Integer.parseInt(args[1]),
                    Integer.parseInt(args[2]));
            field.battle();
        }
    }

}

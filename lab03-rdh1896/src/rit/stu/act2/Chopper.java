package rit.stu.act2;
import rit.stu.act1.StackNode;
import rit.cs.Stack;

public class Chopper {

    private Stack<Player> chopper;
    public static int MAX_OCCUPANCY = 6;
    private int numPassengers;
    private int numRescued;

    public Chopper () {
        this.numPassengers = 0;
        this.numRescued = 0;
        this.chopper = new StackNode<>();
    }

    public boolean isEmpty() {
        return this.numPassengers == 0;
    }

    public boolean isFull() {
        return this.numPassengers == this.MAX_OCCUPANCY;
    }

    public int getNumRescued () {
        return this.numRescued;
    }

    public void rescuePassengers () {
        while (!this.chopper.empty()) {
            Player rescued = this.chopper.pop();
            System.out.println("Chopper transported " + rescued + " to safety!");
            this.numPassengers--;
            this.numRescued++;
        }
    }

    public void boardPassenger (Player player) {
        if (this.numPassengers == this.MAX_OCCUPANCY) {
            this.rescuePassengers();
            this.chopper.push(player);
            this.numPassengers++;
            System.out.println(player + " boards the chopper!");
        } else {
            this.chopper.push(player);
            this.numPassengers++;
            System.out.println(player + " boards the chopper!");
        }
    }
}

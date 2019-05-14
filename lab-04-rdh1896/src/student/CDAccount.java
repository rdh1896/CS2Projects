package student;

import edu.rit.cs.Currency;
import bank.AccountType;

/**
 * CDAccount implementation for BankAccount
 *
 * @author Russell Harvey (rdh1896@rit.edu)
 */

public class CDAccount extends BankAccount {

    /** Minimum balance to create a CDAccount. */
    public static final Currency MINIMUM_BALANCE = new Currency(1000, 0, '$');

    /** Monthly interest rate for a CDAccount. */
    public static final double MONTHLY_INTEREST_RATE = 0.006 / NUM_PERIODS_PER_YEAR;

    /** Constructor for the CDAccount class. */
    public CDAccount (Currency newMoney, String owner) {
        super(newMoney, owner, AccountType.DEBIT);
    }

    /**
     * Calculates the interest gained in the CDAccount at the end of a month.
     */
    @Override
    public void endOfMonth () {
        if (getCurrentBalance().compareTo(MINIMUM_BALANCE) > 0) {
            Currency difference = getCurrentBalance().subtract(MINIMUM_BALANCE);
            Currency interestGained = difference.multiply(1 + MONTHLY_INTEREST_RATE);
            Currency totalInterest = interestGained.subtract(difference);
            credit(totalInterest);
            setInterestAccrued(totalInterest);
        } else {
            assert true;
        }
    }

    /**
     * Gets the type of account in a 2 letter format.
     * @return String
     */
    @Override
    public String getAccountType () {
        return "CD";
    }

    /**
     * CDAccount's version of a toString() function.
     * @return String
     */
    @Override
    public String toString () {
        return super.toString() + " " + getAccountType();
    }
}

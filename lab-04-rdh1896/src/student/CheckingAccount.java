package student;

import bank.AccountType;
import edu.rit.cs.Currency;

public class CheckingAccount extends BankAccount {

    /** Tells if an account earns interest or not. */
    private boolean bonus;

    /** Monthly interest rate for CheckingAccount. */
    public static final double BONUS_MONTHLY_RATE = 0.001 / NUM_PERIODS_PER_YEAR;

    /** Denotes if the account earns interest or not. */
    public final String chkAcctType;

    /** Minimum balance to earn interest on a CheckingAccount. */
    public static final Currency PREMIUM_CHECKING_MINIMUM_BALANCE = new Currency(500,0,
                                                                                '$');

    /** Constructor for CheckingAccount. */
    public CheckingAccount (Currency newMoney, String owner, boolean bonus) {
        super(newMoney, owner, AccountType.DEBIT);
        this.bonus = bonus;
        if (bonus == true) {
            this.chkAcctType = "CI";
        } else {
            this.chkAcctType = "CN";
        }
    }

    /**
     * Calculates the interest at the end of a month if the account has a bonus and
     * is above the minimum balance to earn interest.
     */
    @Override
    public void endOfMonth() {
        Currency origBalance = getCurrentBalance();
        if (bonus == true) {
            if (getCurrentBalance().compareTo(PREMIUM_CHECKING_MINIMUM_BALANCE) > 0) {
                Currency interestGained = getCurrentBalance().multiply(1 + BONUS_MONTHLY_RATE);
                Currency totalInterest = interestGained.subtract(origBalance);
                credit(totalInterest);
                setInterestAccrued(totalInterest);
            } else {
                assert true;
            }
        } else {
            assert true;
        }
    }

    /**
     * Gets the account type in a 2 letter format.
     * @return String
     */
    @Override
    public String getAccountType() {
        return chkAcctType;
    }

    /**
     * CheckingAccount's toString() function.
     * @return String
     */
    @Override
    public String toString() {
        return super.toString() + " Checking";
    }
}

package student;

import bank.AccountType;
import edu.rit.cs.Currency;

public class CreditCard extends BankAccount {

    /** Credit limit for the given CreditCard. */
    private Currency creditLimit;

    /** Monthly interest rate for the CreditCard. */
    private double monthlyInterestRate;

    /**
     * Constructor for the CreditCard Class.
     * @param creditLimit
     * @param monthlyInterestRate
     * @param ownerName
     */
    public CreditCard(Currency creditLimit, double monthlyInterestRate, String ownerName) {
        super(creditLimit, ownerName, AccountType.CREDIT);
        this.creditLimit = creditLimit;
        this.monthlyInterestRate = monthlyInterestRate;
    }

    /**
     * Returns the amount owed on the CreditCard.
     * @return Currency
     */
    @Override
    public Currency getCurrentBalance () {
        Currency balance = super.getCurrentBalance();
        return balance.subtract(creditLimit);
    }

    /**
     * Calculates the interest owed at the end of each month.
     */
    @Override
    public void endOfMonth() {
        Currency currentBalance = getCurrentBalance();
        Currency interestGained = currentBalance.multiply(1 + (monthlyInterestRate/NUM_PERIODS_PER_YEAR));
        Currency totalInterest = currentBalance.subtract(interestGained);
        debit(totalInterest);
        setInterestAccrued(totalInterest.subtract(totalInterest.multiply(2)));
    }

    /**
     * Gets the account type in 2 letter format.
     * @return String
     */
    @Override
    public String getAccountType() {
        return "CC";
    }

    /**
     * Created a toString() for CreditCard to keep consistency in the program.
     * @return String
     */
    @Override
    public String toString() {
        return super.toString() + " Credit Card";
    }
}

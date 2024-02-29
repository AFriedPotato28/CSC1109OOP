/**
 * Represents a Loan with information such as loan ID, loan amount, interest rate, loan duration, and customer details.
 */
public class Loan {

    /**
     * The unique identifier for the loan.
     */
    private int loanId;

    /**
     * The amount of the loan.
     */
    private double loanAmount;

    /**
     * The interest rate associated with the loan.
     */
    private double interestRate;

    /**
     * The duration of the loan in months.
     */
    private int loanDuration;

    /**
     * The customer applying for the loan.
     */
    private Customer customer;

    /**
     * Gets the unique identifier for the loan.
     * @return The loan ID.
     */
    public int getLoanId() {
        return this.loanId;
    }

    /**
     * Gets the amount of the loan.
     * @return The loan amount.
     */
    public double getLoanAmount() {
        return this.loanAmount;
    }

    /**
     * Retrieves the interest rate associated with the loan.
     * @return The interest rate.
     */
    public double getInterestRate() {
        return this.interestRate;
    }

    /**
     * Retrieves the duration of the loan in months.
     * @return The loan duration.
     */
    public int getLoanDuration() {
        return this.loanDuration;
    }

    /**
     * Sets the duration of the loan in months.
     * @param loanDuration The loan duration to be set.
     */
    public void setLoanDuration(int loanDuration) {
        this.loanDuration = loanDuration;
    }

    /**
     * Calculates the total amount to be repaid for the loan.
     * @return The total amount to be repaid after interest.
     */
    public double calculateAmount() {
        return this.loanAmount;
    }
}

package implementations;

import java.time.LocalDate;

/**
 * Represents a Loan with information such as loan ID, loan amount, interest
 * rate, loan duration, and customer details.
 */
public class Loan extends Account {
    /**
     * The unique identifier for the loan.
     */
    private int loanId;

    /**
     * The customer applying for the loan.
     */
    private int customerId;

    /**
     * The amount of the loan.
     */
    private double loanAmount;

    /**
     * The interest rate associated with the loan.
     */
    private double interestRate;

    /**
     * The duration of the loan in LocalDate object.
     */
    private LocalDate loanDueDate;

    /**
     * Constructs a new Loan object with the specified details.
     *
     * @param loanId      The unique identifier of the loan.
     * @param loanAmount  The amount of the loan.
     * @param loanDuration Calculated due date by which the loan should be paid.
     * @param customerId  The customer id associated with loan class.
     */
    public Loan(int loanId, int customerId, double loanAmount, LocalDate loanDuration) {
        super();
        this.setLoanId(loanId);
        this.setCustomerId(customerId);
        this.setLoanAmount(loanAmount);
        this.interestRate = 0.05; // Interest rate for this bank (5%)
        this.setLoanDueDate(loanDuration);

    }

    /**
     * Gets the unique identifier for the loan.
     * 
     * @return The loan ID.
     */
    public int getLoanId() {
        return this.loanId;
    }

    /**
     * Gets the unique identifier of the customer.
     * 
     * @return The customerId.
     */
    public int getCustomerId() {
        return this.customerId;
    }
    
     /**
     * Sets the customer identification of the loan policy
     * 
     * @param customerId The customer identification of the loan policy.
     */

     public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets the amount of money loaned.
     * 
     * @return The loan amount.
     */
    public double getLoanAmount() {
        return this.loanAmount;
    }

    /**
     * Retrieves the interest rate associated with the loan.
     * 
     * @return The interest rate.
     */
    public double getInterestRate() {
        return this.interestRate;
    }

    /**
     * Retrieves the duration of the loan in LocalDate object.
     * 
     * @return The loan duration.
     */
    public LocalDate getLoanDueDate() {
        return this.loanDueDate;
    }

    /**
     * Sets the duration of the loan using LocalDate object
     * 
     * @param loanDueDate The due date by which the loan should be paid.
     */
    public void setLoanDueDate(LocalDate loanDueDate) {
        this.loanDueDate = loanDueDate;
    }

    /**
     * 
     * Sets the identification for the loan policy.
     * 
     * @param loanId get the loan identifier that loan should be set
     */
    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    /**
     * Sets the loan amount for the user
     * 
     * @param loanAmount The amount of loan to be set.
     *                   Should be a non-negative value representing the loan amount
     *                   in currency units.
     */
    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    /**
     * Deducts loan amount for actions towards the loan policy e.g. repayment
     * 
     * @param loanAmount The amount of loan that needs to be paid.
     */

    public void deductLoanAmount(double loanAmount) {
        this.loanAmount -= loanAmount;
    }

}

import java.time.LocalDate;

/**
 * Represents a Loan with information such as loan ID, loan amount, interest rate, loan duration, and customer details.
 */
public class Loan extends Account{
    /**
     * The unique identifier for the loan.
     */
    private int loanId;

    /**
     * The customer applying for the loan.
     */
    private int customerID;


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
    private LocalDate loanDuration;


    /**
     * Constructs a new Loan object with the specified details.
     *
     * @param loanId The unique identifier of the loan.
     * @param loanAmount The amount of the loan.
     * @param loanDuration The duration of the loan in months.
     * @param customerId The customer id associated with loan class.
     */
    public Loan(int loanId, int customerId, double loanAmount, LocalDate loanDuration){
        super(customerId);
        this.loanId = loanId;
        this.loanAmount = loanAmount;
        this.interestRate = 0.05; // Interest rate for this bank (5%)
        this.loanDuration = loanDuration;

    }

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
    public LocalDate getLoanDuration() {
        return this.loanDuration;
    }

    /**
     * Sets the loan amount for the user
     * @param loanAmount The amount of loan to be set.
     *                   Should be a non-negative value representing the loan amount in currency units.
     */
    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    /**
     * Sets the duration of the loan in months.
     * @param loanDuration The loan duration to be set.
     */
    public void setLoanDuration(LocalDate loanDuration) {
        this.loanDuration = loanDuration;
    }


    public void setLoanId(int loanId){
        this.loanId = loanId;
    }

    /**
     * Calculates the total amount to be repaid for the loan.
     * @return The total amount to be repaid after interest.
     */
    public double calculateAmount() {
        return this.loanAmount;
    }


    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

}

import java.time.YearMonth;

/**
 * Represents a Credit Card with information such as card number, expiry date, CVV, customer details, balance, and credit limit.
 */
public class CreditCard {
    /**
     * The unique identifier for the credit card.
     */
    private int creditCardId;

    /**
     * The customer ID associated with the credit card.
     */
    private int customerId;

    /**
     * The account number associated with the credit card
     */
    private int accountNo;

    /**
     * The current balance on the credit card bill.
     */
    private double balance;

    /**
     * The remaining credit usable on the credit card.
     */
    private double remainingCredit;

    /**
     * The cash advance payable on the credit card.
     */
    private double cashAdvancePayable;

    /**
     * The credit limit set for the credit card.
     */
    private int creditLimit;

    /**
     * The card number associated with the credit card.
     */
    private String cardNumber;
    /**
     * The Card Verification Value (CVV) of the credit card.
     */
    private String CVV;

    /**
     * The expiry date of the credit card.
     */
    private YearMonth expiryDate;


    /**
     * Constructs a new CreditCard object with the specified Credit Card ID, Customer ID, account number.
     * Balance is set to 0 by default.
     * Credit Limit (per month) is set to 10% of the Customer's annual income
     * Card Number, CVV and Expiry Date are generated by the Credit Card Generator
     *
     * @param creditCardId The credit card ID associated with the credit card.
     * @param customerId   The customer ID associated with the credit card.
     * @param accountNo    The account number associated with the credit card.
     * @param annualIncome The annual income of the Customer applying for the credit card
     */
    public CreditCard(int creditCardId, int customerId, int accountNo, int annualIncome) {
        this.creditCardId = creditCardId;
        this.customerId = customerId;
        this.accountNo = accountNo;
        this.balance = 0.0; // outstanding credit bill balance is 0 for a new credit card
        this.cashAdvancePayable = 0.0; // cash advance payable is 0 for a new credit card
        this.creditLimit = annualIncome / 10; // credit limit per month set to 10% of customer's annual income
        this.remainingCredit = this.creditLimit; // remaining balance is equal to the credit limit initially

        this.cardNumber = CreditCardGenerator.generateCardNumber(accountNo, this.creditCardId);
        this.CVV = CreditCardGenerator.generateCVV();
        this.expiryDate = CreditCardGenerator.generateExpiryDate();
    }

    /**
     * Construct an existing credit card with the specific Credit Card ID, Customer ID, account number, balance,
     * credit limit, card number, CVV and expiry date.
     *
     * @param creditCardId The credit card ID associated with the credit card.
     * @param customerId   The customer ID associated with the credit card.
     * @param accountNo    The account number associated with the credit card.
     * @param balance      The outstanding balance of the credit bill.
     * @param creditLimit  The credit limit of the credit card.
     * @param cardNumber   The card number associated with the credit card.
     * @param cvv          The Card Verification Value (CVV) associated with the credit card.
     * @param expiryDate   The expiry date of the credit card.
     */
    public CreditCard(int creditCardId, int customerId, int accountNo, double balance, double remainingCredit, int creditLimit, String cardNumber, String cvv, YearMonth expiryDate) {
        this.creditCardId = creditCardId;
        this.customerId = customerId;
        this.accountNo = accountNo;
        this.balance = balance;
        //this.cashAdvancePayable= cashAdvancePayable;
        this.remainingCredit = remainingCredit;
        this.creditLimit = creditLimit;
        this.cardNumber = cardNumber;
        this.CVV = cvv;
        this.expiryDate = expiryDate;
    }

    /**
     * Retrieves the unique credit card ID
     *
     * @return The unique credit card ID
     */
    public int getCreditCardId() {
        return this.creditCardId;
    }

    /**
     * Retrieves the customer ID associated with the credit card
     *
     * @return The customer ID associated with the corresponding credit card
     */
    public int getCustomerId() {
        return this.customerId;
    }

    /**
     * Retrieves the account number associated with the credit card
     *
     * @return The account number associated with the corresponding credit card
     */
    public int getAccountNo() {
        return this.accountNo;
    }

    /**
     * Retrieves the credit card number.
     *
     * @return The credit card number.
     */
    public String getCardNumber() {
        return this.cardNumber;
    }

    /**
     * Retrieves the hashed value of the Card Verification Value (CVV).
     *
     * @return The hashed value of the CVV.
     */
    public String getEncryptedCVV() throws Exception {
        return Security.hashCVV(String.valueOf(this.CVV));
    }

    /**
     * Retrieves the expiry date of the credit card.
     *
     * @return The expiry date of the credit card.
     */
    public YearMonth getExpiryDate() {
        return this.expiryDate;
    }

    /**
     * Retrieves the current balance on the credit card.
     *
     * @return The balance.
     */
    public double getBalance() {
        return this.balance;
    }

    /**
     * Retrieves the remaining balance usable on the credit card.
     *
     * @return The remaining balance.
     */
    public double getRemainingCredit() {
        return this.remainingCredit;
    }

    /**
     * Updates the balance usable on the credit card.
     *
     * @param amountDeducted The amount to be deducted from the remaining balance available.
     */
    public void setRemainingBalance(double amountDeducted) {
        this.remainingCredit -= amountDeducted;
    }

    /**
     * Retrieves the cash advance payable on the credit card.
     */
    public double getCashAdvancePayable() {
        return this.cashAdvancePayable;
    }

    /**
     * Retrieves the credit limit for the credit card.
     *
     * @return The credit limit of the credit card.
     */
    public int getCreditLimit() {
        return this.creditLimit;
    }

    /**
     * Sets the credit limit for the credit card.
     *
     * @param creditLimit The credit limit to be set.
     */
    public void setCreditLimit(int creditLimit) {
        this.creditLimit = creditLimit;
    }

    /**
     * Attempts to pay the credit card bill with the specified payment amount.
     *
     * @param paymentAmount The amount to be paid.
     * @return True if the payment is successful, False otherwise.
     */
    public boolean payCreditBill(double paymentAmount) {
        // Implementation of payment logic goes here
        return false;
    }

    public boolean cashAdvanceWithdrawal(double cashAdvanceAmount) {
        // Implementation of cash advance withdrawal logic goes here
        double minCashAdvanceFee = 10.00; // minimum cash advance fee set at $10.00
        double cashAdvanceFee = Math.max(minCashAdvanceFee, 0.05 * cashAdvanceAmount); // cash advance fee set at 5% of the cash advance amount or $10.00, whichever is higher
        double totalCashAdvanceAmount = cashAdvanceAmount + cashAdvanceFee; // total cash advance amount including the cash advance fee

        // check if the total cash advance amount is less than 30% of the credit limit and less than or equal to the remaining balance
        if (totalCashAdvanceAmount < 0.3 * this.creditLimit && totalCashAdvanceAmount <= this.remainingCredit) {
            this.cashAdvancePayable += totalCashAdvanceAmount;
            this.remainingCredit -= totalCashAdvanceAmount;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if the credit card is expired.
     *
     * @return True if the credit card is expired, False otherwise.
     */
    public boolean isExpired() {
        // Get current date
        YearMonth currentDate = YearMonth.now();

        // Get the expiry date of the credit card
        YearMonth expiryDate = this.expiryDate;

        return expiryDate.isBefore(currentDate);
    }
}




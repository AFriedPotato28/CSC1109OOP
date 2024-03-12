import java.time.YearMonth;

/**
 * Represents a Credit Card with information such as card number, expiry date, CVV, customer details, balance, and credit limit.
 */
public class CreditCard extends Account implements CreditCardGenerator {

    /**
     * The card number associated with the credit card.
     */
    private String cardNumber;

    /**
     * The expiry date of the credit card.
     */
    private YearMonth expiryDate;

    /**
     * The Card Verification Value (CVV) of the credit card.
     */
    private int CVV;

    /**
     * The customer ID associated with the credit card.
     */
    private int customerId;

    /**
     * The account number associated with the credit card
     */
    private int accountNo;

    /**
     * The current balance on the credit card.
     */
    private double balance;

    /**
     * The credit limit set for the credit card.
     */
    private int creditLimit;

    /**
     * Constructs a new CreditCard object with the specified card number, expiry date, CVV, customer, balance, and credit limit.
     * @param customerId The customer ID associated with the credit card.
     * @param accountNo The account number associated with the credit card.
     * @param balance The current balance on the credit card.
     * @param creditLimit The credit limit set for the credit card.
     */
    public CreditCard(int customerId, int accountNo, double balance, int creditLimit) {
        super(customerId);
        this.accountNo = accountNo;
        this.balance = balance;
        this.creditLimit = creditLimit;

        this.cardNumber = generateCardNumber(accountNo);
        this.CVV = generateCVV();
        this.expiryDate = generateExpiryDate();
    }


    /**
     * Retrieves the credit card number.
     * @return The credit card number.
     */
    public String getCardNumber() {
        return this.cardNumber;
    }

    /**
     * Retrieves the expiry date of the credit card.
     * @return The expiry date of the credit card.
     */
    public YearMonth getExpiryDate() {
        return this.expiryDate;
    }

    /**
     * Retrieves the current balance on the credit card.
     * @return The balance.
     */
    public double getBalance() {
        return this.balance;
    }

    /**
     * Sets the credit limit for the credit card.
     * @param creditLimit The credit limit to be set.
     */
    public void setCreditLimit(int creditLimit) {
        this.creditLimit = creditLimit;
    }

    /**
     * Attempts to pay the credit card bill with the specified payment amount.
     * @param paymentAmount The amount to be paid.
     * @return True if the payment is successful, False otherwise.
     */
    public boolean payCreditBill(double paymentAmount) {
        // Implementation of payment logic goes here
        return false;
    }

    /**
     * Checks if the credit card is expired.
     * @return True if the credit card is expired, False otherwise.
     */
    public boolean isExpired() {
        // Implementation of expiration check logic goes here
        return false;
    }
}




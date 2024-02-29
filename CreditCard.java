import java.util.Date;

/**
 * Represents a Credit Card with information such as card number, expiry date, CVV, customer details, balance, and credit limit.
 */
public class CreditCard {

    /**
     * The card number associated with the credit card.
     */
    private String cardNumber;

    /**
     * The expiry date of the credit card.
     */
    private Date expiryDate = new Date();

    /**
     * The Card Verification Value (CVV) of the credit card.
     */
    private int CVV;

    /**
     * The customer associated with the credit card.
     */
    private Customer customer;

    /**
     * The current balance on the credit card.
     */
    private double balance;

    /**
     * The credit limit set for the credit card.
     */
    private int creditLimit;
    /**
     * Constructs a new CreditCard object.
     */
    public CreditCard(){}

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
    public Date getExpiryDate() {
        return this.expiryDate;
    }

    /**
     * Retrieves the CVV of the credit card.
     * @return The CVV.
     */
    public int getCVV() {
        return this.CVV;
    }

    /**
     * Retrieves the customer associated with the account.
     * @return The customer object.
     */
    public Customer getCustomer() {
        return this.customer;
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

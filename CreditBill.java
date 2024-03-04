import java.util.Date;

/**
 * Represents a credit card bill containing information such as the associated credit card, bill date, bill amount, and payment details.
 */
public class CreditBill {
    private CreditCard creditCard;
    private Date date;
    private double billAmount;
    private double amountPaid;

    /**
     * Constructs a new CreditBill object.
     */
    public CreditBill() {
    }

    /**
     * Retrieves the due date of the credit card bill.
     */
    public void getDueDate() {
        // Method implementation goes here
    }

    /**
     * Retrieves the total bill amount.
     *
     * @return The total bill amount.
     */
    public double getBillAmount() {
        // Method implementation goes here
        return billAmount;
    }

    /**
     * Retrieves the amount paid towards the credit card bill.
     *
     * @return The amount paid.
     */
    public double getAmountPaid() {
        // Method implementation goes here
        return amountPaid;
    }

    /**
     * Processes a payment towards the credit card bill.
     *
     * @param paymentAmount The amount to be paid.
     */
    public void pay(double paymentAmount) {
        // Method implementation goes here
    }
}


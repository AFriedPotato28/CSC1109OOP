package implementations;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.YearMonth;

/**
 * Represents a Credit Card with information such as card number, expiry date,
 * CVV, customer details, balance, and credit limit.
 */
public class CreditCard {
    /**
     * The unique identifier for the credit card.
     */
    private int creditCardId;

    private static int lastAssignedId = 0;

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
     * The cash advance withdrawal limit for the credit card.
     */
    private double cashAdvanceLimit;

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
     * Constructs a new CreditCard object with the specified Credit Card ID,
     * Customer ID, account number.
     * Balance is set to 0 by default.
     * Cash Advance Payable is set to 0 by default as no cash advance has been
     * withdrawn.
     * Credit Limit (per month) is set to 10% of the Customer's annual income
     * Remaining Credit is set to the credit limit minus the outstanding balance
     * Cash Advance Limit is set to 30% of the credit limit
     * Card Number, CVV and Expiry Date are generated by the Credit Card Generator
     *
     * @param customerId   The customer ID associated with the credit card.
     * @param accountNo    The account number associated with the credit card.
     * @param annualIncome The annual income of the Customer applying for the credit
     *                     card
     */
    public CreditCard(int customerId, int accountNo, int annualIncome) {
        this.creditCardId = ++lastAssignedId;
        this.customerId = customerId;
        this.accountNo = accountNo;
        this.balance = 0.0; // outstanding credit bill balance is 0 for a new credit card
        this.cashAdvancePayable = 0.0; // outstanding cash advance payable is 0 for a new credit card
        this.creditLimit = annualIncome / 10; // credit limit per month set to 10% of customer's annual income
        this.remainingCredit = this.creditLimit - this.balance; // remaining credit is the credit limit minus the
                                                                // outstanding balance
        this.cashAdvanceLimit = 0.3 * this.creditLimit; // cash advance limit set to 30% of the credit limit

        this.cardNumber = CreditCardGenerator.generateCardNumber(accountNo, this.creditCardId);
        this.CVV = CreditCardGenerator.generateCVV();
        this.expiryDate = CreditCardGenerator.generateExpiryDate();
    }

    /**
     * Construct an existing credit card with the specific Credit Card ID, Customer
     * ID, account number, balance,
     * credit limit, card number, CVV and expiry date.
     *
     * @param creditCardId The unique identifier of the credit card
     * @param customerId   The customer ID associated with the credit card.
     * @param accountNo    The account number associated with the credit card.
     * @param balance      The outstanding balance of the credit bill
     * @param creditLimit  The credit limit of the credit card.
     * @param cardNumber   The card number associated with the credit card.
     * @param cvv          The Card Verification Value (CVV) associated with the
     *                     credit card.
     * @param expiryDate   The expiry date of the credit card.
     */
    public CreditCard(int creditCardId, int customerId, int accountNo, double balance, double remainingCredit,
            int creditLimit, String cardNumber, String cvv, YearMonth expiryDate, double cashAdvancedPayable,
            double cashAdvanceLimit) {
        this.creditCardId = creditCardId;
        this.customerId = customerId;
        this.accountNo = accountNo;
        this.balance = balance;
        this.remainingCredit = remainingCredit;
        this.creditLimit = creditLimit;
        this.cardNumber = cardNumber;
        this.CVV = cvv;
        this.expiryDate = expiryDate;
        this.cashAdvancePayable = cashAdvancedPayable;
        this.cashAdvanceLimit = cashAdvanceLimit;
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
    public String getEncryptedCVV() {
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
     * @return The remaining credit bill balance on the credit card.
     */
    public double getBalance() {
        return this.balance;
    }

    /**
     * Retrieves the remaining balance usable on the credit card.
     *
     * @return The remaining credit balance usable on the credit card.
     */
    public double getRemainingCredit() {
        return this.remainingCredit;
    }

    /**
     * Retrieves the cash advance payable on the credit card.
     * 
     * @return The cash advance payable on the credit card.
     */
    public double getCashAdvancePayable() {
        return this.cashAdvancePayable;
    }

    /**
     * Retrieves the cash advance limit for the credit card.
     *
     * @return The cash advance limit of the credit card.
     */
    public double getCashAdvanceLimit() {
        return this.cashAdvanceLimit;
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
     * Sets the remaining credit for the credit card.
     * 
     * @param cashAdvanceLimit The cash advance withdrawal limit to be set.
     */
    public void setCashAdvanceLimit(double cashAdvanceLimit) {
        this.cashAdvanceLimit = cashAdvanceLimit;
    }

    /**
     * Attempts to pay the credit card bill with the specified payment amount.
     *
     * @param paymentAmount The amount to be paid.
     * @return True if the payment is successful, False otherwise.
     */
    public boolean payCreditBill(Account account,double paymentAmount) {
        DecimalFormat df = new DecimalFormat("##.00");
        paymentAmount = Double.parseDouble(df.format(paymentAmount));

        // Check if the payment amount is valid
        if (paymentAmount < 0) {
            System.out.println("Payment amount is invalid!");
            return false;
        }

        if (account.getBalance() < paymentAmount){
            System.out.println("Payment amount exceeds your savings account");
            return false;
        }


        // Check if payment amount exceed credit bill balance
        if (paymentAmount > this.balance) {
            System.out.println("Payment amount exceeds the outstanding balance!");
            return false;
        }

        // Deduct the payment amount from the balance
        this.balance -= paymentAmount;
        //deducts from account also
        account.withdraw(paymentAmount);
        // Update remainingCredit
        this.remainingCredit += paymentAmount;

        return true;
    }

    /**
     * Attempts to withdraw cash advance from the ATM using the credit card.
     * 
     * @param withdrawalAmount The amount to be withdrawn as cash advance.
     * @return True if the cash advance withdrawal is successful, False otherwise.
     */
    public boolean cashAdvanceWithdrawal(double finalWithdrawlAmount) {
        // Implementation of cash advance withdrawal logic goes here
        // cash advance fee
        DecimalFormat df = new DecimalFormat("##.00");
        finalWithdrawlAmount = Double.parseDouble(df.format(finalWithdrawlAmount));
        // check if the total cash advance amount to be withdrawn totals less than 30%
        // of the credit limit
        // AND less than or equal to the remaining balance
        if (finalWithdrawlAmount + this.cashAdvancePayable <= this.cashAdvanceLimit
                && finalWithdrawlAmount <= this.creditLimit) {
            this.cashAdvancePayable += finalWithdrawlAmount;
            this.remainingCredit -= finalWithdrawlAmount;
            return true;
        } else {
            if (finalWithdrawlAmount + this.cashAdvancePayable >= this.cashAdvanceLimit) {
                double cashAdvanceBalance = this.cashAdvanceLimit - this.cashAdvancePayable;
                System.out.println("\nCash advance withdrawal amount exceeds 30% of the credit limit!");
                System.out.println("Cash advance remaining credit: " + cashAdvanceBalance);
                return false;
            }
            // error message if the total cash advance amount withdrawn exceeds the
            // remaining credit
            else {
                System.out.println("\nCash advance withdrawal amount exceeds the remaining credit!");
                System.out.println("Cash advance remaining credit: " + this.remainingCredit);
                return false;
            }
        }
    }

    /**
     * Attempts to pay the cash advance payable on the credit card and checks with the account balance to validate.
     * 
     * @param paymentAmount The amount paid on the cash advance payable.
     * @return True if the payment is successful, False otherwise.
     */
    public boolean payCashAdvancePayable(Account account,double paymentAmount) {
        DecimalFormat df = new DecimalFormat("##.00");
        paymentAmount = Double.parseDouble(df.format(paymentAmount));

        // Check if the payment amount is valid
        if (paymentAmount < 0) {
            System.out.println("Payment amount is invalid!");
            return false;
        }
      
        // Check if payment amount exceed cash advance payable
        if (paymentAmount > this.cashAdvancePayable ) {
            System.out.println("Payment amount exceeds the cash advance payable!");
            System.out.println("\nCash advance payable: " + this.cashAdvancePayable);
            return false;
        } else if (paymentAmount > account.getBalance()) {
            System.out.println("Payment amount exceeds your account balance!");
            System.out.println("\nCash advance payable: " + account.getBalance());
            return false;
        }

        // Deduct the payment amount from the cash advance payable
        this.cashAdvancePayable -= paymentAmount;
        //updates the account balance
        account.withdraw(paymentAmount);
        // Update remainingCredit
        this.remainingCredit += paymentAmount;

        return true;
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

        return currentDate.isBefore(expiryDate);
    }

    /**
     * Checks credit card has balance left
     * increment by 5% to the balance if unpaid
     * 
     */
    public void computeInterestRate() {
        LocalDate localDate = LocalDate.now();
        localDate.withDayOfMonth(1);
        double interest_rate = 0.05;

        if (localDate.withDayOfMonth(1).equals(localDate)) {
            this.balance = (1 + interest_rate) * this.balance;
        }
    }

}
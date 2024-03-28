package implementations;
/**
 * Represents a bank account associated with a customer.
 * Each account has an account ID, type, balance, associated customer, and transaction limit.
 */
public class Account {
    private int accountNo;
    private int customerId;
    private String accountType;
    private double balance;
    private double transactionLimit;
    private double annualIncome;

    /**
     * Constructs a new Account object with the specified account number, customer ID, account type, balance, and transaction limit.
     * @param accountNo The bank account number.
     * @param customerId The customer ID associated with the account.
     * @param accountType The type of account (e.g. "Savings", "Checking").
     * @param balance The current balance of the account.
     * @param transactionLimit The maximum amount that can be withdrawn from the account in a single transaction.
     */
    public Account(int accountNo, int customerId, String accountType, double balance, double transactionLimit,double AnnualIncome) {
        this.accountNo = accountNo;
        this.customerId = customerId;
        this.accountType = accountType;
        this.balance = balance;
        this.transactionLimit = transactionLimit;
        this.annualIncome = AnnualIncome;
    }
    /* private intiator */
    /**
     * Default constructor.
     */
    public Account() {}


    /**
     * Populates the account object with the specified account details.
     * @param accounts The account object to populate.
     * Returns Account Numbers, Customer ID, Account Type, Balance, Transaction Limit
     */
    public void populateItem(Account accounts) {
        this.accountNo = accounts.accountNo;
        this.customerId = accounts.customerId;
        this.accountType = accounts.accountType;
        this.balance = accounts.balance;
        this.transactionLimit = accounts.transactionLimit;
        this.annualIncome = accounts.annualIncome;
    }

    /**
     * Retrieves the account number.
     *
     * @return The account number.
     */
    public int getAccountNo() {
        return this.accountNo;
    }

    /**
     * Retrieves the current balance of the account.
     *
     * @return The balance of the account.
     */
    public double getBalance() {
        return this.balance;
    }

    /**
     * Retrieves the account type.
     *
     * @return The account type.
     */
    public String getAccountType() {
        return this.accountType;
    }

    /**
     * Retrieves the customer associated with the account.
     *
     * @return The customer object.
     */
    public int getCustomerId() {
        return this.customerId;
    }

    /**
     * Retrieves the transaction limit for the account.
     *
     * @return The transaction limit.
     */
    public double getTransactionLimit() {
        return this.transactionLimit;
    }

    /**
     * Sets the transaction limit for the account.
     *
     * @param amount The new transaction limit to set.
     * Updates the transaction limit for the account.
     */
    public void setTransactionLimit(double amount) {
        this.transactionLimit = amount;
    }

    /**
     * Deposits an amount into the account.
     * @param amount The amount to deposit.
     * Updates the balance of the account.
     */
    public void deposit(double amount) {
        this.balance += amount;
    }

    /**
     * Withdraws an amount from the account.
     * @param amount The amount to withdraw.
     * @return True if the withdrawal was successful, false otherwise.
     */
    public boolean withdraw(double amount) {
        if (this.balance >= amount){
            this.balance -= amount;
            return true;
        }
        return false;
    }

    /**
     * Transfers an amount from this account to another account.
     *
     * @param toAccount The destination account for the transfer.
     * @param amount    The amount to transfer.
     * @return True if the transfer was successful, false otherwise.
     */
    public boolean transfer(Account toAccount, double amount) {
        if (this.balance >= amount && amount <= this.transactionLimit ) {
            this.withdraw(amount);
            toAccount.deposit(amount);
            return true;
           
        }
        return false;
    }


    public double getAnnualIncome(){
        return this.annualIncome;
    }
}

/**
 * Represents a bank account associated with a customer.
 * Each account has an account ID, type, balance, associated customer, and transaction limit.
 */
public class Account {
    private int accountId;
    private String accountType;
    private double balance;
    private Customer customer;
    private double transactionLimit;

    /**
     * Constructs a new Account object.
     */
    public Account(){}

    /**
     * Retrieves the account ID.
     * @return The account ID.
     */
    public int getAccountId(){
        return this.accountId;
    }

    /**
     * Retrieves the current balance of the account.
     * @return The balance of the account.
     */
    public double getBalance(){
        return this.balance;
    }

    /**
     * Retrieves the account type.
     * @return The account type.
     */
    public String getAccountType(){
        return this.accountType;
    }

    /**
     * Retrieves the customer associated with the account.
     * @return The customer object.
     */
    public Customer getCustomer(){
        return this.customer;
    }

    /**
     * Retrieves the transaction limit for the account.
     * @return The transaction limit.
     */
    public double getTransactionLimit(){
        return this.transactionLimit;
    }

    /**
     * Sets the transaction limit for the account.
     * @param amount The new transaction limit to set.
     * @return True if the transaction limit was successfully set, false otherwise.
     */
    public boolean setTransactionLimit(double amount){
        if( amount > 1000){
            return false;
        }
        this.transactionLimit = amount;
        return true;
    }

    /**
     * Deposits an amount into the account.
     * @param amount The amount to deposit.
     */
    public void deposit(double amount){
        this.balance += amount;
    }

    /**
     * Withdraws an amount from the account.
     * @param amount The amount to withdraw.
     * @return True if the withdrawal was successful, false otherwise.
     */
    public boolean withdraw(double amount){
        if (this.balance < amount){
            return false;
        }
        return true;
    }

    /**
     * Transfers an amount from this account to another account.
     * @param toAccount The destination account for the transfer.
     * @param amount The amount to transfer.
     * @return True if the transfer was successful, false otherwise.
     */
    public boolean transfer(Account toAccount, double amount){
        if (this.balance < amount){
            return false;
        }
        return true;
    }
}

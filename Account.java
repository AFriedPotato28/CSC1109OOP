import java.io.FileWriter;
import java.io.IOException;

/**
 * Represents a bank account associated with a customer.
 * Each account has an account ID, type, balance, associated customer, and transaction limit.
 */
public class Account {
    private int accountId;
    private int customerId;
    private String accountType;
    private double balance;
    private double transactionLimit;
    private String filepath = "Account_Data.csv";

    /**
     * Constructs a new Account object.
     */
    public Account(int accountId, int customerId, String accountType, double balance, double transactionLimit) {
        this.accountId = accountId;
        this.customerId = customerId;
        this.accountType = accountType;
        this.balance = balance;
        this.transactionLimit = transactionLimit;
    }
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
    public int getCustomer(){
        return this.customerId;
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
        if (amount < 500){
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
        if(this.balance < amount && amount > this.transactionLimit){
            return false;
        }
        return true;
    }

    public void createAccountDetails(int customerID,int accountId,String accountType,double balance,double transactionLimit) {
        this.customerId = customerID;
        this.accountId = accountId;
        this.accountType = accountType;
        this.balance = balance;
        this.transactionLimit = transactionLimit;
        int[] integerDataToAppend = {this.customerId, this.accountId};
        double[] doubleDataToAppend = {this.balance, this.transactionLimit};
    
        //append data to next row
        try (FileWriter writer = new FileWriter(filepath, true)) {  // Append mode

            // Build CSV line with proper escaping for commas
            StringBuilder csvline = new StringBuilder();
            for (int value: integerDataToAppend){
                csvline.append(value).append(',');
            }
          
            csvline.append(escapeDoubleQuotes(this.accountType)).append(',');

            for (double value: doubleDataToAppend){
                csvline.append(value).append(',');
            }
            
            csvline.deleteCharAt(csvline.length()-1);
            csvline.append("\r\n");
            writer.append(csvline.toString());
        } catch (IOException e) {
            System.err.println("Error appending to CSV: " + e.getMessage());
        }

    }

    private static String escapeDoubleQuotes(String str) {
        if (str == null) {
            return ""; // Handle null values
        }
        StringBuilder sb = new StringBuilder();
        for (char ch : str.toCharArray()) {
            if (ch == '"' || ch == '\\' || ch == '\n' || ch == '\r' || ch == '\t' || ch == '\0' || ch == '\f') {
                sb.append('\\'); // Escape special characters
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

}

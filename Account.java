public class Account {
    private int accountId;
    private String accountType;
    private double balance;
    private Customer customer;
    private double transactionLimit;


    public Account(){}


    public int getAccountId(){
        return this.accountId;
    }

    public double getBalance(){
        return this.balance;
    }

    public String getAccountType(){
        return this.accountType;
    }

    public Customer getCustomer(){
        return this.customer;
    }

    public double getTransactionLimit(){
        return this.transactionLimit;
    }

    public boolean setTransactionLimit(double amount){
        if( amount > 1000){
            return false;
        }
        this.transactionLimit = amount;
        return true;
    }
    
    public void deposit(double amount){
        this.balance += amount;
    }

    public boolean withdraw(double amount){
        if (this.balance < amount){
            return false;
        }
        return true;
    }

    public boolean transfer(Account toAccount, double amount){
        if (this.balance < amount){
            return false;
        }
        return true;
    }

}

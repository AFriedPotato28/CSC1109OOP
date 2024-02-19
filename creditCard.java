import java.util.Date;

public class creditCard {

    private String cardNumber;
    private Date expiryDate = new Date();
    private int CVV;
    private Customer customer;
    private double balance;
    private int creditLimit;

    public String getCardNumber() { return this.cardNumber; }
    public Date getExpiryDate() { return this.expiryDate; }
    public int getCVV() { return this.CVV; }
    public Customer getCustomer() { return this.customer; }
    public double getBalance() { return this.balance; }
    public void setcreditLimit(int creditLimit) { this.creditLimit = creditLimit;}
    public boolean payCreditBill(double paymentAmount){ return false;}
    public boolean isExpired (){ return false; }

    
}

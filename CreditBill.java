import java.util.Date;

public class CreditBill extends CreditCard {
    private Date date;
    private double billAmount;
    private double amountPaid;

    public CreditBill(){
        super();
    }

    public void getDueDate(){}
    public double getBillAmount(){}
    public double getAmountPaid(){}
    public void pay(double paymentAmount){}

}

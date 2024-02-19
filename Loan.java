public class Loan {
    private int loanId;
    private double loanAmount;
    private double interestRate;
    private int loanDuration;
    private Customer customer;

    public int getLoanId(){ return this.loanId; }
    public double getLoanAmount(){ return this.loanAmount; }
    public double getInterestRate(){ return this.interestRate; }
    public int getLoanDuration() { return this.loanDuration; }
    public void setLoanDuration (int loanDuration) { this.loanDuration = loanDuration; }
    public double calculateAmount() { return this.loanAmount; }
    
}

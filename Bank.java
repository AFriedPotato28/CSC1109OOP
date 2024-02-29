import java.util.ArrayList;

public class Bank {
    private String name;
    private ArrayList<Customer> customers;
    private ArrayList<Account> accounts;
    private ArrayList<Loan> loans;
    private ArrayList<CreditCard> creditCards;

    public Bank(String name){
        this.name = name;
        this.customers = new ArrayList<>();
        this.accounts = new ArrayList<>();
        this.loans = new ArrayList<>();
        this.creditCards = new ArrayList<>();
    }

    public void addCustomer(Customer customer){
        customers.add(customer);
    }

    public void removeCustomer(Customer customer){
        customers.remove(customer);
    }

    public void addAccount(Customer customer,Account account){
        if (this.accounts.get(customer)){
            this.accounts.put(customer,account);
        }

    }

    public void removeAccount(Account account){
        accounts.remove(account);
    }

}

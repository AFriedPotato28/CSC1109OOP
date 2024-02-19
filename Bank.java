import java.util.ArrayList;
import java.util.Scanner;

public class Bank {
    private String name;
    private ArrayList<Customer> customers;
    private ArrayList<Account> accounts;
    private ArrayList<Loan> loans;
    private ArrayList<creditCard> creditCards;
    // add Security class later

    public Bank(String name){
        this.name = name;
        this.customers = new ArrayList<>();
        this.accounts = new ArrayList<>();
        this.loans = new ArrayList<>();
        this.creditCards = new ArrayList<>();
        // add Security class later
    }

    public void addCustomer(Customer customer){
        customers.add(customer);
    }

    public void removeCustomer(Customer customer){
        customers.remove(customer);
    }

    public void addAccount(Account account){
        accounts.add(account);
    }

    public void removeAccount(Account account){
        accounts.remove(account);
    }
}

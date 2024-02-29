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

    public void addCustomer(Customer customer,String name,String username, String password){
        int customerSize = this.customers.size();
        boolean usernameExists = false;

        for (Customer cust : this.customers) {
            if (cust.getUserName().equalsIgnoreCase(username)) {
                System.out.println("Customer account already exists for username");
                usernameExists = true;
                break;
            }
        }
    
        if(!usernameExists){
            this.customers.add(customerSize,customer);
            customer.createCustomerAccount(customerSize + 1, name, username, password);
            System.out.println("New Account has been created");
        }

    }

    public void removeCustomer(Customer customer){
        customers.remove(customer);
    }

    public void addAccount(Customer customer,Account account){

    }

    public void removeAccount(Account account){
        accounts.remove(account);
    }

}

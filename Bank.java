import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.IOException;

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

    public void populateCustomersList(){
        //Read data from csv to find out size
        boolean firstLine = true;
        try(BufferedReader bur = new BufferedReader(new FileReader("MOCK_DATA.csv"))){
            String sLine;
            while((sLine = bur.readLine()) !=null){
                if(firstLine){
                    firstLine= false;
                    continue;
                }
                String[] data = sLine.split(",");
                int id = Integer.parseInt(data[0]);
                String customerName = data[1];
                String customerUsername = data[2];
                String customerPassword = data[3];

                Customer newCustomer = new Customer(id, customerName, customerUsername, customerPassword);
                this.customers.add(newCustomer);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addCustomer(Customer customer,String name,String username, String password){
        //check if customer ArrayList is loaded
        if(this.customers.isEmpty()){
            populateCustomersList();
        }

        int customerSize = this.customers.size();
        System.out.println("Initial Customer Size: "+customerSize);
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
            System.out.println("There is a total of "+customerSize+" in the list");
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

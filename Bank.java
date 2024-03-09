import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;

public class Bank {
    private String name;
    private ArrayList<Customer> customers;
    private HashMap<Integer,List<Account>> accounts;
    private ArrayList<Loan> loans;
    private ArrayList<CreditCard> creditCards;

    public Bank(String name){
        this.name = name;
        this.customers = new ArrayList<>();
        this.accounts = new HashMap<Integer,List<Account>>();
        this.loans = new ArrayList<>();
        this.creditCards = new ArrayList<>();
    }

    public void populateCustomersList(){
        //Read data from csv to find out size
       
        try(BufferedReader bur = new BufferedReader(new FileReader("MOCK_DATA.csv"))){
            String sLine;
            bur.readLine();
            while((sLine = bur.readLine()) !=null){
                String[] data = sLine.split(",");
                int id = Integer.parseInt(data[0]);
                String customerName = data[1];
                String customerUsername = data[2];
                String customerPassword = data[3];
                String Salt = data[4];

                Customer newCustomer = new Customer(id, customerName, customerUsername, customerPassword,Salt);
                this.customers.add(newCustomer);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ArrayIndexOutOfBoundsException e){
            throw new RuntimeException(e);
        }
    }

    public void addCustomer(Customer customer,String name,String username, String password, String accountType){
        //check if customer ArrayList is loaded
        if(this.customers.isEmpty()){
            populateCustomersList();
        }

        int customerSize = this.customers.size();
        System.out.println("Initial Customer Size: "+customerSize);
        boolean usernameExists = false;
        int userId = 0;
        for (Customer cust : this.customers) {
            if (cust.getUserName().equalsIgnoreCase(username)) {
                System.out.println("Customer account already exists for username");
                userId = cust.getCustomerId();
                usernameExists = true;
            }
        }
    
        if(!usernameExists){
            String salt = Security.generateSalt();
            String hashPassword = Security.hashPasword(password,salt);

            this.customers.add(customerSize,customer);
            customer.createCustomerAccount(customerSize + 1, name, username, hashPassword,salt);
            System.out.println("New Account has been created");
            System.out.println("There is a total of "+(customerSize + 1) +" in the list");
        }
        
        addAccount(userId, accountType);
    }

    public void removeCustomer(Customer customer){
        customers.remove(customer);
    }

    public void populateAccountList(){
        try(BufferedReader bur = new BufferedReader(new FileReader("Account_Data.csv"))){
            String sLine;
            bur.readLine();
            while((sLine = bur.readLine()) !=null){
                String[] data = sLine.split(",");
                int customerID = Integer.parseInt(data[0]);
                int AccountID = Integer.parseInt(data[1]);
                String accountType = data[2];
                double balance = Double.parseDouble(data[3]);
                double transactionLimit = Double.parseDouble(data[4]);

                Account account = new Account(AccountID,customerID, accountType, balance , transactionLimit);

                if (!this.accounts.containsKey(customerID)){
                    this.accounts.put(customerID, new ArrayList<>());
                }

                this.accounts.get(customerID).add(account);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ArrayIndexOutOfBoundsException e){
            throw new RuntimeException(e);
        }
    }


    public void addAccount(Integer customerID,String accountType){

        if(this.accounts.isEmpty()){
            populateAccountList();
        }

        
        boolean customerIdExists = false;
        boolean accountTypeExists = false;
        int sizeOfAccount = 0;
        
        if (accountType.equals("1")){
            accountType = "Savings";
        }else{
            accountType = "Normal";
        }    

        for (Map.Entry<Integer, List<Account>> entry : this.accounts.entrySet()) {
            if(entry.getKey() == customerID){
                customerIdExists = true;
            }

            List<Account> value = entry.getValue();
            sizeOfAccount = value.size();

            for (Account accountItems : value){
                if(accountItems.getAccountType().equalsIgnoreCase(accountType)){
                    accountTypeExists = true;
                    return;
                };                
            }

        } 

        Account account = new Account((sizeOfAccount + 1),customerID, accountType, 0 , 0);
        
        if (!customerIdExists){
            this.accounts.put(customerID, new ArrayList<>());
        }
        
        if (!accountTypeExists){
            this.accounts.get(customerID).add(account);
            account.createAccountDetails(customerID, (sizeOfAccount + 1), accountType, 0, 0);
        }
        
    }

    public void removeAccount(int customerID){
       // accounts.remove(account);
    }

}

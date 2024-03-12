import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Bank {
    private String name;
    private ArrayList<Customer> customers;
    private HashMap<Integer,List<Account>> accounts;
    private ArrayList<Loan> loans;
    private ArrayList<CreditCard> creditCards;
    private Security securityInstance;

    public Bank(String name){
        this.name = name;
        this.customers = new ArrayList<>();
        this.accounts = new HashMap<Integer,List<Account>>();
        this.loans = new ArrayList<>();
        this.creditCards = new ArrayList<>();
        securityInstance = new Security();

        populateCustomersList();
        populateAccountList();
    }

    public void welcomeMessage(){
        System.out.println("Welcome to " + this.name + " Bank");
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

    public void addCustomer(Customer customer, String name,String username, String password, String accountType){
        int customerSize = this.customers.size();

        Optional<Customer> custOptional = this.customers.stream().filter((cust) -> cust.getUserName().equalsIgnoreCase(username)).findFirst();
        int userId = !custOptional.isEmpty() ? custOptional.get().getCustomerId() : customerSize + 1;
        
        if(!custOptional.isEmpty()) {
            System.out.println("Customer account already exists for username");
        }else{
            String salt = Security.generateSalt();
            String hashPassword = Security.hashPasword(password,salt);

            this.customers.add(customerSize,customer);
            customer.createCustomerAccount(userId, name, username, hashPassword, salt);
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

        boolean customerIdExists = false;
        int sizeOfAccount = 0;
        accountType = accountType.equals("1") ? "Savings" : "Normal";
      
        for (Map.Entry<Integer, List<Account>> entry : this.accounts.entrySet()) {
            if(entry.getKey() == customerID){
                customerIdExists = true;
            }

            List<Account> value = entry.getValue();
            sizeOfAccount = value.size();

            for (Account accountItems : value){
                if(accountItems.getAccountType().equalsIgnoreCase(accountType)){
                    return;
                };                
            }

        } 

        Account account = new Account((sizeOfAccount + 1),customerID, accountType, 0 , 0);
        
        if (!customerIdExists){
            this.accounts.put(customerID, new ArrayList<>());
        }

        this.accounts.get(customerID).add(account);
        account.createAccountDetails(customerID, (sizeOfAccount + 1), accountType, 0, 0);
    }

    public void removeAccount(int customerID){
       // accounts.remove(account);
    }

    public void setLoans(int customerId){
        try{
            String sLine;
            BufferedReader br = new BufferedReader(new FileReader("Loan_Data.csv"));
            br.readLine();
            while((sLine = br.readLine()) != null){
                String[] data = sLine.split(",");
                if(Integer.parseInt(data[1]) == customerId){
                    int loanId = Integer.parseInt(data[0]);
                    double loanAmount = Double.parseDouble(data[2]);
                    int loanDuration = Integer.parseInt(data[4]);

                    Loan loan = new Loan(loanId, customerId, loanAmount, loanDuration);
                    this.loans.add(loan);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean validateLogin(String loginUsername, String loginPassword) {
        Optional<Customer> customerOptional = this.customers.stream().filter((customer) -> customer.getUserName().equalsIgnoreCase(loginUsername)).findFirst();
        if(customerOptional.isEmpty()) {
            return false;
        }
        
        Customer customer = customerOptional.get();
        String hashedPw = Security.hashPasword(loginPassword, customer.getSalt());
        if(!customer.getPassword().equals(hashedPw)) {
            return false;
        }
        return true;
    }

    public int generateOTP(String loginUsername) { 
        return securityInstance.generateOTP(loginUsername);
    }

    public boolean authenticateOTP(String loginUsername, int OTP){
        return securityInstance.authenticateWithOTP(loginUsername, OTP);
    }

    public boolean getUsername(String username){
        return securityInstance.getAccount(username);
    }

    public int retrieveUserID(String username){
        Optional<Customer> customerOptional = this.customers.stream().filter((customer) -> customer.getUserName().equalsIgnoreCase(username)).findFirst();
        Customer customer = customerOptional.get(); 
        return customer.getCustomerId();
    }

    public void setLoginDetails(String username, String password){
        Optional<Customer> customerOptional = this.customers.stream().filter((customer) -> customer.getUserName().equalsIgnoreCase(username)).findFirst();
        Customer customer = customerOptional.get();
        securityInstance.setLoginAccount(username, password,customer.getSalt());
    }

    public boolean authenticateDetails (String username, String password){
        Optional<Customer> customerOptional = this.customers.stream().filter((customer) -> customer.getUserName().equalsIgnoreCase(username)).findFirst();
        Customer customer = customerOptional.get();
        return securityInstance.authenticateUser(username, password,customer.getSalt());
    }

    public void resetPassword(String userInfo, String newPassword) {
        ArrayList<String> HashedPasswordandSalt = securityInstance.resetPassword(userInfo, newPassword);
        updateCSV(userInfo,HashedPasswordandSalt);
    }

    public void updateCSV(String userInfo,ArrayList<String> HashedPasswordandSalt){

        String fileName = "MOCK_DATA.csv";
        String tempFile = "temp.csv";
        File oldFile = new File(fileName);
        File newFile = new File(tempFile);

        String[] titleToAppend = {"id","name","username","password","salt"};
        String csvLine = Arrays.stream(titleToAppend)
                                .map(this::escapeDoubleQuotes)
                                .collect(Collectors.joining(","));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(newFile));) {
            writer.append(csvLine);
            for (Customer cust : this.customers){
                int customerId = cust.getCustomerId();
                String customerName = cust.getName();
                String customerUserName = cust.getUserName();
                String customerPassword = cust.getPassword();
                String customerSalt = cust.getSalt();

                if(cust.getUserName().equals(userInfo)){
                    customerPassword = HashedPasswordandSalt.get(0);
                    customerSalt = HashedPasswordandSalt.get(1);
                }

                String[] datatoAppend = {String.valueOf(customerId),customerName,customerUserName,customerPassword,customerSalt};
                csvLine = Arrays.stream(datatoAppend).map(this::escapeDoubleQuotes)
                        .collect(Collectors.joining(","));

                writer.append("\n" + csvLine);
            }
            writer.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }


        if(oldFile.delete()){
            if(!newFile.renameTo(oldFile)){
                System.out.println("Done deleting old file " + oldFile);
            }
            this.customers.clear();
            populateCustomersList();
        }

    }

    private String escapeDoubleQuotes(String str) {
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

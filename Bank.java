import java.io.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Bank {
    private String name;
    private ArrayList<Customer> customers;
    private HashMap<Integer, List<Account>> accounts;
    private ArrayList<Loan> loans;
    private ArrayList<CreditCard> creditCards;
    private Security securityInstance;
    private Account account;

    public Bank(String name) {
        this.name = name;
        this.customers = new ArrayList<>();
        this.accounts = new HashMap<Integer, List<Account>>();
        this.loans = new ArrayList<>();
        this.creditCards = new ArrayList<>();
        this.account = new Account();
        securityInstance = new Security();

        populateCustomersList();
        populateAccountList();
    }

    public void welcomeMessage() {
        System.out.println("Welcome to " + this.name + " Bank");
    }

    public void populateCustomersList() {
        try (BufferedReader bur = new BufferedReader(new FileReader("MOCK_DATA.csv"))) {
            String sLine;
            bur.readLine();
            while ((sLine = bur.readLine()) != null) {
                String[] data = sLine.split(",");
                int id = Integer.parseInt(data[0]);
                String customerName = data[1];
                String customerUsername = data[2];
                String customerPassword = data[3];
                String Salt = data[4];

                Customer newCustomer = new Customer(id, customerName, customerUsername, customerPassword, Salt);
                this.customers.add(newCustomer);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new RuntimeException(e);
        }
    }

    public void populateAccountList() {
        try (BufferedReader bur = new BufferedReader(new FileReader("Account_Data.csv"))) {
            String sLine;
            bur.readLine();
            while ((sLine = bur.readLine()) != null) {
                String[] data = sLine.split(",");
                int AccountID = Integer.parseInt(data[0]);
                int customerID = Integer.parseInt(data[1]);
                String accountType = data[2];
                double balance = Double.parseDouble(data[3]);
                double transactionLimit = Double.parseDouble(data[4]);

                Account account = new Account(AccountID, customerID, accountType, balance, transactionLimit);

                if (!this.accounts.containsKey(customerID)) {
                    this.accounts.put(customerID, new ArrayList<>());
                }

                this.accounts.get(customerID).add(account);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new RuntimeException(e);
        }
    }


    public void addCustomer(Customer customer){
        int customerSize = this.customers.size();
        Optional<Customer> custOptional = this.customers.stream().filter((cust) -> cust.getUserName().equalsIgnoreCase(customer.getUserName())).findFirst();

        if (!custOptional.isEmpty()) {
            System.out.println("Customer account already exists for username");
        } else {
            String salt = Security.generateSalt();
            String hashPassword = Security.hashPassword(customer.getPassword(), salt);
            customer.setItems(customerSize + 1, hashPassword, salt);

            this.customers.add(customerSize, customer);
            csv_help.createCustomerAccount(customer);
            System.out.println("New Customer has been created");
            System.out.println("There is a total of " + (customerSize + 1) + " in the list");
        }

        int userId = !custOptional.isEmpty() ? custOptional.get().getCustomerId() : this.customers.size();
        addAccount(userId,"1");
    }

    public boolean addAccount(Integer customerID, String accountType) {

        boolean customerIdExists = false;
        boolean accountTypeExists = false;
        int sizeOfAccount = 0;
        accountType = accountType.equals("1") ? "Savings" : accountType.equals("2") ?  "Credit Card" : "Loan";
  
        for (Map.Entry<Integer, List<Account>> entry : this.accounts.entrySet()) {
            List<Account> accounts = entry.getValue();
            sizeOfAccount = accounts.size();
            if (entry.getKey() == customerID) {
                customerIdExists = true;
                for (Account account : accounts) {
                    if (account.getAccountType().equalsIgnoreCase(accountType)) {
                        accountTypeExists = true;
                    }
                    ;
                }
            }
        }

        Account account = new Account((sizeOfAccount + 1),customerID, accountType, 0 , 500);
        
        if (!customerIdExists){
            this.accounts.put(customerID, new ArrayList<>());
        }
        if (!accountTypeExists) {
            this.accounts.get(customerID).add(account);
            csv_help.generateCSVtoAccount(customerID, account);
            return true;
        }
        return false;
    }

    /**credit card **/

    public int getCustomerCreditCards(int customerId) {
        int creditCardCount = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("mock_credit_card.csv"))) {
            String sLine;
            br.readLine();
            while ((sLine = br.readLine()) != null) {
                String[] data = sLine.split(",");
                if (Integer.parseInt(data[1]) == customerId) {
                    int creditCardId = Integer.parseInt(data[0]);
                    int accountNo = Integer.parseInt(data[2]);
                    String cardNumber = data[3];
                    int cvv = Integer.parseInt(Security.decryptCVV(data[4]));
                    YearMonth expiration_date = YearMonth.parse(data[5]);
                    double balance = Double.parseDouble(data[6]);
                    int creditLimit = Integer.parseInt(data[7]);

                    CreditCard creditCard = new CreditCard(creditCardId, customerId, accountNo, balance, creditLimit, cardNumber, cvv, expiration_date);
                    this.creditCards.add(creditCard);
                }
                creditCardCount++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!" + e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return creditCardCount + 1;
    }

    public void applyCreditCard(int newCreditCardId, int customerId, int accountNo, int annualIncome) {
        CreditCard creditCard = new CreditCard(newCreditCardId, customerId, accountNo, annualIncome);
        updateCreditCardToCSV(creditCard);
    }

    public void updateCreditCardToCSV(CreditCard creditCard) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("mock_credit_card.csv", true))) {
            String[] dataToAppend = {String.valueOf(creditCard.getCreditCardId()),
                    String.valueOf(creditCard.getCustomerId()),
                    String.valueOf(creditCard.getAccountNo()), creditCard.getCardNumber(),
                    creditCard.getEncryptedCVV(), String.valueOf(creditCard.getExpiryDate()),
                    String.valueOf(creditCard.getBalance()), String.valueOf(creditCard.getCreditLimit())};
            String line = String.join(",", dataToAppend);
            bw.write(line);
            bw.newLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void cancelCreditCard(int custId){
        try {
            // Read the existing CSV file
            BufferedReader br = new BufferedReader(new FileReader("mock_credit_card.csv"));
            StringBuilder csvContent = new StringBuilder();
            String sLine;

            while ((sLine = br.readLine()) != null) {
                String[] columns = sLine.split(",");

                // Assuming the data-to-delete is in the fourth column
                if (columns.length >= 2 && !columns[1].equals(String.valueOf(custId))) {
                    csvContent.append(sLine).append("\n");
                }
            }
            br.close();

            BufferedWriter bw = new BufferedWriter(new FileWriter("mock_credit_card.csv"));
            bw.write(csvContent.toString());
            bw.close();

        }catch (FileNotFoundException e) {
            System.out.println("File not found!" + e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /** end creditcard */

    /** authentication */
    public boolean validateLogin(String loginUsername, String loginPassword) {
        Optional<Customer> customerOptional = this.customers.stream().filter((customer) -> customer.getUserName().equalsIgnoreCase(loginUsername)).findFirst();
        if (customerOptional.isEmpty()) {
            return false;
        }

        Customer customer = customerOptional.get();
        String hashedPw = Security.hashPassword(loginPassword, customer.getSalt());
        if (!customer.getPassword().equals(hashedPw)) {
            return false;
        }
        return true;
    }

    public int generateOTP(String loginUsername) {
        return securityInstance.generateOTP(loginUsername);
    }

    public boolean authenticateOTP(String loginUsername, int OTP) {
        return securityInstance.authenticateWithOTP(loginUsername, OTP);
    }

    public boolean validateUsername(String username){
        return securityInstance.validateUsername(username);
    }
    
    
    public Customer retrieveUserInfo(String username){
        Optional<Customer> customerOptional = this.customers.stream().filter(customer -> customer.getUserName().equalsIgnoreCase(username)).findFirst();
        Customer customer = customerOptional.get();
        return customer;
    }

    public void setLoginDetails(String username, String password){
        securityInstance.setLoginAccount(username, password,retrieveUserInfo(username).getSalt());
    }

    public boolean authenticateDetails (String username, String password){
        return securityInstance.authenticateUser(username, password,retrieveUserInfo(username).getSalt());
    }

    public void resetPassword(String userInfo, String newPassword) {
        Customer customer = retrieveUserInfo(userInfo);
        
        ArrayList<String> HashedPasswordandSalt = securityInstance.resetPassword(userInfo, newPassword);
        customer.setPassword(HashedPasswordandSalt.get(0));
        customer.setSalt(HashedPasswordandSalt.get(1));
        boolean success = csv_help.updateCSVOfCustomerData(userInfo,this.customers,HashedPasswordandSalt);
        

        if (success) {
            this.customers.set(customer.getCustomerId(), customer);
        }
    }
    /** Final Authentication */


    /** For Loans *
     * 
     * @param customerId
     * @return
    */

    public int getCustomerLoans(int customerId) {
        int loanCount = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("Loan_Data.csv"))){
            String sLine;
            
            br.readLine();
            while ((sLine = br.readLine()) != null) {
                String[] data = sLine.split(",");
                if (Integer.parseInt(data[1]) == customerId) {
                    int loanId = Integer.parseInt(data[0]);
                    double loanAmount = Double.parseDouble(data[2]);
                    int loanDuration = Integer.parseInt(data[4]);

                    Loan loan = new Loan(loanId, customerId, loanAmount, loanDuration);
                    this.loans.add(loan);
                    System.out.println("Loan Amount: " + loanAmount + ", Loan Duration: " + loanDuration);

                }
                loanCount++;
            }
            return loanCount + 1;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void applyLoan(int customerId, int newLoanNumber, double loanAmount, int loanDuration) {
        getCustomerLoans(customerId);

        Loan newloan = new Loan(newLoanNumber, customerId, loanAmount, loanDuration);
        updateLoantoCsv(newloan);
    }
    
    public void updateLoantoCsv(Loan newloan) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Loan_Data.csv", true))) {
            //Append object to csv
            String loanData = newloan.getLoanId() + "," + newloan.getCustomerId() + "," + newloan.getLoanAmount() + "," + newloan.getInterestRate() + "," + newloan.getLoanDuration();
            bw.write(loanData);
            bw.newLine();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
 
     public void setLoans(int customerId){
        try (BufferedReader br = new BufferedReader(new FileReader("Loan_Data.csv"))){
            String sLine;
            
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
    /** no more loans */

    /** Accounts **/
    
    public void populateAccount(String username){
        
        Account accounts = getAccountInfo(username);
        this.account.populateItem(accounts);
    }

    private Account getAccountInfo(String username){
        Optional<Account> accountInformation = this.accounts.entrySet().stream()
                                                .filter(entry -> entry.getKey() == retrieveUserInfo(username).getCustomerId())
                                                .flatMap(entry -> entry.getValue().stream()).filter((account) -> account.getAccountType().equals("Savings")).findFirst();
        Account accountInfo = accountInformation.get();
        return accountInfo;
    }

    public int getAccountNo(){
        return this.account.getAccountNo();
    }

    public double getBalance() {
        return this.account.getBalance();
    }

    public double getTransactionLimit() {
        return this.account.getTransactionLimit();
    }

    public void withdraw(double money, String username) {
        Account accountInformation = getAccountInfo(username);
        if (accountInformation.getCustomerId() == retrieveUserInfo(username).getCustomerId()){
            if(this.account.withdraw(money)){
                csv_help.updateCSVOfAccount(accounts, account);
                securityInstance.logActivity(this.account.getCustomerId(), 5);
                return;
            };
        }
    }

    public void deposit(double money, String username) {
        Account accountInformation = getAccountInfo(username);
        if (accountInformation.getCustomerId() == retrieveUserInfo(username).getCustomerId()){
            this.account.deposit(money);
            csv_help.updateCSVOfAccount(accounts, this.account);
            securityInstance.logActivity(this.account.getCustomerId(),4);
            return;
        }
    }

    public boolean changeTransactionLimit(int limit, String userInfo) {
        Account accountInformation = getAccountInfo(userInfo);
        if (accountInformation.getCustomerId() == retrieveUserInfo(userInfo).getCustomerId()){
            this.account.setTransactionLimit(limit);
            csv_help.updateCSVOfAccount(accounts, this.account);
            return true;
        }

        return false;
    }

    public void transferAmount(double money, String recipient) {
        Account accountRecipient = getAccountInfo(recipient);

        if(this.account.transfer(accountRecipient, money)){
            csv_help.updateCSVofTwoAccounts(this.accounts, this.account, accountRecipient);
            securityInstance.logActivity(this.account.getCustomerId(),2);
        }
    } 
    
}
    


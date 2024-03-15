import java.io.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Bank {
    private String name;
    private ArrayList<Customer> customers;
    private HashMap<Integer, List<Account>> accounts;
    private ArrayList<Loan> loans;
    private ArrayList<CreditCard> creditCards;
    private Security securityInstance;
    private Account account;
    private HashMap<String, Currency> listofCurrencies;

    public Bank(String name) {
        this.name = name;
        this.customers = new ArrayList<>();
        this.accounts = new HashMap<Integer, List<Account>>();
        this.loans = new ArrayList<>();
        this.creditCards = new ArrayList<>();
        this.account = new Account();
        this.securityInstance = new Security();
        this.listofCurrencies = new HashMap<String, Currency>();

        csv_get_help.populateCustomersList(this.customers);
        csv_get_help.populateAccountList(this.accounts);
        csv_get_help.populateCurrencyList(this.listofCurrencies);
    }

    public void welcomeMessage() {
        System.out.println("Welcome to " + this.name + " Bank");
    }

    public void addCustomer(Customer customer) {
        int customerSize = this.customers.size();
        Optional<Customer> custOptional = this.customers.stream()
                .filter((cust) -> cust.getUserName().equalsIgnoreCase(customer.getUserName())).findFirst();

        if (!custOptional.isEmpty()) {
            System.out.println("Customer account already exists for username");
        } else {
            String salt = Security.generateSalt();
            String hashPassword = Security.hashPassword(customer.getPassword(), salt);
            customer.setItems(customerSize + 1, hashPassword, salt);

            this.customers.add(customerSize, customer);
            csv_update_help.createCustomerAccount(customer);
            System.out.println("New Customer has been created");
            System.out.println("There is a total of " + (customerSize + 1) + " in the list");
        }

        int userId = !custOptional.isEmpty() ? custOptional.get().getCustomerId() : this.customers.size();
        addAccount(userId, "1");
    }

    public boolean addAccount(Integer customerID, String accountType) {

        boolean customerIdExists = false;
        boolean accountTypeExists = false;
        int sizeOfAccount = 0;
        accountType = accountType.equals("1") ? "Savings" : accountType.equals("2") ? "Credit Card" : "Loan";

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

        Account account = new Account((sizeOfAccount + 1), customerID, accountType, 0, 500);

        if (!customerIdExists) {
            this.accounts.put(customerID, new ArrayList<>());
        }
        if (!accountTypeExists) {
            this.accounts.get(customerID).add(account);
            csv_update_help.generateCSVtoAccount(customerID, account);
            return true;
        }
        return false;
    }

    /**
     * credit card
     **/

    public void getCustomerCreditCards(int customerId) {
        try (BufferedReader br = new BufferedReader(new FileReader("mock_credit_card.csv"))) {
            String sLine;
            br.readLine();
            while ((sLine = br.readLine()) != null) {
                String[] data = sLine.split(",");
                if (Integer.parseInt(data[1]) == customerId) {
                    int creditCardId = Integer.parseInt(data[0]);
                    int accountNo = Integer.parseInt(data[2]);
                    String cardNumber = data[3];
                    String cvv = data[4];
                    YearMonth expiration_date = YearMonth.parse(data[5]);
                    double balance = Double.parseDouble(data[6]);
                    double remainingCredit = Double.parseDouble(data[7]);
                    int creditLimit = Integer.parseInt(data[8]);

                    CreditCard creditCard = new CreditCard(creditCardId, customerId, accountNo, balance,
                            remainingCredit, creditLimit, cardNumber, cvv, expiration_date);
                    this.creditCards.add(creditCard);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!" + e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int getCreditCardCount(int customerId) {
        int count = 0;
        for (CreditCard card : creditCards) {
            if (card.getCustomerId() == customerId) {
                count++;
            }
        }
        return count;
    }

    public void applyCreditCard(int newCreditCardId, int customerId, int accountNo, int annualIncome) {
        int existingCreditCardCount = getCreditCardCount(customerId);
        if (existingCreditCardCount < 2) {
            CreditCard creditCard = new CreditCard(newCreditCardId, customerId, accountNo, annualIncome);
            updateCreditCardToCSV(creditCard);
            System.out.println("Credit Card application successful!");
        } else {
            // Deny the application of a new credit card
            System.out.println("You have reached the limit of two credit cards per account!");
        }

    }

    public void updateCreditCardToCSV(CreditCard creditCard) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("mock_credit_card.csv", true))) {
            String[] dataToAppend = { String.valueOf(creditCard.getCreditCardId()),
                    String.valueOf(creditCard.getCustomerId()),
                    String.valueOf(creditCard.getAccountNo()), creditCard.getCardNumber(),
                    creditCard.getEncryptedCVV(), String.valueOf(creditCard.getExpiryDate()),
                    String.valueOf(creditCard.getBalance()), String.valueOf(creditCard.getRemainingCredit()),
                    String.valueOf(creditCard.getCreditLimit()) };
            String line = String.join(",", dataToAppend);
            bw.write(line);
            bw.newLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void cancelCreditCard(int custId, String username) {
        try {
            // Read the existing CSV file
            BufferedReader br = new BufferedReader(new FileReader("mock_credit_card.csv"));
            StringBuilder csvContent = new StringBuilder();
            String sLine;
            String last4Digits;
            Scanner scanner = new Scanner(System.in);

            // Display credit cards for the given customer
            System.out.println("Credit cards for customer " + username + ":");
            while ((sLine = br.readLine()) != null) {
                String[] columns = sLine.split(",");
                if (columns.length >= 2 && columns[1].equals(String.valueOf(custId))) {
                    last4Digits = columns[3].substring(columns[3].length() - 4);
                    System.out.println("Card Number: **** **** **** " + last4Digits); // Display the last 4 digits
                }
            }
            br.close();

            // Prompt user to select which credit card to delete
            System.out.print("Enter the last 4 digits of the card number to delete: ");
            String cardNumberToDelete = scanner.nextLine();

            // Read the file again to filter out the selected credit card
            br = new BufferedReader(new FileReader("mock_credit_card.csv"));
            while ((sLine = br.readLine()) != null) {
                String[] columns = sLine.split(",");
                // Keep the credit card if it doesn't match the one to delete
                if (columns.length >= 4 && !columns[3].substring(columns[3].length() - 4).equals(cardNumberToDelete)) {
                    csvContent.append(sLine).append("\n");
                }
            }
            br.close();

            // Write the updated content back to the CSV file
            BufferedWriter bw = new BufferedWriter(new FileWriter("mock_credit_card.csv"));
            bw.write(csvContent.toString());
            bw.close();
            scanner.close();

            System.out.println("Credit card ending in " + cardNumberToDelete + " has been deleted.");

        } catch (FileNotFoundException e) {
            System.out.println("File not found!" + e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /** end creditcard */

    /**
     * authentication
     */
    public boolean validateLogin(String loginUsername, String loginPassword) {
        Optional<Customer> customerOptional = this.customers.stream()
                .filter((customer) -> customer.getUserName().equalsIgnoreCase(loginUsername)).findFirst();
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

    public boolean validateUsername(String username) {
        return securityInstance.validateUsername(username);
    }

    public Customer retrieveUserInfo(String username) {
        Optional<Customer> customerOptional = this.customers.stream()
                .filter(customer -> customer.getUserName().equalsIgnoreCase(username)).findFirst();
        Customer customer = customerOptional.get();
        return customer;
    }

    public void setLoginDetails(String username, String password) {
        securityInstance.setLoginAccount(username, password, retrieveUserInfo(username).getSalt());
    }

    public boolean authenticateDetails(String username, String password) {
        return securityInstance.authenticateUser(username, password, retrieveUserInfo(username).getSalt());
    }

    public void resetPassword(String userInfo, String newPassword) {
        Customer customer = retrieveUserInfo(userInfo);

        ArrayList<String> HashedPasswordandSalt = securityInstance.resetPassword(userInfo, newPassword);
        customer.setPassword(HashedPasswordandSalt.get(0));
        customer.setSalt(HashedPasswordandSalt.get(1));
        boolean success = csv_update_help.updateCSVOfCustomerData(userInfo, this.customers, HashedPasswordandSalt);

        if (success) {
            this.customers.set(customer.getCustomerId(), customer);
        }
    }

    /** Final Authentication */

    /**
     * For Loans *
     *
     * @param customerId
     * @return
     */

    public int getCustomerLoans(int customerId) {
        int loanCount = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("Loan_Data.csv"))) {
            this.loans.clear();
            String sLine;
            br.readLine();
            while ((sLine = br.readLine()) != null) {
                String[] data = sLine.split(",");
                if (Integer.parseInt(data[1]) == customerId) {
                    int loanId = Integer.parseInt(data[0]);
                    double loanAmount = Double.parseDouble(data[2]);
                    LocalDate loanDueDate = LocalDate.parse(data[4]);

                    Loan loan = new Loan(loanId, customerId, loanAmount, loanDueDate);
                    this.loans.add(loan);
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

    public void applyLoan(int newLoanNumber, int customerId, double loanAmount) {
        LocalDate loanDueDate = calculateLoanDueDate(loanAmount);
        Loan newloan = new Loan(newLoanNumber, customerId, loanAmount, loanDueDate);
        addLoanToCsv(newloan);
    }

    public LocalDate calculateLoanDueDate(double loanAmount) {
        LocalDate currentDate = LocalDate.now();

        if (loanAmount > 50000) {
            return currentDate.plusDays(60);
        } else if (loanAmount > 30000) {
            return currentDate.plusDays(40);
        } else if (loanAmount > 20000) {
            return currentDate.plusDays(30);
        } else {
            return currentDate.plusDays(3);
        }
    }

    public void addLoanToCsv(Loan newloan) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Loan_Data.csv", true))) {
            // Append object to csv
            String loanData = newloan.getLoanId() + "," + newloan.getCustomerId() + "," + newloan.getLoanAmount() + ","
                    + newloan.getInterestRate() + "," + newloan.getLoanDueDate();
            bw.write(loanData);
            bw.newLine();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void repayLoan(int repayLoanId, double repayLoanAmount) {
        for (Loan loan : this.loans) {
            if (loan.getLoanId() == repayLoanId) {
                if (repayLoanAmount <= getBalance()) {
                    this.account.withdraw(repayLoanAmount);
                    csv_update_help.updateCSVOfAccount(accounts, account);

                    String filePath = "Loan_Data.csv";
                    // Read existing data from CSV file
                    List<String[]> lines = new ArrayList<>();
                    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                        String line;
                        System.out.println("Existing data in the CSV file:");
                        while ((line = reader.readLine()) != null) {
                            StringTokenizer tokenizer = new StringTokenizer(line, ",");
                            List<String> tokens = new ArrayList<>();
                            while (tokenizer.hasMoreTokens()) {
                                tokens.add(tokenizer.nextToken());
                            }
                            lines.add(tokens.toArray(new String[0]));
                            System.out.println("THIS IS TOKENS:" + tokens);
                        }
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    // Modify csv data
                    if (!lines.isEmpty()) {
                        for (int i = 1; i < lines.size(); i++) {
                            // System.out.println(lines.get(i)[0]);
                            String[] row = lines.get(i);
                            if (Integer.parseInt(row[0]) == repayLoanId) {
                                row[2] = String.valueOf(Double.parseDouble(row[2]) - repayLoanAmount); // subtract
                                                                                                       // repaidLoanAmount
                                                                                                       // from
                                                                                                       // loanAmount
                                System.out.println("\nModified data:");
                                StringBuilder rowStr = new StringBuilder();
                                for (String value : row) {
                                    rowStr.append(value).append(",");
                                }
                                rowStr.deleteCharAt(rowStr.length() - 1); // Removes last comma
                                System.out.println(rowStr.toString());
                                break; // Break the loop once the row is modified
                            }
                        }
                    }

                    // Write the modified data back to the CSV file
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                        for (String[] row : lines) {
                            for (int i = 0; i < row.length; i++) {
                                writer.write(row[i]);
                                if (i < row.length - 1) {
                                    writer.write(","); // if current element isn't last element, write "," to separate
                                                       // values in CSV file
                                }
                            }
                            writer.newLine();
                        }
                        // System.out.println("\nCSV file has been edited successfully.");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println(
                            "There is insufficient funds in your account to repay the loan by the specified amount.");
                }
            } else {
                System.out.println("There is no loan with that loanID in your account.");
            }
        }
    }

    public void getLoans(int customerId) {
        try (BufferedReader br = new BufferedReader(new FileReader("Loan_Data.csv"))) {
            String sLine;
            this.loans.clear();
            br.readLine();
            while ((sLine = br.readLine()) != null) {
                String[] data = sLine.split(",");
                if (Integer.parseInt(data[1]) == customerId) {
                    int loanId = Integer.parseInt(data[0]);
                    double loanAmount = Double.parseDouble(data[2]);
                    LocalDate loanDueDate = LocalDate.parse(data[4]);

                    Loan loan = new Loan(loanId, customerId, loanAmount, loanDueDate);
                    this.loans.add(loan);
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateOverdueLoans() {
        LocalDate dateNow = LocalDate.now();
        for (Loan loan : this.loans) {
            if (dateNow.isAfter(loan.getLoanDueDate())) {
                double newLoanAmount = loan.getLoanAmount() * (1 + loan.getInterestRate());
                LocalDate newLoanDueDate = calculateLoanDueDate(newLoanAmount);
                loan.setLoanAmount(newLoanAmount);
                // not sure if loanDueDate automatically updates
                loan.setLoanDueDate(newLoanDueDate);
                csv_update_help.updateLoanToCsv(this.loans);
            }
        }
    }

    public void printLoans() {
        System.out.println("Outstanding Loans:");
        for (Loan loan : this.loans) {
            System.out.println("Loan ID: " + loan.getLoanId() + ", Loan amount: " + loan.getLoanAmount()
                    + " Loan Deadline: " + loan.getLoanDueDate());
        }
    }

    /** no more loans */

    /**
     * Accounts
     **/

    public void populateAccount(String username) {

        Account accounts = getAccountInfo(username);
        this.account.populateItem(accounts);
    }

    private Account getAccountInfo(String username) {
        Optional<Account> accountInformation = this.accounts.entrySet().stream()
                .filter(entry -> entry.getKey() == retrieveUserInfo(username).getCustomerId())
                .flatMap(entry -> entry.getValue().stream())
                .filter((account) -> account.getAccountType().equals("Savings")).findFirst();
        Account accountInfo = accountInformation.get();
        return accountInfo;
    }

    public int getAccountNo() {
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
        if (accountInformation.getCustomerId() == retrieveUserInfo(username).getCustomerId()) {
            if (this.account.withdraw(money)) {
                csv_update_help.updateCSVOfAccount(this.accounts, account);
                securityInstance.logActivity(this.account.getCustomerId(), 5);
                return;
            }
            ;
        }
    }

    public void deposit(double money, String username) {
        Account accountInformation = getAccountInfo(username);
        if (accountInformation.getCustomerId() == retrieveUserInfo(username).getCustomerId()) {
            this.account.deposit(money);
            csv_update_help.updateCSVOfAccount(this.accounts, this.account);
            securityInstance.logActivity(this.account.getCustomerId(), 4);
            return;
        }
    }

    public boolean changeTransactionLimit(int limit, String userInfo) {
        Account accountInformation = getAccountInfo(userInfo);
        if (accountInformation.getCustomerId() == retrieveUserInfo(userInfo).getCustomerId()) {
            this.account.setTransactionLimit(limit);
            csv_update_help.updateCSVOfAccount(this.accounts, this.account);
            return true;
        }

        return false;
    }

    public void transferAmount(double money, String recipient) {
        Account accountRecipient = getAccountInfo(recipient);

        if (this.account.transfer(accountRecipient, money)) {
            csv_update_help.updateCSVofTwoAccounts(this.accounts, this.account, accountRecipient);
            securityInstance.logActivity(this.account.getCustomerId(), 2);
        }
    }

    public void seeAllCurrencyExchanges() {
        StringBuilder sb = new StringBuilder();
        sb.append("Current supported exchange rate:\n");
        for (Entry<String, Currency> currency : this.listofCurrencies.entrySet()) {
            sb.append("Currency from " + currency.getValue().getToSource() + " to " +
                    currency.getKey() + " Purchase Price: " +
                    currency.getValue().getpurchasePrice() + " Selling Price: " +
                    currency.getValue().getsellingPrice()
                    + " \n");
        }
        System.out.println(sb.toString());
    }

    public void exchangeCurrency(String currencyName, double amount) {

    }

    // left off here still got issue
    private Currency getRelatedCurrency(String currencyName) {
        Currency currency = this.listofCurrencies.get(currencyName);
        return currency;
    }

    public boolean checkCurrency(String currency) {
        if (this.listofCurrencies.containsKey(currency)) {
            return true;
        }
        return false;
    }

    public Currency getRates(String currency) {
        if (this.listofCurrencies.containsKey(currency)) {
            return getRelatedCurrency(currency);
        }
        return null;
    }

}

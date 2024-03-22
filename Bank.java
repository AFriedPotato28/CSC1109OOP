import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

import implementations.*;

/**
 * Represents a bank with a name, list of customers, accounts, loans, and credit
 * cards.
 * Supports operations such as adding customers, accounts, and credit cards,
 * applying for loans, and managing accounts.
 * Supports authentication and security features such as password hashing and
 * OTP generation.
 * Supports currency exchange and transaction operations.
 */

public class Bank {
    /**
     * The name of the bank.
     */
    private String name;

    /**
     * The list of customers associated with the bank.
     */
    private ArrayList<Customer> customers;

    /**
     * The list of accounts associated with the bank.
     */
    private HashMap<Integer, ArrayList<Account>> accounts;

    /**
     * The list of loans associated with the bank.
     */
    private HashMap<Integer, ArrayList<Loan>> loanList;

    /*
     * The list assocaited with the credit cards associated with the bank
     */

    private HashMap<Integer, ArrayList<CreditCard>> creditList;

    /**
     * The list of loans associated with the bank.
     */
    private ArrayList<Loan> loans;

    /**
     * The list of credit cards associated with the bank.
     */
    private ArrayList<CreditCard> creditCards;

    /**
     * The security instance associated with the bank.
     */
    private Security securityInstance;

    /**
     * The account object associated with the bank.
     */
    private ArrayList<Account> account;

    /**
     * The list of currencies associated with the bank.
     */
    // private HashMap<String, Currency> listofCurrencies;

    /**
     * Constructs a new Bank object with the specified name.
     * 
     * @param name           The name of the bank.
     *                       name - The name of the bank.
     *                       customers - The list of customers associated
     *                       with the bank.
     *                       accounts - The list of accounts associated with
     *                       the bank.
     *                       loans - The list of loans associated with the
     *                       bank.
     *                       creditCards - The list of credit cards
     *                       associated with the bank.
     *                       account - The account object associated with the
     *                       bank.
     *                       securityInstance - The security instance
     *                       associated with the bank.
     * 
     * 
     * 
     *                       csv_get_help.populateCustomersList(this.customers)
     *                       - Populates the list of customers from the CSV
     *                       file.
     * @param this.customers - The list of customers associated with the
     *                       bank.
     * 
     *                       csv_get_help.populateAccountList(this.accounts)
     *                       - Populates the list of accounts from the CSV
     *                       file.
     * @param this.accounts  - The list of accounts associated with the bank.
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     */

    public Bank(String name) {
        this.name = name;
        this.customers = new ArrayList<>();
        this.accounts = new HashMap<Integer, ArrayList<Account>>();
        this.loanList = new HashMap<Integer, ArrayList<Loan>>();
        this.creditList = new HashMap<Integer, ArrayList<CreditCard>>();
        this.loans = new ArrayList<>();
        this.creditCards = new ArrayList<>();
        this.account = new ArrayList<Account>();
        this.securityInstance = new Security();

        populateList();
    }

    /**
     * Bank
     */

    /**
     * Prints a welcome message from the bank.
     */
    public void welcomeMessage() {
        System.out.println("Welcome to " + this.name + " Bank");
    }

    /**
     * 
     * This is use to populate the list of dataset into hashmap for easier
     * access/updates.
     */

    private void populateList() {
        csv_get_help.populateCustomersList(this.customers);
        csv_get_help.populateAccountList(this.accounts);
        csv_get_help.populateListLoan(this.loanList);
        csv_get_help.populateCreditList(this.creditList);
    }

    /** Related to Customers Here */

    /**
     * Attempts to add a new customer to the bank.
     * 
     * @param customer The customer object to add.
     *                 customerSize - The size of the customer list.
     *                 custOptional - The optional customer object.
     *                 salt - The salt for the customer's password.
     *                 hashPassword - The hashed password for the customer's
     *                 password.
     * 
     *                 if customer already exists, print "Customer account already
     *                 exists for username".
     *                 else, generate a new customer account and print "New Customer
     *                 has been created".
     * 
     *                 csv_update_help.createCustomerAccount(customer) - Creates a
     *                 new customer account in the CSV file.
     * @param customer - The customer object to add.
     * 
     *                 Prints amount of customers in the list.
     */

    public void addCustomer(String name, String username, String password) {

        Customer customer = new Customer(name, username, password);
        int customerSize = this.customers.size();

        Optional<Customer> custOptional = this.customers.stream()
                .filter((cust) -> cust.getUserName().equalsIgnoreCase(customer.getUserName())).findFirst();

        // Check if the customer already exists
        if (!custOptional.isEmpty()) {
            System.out.println("Customer account already exists for username");
        } else {
            String salt = Security.generateSalt();
            String hashPassword = Security.hashPassword(customer.getPassword(), salt);
            customer.setItems(customerSize + 1, hashPassword, salt);

            this.customers.add(customerSize, customer);
            csv_update_help.createCustomerAccount(customer);
            System.out.println("New Customer has been created");
        }

        int userId = !custOptional.isEmpty() ? custOptional.get().getCustomerId() : this.customers.size();
        addAccount(userId, "1", 0);
    }

    /**
     * Attempts to add a new account to the bank.
     * 
     * @param customerID       The ID of the customer.
     * @param accountType      The type of account to add.
     *                         accountType - The type of account to add.
     * 
     *                         if accountType is "1", set accountType to "Savings",
     *                         else if accountType is "2", set accountType to
     *                         "Credit Card", else set accountType to "Loan".
     * 
     *                         if customerID exists, set customerIdExists to true,
     *                         else customerIDExists remains false.
     * 
     *                         if accountType exists, set accountTypeExists to true,
     *                         else accountTypeExists remains false.
     * 
     *                         Calculate the size of the account.
     * 
     *                         Create a new account object with the new size of the
     *                         account, customerID, accountType, balance, and
     *                         transactionLimit.
     * @param accountNo        - The new size of the account.
     * @param customerID       - The ID of the customer.
     * @param accountType      - The type of account to add.
     * @param balance          - The balance of the account, set to 0.
     * @param transactionLimit - The transaction limit of the account, set to 500.
     * 
     *                         if customerID does not exist, add a new account to
     *                         the list of accounts.
     *                         if accountType does not exist, add a new account to
     *                         the list of accounts and generate a new CSV file for
     *                         the account.
     *                         return true if the account was added successfully,
     *                         else return false.
     */
    public boolean addAccount(Integer customerID, String accountType, double balance) {

        boolean customerIdExists = false;
        boolean accountTypeExists = false;
        accountType = accountType.equals("1") ? "Savings" : "Loan";

        for (Map.Entry<Integer, ArrayList<Account>> entry : this.accounts.entrySet()) {
            List<Account> accounts = entry.getValue();
            if (entry.getKey() == customerID) {
                customerIdExists = true;
                for (Account account : accounts) {
                    if (account.getAccountType().equalsIgnoreCase(accountType)) {
                        accountTypeExists = true;
                    }
                }
            }
        }

        int sizeOfAccount = (int) this.accounts.values().stream().flatMap(List::stream).count();

        int transactionLimit = accountType.equalsIgnoreCase("Savings") ? 500 : 0;
        Account account = new Account((sizeOfAccount + 1), customerID, accountType, balance, transactionLimit);
        if (!customerIdExists) {
            this.accounts.put(customerID, new ArrayList<>());
        }

        if (!accountTypeExists || account.getAccountType().equalsIgnoreCase("Loan")) {
            this.accounts.get(customerID).add(account);
            csv_update_help.generateCSVtoAccount(customerID, account);
            return true;
        }
        return false;
    }

    public void populateUserInfo(String username) {

        Account accounts = getAccountInfo(username);
        this.account = this.accounts.get(accounts.getCustomerId());
        this.loans = this.loanList.get(accounts.getCustomerId());
        this.creditCards = this.creditList.get(accounts.getCustomerId());

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

        return this.account.stream().filter((cust) -> cust.getAccountType().equals("Savings")).findFirst().get()
                .getAccountNo();
    }

    public double getBalance() {
        return this.account.stream().filter((cust) -> cust.getAccountType().equals("Savings")).findFirst().get()
                .getBalance();
    }

    public double getTransactionLimit() {
        return this.account.stream().filter((cust) -> cust.getAccountType().equals("Savings")).findFirst().get()
                .getTransactionLimit();
    }

    public Account getAccountInfo() {
        return this.account.stream().filter((cust) -> cust.getAccountType().equals("Savings"))
                .findFirst().get();
    }

    public boolean withdraw(double money, String username) {
        Account accountInformation = getAccountInfo(username);
        if (accountInformation.getCustomerId() == retrieveUserInfo(username).getCustomerId()) {
            if (accountInformation.withdraw(money)) {
                csv_update_help.updateCSVOfAccount(this.accounts);
                securityInstance.logActivity(accountInformation.getCustomerId(), 5);
                return true;
            }
        }
        return false;
    }

    public boolean deposit(double money, String username) {
        Account accountInformation = getAccountInfo(username);
        if (accountInformation.getCustomerId() == retrieveUserInfo(username).getCustomerId()) {
            accountInformation.deposit(money);
            csv_update_help.updateCSVOfAccount(this.accounts);
            securityInstance.logActivity(accountInformation.getCustomerId(), 4);
            return true;
        }
        return false;
    }

    public boolean changeTransactionLimit(int limit, String userInfo) {
        Account accountInformation = getAccountInfo(userInfo);
        if (accountInformation.getCustomerId() == retrieveUserInfo(userInfo).getCustomerId()) {
            accountInformation.setTransactionLimit(limit);
            csv_update_help.updateCSVOfAccount(this.accounts);
            return true;
        }

        return false;
    }

    public boolean transferAmount(double money, String recipient) {
        Account toAccount = getAccountInfo(recipient);
        Account personalAccount = getAccountInfo();
        if (personalAccount.transfer(toAccount, money)) {
            csv_update_help.updateCSVOfAccount(this.accounts);
            securityInstance.logActivity(personalAccount.getCustomerId(), 2);
            return true;
        }

        return false;
    }

    /**
     * Retrieves the number of credit cards for a given customer.
     * 
     * @param customerId The ID of the customer.
     * @return The number of credit cards for the customer.
     */
    public int getCreditCardCount(int customerId) {
        int count = 0;

        if (this.creditCards == null) {
            return count;
        }

        for (CreditCard card : this.creditCards) {
            if (card.getCustomerId() == customerId) {
                count++;
            }
        }
        return count;
    }

    /**
     * Attempts to apply for a new credit card for a given customer.
     * 
     * @param customerId   The ID of the customer.
     * @param accountNo    The account number of the customer.
     * @param annualIncome The annual income of the customer.
     * 
     *                     if the customer has less than 2 credit cards, create a
     *                     new credit card object with the specified customer ID,
     *                     account number, and annual income.
     *                     Add the credit card to the list of credit cards and
     *                     update the credit card to the CSV file.
     *                     else, deny the application of a new credit card and print
     *                     "You have reached the limit of two credit cards per
     *                     account!".
     */
    public void applyCreditCard(int customerId, int accountNo, int annualIncome) {
        int existingCreditCardCount = getCreditCardCount(customerId);

        if (existingCreditCardCount < 2) {
            CreditCard creditCard = new CreditCard(customerId, accountNo, annualIncome);

            if (this.creditCards == null) {
                this.creditCards = new ArrayList<CreditCard>();
            }

            this.creditList.get(customerId).add(creditCard);
            csv_update_help.updateCreditCardToCSV(creditCard);
            System.out.println("Credit Card application successful!");
        } else {
            // Deny the application of a new credit card
            System.out.println("You have reached the limit of two credit cards per account!");
        }

    }


    /**
     * Attempts to cancels a credit card for a given customer.
     * 
     * @param scanner  The Scanner object to read user input.
     * @param custId   The ID of the customer.
     * @param username The username of the customer.
     * 
     *                 try to read the mock_credit_card.csv file and display the
     *                 credit cards for the given customer.
     *                 while the CSV file is not empty, split the data and display
     *                 details for all credit cards for the given customer.
     * 
     *                 Prompt the user to select which credit card to delete.
     *                 if the user enters -1, return.
     *                 if card number to delete is invalid, print "The digits you
     *                 have input is invalid.".
     *                 if the credit card has a balance or cash advance payable,
     *                 print "You still have balance left in your bank, you cannot
     *                 delete this card.".
     *                 if credit card is found, read the file again to filter out
     *                 the selected credit card.
     *                 keep the credit card if it doesn't match the one to delete.
     * 
     *                 close the file and write the updated content back to the
     *                 'mock_credit_card.csv' file.
     *                 if the credit card is deleted, print "Credit card ending in "
     *                 + cardNumberToDelete + " has been deleted.".
     * 
     *                 catch FileNotFoundException and IOException and throw a new
     *                 RuntimeException with the exception message.
     */
    public void cancelCreditCard(Scanner scanner, int custId, String username) {
        try {
            System.out.println("Credit cards for customer " + username + ":");

            for (CreditCard card : this.creditCards) {
                String last4Digits = card.getCardNumber().substring(12, 16).toString();
                System.out.println("Card Number: **** **** **** " + last4Digits); // Display the last 4 digits

            }

            // Prompt user to select which credit card to delete
            System.out.print("Enter the digits of the card number to delete: (Press -1 to exit) ");
            String cardNumberToDelete = scanner.next();

            if (cardNumberToDelete.equals("-1"))
                return;

            Optional<CreditCard> cardItem = this.creditCards.stream()
                    .filter((cust) -> cust.getCardNumber().equalsIgnoreCase(cardNumberToDelete)).findFirst();

            if (!cardItem.isPresent()) {
                System.out.println("The digits you have input is invalid.");
                return;
            }

            CreditCard card = cardItem.get();

            if (card.getBalance() > 0 || card.getCashAdvancePayable() > 0) {
                System.out
                        .println("You still have bills unpaid left in your bank account, you cannot delete this card.");
                return;
            }

            this.creditCards.remove(card);
            csv_update_help.writeCSVToCreditCard(this.creditList);
            System.out.println("Credit card ending in " + cardNumberToDelete + " has been deleted.");

        } catch (Exception e) {
            return;
        }
    }

    /**
     * Attemps to pay the credit card bills for a given customer.
     * 
     * @param scanner    The Scanner object to read user input.
     * @param customerId The ID of the customer.
     * @param username   The username of the customer.
     * 
     *                   Display credit cards for the given customer.
     *                   while the CSV file is not empty, split the data and display
     *                   details for all credit cards for the given customer.
     *                   print the card number and the outstanding balance for each
     *                   credit card.
     * 
     *                   Prompt the user to select which credit card to pay.
     *                   if the user input is invalid, print "Credit card with the
     *                   specified card number not found.".
     * 
     *                   Prompt the user to enter the payment amount.
     *                   if the payment amount is greater than the balance, print
     *                   "Payment of $" + paymentAmount + " for card ending in " +
     *                   card.getCardNumber().substring(card.getCardNumber().length()
     *                   - 4) + " failed.".
     *                   if the payment amount is less than or equal to the balance,
     *                   pay the bill and update the CSV file, print "Payment of $"
     *                   + paymentAmount + " for card ending in " +
     *                   card.getCardNumber().substring(card.getCardNumber().length()
     *                   - 4) + " was successful.".
     */
    public void payCreditCardBills(Scanner scanner, int customerId, String username) {
        // Display credit cards for the given customer
        System.out.println("Credit cards for customer " + username + ":");

        for (CreditCard card : this.creditCards) {
            System.out.println(
                    "Card Number ending in " +
                            card.getCardNumber().substring(card.getCardNumber().length() - 4) +
                            " (Outstanding balance: " + card.getBalance() + ")");
        }

        // Prompt user to select which credit card to pay
        System.out.println("Enter the card number to pay the bill: ");
        String cardNumber = scanner.next();

        Optional<CreditCard> creditCardExists = this.creditCards.stream()
                .filter((card) -> card.getCardNumber().equals(cardNumber)).findFirst();

        if (!(creditCardExists.isPresent())) {
            System.out.println("Credit card with the specified card number not found.");
            return;
        }

        // Prompt user to enter payment amount
        System.out.println("Enter the payment amount: ");
        double paymentAmount = scanner.nextDouble();

        if (paymentAmount == 0) {
            System.out.println("Payment amount is invalid. ");
            return;
        }

        // Find the credit card with the specific card number and pay the bill
        CreditCard card = creditCardExists.get();
        if (getBalance() >= paymentAmount) {
            if (card.payCreditBill(paymentAmount)) {
                // Payment successful
                System.out.println("Payment of $" + paymentAmount + " for card ending in " +
                        card.getCardNumber().substring(card.getCardNumber().length() - 4) + " was successful.");
                csv_update_help.updateExistingCreditCardBills(card, cardNumber);
                Account savingsAccount = getAccountInfo();
                savingsAccount.withdraw(paymentAmount);
                csv_update_help.updateCSVOfAccount(this.accounts);
            }
        } else {
            // Payment failed
            System.out.println("Payment of $" + paymentAmount + " for card ending in " +
                    card.getCardNumber().substring(card.getCardNumber().length() - 4) + " failed.");

        }
    }

    /**
     * Credit Card Cash Advance Withdrawal
     * 
     * @param scanner    Scanner object to read user input
     * @param customerId ID of the customer
     * @param username   Username of the customer
     * 
     *                   Display credit cards for the given customer and their
     *                   remaining cash advance credit
     * 
     *                   Prompt user to select which credit card to withdraw from
     *                   if the credit card does not exist, print "Credit card with
     *                   the specified card number not found."
     *                   if the credit card cannot withdraw anymore, print "Credit
     *                   card with the specified card number cannot withdraw
     *                   anymore"
     * 
     *                   Prompt user to enter withdrawal amount
     *                   try to read the withdrawal amount from the scanner and
     *                   calculate the final withdrawal amount inclusive of the cash
     *                   advance withdrawal fee
     *                   if the withdrawal amount is valid, withdraw the amount and
     *                   update the CSV file, print "Withdrawal of $" +
     *                   finalwithdrawalAmount + " for card ending in " +
     *                   card.getCardNumber().substring(card.getCardNumber().length()
     *                   - 4) + " was successful."
     *                   if the withdrawal amount is invalid, print "Withdrawal of
     *                   $" + finalwithdrawalAmount + " for card ending in " +
     *                   card.getCardNumber().substring(card.getCardNumber().length()
     *                   - 4) + " failed."
     *                   catch any exceptions and return
     * 
     */
    public void CashAdvanceWithdrawal(Scanner scanner, int customerId, String username) {
        // Display credit cards for given customer
        System.out.println("Credit cards for customer " + username + ":");

        for (CreditCard card : this.creditCards) {
            if (card.getCustomerId() == customerId) {
                System.out.println(
                        "Card Number ending in " +
                                card.getCardNumber().substring(card.getCardNumber().length() - 4) +
                                " (Remaining Credit " + (0.3 * card.getCreditLimit() - card.getCashAdvancePayable())
                                + ")");
            }
        }

        // Prompt user to select which credit card to withdraw from
        System.out.println("Enter the card number to withdraw from: ");
        String cardNumber = scanner.next();

        Optional<CreditCard> creditCardExists = this.creditCards.stream()
                .filter((card) -> card.getCardNumber().equals(cardNumber)).findFirst();

        if (!(creditCardExists.isPresent())) {
            System.out.println("Credit card with the specified card number not found.");
            return;
        } else if ((creditCardExists.get().getCashAdvanceLimit()
                - creditCardExists.get().getCashAdvancePayable()) <= 0) {
            System.out.println("Credit card with the specified card number cannot withdraw anymore");
            return;
        }

        // Prompt user to enter withdrawal amount
        System.out.println(
                "Do note that cash advance withdrawal fee of $10 or 5% of cash withdrawal amount will be charged, whichever is higher.");
        System.out.println("Enter the withdrawal amount: ");

        try {
            double withdrawalAmount = scanner.nextDouble();
            double finalwithdrawalAmount = Math.max(10 + withdrawalAmount, withdrawalAmount * 1.05);
            CreditCard card = creditCardExists.get();

            if (card.cashAdvanceWithdrawal(withdrawalAmount)) {
                // Withdrawal successful
                System.out.println("Withdrawal of $" + finalwithdrawalAmount + " for card ending in " +
                        card.getCardNumber().substring(card.getCardNumber().length() - 4) + " was successful.");
                csv_update_help.updateExistingCreditCardBills(card, cardNumber);
            } else {
                // Withdrawal failed
                System.out.println("Withdrawal of $" + finalwithdrawalAmount + " for card ending in " +
                        card.getCardNumber().substring(card.getCardNumber().length() - 4) + " failed.");
            }

        } catch (Exception e) {
            return;
        }

        return;
    }

    /**
     * Pay Cash Advance Payables
     * 
     * @param scanner    Scanner object to read user input
     * @param customerId ID of the customer
     * @param username   Username of the customer
     */
    public void payCashAdvancePayables(Scanner scanner, int customerId, String username) {
        // Display credit cards for given customer
        System.out.println("Credit cards for customer " + username + ":");

        for (CreditCard card : this.creditCards) {
            if (card.getCustomerId() == customerId) {
                System.out.println(
                        "Card Number ending in " +
                                card.getCardNumber().substring(card.getCardNumber().length() - 4) +
                                " (Cash Advance Payable " + card.getCashAdvancePayable() + ")");
            }
        }

        // Prompt user to select which credit card to pay
        System.out.println("Enter the card number to pay the cash advance payable: ");
        String cardNumber = scanner.next();

        Optional<CreditCard> creditCardExists = this.creditCards.stream()
                .filter((card) -> card.getCardNumber().equals(cardNumber)).findFirst();

        if (!(creditCardExists.isPresent())) {
            System.out.println("Credit card with the specified card number not found.");
            return;
        } else if (creditCardExists.get().getCashAdvancePayable() == 0) {
            System.out.println("Credit card with this specified card number has no cash advance payable.");
            return;
        }

        // Prompt user to enter payment amount
        System.out.println("Enter the payment amount: ");
        double paymentAmount = scanner.nextDouble();
        // Find the credit card with the specific card number and pay the cash advance
        // payable
        CreditCard creditCard = creditCardExists.get();

        if (creditCard.payCashAdvancePayable(paymentAmount)) {
            // Payment successful
            System.out.println("Payment of $" + paymentAmount + " for card ending in " +
                    creditCard.getCardNumber().substring(creditCard.getCardNumber().length() - 4) + " was successful.");
            csv_update_help.updateExistingCreditCardBills(creditCard, cardNumber);
            
            Account savingsAccount = getAccountInfo();
            savingsAccount.withdraw(paymentAmount);
            csv_update_help.updateCSVOfAccount(this.accounts);
            System.out.println("Your current balance is: " + getBalance());
        } else {
            // Payment failed
            System.out.println("Payment of $" + paymentAmount + " for card ending in " +
                    creditCard.getCardNumber().substring(creditCard.getCardNumber().length() - 4) + " failed.");
        }

        return;
    }

    /* end creditcard */

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

        if(success){
            System.out.println("Successfully updated your new password");
        }
    }

    /** Final Authentication */

    public Optional<Loan> checkExistingLoan(int loanId) {
        Optional<Loan> loanOptional = this.loans.stream().filter((loan) -> loan.getLoanId() == loanId).findFirst();
        return loanOptional;
    }

    public int totalLoanCount() {
        return (int) this.loanList.values().stream().flatMap(ArrayList::stream).count();
    }

    public double totalLoanAmount() {
        double totalLoan = 0.0;

        if (this.loans == null) {
            return totalLoan;
        }

        for (Loan loan : this.loans) {
            totalLoan += loan.getLoanAmount();
        }
        return totalLoan;
    }

    public void applyLoan(int customerId, double loanAmount) {
        int newLoanId = totalLoanCount() + 1;
        LocalDate loanDueDate = calculateLoanDueDate(loanAmount);
        Loan newLoan = new Loan(newLoanId, customerId, loanAmount, loanDueDate);

        Optional<Account> accountInformation = this.account.stream().filter((account) -> account.getAccountType().equals("Loan")).findFirst();

        if (accountInformation.isPresent()) {
            Account account = accountInformation.get();
            account.deposit(loanAmount);
            csv_update_help.updateCSVOfAccount(this.accounts);

         } else {
            addAccount(customerId, "2", loanAmount);
        }
        this.loanList.get(customerId).add(newLoan);
        csv_update_help.addLoanToCsv(newLoan);
    }

    public LocalDate calculateLoanDueDate(double loanAmount) {
        LocalDate currentDate = LocalDate.now();

        return currentDate.plusDays(loanAmount > 50000 ? 60 : loanAmount > 30000 ? 40 : loanAmount > 20000 ? 30 : 3);
    }

    public void repayLoan(int repayLoanId, double repayLoanAmount) {
        DecimalFormat df = new DecimalFormat("##.00");
        Account savingsAccount = getAccountInfo();

        savingsAccount.withdraw(Double.parseDouble(df.format(repayLoanAmount)));

        Optional<Account> accountInformation = this.account.stream().filter((account) -> account.getAccountType().equals("Loan")).findFirst();

        accountInformation.get().withdraw(repayLoanAmount);
        Loan newLoanItems = checkExistingLoan(repayLoanId).get();
        newLoanItems.deductLoanAmount(repayLoanAmount);

        csv_update_help.updateCSVOfLoan(this.loanList, newLoanItems);
        csv_update_help.updateCSVOfAccount(this.accounts);
    }

    public void updateOverdueLoans() {
        LocalDate dateNow = LocalDate.now();
        

        for (Loan loan : this.loans) {
            if (dateNow.isAfter(loan.getLoanDueDate())) {
                double newLoanAmount = loan.getLoanAmount() * (1 + loan.getInterestRate());
                LocalDate newLoanDueDate = calculateLoanDueDate(newLoanAmount);
                loan.setLoanAmount(newLoanAmount);
                loan.setLoanDueDate(newLoanDueDate);
                csv_update_help.updateLoanToCsv(this.loans);
                System.out.println("Overdue loanId: " + loan.getLoanId()
                        + ", the outstanding amount has been increased due to late payment.\n");
            }
        }
    }

    public void printLoans() {
        System.out.println("Outstanding Loans:");
        if(this.loans != null){
            for (Loan loan : this.loans) {
                if (loan.getLoanAmount() > 0) {
                    System.out.println("Loan ID: " + loan.getLoanId() + ", Loan amount: " + loan.getLoanAmount()
                            + " Loan Deadline: " + loan.getLoanDueDate());
                }
    
            }
        }
    }
}
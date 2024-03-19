import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Scanner;

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
    private HashMap<Integer, List<Account>> accounts;

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
    private Account account;

    /**
     * The list of currencies associated with the bank.
     */
    private HashMap<String, Currency> listofCurrencies;

    /**
     * Constructs a new Bank object with the specified name.
     * 
     * @param name                  The name of the bank.
     *                              name - The name of the bank.
     *                              customers - The list of customers associated
     *                              with the bank.
     *                              accounts - The list of accounts associated with
     *                              the bank.
     *                              loans - The list of loans associated with the
     *                              bank.
     *                              creditCards - The list of credit cards
     *                              associated with the bank.
     *                              account - The account object associated with the
     *                              bank.
     *                              securityInstance - The security instance
     *                              associated with the bank.
     *                              listofCurrencies - The list of currencies
     *                              associated with the bank.
     * 
     *                              csv_get_help.populateCustomersList(this.customers)
     *                              - Populates the list of customers from the CSV
     *                              file.
     * @param this.customers        - The list of customers associated with the
     *                              bank.
     * 
     *                              csv_get_help.populateAccountList(this.accounts)
     *                              - Populates the list of accounts from the CSV
     *                              file.
     * @param this.accounts         - The list of accounts associated with the bank.
     * 
     *                              csv_get_help.populateCurrencyList(this.listofCurrencies)
     *                              - Populates the list of currencies from the CSV
     *                              file.
     * @param this.listofCurrencies - The list of currencies associated with the
     *                              bank.
     */
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
    public void addCustomer(Customer customer) {
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
            System.out.println("There is a total of " + (customerSize + 1) + " in the list");
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

        for (Map.Entry<Integer, List<Account>> entry : this.accounts.entrySet()) {
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

    /**
     * credit card
     **/

    /**
     * Checks if a credit card with the specified card number already exists.
     * 
     * @param cardNumber The card number to check.
     * @return True if the credit card exists, false otherwise.
     */
    private boolean checkExistingCard(String cardNumber) {
        Optional<CreditCard> card = this.creditCards.stream().filter((cust) -> cust.getCardNumber().equals(cardNumber))
                .findFirst();
        return card.isPresent();
    }

    /**
     * Retrieves the credit cards for a given customer.
     * 
     * @param customerId        The ID of the customer.
     * 
     *                          read the mock_credit_card.csv file and get the
     *                          credit cards for the given customer.
     *                          while csv file is not empty, split the data
     *                          if customerId matches the customer ID in the csv
     *                          file, retrieve the credit card details and create a
     *                          new credit card object.
     * 
     *                          Create a new credit card object with the specified
     *                          credit card ID, customer ID, account number,
     *                          balance, remaining credit, credit limit, card
     *                          number, CVV, expiration date, and cash advance
     *                          payable.
     * @param creditCardId      - The ID of the credit card.
     * @param customerId        - The ID of the customer.
     * @param accountNo         - The account number of the credit card.
     * @param balance           - The balance of the credit card.
     * @param remainingCredit   - The remaining credit of the credit card.
     * @param creditLimit       - The credit limit of the credit card.
     * @param cardNumber        - The card number of the credit card.
     * @param cvv               - The CVV of the credit card.
     * @param expiration_date   - The expiration date of the credit card.
     * @param cashAdvancedPay   - The cash advance payable of the credit card.
     * @param cashAdvancedLimit - The cash advance limit of the credit card.
     * 
     *                          if the credit card does not exist, add the credit
     *                          card to the list of credit cards.
     * 
     *                          catch FileNotFoundException and IOException and
     *                          throw a new RuntimeException with the exception
     *                          message.
     */
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
                    double cashAdvancedPay = Double.parseDouble(data[9]);
                    double cashAdvancedLimit = Double.parseDouble(data[10]);

                    CreditCard creditCard = new CreditCard(creditCardId, customerId, accountNo, balance,
                            remainingCredit, creditLimit, cardNumber, cvv, expiration_date, cashAdvancedPay,
                            cashAdvancedLimit);
                    if (!checkExistingCard(cardNumber)) {
                        this.creditCards.add(creditCard);
                    }

                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!" + e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves the number of credit cards for a given customer.
     * 
     * @param customerId The ID of the customer.
     * @return The number of credit cards for the customer.
     */
    public int getCreditCardCount(int customerId) {
        int count = 0;
        for (CreditCard card : creditCards) {
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
            this.creditCards.add(creditCard);
            updateCreditCardToCSV(creditCard);
            System.out.println("Credit Card application successful!");
        } else {
            // Deny the application of a new credit card
            System.out.println("You have reached the limit of two credit cards per account!");
        }

    }

    /**
     * Attempts to update the credit card to the CSV file.
     * 
     * @param creditCard The credit card object to update.
     * 
     *                   try to append the credit card object to the
     *                   mock_credit_card.csv file.
     *                   Append Customer ID, Account Number, Card Number, CVV,
     *                   Expiry Date, Balance, Remaining Credit, Credit Limit, Cash
     *                   Advance Payable and Cash Advance Limit to the CSV file.
     *                   catch any exceptions and throw a new RuntimeException with
     *                   the exception message.
     */
    public void updateCreditCardToCSV(CreditCard creditCard) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("mock_credit_card.csv", true))) {
            String[] dataToAppend = { String.valueOf(creditCard.getCreditCardId()),
                    String.valueOf(creditCard.getCustomerId()),
                    String.valueOf(creditCard.getAccountNo()), creditCard.getCardNumber(),
                    creditCard.getEncryptedCVV(), String.valueOf(creditCard.getExpiryDate()),
                    String.valueOf(creditCard.getBalance()), String.valueOf(creditCard.getRemainingCredit()),
                    String.valueOf(creditCard.getCreditLimit()), String.valueOf(creditCard.getCashAdvancePayable()),
                    String.valueOf(creditCard.getCashAdvanceLimit()) };
            String line = String.join(",", dataToAppend);
            bw.write(line);
            bw.newLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
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
            // Read the existing CSV file
            BufferedReader br = new BufferedReader(new FileReader("mock_credit_card.csv"));
            StringBuilder csvContent = new StringBuilder();
            String sLine;
            String last4Digits;

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

            // Read the file again to filter out the selected credit card
            br = new BufferedReader(new FileReader("mock_credit_card.csv"));
            while ((sLine = br.readLine()) != null) {
                String[] columns = sLine.split(",");
                // Keep the credit card if it doesn't match the one to delete
                if (columns.length >= 4 && !columns[3].equals(cardNumberToDelete)) {
                    csvContent.append(sLine).append("\n");
                }
            }
            br.close();

            // Write the updated content back to the CSV file
            BufferedWriter bw = new BufferedWriter(new FileWriter("mock_credit_card.csv"));
            bw.write(csvContent.toString());
            bw.close();

            System.out.println("Credit card ending in " + cardNumberToDelete + " has been deleted.");

        } catch (

        FileNotFoundException e) {
            System.out.println("File not found!" + e);
        } catch (Exception e) {
            throw new RuntimeException(e);
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
            if (card.getCustomerId() == customerId) {
                System.out.println(
                        "Card Number ending in " +
                                card.getCardNumber().substring(card.getCardNumber().length() - 4) +
                                " (Outstanding balance: " + card.getBalance() + ")");
            }
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
        // Find the credit card with the specific card number and pay the bill
        CreditCard card = creditCardExists.get();
        if (getBalance() >= paymentAmount) {
            if (card.payCreditBill(paymentAmount)) {
                // Payment successful
                System.out.println("Payment of $" + paymentAmount + " for card ending in " +
                        card.getCardNumber().substring(card.getCardNumber().length() - 4) + " was successful.");
                csv_update_help.updateExistingCreditCardBills(card, cardNumber);
                account.withdraw(paymentAmount);
                csv_update_help.updateCSVOfAccount(this.accounts, this.account);
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
            this.account.withdraw(paymentAmount);
            csv_update_help.updateCSVOfAccount(this.accounts, this.account);
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
    public void getLoans(int customerId) {
        try (BufferedReader br = new BufferedReader(new FileReader("Loan_Data.csv"))) {
            String sLine;
            this.loans.clear();
            br.readLine();
            while ((sLine = br.readLine()) != null) {
                String[] data = sLine.split(",");
                if (Integer.parseInt(data[1]) == customerId && Double.parseDouble(data[2]) > 0.0) {
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

    public int totalLoanCount() {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("Loan_Data.csv"))) {
            br.readLine();
            while (br.readLine() != null) {
                count++;
            }
            return count;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public double loanAmountFromId(int loanId) {
        double loanAmount = 0.0;
        for (Loan loan : this.loans) {
            if (loan.getLoanId() == loanId) {
                loanAmount = loan.getLoanAmount();
            }
        }
        return loanAmount;
    }

    public double totalLoanAmount() {
        double totalLoan = 0.0;
        for (Loan loan : this.loans) {
            totalLoan += loan.getLoanAmount();
        }
        return totalLoan;
    }

    public void applyLoan(int customerId, double loanAmount) {
        int newLoanId = totalLoanCount() + 1;
        LocalDate loanDueDate = calculateLoanDueDate(loanAmount);
        Loan newLoan = new Loan(newLoanId, customerId, loanAmount, loanDueDate);
        addAccount(customerId, "2", loanAmount);
        addLoanToCsv(newLoan);
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

    public boolean checkExistingLoan(int loanId) {
        Optional<Loan> loanOptional = this.loans.stream().filter((loan) -> loan.getLoanId() == loanId).findFirst();
        return loanOptional.isPresent();
    }

    public void repayLoan(int repayLoanId, double repayLoanAmount) {
        DecimalFormat df = new DecimalFormat("##.00");
        this.account.withdraw(Double.parseDouble(df.format(repayLoanAmount)));
        csv_update_help.updateCSVOfAccount(accounts, account);

        String filePath = "Loan_Data.csv";
        List<String[]> lines = new ArrayList<>();

        // Read existing data from CSV file
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            lines.add(reader.readLine().split(","));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (Integer.parseInt(data[0]) == repayLoanId) {
                    data[2] = String.valueOf(
                            Double.parseDouble(df.format(Double.parseDouble(data[2]) - repayLoanAmount)));
                }
                lines.add(data);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
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

    public boolean withdraw(double money, String username) {
        Account accountInformation = getAccountInfo(username);
        if (accountInformation.getCustomerId() == retrieveUserInfo(username).getCustomerId()) {
            if (this.account.withdraw(money)) {
                csv_update_help.updateCSVOfAccount(this.accounts, account);
                securityInstance.logActivity(this.account.getCustomerId(), 5);
                return true;
            }
        }
        return false;
    }

    public boolean deposit(double money, String username) {
        Account accountInformation = getAccountInfo(username);
        if (accountInformation.getCustomerId() == retrieveUserInfo(username).getCustomerId()) {
            this.account.deposit(money);
            csv_update_help.updateCSVOfAccount(this.accounts, this.account);
            securityInstance.logActivity(this.account.getCustomerId(), 4);
            return true;
        }
        return false;
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

    public boolean transferAmount(double money, String recipient) {
        Account accountRecipient = getAccountInfo(recipient);

        if (this.account.transfer(accountRecipient, money)) {
            csv_update_help.updateCSVofTwoAccounts(this.accounts, this.account, accountRecipient);
            securityInstance.logActivity(this.account.getCustomerId(), 2);
            return true;
        }

        return false;
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

    public void withdrawCurrency(String currencyName, double amount) {
        DecimalFormat df = new DecimalFormat("##.00");
        amount /= getCurrencyInformation(currencyName).getpurchasePrice();

        this.account.withdraw(Double.parseDouble(df.format(amount)));
        csv_update_help.updateCSVOfAccount(this.accounts, account);

    }

    private Currency getCurrencyInformation(String currencyName) {
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
            return getCurrencyInformation(currency);
        }
        return null;
    }

    public void buyInsurance(int customerId, String insuranceType, String beneficiaryName) {
        Insurance insurance = new Insurance(customerId, insuranceType, beneficiaryName);
        addInsuranceToCSV(insurance);
    }

    public void addInsuranceToCSV(Insurance insurance) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("mock_insurance_data.csv", true))) {
            // Append object to csv
            String insuranceData = insurance.getInsuranceID() + "," + insurance.getCustomerId() + ","
                    + insurance.getInsuranceType() + "," + insurance.getInsurancePremium()
                    + "," + insurance.getCoverageAmount() + "," + insurance.getBeneficiaryName();
            bw.write(insuranceData);
            bw.newLine();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
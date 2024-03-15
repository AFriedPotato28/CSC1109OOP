import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class BankSystem {
    public static void main(String[] args) {
        Bank bank = new Bank("Matthias");
        Security securityInstance = new Security();

        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        String userInfo = "";

        do {
            if (!bank.validateUsername(userInfo)) {

                bank.welcomeMessage();
                System.out.println("\nChoose an action:");
                System.out.println("1. Create new Account");
                System.out.println("2. Login to Account");
                System.out.println("0. Exit");

                System.out.println("Enter your choice: ");
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        createAccountDetails(scanner, bank, securityInstance);
                        break;
                    case 2:
                        userInfo = loginToAccount(scanner, bank, securityInstance);
                        break;
                    default:
                        if (choice != 0) {
                            System.out.println("Please enter between 1 : Creating Account or 2: Login Account");
                        }
                        break;
                }
            } else {
                System.out.println("\nChoose an action:");
                System.out.println("1. Transfer / Withdraw / Deposit");
                System.out.println("2. Credit Card Options");
                System.out.println("3. Loan Options");
                System.out.println("4. Foreign Exchanges Options");
                System.out.println("5. Account Settings");
                System.out.println("6. Log out");
                System.out.println("0. Exit");

                System.out.println("Enter your choice: ");
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        transferOrWithDraworDeposit(scanner, bank, securityInstance, userInfo);
                        break;
                    case 2:
                        creditCardOptions(scanner, bank, userInfo);
                        break;
                    case 3:
                        loanOptions(scanner, bank, userInfo);
                        break;
                    case 4:
                        foreignExchangeOptions(scanner, bank, userInfo);
                        break;
                    case 5:
                        settings(scanner, bank, securityInstance, userInfo);
                        break;
                    case 6:
                        securityInstance.logActivity(bank.retrieveUserInfo(userInfo).getCustomerId(), 3);
                        userInfo = "";
                        break;
                    default:
                        break;
                }
            }

        } while (choice != 0);

        scanner.close();

    }

    /* This creates a reusable code of proompting Input */
    private static String promptInput(String prompt, Scanner scanner) {
        System.out.println(prompt);
        return scanner.next();
    }

    private static void createAccountDetails(Scanner scanner, Bank bank, Security securityInstance) {
        String name = promptInput("Please enter your name:", scanner);
        String username = promptInput("Please enter your username:", scanner);
        String password = promptInput("Please enter your password:", scanner);
        while (!securityInstance.validatePassword(password)) {
            System.out.println("Did not meet password requirements");
            password = promptInput("Please enter your password:", scanner);
        }

        bank.addCustomer(new Customer(0, name, username, password, ""));
    }

    private static String loginToAccount(Scanner scanner, Bank bank, Security securityInstance) {
        String loginUsername = promptInput("Please enter your username", scanner);
        String loginPassword = promptInput("Please enter your password", scanner);

        while (!bank.validateLogin(loginUsername, loginPassword)) {
            System.out.println("Wrong Crediential Informations");
            loginUsername = promptInput("Please enter your username", scanner);
            loginPassword = promptInput("Please enter your password", scanner);
        }

        bank.generateOTP(loginUsername);
        bank.setLoginDetails(loginUsername, loginPassword);
        int attemptOfTries = 1;
        String OTP = promptInput("Please enter your OTP", scanner);

        while (!bank.authenticateOTP(loginUsername, Integer.valueOf(OTP)) && attemptOfTries <= 3) {
            OTP = promptInput("Please enter your OTP", scanner);

            if (attemptOfTries >= 3) {
                System.out.println("You have tried more than 3 tries to authenticate");
                break;
            }
            // Things to be fixed how to break this loop
            System.out.println("You have " + (3 - attemptOfTries) + " attempts left");
            attemptOfTries++;
        }

        if (bank.authenticateOTP(loginUsername, Integer.valueOf(OTP))) {
            securityInstance.logActivity(bank.retrieveUserInfo(loginUsername).getCustomerId(), 1);
            bank.populateAccount(loginUsername);
            return loginUsername;
        }

        return "";
    }

    /**
     * @param scanner
     * @param bank
     * @param securityInstance
     * @param userInfo
     */
    private static void resetPassword(Scanner scanner, Bank bank, Security securityInstance, String userInfo) {
        String currentPassword = promptInput("Please enter your current password", scanner);

        while (!bank.authenticateDetails(userInfo, currentPassword)) {
            currentPassword = promptInput("Please re-enter your current password", scanner);
        }

        String newPassword = promptInput("Please enter your new password", scanner);

        while (!securityInstance.validatePassword(newPassword)) {
            System.out.println("You did not meet password requirements");
            newPassword = promptInput("Please re-enter your new password", scanner);
        }

        String checkNewPassword = promptInput("Please re-enter your new password", scanner);

        while (!newPassword.equals(checkNewPassword)) {
            checkNewPassword = promptInput("Please re-enter your password again", scanner);
        }

        bank.resetPassword(userInfo, newPassword);
    }

    /**
     * @param scanner
     * @param bank
     * @param securityInstance
     * @param userInfo
     */

    private static void settings(Scanner scanner, Bank bank, Security securityInstance, String userInfo) {

        int accountChoice = -1;

        do {
            System.out.println("\nChoose an action:");
            System.out.println("1. Reset Password");
            System.out.println("2. Change Transaction Limit");
            System.out.println("3. Check Balance");
            System.out.println("0. Exit");

            System.out.println("Enter your choice: ");
            accountChoice = scanner.nextInt();

            switch (accountChoice) {
                case 1:
                    resetPassword(scanner, bank, securityInstance, userInfo);
                    break;
                case 2:
                    changeTransactionLimit(scanner, bank, userInfo);
                    break;
                case 3:
                    checkBalance(scanner, bank, userInfo);
                    break;
            }
        } while (accountChoice != 0);

    }

    private static void checkBalance(Scanner scanner, Bank bank, String userInfo) {
        double balance = bank.getBalance();
        System.out.println("Your current balance is " + balance + " left in your bank");
    }

    /**
     * @param scanner
     * @param bank
     * @param userInfo
     */

    private static void changeTransactionLimit(Scanner scanner, Bank bank, String userInfo) {

        int limit = 0;
        boolean valid = false;

        System.out.println("Your current transaction limit is " + bank.getTransactionLimit());

        while (!valid || limit <= 500) {
            try {
                System.out.println("Please enter a valid transaction limit and a numeric value above 500. Type -1 to exit");
                limit = scanner.nextInt();

                if (limit == -1.0) {
                    break;
                }

                valid = true;
            } catch (InputMismatchException e) {
                scanner.nextLine();
            }
        }
        if (valid) {
            if (bank.changeTransactionLimit(limit, userInfo)) {
                System.out.println("You have successfully updated your transaction limit to " + limit);
            }
        } else {
            System.out.println("You have not successfully updated your transaction limit");
        }
    }

    private static void transferOrWithDraworDeposit(Scanner scanner, Bank bank, Security securityInstance, String userInfo) {

        int accountChoice = -1;

        do {
            System.out.println("\nChoose an action:");
            System.out.println("1. Transfer");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("0. Exit");

            System.out.println("Please enter your choice");
            accountChoice = scanner.nextInt();

            switch (accountChoice) {
                case 1:
                    transferAmount(scanner, bank, userInfo);
                    break;
                case 2:
                    withdraw(scanner, bank, userInfo);
                    break;
                case 3:
                    deposit(scanner, bank, userInfo);
                    break;
                default:
                    break;
            }

        } while (accountChoice != 0);
    }

    private static void transferAmount(Scanner scanner, Bank bank, String username) {
        boolean valid = false;
        System.out.println("You currently have $" + bank.getBalance() + " in your bank account");
        String recipientUsername = promptInput("Please enter a recipient username: ", scanner);

        try {
            if (bank.retrieveUserInfo(recipientUsername) != null) {
                valid = true;
            }
        } catch (NoSuchElementException e) {
            System.out.println("Invalid Recipient username: " + recipientUsername);
            return;
        }

        double transferAmount = 0.0;
        boolean getAmounttoPaid = false;

        do {
            if (valid == true) {
                try {
                    System.out.println("Please enter a valid transfer amount. Press -1 to exit.");
                    transferAmount = scanner.nextDouble();
                    scanner.nextLine();

                    if (transferAmount == -1.0) return;

                    if (transferAmount > 0.0 && bank.getBalance() >= transferAmount && transferAmount <= bank.getTransactionLimit()) {
                        getAmounttoPaid = true;
                    }
                } catch (InputMismatchException | IllegalArgumentException e) {
                    System.out.println("That's not a valid number. Please enter a valid amount.");
                    scanner.nextLine(); // Consume the invalid input
                }
            }
        } while (!getAmounttoPaid);

        if (getAmounttoPaid) {
            bank.transferAmount(transferAmount, recipientUsername);
            System.out.println("Successfully transferred " + transferAmount + " to " + recipientUsername + ".");
            System.out.println("Your current balance is: " + bank.getBalance());
        } else {
            System.out.println("Transfer failed.");
        }
    }

    private static void withdraw(Scanner scanner, Bank bank, String username) {
        double money = 0.0;
        boolean valid = false;
        System.out.println("You currently have " + bank.getBalance() + " in your bank account");

        while (!valid) {
            try {
                if (money == -1.0) {
                    break;
                }
                System.out.println("Please enter a valid deposit amount, Press -1 to exit.");
                money = scanner.nextDouble();

                if (bank.getBalance() >= money && money > 0.0) {
                    valid = true;
                }
            } catch (InputMismatchException e) {
                scanner.nextLine();
            }
        }

        if (valid) {
            bank.withdraw(money, username);
            System.out.println("You have successfully withdraw " + money + ". Currently you have value of " + bank.getBalance());
        } else {
            System.out.println("You have failed to withdraw " + money);
        }

    }

    private static void deposit(Scanner scanner, Bank bank, String username) {
        double money = 0.0;
        boolean valid = false;

        while (!valid) {
            try {
                if (money == -1.0) {
                    break;
                }
                System.out.println("Please enter a valid deposit amount, Press -1 to exit.");
                money = scanner.nextDouble();
                valid = true;
            } catch (InputMismatchException e) {
                scanner.nextLine();
            }
        }

        if (valid) {
            bank.deposit(money, username);
            System.out.println("You currently have a amount of " + bank.getBalance() + " in your account ");
        } else {
            System.out.println("You have not updated your account and your current balance is " + bank.getBalance());
        }
    }

    private static void creditCardOptions(Scanner scanner, Bank bank, String userInfo) {
        int customerId = bank.retrieveUserInfo(userInfo).getCustomerId();
       
        bank.getCustomerCreditCards(customerId);
        

        System.out.println("1. Apply Credit Card");
        System.out.println("2. Cancel Credit Card");
        System.out.println("3. Pay Credit Card Bill");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                int accountNo = bank.getAccountNo();
                int annualIncome;
                do {
                    annualIncome = Integer.parseInt(promptInput("Please enter your annual income: ", scanner));
                    if (annualIncome < 15000) {
                        System.out.println("Unable to apply for a credit card! (Annual income is less than $15000)");
                    }
                } while (annualIncome < 15000);
                bank.applyCreditCard(customerId, accountNo, annualIncome);
                break;
            case 2:
                bank.cancelCreditCard(scanner, customerId, userInfo);
                break;
            case 3:
                bank.payCreditCardBills(scanner, customerId, userInfo);
                break;
            default:
                if (choice != 0) {
                    System.out.println("Please enter a number between 1-3!");
                }
                break;
        }
    }

    private static void loanOptions(Scanner scanner, Bank bank, String userInfo) {
        int customerId = bank.retrieveUserInfo(userInfo).getCustomerId();
        bank.getLoans(customerId);
        bank.updateOverdueLoans();
        bank.printLoans();

        System.out.println("1. Apply Loan");
        System.out.println("2. Repay Loan");
        System.out.println("3. Exit ");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                int newLoanNumber = bank.getLoanCount(customerId);
                double bankBalance = bank.getBalance();
                double currentLoanAmount = bank.totalLoanAmount();
                double maximumLoanAmount = bankBalance * 2 - currentLoanAmount;
                System.out.println("Bank balance: $" + bankBalance + "\nYou can loan up to: $" + (maximumLoanAmount));
                double loanAmount = 0.0;
                do {
                    loanAmount = Double.parseDouble(promptInput("\nPlease enter amount to loan. Enter -1 to exit", scanner));
                    if (loanAmount == -1.0) {
                        System.out.println("Exiting...");
                        break;
                    }
                    try {
                        if (loanAmount <= 0) {
                            System.out.println("Invalid input, loan amount must be a positive number");
                        } else if (loanAmount > maximumLoanAmount) {
                            System.out.println("Unsuccessful, you can only loan up to: " + maximumLoanAmount);
                        } else {
                            System.out.println("inputs customerID: " + customerId);
                            bank.applyLoan(newLoanNumber, customerId, loanAmount);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Entered an invalid input");
                        loanAmount = -2;
                    }
                } while (loanAmount <= 0);
                break;
            case 2:
                int repayLoanId;
                bank.printLoans();
                while (true) {
                    try {
                        repayLoanId = Integer.parseInt(promptInput("Please enter LoanID of the loan you are repaying", scanner));
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter valid integer for LoanId");
                    }
                }
                double repayLoanAmount = Double.parseDouble(promptInput("Please enter the amount you are repaying", scanner));
                bank.repayLoan(repayLoanId, repayLoanAmount);
                break;
            default:
                if (choice != 0) {
                    System.out.println("Please enter between 1 : Apply Loan or 2: Repay Loan");
                }
                break;
        }

    }

    private static void foreignExchangeOptions(Scanner scanner, Bank bank, String userInfo) {

        bank.seeAllCurrencyExchanges();

        System.out.println("1. Withdrawal of Foreign Exchange");
        System.out.println("2. Exit");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                exchangeMoney(scanner, bank, userInfo);
        }
    }

    private static void exchangeMoney(Scanner scanner, Bank bank, String userInfo) {
        try {
            String chString = promptInput("Please enter a valid currency to exchange", scanner);

            while (!bank.checkCurrency(chString)) {
                bank.seeAllCurrencyExchanges();
                chString = promptInput("Please enter a valid currency to exchange", scanner);
            }
            System.out.println("Your bank has " + bank.getBalance() * bank.getRates(chString).getpurchasePrice() + " " + bank.getRates(chString).getSymbol() + " amount");

            System.out.println("Please enter a valid amount to exchange");
            int amount = scanner.nextInt();

            while (!(amount >= 0 && amount < (bank.getBalance() * bank.getRates(chString).getpurchasePrice()))) {
                System.out.println("Please enter a valid amount to exchange");
                amount = scanner.nextInt();
            }

            bank.withdrawCurrency(chString, amount);

        } catch (InputMismatchException e) {
            return;
        }

    }
}

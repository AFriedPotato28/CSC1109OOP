import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import implementations.Security;

/**
 * Main class for the Bank System.
 */
public class BankSystem {
    private static final int TIME_TO_KICK = 5; // time to kick in seconds
    private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /**
     * Main method to run the Bank System.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        Bank bank = new Bank("Matthias");
        Security securityInstance = new Security();

        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        String userInfo = "";

        do {
            if (!bank.validateUsername(userInfo)) {
                // User is not logged in. Handle account creation or login.
                bank.welcomeMessage();
                System.out.println("\nChoose an action:");
                System.out.println("1. Create new Account");
                System.out.println("2. Login to Account");
                System.out.println("0. Exit");
                System.out.print("Enter your choice: ");

                try {
                    choice = scanner.nextInt();
                } catch (Exception e) {
                    System.out.println("Please enter a valid choice");
                    scanner.nextLine(); // consume the newline
                    choice = -1;
                }

                switch (choice) {
                    case 1:
                        createAccountDetails(scanner, bank, securityInstance);
                        break;
                    case 2:
                        userInfo = loginToAccount(scanner, bank, securityInstance);
                        // Once logged in, initiate the logout timer
                        // initiateLogoutTimer();
                        break;
                }
            } else {
                System.out.println("\nChoose an action:");
                System.out.println("1. Transfer / Withdraw / Deposit");
                System.out.println("2. Credit Card Options");
                System.out.println("3. Loan Options");
                System.out.println("4. Account Settings");
                System.out.println("5. Log out");
                System.out.println("0. Exit");

                System.out.println("Enter your choice: ");

                try {
                    choice = scanner.nextInt();
                } catch (Exception e) {
                    System.out.println("Please enter a valid choice");
                    scanner = new Scanner(System.in);
                    choice = -1;
                }

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
                    // case 100:
                    // foreignExchangeOptions(scanner, bank, userInfo);
                    // break;
                    case 4:
                        settings(scanner, bank, securityInstance, userInfo);
                        break;
                    // case 101:
                    // insuranceOptions(scanner, bank, userInfo);
                    // break;
                    case 5:
                        securityInstance.logActivity(bank.retrieveUserInfo(userInfo).getCustomerId(), 3);
                        userInfo = "";
                        break;
                    default:
                        break;
                }

            }
        } while (choice != 0);

        scanner.close();
        scheduler.shutdownNow(); // Properly shutdown the scheduler
    }

    /**
     * Initiates the logout timer.
     */
    private static void initiateLogoutTimer() {
        scheduler.schedule(() -> {
            System.out.println("You have been logged out due to inactivity.");
            System.exit(0); // or any other way to handle logout
        }, TIME_TO_KICK, TimeUnit.SECONDS);
    }

    /**
     * Resets the logout timer.
     */
    private static void resetLogoutTimer() {
        scheduler.shutdownNow(); // Cancel any previously running tasks
        scheduler = Executors.newScheduledThreadPool(1); // Reinitialize the scheduler
        initiateLogoutTimer();
    }

    /**
     * Prompt user for input with a message.
     *
     * @param prompt  The message to prompt the user.
     * @param scanner The scanner object for user input.
     * @return The user input as a String.
     */
    /* This creates a reusable code of prompting Input */
    private static String promptInput(String prompt, Scanner scanner) {
        System.out.println(prompt);
        return scanner.next();
    }

    /**
     * Creates a new account with user-provided details.
     *
     * @param scanner          The scanner object for user input.
     * @param bank             The Bank object to add the customer to.
     * @param securityInstance The Security object for password validation.
     */
    private static void createAccountDetails(Scanner scanner, Bank bank, Security securityInstance) {
        String name = promptInput("Please enter your name:", scanner);
        String username = promptInput("Please enter your username:", scanner);
        String password = promptInput("Please enter your password:", scanner);
        while (!securityInstance.validatePassword(password)) {
            System.out.println("Did not meet password requirements");
            password = promptInput("Please enter your password:", scanner);
        }

        bank.addCustomer(name, username, password);
    }

    /**
     * Logs the user into their account.
     *
     * @param scanner          The scanner object for user input.
     * @param bank             The Bank object to validate login credentials.
     * @param securityInstance The Security object for OTP authentication.
     * @return The username of the logged-in user.
     */
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

        while (attemptOfTries <= 3) {
            try {
                String OTP = promptInput("Please enter your OTP", scanner);

                if (bank.authenticateOTP(loginUsername, Integer.valueOf(OTP))) {
                    securityInstance.logActivity(bank.retrieveUserInfo(loginUsername).getCustomerId(), 1);
                    bank.populateUserInfo(loginUsername);
                    return loginUsername;
                }
            } catch (NumberFormatException e) {
            }
            System.out.println("You have " + (3 - attemptOfTries) + " attempts left");
            attemptOfTries++;

        }
        return null;
    }

    /**
     * Resets the user's password.
     *
     * @param scanner          The scanner object for user input.
     * @param bank             The Bank object to reset the password for.
     * @param securityInstance The Security object for password validation and hashing.
     * @param userInfo         The username of the user whose password is being reset.
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
     * Displays and handles settings options for the user.
     *
     * @param scanner          The scanner object for user input.
     * @param bank             The Bank object to perform settings operations.
     * @param securityInstance The Security object for security-related operations.
     * @param userInfo         The username of the logged-in user.
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

            try {
                accountChoice = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Please enter a valid choice");
                scanner = new Scanner(System.in);
                accountChoice = -1;
            }

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

    /**
     * Displays the current balance of the user's bank account.
     *
     * @param scanner  The scanner object for user input.
     * @param bank     The Bank object from which the balance is retrieved.
     * @param userInfo The username of the logged-in user.
     */
    private static void checkBalance(Scanner scanner, Bank bank, String userInfo) {
        double balance = bank.getBalance();
        System.out.println("Your current balance is " + balance + " left in your bank");
    }

    /**
     * Allows the user to change their transaction limit.
     *
     * @param scanner  The scanner object for user input.
     * @param bank     The Bank object associated with the user's account.
     * @param userInfo The username of the logged-in user.
     */
    private static void changeTransactionLimit(Scanner scanner, Bank bank, String userInfo) {

        int limit = 0;
        boolean valid = false;

        System.out.println("Your current transaction limit is " + bank.getTransactionLimit());

        while (!valid || limit <= 500) {
            try {
                System.out.println(
                        "Please enter a valid transaction limit and a numeric value above 500. Type -1 to exit");
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

    /**
     * Allows the user to perform transfer, withdrawal, or deposit actions.
     *
     * @param scanner          The scanner object for user input.
     * @param bank             The Bank object associated with the user's account.
     * @param securityInstance The Security instance for security-related operations.
     * @param userInfo         The username of the logged-in user.
     */
    private static void transferOrWithDraworDeposit(Scanner scanner, Bank bank, Security securityInstance,
            String userInfo) {

        int accountChoice = -1;

        do {
            System.out.println("\nChoose an action:");
            System.out.println("1. Transfer");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("0. Exit");

            System.out.println("Please enter your choice");
            try {
                accountChoice = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Please enter a valid choice");
                scanner = new Scanner(System.in);
                accountChoice = -1;
            }

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

    /**
     * Transfers money from the user's bank account to another user's account.
     *
     * @param scanner         The scanner object for user input.
     * @param bank            The Bank object associated with the user's account.
     * @param username        The username of the user initiating the transfer.
     */
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

        double money = 0.0;
        if (valid == true) {
            try {
                do {
                    System.out.println("Please enter a valid deposit amount, Press -1 to exit.");
                    money = scanner.nextDouble();

                    if (money == -1.0)
                        return;

                } while (money < -1.0);

            } catch (InputMismatchException | IllegalArgumentException e) {
                System.out.println("That's not a valid number. Please enter a valid amount.");
                scanner.nextLine(); // Consume the invalid input
            }
        }

        if (bank.transferAmount(money, recipientUsername)) {
            System.out.println("Successfully transferred " + money + " to " + recipientUsername + ".");
            System.out.println("Your current balance is: " + bank.getBalance());
            return;
        }

        System.out.println("Transfer failed.");
    }

    /**
     * Withdraws money from the user's bank account.
     *
     * @param scanner  The scanner object for user input.
     * @param bank     The Bank object associated with the user's account.
     * @param username The username of the user initiating the withdrawal.
     */
    private static void withdraw(Scanner scanner, Bank bank, String username) {
        double money = 0.0;
        System.out.println("You currently have " + bank.getBalance() + " in your bank account");

        try {
            do {
                System.out.println("Please enter a valid withdraw amount, Press -1 to exit.");
                money = scanner.nextDouble();

                if (money == -1.0)
                    return;

            } while (money < -1.0);

        } catch (InputMismatchException e) {
            scanner.nextLine();
        }

        if (bank.withdraw(money, username)) {
            System.out.println(
                    "You have successfully withdraw " + money + ". Currently you have value of " + bank.getBalance());
            return;
        }

        System.out.println("You have failed to withdraw " + money);
    }

    /**
     * Deposits money into the user's bank account.
     *
     * @param scanner  The scanner object for user input.
     * @param bank     The Bank object associated with the user's account.
     * @param username The username of the user initiating the deposit.
     */
    private static void deposit(Scanner scanner, Bank bank, String username) {
        double money = 0.0;
        try {

            do {
                System.out.println("Please enter a valid deposit amount, Press -1 to exit.");
                money = scanner.nextDouble();

                if (money == -1.0)
                    return;

            } while (money < -1.0);

        } catch (InputMismatchException e) {
            scanner.nextLine();
        }

        if (bank.deposit(money, username)) {
            System.out.println("You currently have a amount of " + bank.getBalance() + " in your account ");
            return;
        }

        System.out.println("You have not updated your account and your current balance is " + bank.getBalance());
    }

    /**
     * Provides various options related to credit cards such as applying, canceling, paying bills, and cash advance operations.
     *
     * @param scanner   The scanner object for user input.
     * @param bank      The Bank object associated with the user's account.
     * @param userInfo  The username of the logged-in user.
     */
    private static void creditCardOptions(Scanner scanner, Bank bank, String userInfo) {
        int customerId = bank.retrieveUserInfo(userInfo).getCustomerId();

        int choice = -1;

        do {

            System.out.println("1. Apply Credit Card");
            System.out.println("2. Cancel Credit Card");
            System.out.println("3. Pay Credit Card Bill");
            System.out.println("4. Cash Advance Withdrawal");
            System.out.println("5. Pay Cash Advance Payables");
            System.out.println("6: Exit");

            System.out.println("Please enter your choice between 1 to 6");

            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Please enter a valid choice");
                scanner = new Scanner(System.in);
                choice = -1;
            }

            switch (choice) {
                case 1:
                    int accountNo = bank.getAccountNo();
                    int annualIncome = 0;
                    do {
                        try {
                            annualIncome = Integer.parseInt(
                                    promptInput("Please enter your annual income: (Please enter -1 to exit) ",
                                            scanner));

                            if (annualIncome == -1) {
                                return;
                            } else if (annualIncome < 15000) {
                                System.out.println(
                                        "Unable to apply for a credit card! (Annual income is less than $15000)");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid annual income... please try again");
                        }
                    } while (annualIncome < 15000);
                    bank.applyCreditCard(customerId, accountNo, annualIncome);
                    break;
                case 2:
                    if (bank.getCreditCardCount(customerId) == 0) {
                        System.out.println(
                                "There is no existing credit card for you to explore. Please apply your credit card");
                        break;
                    }

                    bank.cancelCreditCard(scanner, customerId, userInfo);
                    break;
                case 3:
                    if (bank.getCreditCardCount(customerId) == 0) {
                        System.out.println(
                                "There is no existing credit card for you to explore. Please apply your credit card");
                        break;
                    }
                    bank.payCreditCardBills(scanner, customerId, userInfo);
                    break;
                case 4:
                    if (bank.getCreditCardCount(customerId) == 0) {
                        System.out.println(
                                "There is no existing credit card for you to explore. Please apply your credit card");
                        break;
                    }

                    bank.CashAdvanceWithdrawal(scanner, customerId, userInfo);
                    break;
                case 5:
                    if (bank.getCreditCardCount(customerId) == 0) {
                        System.out.println(
                                "There is no existing credit card for you to explore. Please apply your credit card");
                        break;
                    }

                    bank.payCashAdvancePayables(scanner, customerId, userInfo);
                    break;
                default:
                    break;
            }
        } while (choice != 6);
    }

    /**
     * Provides various options related to loans such as applying for a loan and repaying a loan.
     *
     * @param scanner   The scanner object for user input.
     * @param bank      The Bank object associated with the user's account.
     * @param userInfo  The username of the logged-in user.
     */
    private static void loanOptions(Scanner scanner, Bank bank, String userInfo) {
        int choice = -1;
        int customerId = bank.retrieveUserInfo(userInfo).getCustomerId();

        do {

            System.out.println("1. Apply Loan");
            System.out.println("2. Repay Loan");
            System.out.println("3. Exit ");
            System.out.println("Please enter your choice between 1 to 3");
            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Please enter a valid choice");
                scanner = new Scanner(System.in);
                choice = -1;
            }
            switch (choice) {
                case 1:
                    applyLoan(scanner, bank, customerId);
                    break;
                case 2:
                    repayLoan(scanner, bank);
                    break;
                default:
                    break;
            }
        } while (choice != 3);

    }

    /**
     * Allows the user to apply for a loan by specifying the loan amount.
     *
     * @param scanner       The scanner object for user input.
     * @param bank          The Bank object associated with the user's account.
     * @param customerId    The ID of the customer applying for the loan.
     */
    private static void applyLoan(Scanner scanner, Bank bank, int customerId) {
        double bankBalance = bank.getBalance();
        double currentTotalLoanAmount = bank.totalLoanAmount();
        double maximumLoanAmount = (bankBalance * 2) - currentTotalLoanAmount;
        double loanAmount = 0.0;
        bank.printLoans();

        if (bank.getBalance() <= 0) {
            System.out.println("Rejected, you are unable to borrow funds. Please top up your bank account");
            return;
        }

        if (maximumLoanAmount <= 0) {
            System.out.println(
                    "Rejected, you are unable to borrow additional funds. Please repay your outstanding loans.");
            return;
        } else {
            System.out.println("Bank balance: $" + bankBalance + "\nYou can loan up to: $" + (maximumLoanAmount));
        }
        do {
            loanAmount = Double
                    .parseDouble(promptInput("\nPlease enter amount to loan. Enter -1 to exit", scanner));
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
                    bank.applyLoan(customerId, loanAmount);
                }
            } catch (NumberFormatException e) {
                System.out.println("Entered an invalid input");
                loanAmount = -2;
            }
        } while (loanAmount <= 0);
    }

    /**
     * Allows the user to repay a loan by specifying the loan ID and the amount to repay.
     *
     * @param scanner   The scanner object for user input.
     * @param bank      The Bank object associated with the user's account.
     */
    private static void repayLoan(Scanner scanner, Bank bank) {
        if (bank.totalLoanAmount() != 0.0) {
            bank.updateOverdueLoans();
            bank.printLoans();

        } else {

            System.out.println("Rejected, You dont have any loans in your bank.");
            return;
        }

        try {
            int repayLoanId = Integer
                    .parseInt(promptInput("Please enter LoanID of the loan you are repaying", scanner));

            if (!bank.checkExistingLoan(repayLoanId).isPresent()
                    || bank.checkExistingLoan(repayLoanId).get().getLoanAmount() == 0) {
                System.out.println("Invalid input, your account does not have a loan corresponding with this loanID.");
                return;
            }

            double repayLoanAmount = Double
                    .parseDouble(promptInput("Please enter the amount you are repaying", scanner));

            if (repayLoanAmount > bank.checkExistingLoan(repayLoanId).get().getLoanAmount()
                    && bank.checkExistingLoan(repayLoanId).get().getLoanAmount() > 0) {
                System.out.println("The amount you have input is over the amount you are repaying");
                return;
            }

            bank.repayLoan(repayLoanId, repayLoanAmount);
        } catch (Exception e) {
            return;
        }
    }

}
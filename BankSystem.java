import java.util.InputMismatchException;
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
                System.out.println("4. Account Settings");
                System.out.println("5. Log out");
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
                        settings(scanner, bank, securityInstance, userInfo);
                        break;
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

            if (attemptOfTries >= 4) {
                System.out.println("You have tried more than 3 tries to authenticate");
                break;
            }
            // Things to be fixed how to break this loop
            System.out.println("You have " + (3 - attemptOfTries) + " attempts left");
            attemptOfTries++;
        }

        if (bank.authenticateOTP(loginUsername, Integer.valueOf(OTP))) {
            securityInstance.logActivity(bank.retrieveUserInfo(loginUsername).getCustomerId(), 1);
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
        double balance = bank.getBalance(userInfo);
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

        System.out.println("Your current transaction limit is " + bank.getTransactionLimit(userInfo));

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
                    transfer(scanner, bank, userInfo);
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

    private static void transfer(Scanner scanner, Bank bank, String username) {
        double money = 0.0;
        boolean valid = false;
        System.out.println("You currently have " + bank.getBalance(username) + " in your bank account");
    }

    private static void withdraw(Scanner scanner, Bank bank, String username) {
        double money = 0.0;
        boolean valid = false;
        System.out.println("You currently have " + bank.getBalance(username) + " in your bank account");

        while (!valid) {
            try {
                if (money == -1.0) {
                    break;
                }
                System.out.println("Please enter a valid deposit amount, Press -1 to exit.");
                money = scanner.nextDouble();

                if (bank.getBalance(username) >= money && money > 0.0) {
                    valid = true;
                }
            } catch (InputMismatchException e) {
                scanner.nextLine();
            }
        }

        if (valid) {
            bank.withdraw(money, username);
            System.out.println("You have successfully withdraw " + money + ". Currently you have value of :" + bank.getBalance(username));
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
            System.out.println("You currently have a amount of " + bank.getBalance(username) + " in your account ");
        } else {
            System.out.println("You have not updated your account and your current balance is " + bank.getBalance(username));
        }
    }

    private static void creditCardOptions(Scanner scanner, Bank bank, String userInfo) {
        int customerId = bank.retrieveUserInfo(userInfo).getCustomerId();

        System.out.println("1. Apply Credit Card");
        System.out.println("2. Cancel Credit Card");
        System.out.println("3. Pay Credit Card Bill");


        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                int newCreditCardId = bank.getCustomerCreditCards(customerId);
                int accountNo = bank.getAccountNo(userInfo);
                int annualIncome;
                do {
                    annualIncome = Integer.parseInt(promptInput("Please enter your annual income: ", scanner));
                    if (annualIncome < 15000) {
                        System.out.println("Unable to apply for a credit card! (Annual income is less than $15000)");
                    }
                } while (annualIncome < 15000);
                bank.applyCreditCard(newCreditCardId, customerId, accountNo, annualIncome);
                System.out.println("Credit Card application successful!");
                break;
            case 2:
                bank.cancelCreditCard(customerId);
                System.out.println("All Credit Cards associated with your account has been cancelled");
            case 3:
                break;
            default:
                if (choice != 0) {
                    System.out.println("Please enter a number between 1-3!");
                }
                break;
        }
    }

    private static void loanOptions(Scanner scanner, Bank bank, String userInfo, Loan loan) {
        int customerId = bank.retrieveUserInfo(userInfo).getCustomerId();

        System.out.println("1. Apply Loan");
        System.out.println("2. Repay Loan");
        System.out.println("3. Exit ");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                //do try catch in a case where they type something unparsable, break
                int newLoanNumber = bank.getCustomerLoans(customerId);
                double monthlyIncome = Double.parseDouble(promptInput("Please enter annual income to check availability of loan", scanner));
                if (monthlyIncome == 'b'){
                    break;
                }
                double loanAmount;
                do {
                    loanAmount = Double.parseDouble(promptInput("Please enter amount to loan", scanner));
                    if(loanAmount == 'b'){
                        break;
                    }
                    if (loanAmount > monthlyIncome*2){
                        System.out.println("Unsuccessful. You can only loan up to: $"+monthlyIncome*2);
                    }
                }while (loanAmount > monthlyIncome*2);
                int loanDuration = loan.calculateDate(loanAmount);
                if(loanDuration == 'b') {
                    break;
                }
                bank.applyLoan(customerId, newLoanNumber, loanAmount, loanDuration);
                break;
            case 2:
                // retrieve data base on customer id to store the loans in the array, let user
                // choose which loans they want to pay back,
                break;
            default:
                if (choice != 0) {
                    System.out.println("Please enter between 1 : Apply Loan or 2: Repay Loan");
                }
                break;
        }

    }

}

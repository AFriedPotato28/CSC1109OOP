import java.sql.SQLOutput;
import java.util.Scanner;

public class BankSystem {
    public static void main(String[] args) {
        Bank bank = new Bank("Matthias");
        Security securityInstance = new Security();

        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        String userInfo = "";

        do {
            if (!bank.getUsername(userInfo)) {

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
                System.out.println("3. Apply/Repay Loan");
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
                        creditCardOptions(scanner, bank, bank.retrieveUserID(userInfo));
                        break;
                    case 3:
                        applyRepayLoan(scanner, bank, userInfo);
                        break;
                    case 4:
                        settings(scanner, bank, securityInstance, userInfo);
                        break;
                    case 5:
                        bank.logOut(userInfo);
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
        String accountType = promptInput("Please enter either 1:Savings Account, 2: Normal Account", scanner);

        while (!accountType.equals("1") && !accountType.equals("2")) {
            System.out.println("Did not choose account type");
            accountType = promptInput("Please enter either 1:Savings Account, 2: Normal Account", scanner);
        }

        bank.addCustomer(new Customer(0,name,username,password,""),accountType);
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
            securityInstance.logActivity(bank.retrieveUserID(loginUsername), 1);

            return loginUsername;
        }

        return "";
    }

    /**
     *
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
     *
     * @param scanner
     * @param bank
     * @param securityInstance
     * @param userInfo
     *
     *
     */

    private static void settings(Scanner scanner, Bank bank, Security securityInstance, String userInfo) {

        int accountChoice = -1;

        do {
            System.out.println("\nChoose an action:");
            System.out.println("1. Reset Password");
            System.out.println("2. Change Transaction Limit");
            System.out.println("3. Check Balance");
            System.out.println("4. Add Account");
            System.out.println("5. Remove Account");
            System.out.println("0. Exit");

            System.out.println("Enter your choice: ");
            accountChoice = scanner.nextInt();

            switch (accountChoice) {
                case 1:
                    resetPassword(scanner, bank, securityInstance, userInfo);
                case 2:
                    changeTransactionLimit(scanner, bank, securityInstance, userInfo);
                case 3:
                    //checkBalance(scanner,userInfo);
                case 4:
                    boolean success = createAccount(scanner,bank,userInfo);
                    if (success) {
                        System.out.println("Account created successfully!");
                    }
                case 5:
                    //removeAccount();
            }
        } while (accountChoice != 0);

    }

    /**
     *
     * @param scanner
     * @param bank
     * @param securityInstance
     * @param userInfo
     *
     */

    private static void changeTransactionLimit(Scanner scanner, Bank bank, Security securityInstance, String userInfo) {
    }

    // private static void checkBalance(Scanner scanner, String username) {
    //     int accountChoice = -1;
    //     do{
    //         System.out.println("Choose an account to view balance: ");
    //         int accountIndex = 1;
    //         for (Account account : accounts) {
    //             System.out.println(accountIndex++ + "Account Number" + account.getAccountNo());
    //         }
    //         System.out.println("0.Exit");
    //         System.out.print("Enter choice: ");

    //         accountChoice = scanner.nextInt();

    //         if (accountChoice > 0 && accountChoice <= accounts.size()) {
    //             Account selectedAccount = accounts.get(accountChoice -1);
    //             System.out.println("The balance for account" + selectedAccount.getAccountNo() + " is $" + selectedAccount.getBalance());
    //         } else if (accountChoice == 0) {
    //             System.out.println("Exiting balance check.");
    //         } else {
    //             System.out.println("Invalid Choice. Please try again");
    //         }
    //     } while (accountChoice != 0);

    // }

    private static boolean createAccount(Scanner scanner,Bank bank,String username) {

        System.out.println("Creating a new Account:");
        System.out.println("Enter account type you wish to create: ");
        System.out.println("1. Savings");
        System.out.println("2. Normal");
        String accountType = promptInput("Choose between 1 or 2", scanner);

        while (!accountType.equals("1") && !accountType.equals("2")) {
            accountType = promptInput("Choose between 1 or 2", scanner);
        }

        if(bank.addAccount(bank.retrieveUserID(username), accountType)){
            return true;
        };

        return false;
    }

    private static void applyRepayLoan(Scanner scanner, Bank bank, String userInfo){
        System.out.println("1. Apply Loan");
        System.out.println("2. Repay Loan");
        System.out.println("3. Back ");

        int choice = scanner.nextInt();
        switch (choice){
            case 1:
                int newLoanNumber = bank.getCustomerLoans(bank.retrieveUserID(userInfo));
                double loanAmount = Double.parseDouble(promptInput("Please enter amount to loan", scanner));
                int loanDuration = Integer.parseInt(promptInput("Please enter the amount of days for loan", scanner));
                bank.applyLoan(bank.retrieveUserID(userInfo),newLoanNumber, loanAmount, loanDuration);
                break;
            case 2:
                break;
            default:
                if (choice != 0){
                    System.out.println("Please enter between 1 : Apply Loan or 2: Repay Loan");
                }
                break;
        }

    }

    private static void creditCardOptions(Scanner scanner, Bank bank, int customerId){
        System.out.println("1. Apply Credit Card");
        System.out.println("2. Cancel Credit Card");
        System.out.println("3. Pay Credit Card Bill");

        int choice = scanner.nextInt();
        switch (choice){
            case 1:
                int newCreditCardId = bank.getCustomerCreditCards(customerId);
                int accountNo = bank.retrieveAccountNo(customerId);
                int annualIncome;
                do {
                    annualIncome = Integer.parseInt(promptInput("Please enter your annual income: ", scanner));
                    if (annualIncome < 15000) {
                        System.out.println("Unable to apply for a credit card! (Annual income is less than $15000)");
                    }
                } while(annualIncome < 15000);
                bank.applyCreditCard(newCreditCardId, customerId, accountNo, annualIncome);
                break;
            case 2, 3:
                break;
            default:
                if(choice != 0) {
                    System.out.println("Please enter a number between 1-3!");
                }
                break;
            }
        }

    // private static void removeAccount() {
    //     System.out.println("Remove an Account");
    //     System.out.println("Enter Account Number to remove:");
    //     int accountNo = scanner.nextInt();

    //     Account accountToRemove = null;
    //     for (Account account == accounts) {
    //         if (account.getAccountNo() == accountNo) {
    //             accountToRemove = account;
    //             break;
    //         }
    //     }

    //     if (accountToRemove != null) {
    //         accounts.remove(accountToRemove);
    //         System.out.println("Account removed.");
    //     } else {
    //         System.out.println("Account not found. Please try again.");
    //     }

    // }

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
            accountChoice = scanner.nextInt();

            switch (accountChoice) {
                case 1:
                    transfer();
                case 2:
                    withdraw();
                case 3:
                    deposit();
                default:
                    break;
            }

        } while (accountChoice != 0);
    }

    private static void transfer() {

    }

    private static void withdraw() {
    }

    private static void deposit() {
    }

}

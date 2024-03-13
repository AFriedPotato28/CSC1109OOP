import java.sql.SQLOutput;
import java.io.IOException;
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
                        //creditCardOptions(scanner, bank, bank.retrieveUserInfo(userInfo));
                        break;
                    case 3:
                        //applyRepayLoan(scanner, bank, userInfo);
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

        bank.addCustomer(new Customer(0,name,username,password,""));
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
            bank.setLoans(bank.retrieveUserInfo(loginUsername).getCustomerId());
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
                    checkBalance(scanner,bank,userInfo);
                    break;
            }
        } while (accountChoice != 0);

    }


    private static void checkBalance(Scanner scanner, Bank bank, String userInfo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkBalance'");
    }


    /**
     *
     * @param scanner
     * @param bank
     * @param userInfo
     *
     */

    private static void changeTransactionLimit(Scanner scanner, Bank bank, String userInfo) {

        int limit = 0;
        boolean valid = false;

        while (!valid || limit <= 500){
            try {
                System.out.println("Please enter a valid transaction limit and a numeric value above 500");
                limit = scanner.nextInt();
                valid = true;
            } catch ( InputMismatchException e){
                scanner.nextLine();
            }
        }

        if(bank.changeTransactionLimit(limit, userInfo)){
            System.out.println("You have successfully updated your transaction limit to " + limit);
        } else{
            System.out.println("You have not successfully updated your transaction limit");
        }
    }

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
                    transfer(scanner,bank);
                    break;
                case 2:
                    withdraw(scanner,bank,userInfo);
                    break;
                case 3:
                    deposit(scanner,bank,userInfo);
                    break;
                default:
                    break;
            }

        } while (accountChoice != 0);
    }

    private static void transfer(Scanner scanner, Bank bank) {
        int accountChoice = -1;

        do {




        } while (accountChoice != 0);
    }

    private static void withdraw(Scanner scanner, Bank bank,String username) {
        double money = -1.0;
        boolean valid = false;
        double value = -1.0;
        
        do{
            try {
                System.out.println("Please enter a valid deposit amount");
                money = scanner.nextDouble();
                value = bank.getBalance(username) - money;

                if (bank.getBalance(username) >= money ){
                   value = bank.getBalance(username) - money;
                   valid = true;
                   break;
                }

                // if (bank.getBalance(username) <= value && bank.getTransactionLimit(username) ){

                // }


            } catch (InputMismatchException e){
            }
        }while (!valid);

        if(valid){
            bank.withdraw(value,username);
        }

        System.out.println("You have " + value + " left in your bank.");
    }

    private static void deposit(Scanner scanner, Bank bank,String username) {
        double money = -1.0;
        boolean valid = false;

        while (!valid){
            try {
                System.out.println("Please enter a valid deposit amount");
                money = scanner.nextDouble();
                valid = true;
            } catch ( InputMismatchException e){
                scanner.nextLine();
            }
        }
        bank.deposit(money,username);
        System.out.println("You currently have a amount of " + bank.getBalance(username) + " in your account)");
    }

}

import java.util.Scanner;

public class BankSystem {
    public static void main(String[] args) {
        Bank bank = new Bank("Random");
        Security securityInstance = new Security();

        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        String userInfo = "";
        
        do{
            if (userInfo.equals("")){
                System.out.println("\nChoose an action:");
                System.out.println("1. Create new Account");
                System.out.println("2. Login to Account");
                System.out.println("0. Exit");

                System.out.println("Enter your choice: ");
                choice = scanner.nextInt();

                switch(choice){
                    case 1:
                        createAccountDetails(scanner, bank, securityInstance);
                        break;
                    case 2:
                        userInfo = loginToAccount(scanner, bank, securityInstance);
                        break;
                    default:
                        if (choice != 0){
                            System.out.println("Please enter between 1 : Creating Account or 2: Login Account");
                        }
                        break;
                    }
            }else{
                System.out.println("\nChoose an action:");
                System.out.println("1. Transfer ");
                System.out.println("2. Apply/Cancel Credit Card");
                System.out.println("3. Apply/Repay Loan");
                System.out.println("0. Exit");
                    
                System.out.println("Enter your choice: ");
                choice = scanner.nextInt();
            }
            
        } while(choice != 0);
    
        scanner.close();
        
    }
    /* This creates a reusable code of proompting Input */
    private static String promptInput(String prompt,Scanner scanner) {
        System.out.println(prompt);
        return scanner.next();
    }

    private static void createAccountDetails(Scanner scanner,Bank bank, Security securityInstance){
        String name = promptInput("Please enter your name:", scanner);
        String username = promptInput("Please enter your username:",scanner);
        String password = promptInput("Please enter your password:",scanner);
        while(!securityInstance.validatePassword(password)){
            System.out.println("Did not meet password requirements");
            password = promptInput("Please enter your password:",scanner);
        }
        String accountType = promptInput("Please enter either 1:Savings Account, 2: Normal Account", scanner);

        while (!accountType.equals("1")  && !accountType.equals("2")){
            System.out.println("Did not choose account type");
            accountType = promptInput("Please enter either 1:Savings Account, 2: Normal Account", scanner);
        }

        bank.addCustomer(new Customer(),name,username,password,accountType);
    }

    private static String loginToAccount(Scanner scanner, Bank bank, Security securityInstance) {
        String loginUsername = promptInput("Please enter your username", scanner);
        String loginPassword = promptInput("Please enter your password", scanner);

        while (!bank.validateLogin(loginUsername,loginPassword)){
            System.out.println("Wrong Crediential Informations");
            loginUsername = promptInput("Please enter your username", scanner);
            loginPassword = promptInput("Please enter your password", scanner);
        }

        bank.generateOTP(loginUsername);
        int attemptOfTries = 1;
        String OTP = promptInput("Please enter your OTP", scanner); 
        
        while(!bank.authenticateOTP(loginUsername, Integer.valueOf(OTP)) && attemptOfTries <= 3){
            OTP = promptInput("Please enter your OTP", scanner); 

            if(attemptOfTries >= 4){
                System.out.println("You have tried more than 3 tries to authenticate");
                break;
            }

            System.out.println("You have " + (3-attemptOfTries) + " attempts left");
            attemptOfTries++;
        }
        
        if (bank.authenticateOTP(loginUsername, Integer.valueOf(OTP))){
            return loginUsername;
        }

        return "";
    }
}
    


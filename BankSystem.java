import java.util.Scanner;

public class BankSystem {
    public static void main(String[] args) {
        Bank bank = new Bank("Random");
        Security securityInstance = new Security();

        Scanner scanner = new Scanner(System.in);
        int choice;

        do{
            
            System.out.println("\nChoose an action:");
            System.out.println("1. Create new Account");
            System.out.println("2. Login");
            System.out.println("0. Exit");

            System.out.println("Enter your choice: ");
            choice = scanner.nextInt();

            switch(choice){
                case 1:
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
                    break;
                case 2:
                    String loginUsername = promptInput("Please enter your username", scanner);
                    String loginPassword = promptInput("Please enter your password", scanner);

                    while (loginUsername.equals("") && loginPassword.equals("")){
                        System.out.println("Wrong Crediential Informations");
                        loginUsername = promptInput("Please enter your username", scanner);
                        loginPassword = promptInput("Please enter your password", scanner);
                    
                    }
                default:
                    if (choice != 0 ){
                        System.out.println("Please enter between 1 or 2");
                    }
                    break;
            }

        }
        while(choice != 0);

        scanner.close();
    }

    

    /* This creates a reusable code of proompting Input */
    private static String promptInput(String prompt,Scanner scanner) {
        System.out.println(prompt);
        return scanner.next();
    }



}
    


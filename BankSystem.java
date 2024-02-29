import java.util.Scanner;

public class BankSystem {

    public static void main(String[] args) {
            
        Bank bank = new Bank("Random");

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
                    bank.addCustomer(new Customer(),name,username,password);

            }

        }
        while(choice != 0);

        scanner.close();
    }

    private static String promptInput(String prompt,Scanner scanner) {
        System.out.println(prompt);
        return scanner.next();
    }
}
    


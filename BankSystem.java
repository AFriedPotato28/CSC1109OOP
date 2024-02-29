import java.util.Scanner;
public class BankSystem{
    public static void main(String[] args) {

        Bank bank = new Bank();

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
                case 0:
                    break;
            }

        }
        while(choice != 0);

        scanner.close();
    }
}

import java.util.ArrayList;
import java.util.Scanner;

public class BankInterface {
    public static void userMenu(double balance, int menuSize) {
        for (int i = 0; i < menuSize; i++) {
            System.out.print("_");
        }

        System.out.println("\nBanking App");

        for (int i = 0; i < menuSize; i++) {
            System.out.print("_");
        }

        System.out.println("\nBalance: " + balance);
        System.out.println("Select your option... \n");
        System.out.print(
                """
                        1 - Deposit:\s
                        2 - Withdraw:\s
                        3 - Add account:\s
                        4 - View accounts:\s
                        5 - Transfer:\s
                        6 - Exit:\s
                        """);

        for (int i = 0; i < menuSize; i++) {
            System.out.print("_");
        }
        System.out.print("\nEnter: ");
    }

    public static double checkInput() {
        Scanner sc = new Scanner(System.in);
        String userInput = sc.nextLine();
        try {
            return Double.parseDouble(userInput);
        } catch (Exception ignored) {
            System.out.printf("%s is not a number!%n", userInput);
        }
        return 0;
    }

    public static void inputErrorHandler(double sum) {
        if (sum <= 0) {
            System.out.println("Cannot withdraw! Input positive...");
            System.out.print("Enter deposit amount: ");
            sum = checkInput();
        }
    }

    public static void menuLoop(ArrayList<BankAccount> accounts, BankAccount current, int menuSize) {
        boolean exitComm = false;

        while (!exitComm) {
            Scanner sc = new Scanner(System.in);
            userMenu(current.getBalance(), menuSize);
            int menuInput = (int)checkInput();
            double sum;

            switch (menuInput) {
                case 1:
                    System.out.print("Enter deposit amount: ");
                    sum = checkInput();
                    inputErrorHandler(sum);
                    current.setBalance(current.deposit(sum));
                    break;

                case 2:
                    System.out.print("Enter withdraw amount: ");
                    sum = checkInput();
                    inputErrorHandler(sum);
                    current.setBalance(current.withdraw(sum));
                    break;

                case 3:
                    System.out.print("Enter full name: ");
                    String nameInput = sc.nextLine();

                    System.out.print("Enter account number: ");
                    int newNum = (int)checkInput();

                    try {
                        current.addAccount(accounts, nameInput, newNum, 0);
                        BankDbHandler.setAccount(nameInput, newNum, 0);
                    }
                    catch (Exception ignored) {
                        System.out.println("Error on adding account!");
                    }
                    break;

                case 4:
                    System.out.println("\nAvailable accounts: ");
                    current.displayAccounts(accounts);
                    sc.nextLine();
                    break;

                case 5:
                    System.out.print("Enter account number: ");
                    int accNum = (int)checkInput();
                    System.out.print("Enter deposit amount: ");
                    sum = checkInput();
                    inputErrorHandler(sum);
                    current.transfer(accounts, accNum, sum);
                    break;

                case 6:
                    System.out.println("Goodbye!\n");
                    exitComm = true;
                    break;
            }
        }
        System.exit(0);
    }
}

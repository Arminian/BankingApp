import java.util.ArrayList;
import java.util.Scanner;
import java.math.BigDecimal;

public class BankInterface {
    public static void userMenu(BigDecimal balance, int menuSize) {
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

    public static BigDecimal parseDecimalInput() {
        Scanner sc = new Scanner(System.in);
        String userInput = sc.nextLine();
        try {
            return BigDecimal.valueOf(Long.parseLong(userInput));
        } catch (Exception ignored) {
            System.out.printf("%s is not a number!%n", userInput);
        }
        return BigDecimal.ZERO;
    }

    public static int parseIntInput() {
        Scanner sc = new Scanner(System.in);
        String userInput = sc.nextLine();
        try {
            return Integer.parseInt(userInput);
        } catch (Exception ignored) {
            System.out.printf("%s is not a number!%n", userInput);
        }
        return 0;
    }

    public static void inputErrorHandler(BigDecimal sum) {
        int inputValue = sum.compareTo(BigDecimal.ZERO);
        if (inputValue != 1) {
            System.out.println("Cannot withdraw! Input positive...");
            System.out.print("Enter deposit amount: ");
        }
    }

    public static void menuLoop(ArrayList<BankAccount> accounts, BankAccount current, int menuSize) {
        boolean exitComm = false;

        while (!exitComm) {
            Scanner sc = new Scanner(System.in);
            userMenu(current.getBalance(), menuSize);
            int menuInput = parseIntInput();
            BigDecimal sum;

            switch (menuInput) {
                case 1:
                    System.out.print("Enter deposit amount: ");
                    sum = parseDecimalInput();
                    inputErrorHandler(sum);
                    current.setBalance(current.deposit(sum));
                    break;

                case 2:
                    System.out.print("Enter withdraw amount: ");
                    sum = parseDecimalInput();
                    inputErrorHandler(sum);
                    current.setBalance(current.withdraw(sum));
                    break;

                case 3:
                    System.out.print("Enter full name: ");
                    String nameInput = sc.nextLine();

                    System.out.print("Enter account number: ");
                    int newNum = parseIntInput();

                    try {
                        current.addAccount(accounts, nameInput, newNum, BigDecimal.ZERO);
                        BankDbHandler.setAccount(nameInput, newNum, BigDecimal.ZERO);
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
                    int accNum = parseIntInput();
                    System.out.print("Enter deposit amount: ");
                    sum = parseDecimalInput();
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

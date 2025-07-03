import java.util.ArrayList;
import java.util.Scanner;

public class BankInterface {
    public static void userMenu(double balance, int menuSize) {
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

    public static void menuLoop(ArrayList<BankAccount> accounts, BankAccount current, int menuSize) {
        boolean exitComm = false;

        while (!exitComm) {
            userMenu(current.getBalance(), menuSize);
            int menuInput = (int)checkInput();

            switch (menuInput) {
                case 1:
                    double sumDep = checkInput();
                    current.setBalance(current.deposit(sumDep));
                    break;
                case 2:
                    double sumWith = checkInput();
                    current.setBalance(current.withdraw(sumWith));
                    break;
                case 3:
                    int newNum = (int)checkInput();
                    try {
                        current.addAccount(accounts, newNum);
                    }
                    catch (Exception ignored) {
                        System.out.println("Error on adding account!");
                    }
                    break;
                case 4:
                    current.displayAccounts(accounts);
                    Scanner sc = new Scanner(System.in);
                    sc.nextLine();
                    break;
                case 5:
                    int accNum = (int)checkInput();
                    double sumTran = checkInput();
                    current.transfer(accounts, accNum, sumTran);
                    break;
                case 6:
                    exitComm = true;
            }
        }
        System.exit(0);
    }
}

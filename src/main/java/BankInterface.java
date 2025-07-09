import java.util.Objects;
import java.util.Scanner;
import java.math.BigDecimal;
import java.util.UUID;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class BankInterface {
    private static final Logger logger = LogManager.getLogger(BankInterface.class);

    private static void userMenu(BigDecimal balance, int menuSize) {
        for (int i = 0; i < menuSize; i++) {
            System.out.print("_");
        }

        System.out.println("\n\tBanking App");

        for (int i = 0; i < menuSize; i++) {
            System.out.print("_");
        }

        System.out.println("\nBalance: " + balance);
        System.out.println("Select your option... \n");
        System.out.print(
                """
                        1 - Deposit:\s
                        2 - Withdraw:\s
                        3 - View accounts:\s
                        4 - Add account:\s
                        5 - Delete account:\s
                        6 - Change password:\s
                        7 - Transfer:\s
                        8 - Exit:\s
                        """);

        for (int i = 0; i < menuSize; i++) {
            System.out.print("_");
        }
        System.out.print("\nEnter: ");
    }

    private static void menuPause() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nPress enter to continue...");
        sc.nextLine();
    }

    private static BigDecimal parseDecimalInput() {
        Scanner sc = new Scanner(System.in);
        String userInput = sc.nextLine();
        try {
            return BigDecimal.valueOf(Long.parseLong(userInput));
        } catch (Exception ignored) {
            System.out.printf("%s is not a number!%n", userInput);
            logger.error("Non-numerical decimal user input error");
        }
        return BigDecimal.ZERO;
    }

    private static int parseIntInput() {
        Scanner sc = new Scanner(System.in);
        String userInput = sc.nextLine();
        try {
            return Integer.parseInt(userInput);
        } catch (Exception ignored) {
            System.out.printf("%s is not a number!%n", userInput);
            logger.error("Non-numerical integer user input error");
        }
        return 0;
    }

    private static void inputErrorHandler(BigDecimal sum) {
        int inputValue = sum.compareTo(BigDecimal.ZERO);
        if (inputValue != 1) {
            System.out.println("Cannot complete operation! Input positive amount...");
            System.out.print("Enter amount: ");
            logger.error("Negative or zero user input error");
        }
    }

    public static void menuLoop(int menuSize) {
        boolean exitMenu;

        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.print("'qt' to quit or enter to continue: ");
            String userInput = sc.nextLine();
            if (Objects.equals(userInput, "qt")) {
                break;
            }

            System.out.print("Enter username: ");
            String login = sc.nextLine();
            System.out.print("Enter password: ");
            String password = sc.nextLine();
            BankAccount account = BankDbHandler.logIn(login, password);

            if (account != null && BankAccount.compare(account.getLogin(), login) && BankAccount.compare(account.getPassword(), password)) {
                System.out.println("Successful login");
                exitMenu = false;
                logger.info("Logged in to account: {} AND ID: {}", login, account.getAccountID());
            } else {
                System.out.println("Wrong credentials, try again!");
                exitMenu = true;
                logger.error("Failed to login to account by username: {}", login);
            }

            while (!exitMenu) {
                userMenu(account.getBalance(), menuSize);
                int menuInput = parseIntInput();
                BigDecimal sum;

                switch (menuInput) {
                    case 1:
                        System.out.print("Enter deposit sum: ");
                        sum = parseDecimalInput();
                        inputErrorHandler(sum);
                        BankDbHandler.deposit(sum, account);

                        logger.info("Deposited sum to account: {}", sum);
                        break;

                    case 2:
                        System.out.print("Enter withdraw sum: ");
                        sum = parseDecimalInput();
                        inputErrorHandler(sum);
                        BankDbHandler.withdraw(sum, account);

                        logger.info("Withdrawn sum from account: {}", sum);
                        break;

                    case 3:
                        System.out.println("\nAvailable accounts: ");
                        BankDbHandler.displayAccountList();
                        menuPause();

                        logger.info("Accounts were listed to user");
                        break;

                    case 4:
                        System.out.print("Enter login: ");
                        String loginInput = sc.nextLine();

                        System.out.print("Enter password: ");
                        String passwordInput = sc.nextLine();

                        String newID = UUID.randomUUID().toString();

                        BankDbHandler.setAccount(newID, loginInput, passwordInput, BigDecimal.ZERO);

                        logger.info("Account was created by name: {} AND ID: {}", login, newID);
                        break;

                    case 5:
                        System.out.print("Enter password to delete this account: ");
                        String deleteInput = sc.nextLine();
                        BankAccount forDeletion = BankDbHandler.getAccount(deleteInput);
                        if (forDeletion != null && BankAccount.compare(forDeletion.getPassword(), deleteInput)) {
                            BankDbHandler.deleteAccount(account.getAccountID());
                            System.out.println("Please login again.");

                            menuPause();
                            exitMenu = true;
                            logger.info("Account was deleted by ID: {}", forDeletion);
                        } else {
                            System.out.println("Wrong password!");
                            logger.error("Wrong password before deletion error");
                        }

                        break;

                    case 6:
                        System.out.print("Enter current password: ");
                        String oldPass = sc.nextLine();
                        if
                        (BankAccount.compare(account.getPassword(), oldPass)) {
                            System.out.print("Enter new password: ");
                            String newPass = sc.nextLine();
                            BankDbHandler.updatePassword(oldPass, newPass);
                            logger.info("Account {} updated password to: {}", account, newPass);
                        } else {
                            System.out.println("Wrong password!");
                            logger.error("Wrong old password error");
                        }

                        break;

                    case 7:
                        System.out.print("Enter recipient ID: ");
                        String recip = sc.nextLine();
                        BankAccount recipAcc = BankDbHandler.getAccount(recip);

                        if (recipAcc != null && !Objects.equals(recipAcc.getAccountID(), account.getAccountID())) {
                            System.out.print("Enter transfer sum: ");
                            sum = parseDecimalInput();
                            inputErrorHandler(sum);
                            BankDbHandler.transfer(sum, account, recip);
                            logger.info("Transferred sum from {} to {} in amount: {}", account, recipAcc, sum);
                        } else {
                            System.out.println("No recipient account found");
                            logger.error("No recipient error");
                        }

                        break;

                    case 8:
                        System.out.println("Goodbye!\n");
                        exitMenu = true;

                        break;

                    default:
                        System.out.println("Try again!");
                        menuPause();

                        logger.info("Wrong menu input");
                        break;
                }
            }
        }
        System.exit(0);
    }
}

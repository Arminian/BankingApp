import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        ArrayList<BankAccount> accounts = new ArrayList<>();
        BankDbHandler.getAccounts(accounts);

        BankAccount personal = accounts.getFirst();

        BankInterface.menuLoop(accounts, personal, 24);
    }
}

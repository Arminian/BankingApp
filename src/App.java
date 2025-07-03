import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        ArrayList<BankAccount> accounts = new ArrayList<>();
        BankAccount personal = new BankAccount(38.49, 92512);
        accounts.add(personal);

        BankInterface.menuLoop(accounts, personal, 12);
    }
}

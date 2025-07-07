import java.util.ArrayList;
import java.math.BigDecimal;

public class BankAccount {
    // Attributes
    private String fullName;
    private int accountNumber;
    private BigDecimal balance;

    // Constructors
    public BankAccount(String fullName, int accountNumber, BigDecimal balance) {
        this.fullName = fullName;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    // Getters
    public String getFullName() {
        return fullName;
    }
    public int getAccountNumber() {
        return accountNumber;
    }
    public BigDecimal getBalance() {
        return balance;
    }

    // Setters
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    // Methods
    public BigDecimal deposit(BigDecimal amount) {
        balance = balance.add(amount);

        return balance;
    }

    public BigDecimal withdraw(BigDecimal amount) {
        balance = balance.subtract(amount);

        return balance;
    }

    public void addAccount(ArrayList<BankAccount> accounts, String fullName, int accountNumber, BigDecimal balance) {
        BankAccount newAcc = new BankAccount(fullName, accountNumber, balance);
        accounts.add(newAcc);
    }

    public void displayAccounts(ArrayList<BankAccount> accounts) {
        for (int i = 0; i < accounts.size(); i++) {
            System.out.println(i + ": " + accounts.get(i).fullName + " | account - " + accounts.get(i).accountNumber);
        }
        System.out.print("\nPress enter to go back...");
    }

    public void transfer(ArrayList<BankAccount> accounts, int input, BigDecimal sum) {
        for (int i = 0; i < accounts.size(); i++) {
            if (i == input) {
                this.balance = withdraw(sum);
                accounts.get(i).deposit(sum);
            } else {
                System.out.println("No account number found!");
                break;
            }
        }
    }
}

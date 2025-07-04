import java.util.ArrayList;

public class BankAccount {
    // Attributes
    private double balance;
    public int accountNumber;

    // Constructors
    public BankAccount(double balance, int accountNumber) {
        this.balance = balance;
        this.accountNumber = accountNumber;
    }

    // Getters
    public double getBalance() {
        return balance;
    }
    public int getAccountNumber() {
        return accountNumber;
    }

    // Setters
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    // Methods
    public double deposit(double amount) {
        balance += amount;

        return balance;
    }

    public double withdraw(double amount) {
        balance -= amount;

        return balance;
    }

    public void addAccount(ArrayList<BankAccount> accounts, int accountNumber) {
        BankAccount newAcc = new BankAccount(0, accountNumber);
        accounts.add(newAcc);
    }

    public void displayAccounts(ArrayList<BankAccount> accounts) {
        for (int i = 0; i < accounts.size(); i++) {
            System.out.println(i + ": " + accounts.get(i));
        }
        System.out.print("\nPress enter to go back...");
    }

    public void transfer(ArrayList<BankAccount> accounts, int input, double sum) {
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

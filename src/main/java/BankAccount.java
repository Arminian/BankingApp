import java.util.ArrayList;

public class BankAccount {
    // Attributes
    private String fullName;
    public int accountNumber;
    private double balance;

    // Constructors
    public BankAccount(String fullName, int accountNumber, double balance) {
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
    public double getBalance() {
        return balance;
    }

    // Setters
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }
    public void setBalance(double balance) {
        this.balance = balance;
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

    public void addAccount(ArrayList<BankAccount> accounts, String fullName, int accountNumber, double balance) {
        BankAccount newAcc = new BankAccount(fullName, accountNumber, balance);
        accounts.add(newAcc);
    }

    public void displayAccounts(ArrayList<BankAccount> accounts) {
        for (int i = 0; i < accounts.size(); i++) {
            System.out.println(i + ": " + accounts.get(i).fullName + " | account - " + accounts.get(i).accountNumber);
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

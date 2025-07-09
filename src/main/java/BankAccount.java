import java.math.BigDecimal;
import java.util.Objects;

public class BankAccount {
    // Attributes
    public String accountID;
    private String login;
    private String password;
    public BigDecimal balance;

    // Constructors
    public BankAccount(String accountID, String login, String password, BigDecimal balance) {
        this.accountID = accountID;
        this.login = login;
        this.password = password;
        this.balance = balance;
    }

    // Getters
    public String getAccountID() {
        return accountID;
    }
    public String getLogin() {
        return login;
    }
    public String getPassword() {
        return password;
    }
    public BigDecimal getBalance() {
        return balance;
    }

    // Setters
    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public static boolean compare(String str1, String str2) {
        return (Objects.equals(str1, str2));
    }
}

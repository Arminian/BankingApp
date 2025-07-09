import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class BankDbHandler {
    private static final String jdbcUrl = "jdbc:sqlite:src/main/resources/accounts.db";
    private static final Logger logger = LogManager.getLogger(BankDbHandler.class);

    public static void setAccount(String accountID, String login, String password, BigDecimal balance) {
        try (Connection conn = DriverManager.getConnection(jdbcUrl)) {

            String table = "CREATE TABLE IF NOT EXISTS accounts ("
                    + "account_ID text PRIMARY KEY,"
                    + "login text NOT NULL UNIQUE,"
                    + "password text NOT NULL,"
                    + "balance DOUBLE"
                    + ");";

            var stat = conn.createStatement();
            stat.execute(table);

            String insertQuery = "INSERT INTO accounts (account_ID, login, password, balance) VALUES (?, ?, ?, ?);";
            PreparedStatement preparedStatement = conn.prepareStatement(insertQuery);
            preparedStatement.setString(1, accountID);
            preparedStatement.setString(2, login);
            preparedStatement.setString(3, password);
            preparedStatement.setBigDecimal(4, balance);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            logger.info("account was created successfully");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            logger.error("Account creation error: {}", e.getMessage());
        }
    }

    public static BankAccount logIn(String login, String pass) {
        try (Connection conn = DriverManager.getConnection(jdbcUrl)) {

            String selectQuery = "SELECT * FROM accounts WHERE login = ? AND password = ?;";
            PreparedStatement preparedStatement = conn.prepareStatement(selectQuery);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, pass);
            ResultSet rs = preparedStatement.executeQuery();

            BankAccount account = new BankAccount(rs.getString("account_ID"), rs.getString("login"),
                    rs.getString("password"), rs.getBigDecimal("balance"));

            conn.close();
            logger.info("Successfully logged in");
            return account;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            logger.error("log in error: {}", e.getMessage());
        }
        return null;
    }

    public static BankAccount getAccount(String accountID) {
        try (Connection conn = DriverManager.getConnection(jdbcUrl)) {
            String selectQuery ="SELECT * FROM accounts WHERE account_ID = ?;";
            PreparedStatement preparedStatement = conn.prepareStatement(selectQuery);
            preparedStatement.setString(1, accountID);
            ResultSet rs = preparedStatement.executeQuery();

            BankAccount account = new BankAccount(rs.getString("account_ID"), rs.getString("login"),
                    rs.getString("password"), rs.getBigDecimal("balance"));

            conn.close();
            logger.info("Account retrieved successfully");
            return account;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            logger.error("Account retrieval error: {}", e.getMessage());
            return null;
        }
    }

    public static ArrayList<String> getAccountList() {
        ArrayList<String> accounts = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(jdbcUrl)) {

            var stat = conn.createStatement();
            ResultSet rs = stat.executeQuery( "SELECT account_ID FROM accounts;" );

            while (rs.next()) {
                accounts.add(rs.getString("account_ID"));
            }

            conn.close();
            logger.info("All accounts retrieved successfully");
            return accounts;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            logger.error("Accounts list retrieval error: {}", e.getMessage());
            return null;
        }
    }

    public static void displayAccountList() {
        ArrayList<String> accList = getAccountList();

        for (int i = 0; i < accList.size(); i++) {
            System.out.println(i + ": " + accList.get(i));
        }
    }

    public static void updateBalance(BigDecimal amount, String accountID) {
        String updateQuery = "UPDATE accounts SET balance = ? WHERE account_ID = ?;";

        try (Connection conn = DriverManager.getConnection(jdbcUrl)) {

            PreparedStatement preparedStatement = conn.prepareStatement(updateQuery);
            preparedStatement.setBigDecimal(1, amount);
            preparedStatement.setString(2, accountID);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            logger.info("Balance updated successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            logger.error("Balance update error: {}", e.getMessage());
        }
    }

    public static void deleteAccount(String accountID) {
        String deleteQuery = "DELETE FROM accounts WHERE account_ID = ?;";

        try (Connection conn = DriverManager.getConnection(jdbcUrl)) {

            PreparedStatement preparedStatement = conn.prepareStatement(deleteQuery);
            preparedStatement.setString(1, accountID);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            logger.info("Account deleted successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            logger.error("Account deletion error: {}", e.getMessage());
        }
    }

    public static void updatePassword(String oldPass, String newPass) {
        String updateQuery = "UPDATE accounts SET password = ? WHERE password = ?;";

        try (Connection conn = DriverManager.getConnection(jdbcUrl)) {
            PreparedStatement preparedStatement = conn.prepareStatement(updateQuery);
            preparedStatement.setString(1, newPass);
            preparedStatement.setString(2, oldPass);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            logger.info("Password updated successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            logger.error("Password update error: {}", e.getMessage());
        }
    }

    public static void deposit(BigDecimal amount, BankAccount acc) {
        acc.balance = acc.balance.add(amount);
        updateBalance(acc.balance, acc.accountID);
    }

    public static void withdraw(BigDecimal amount, BankAccount acc) {
        acc.balance = acc.balance.subtract(amount);
        updateBalance(acc.balance, acc.accountID);
    }

    public static void transfer(BigDecimal amount, BankAccount sender, String receiverID) {
        BankAccount receiver = getAccount(receiverID);

        withdraw(amount, sender);
        assert receiver != null;
        deposit(amount, receiver);
    }
}
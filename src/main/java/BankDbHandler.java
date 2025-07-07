import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class BankDbHandler {
    public static void setAccount(String fullName, int accountNum, BigDecimal balance) {
        String jdbcUrl = "jdbc:sqlite:src/main/resources/accounts.db";

        try {
            Connection conn = DriverManager.getConnection(jdbcUrl);

            String table = "CREATE TABLE IF NOT EXISTS accounts ("
                                + "id INTEGER PRIMARY KEY,"
                                + "full_name text NOT NULL,"
                                + "account_number INTEGER,"
                                + "balance DOUBLE"
                                + ");";

            var stat = conn.createStatement();
            stat.execute(table);

            String insertQuery = "INSERT INTO accounts (full_name, account_number, balance) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(insertQuery);
            preparedStatement.setString(1, fullName);
            preparedStatement.setInt(2, accountNum);
            preparedStatement.setBigDecimal(3, balance);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void getAccounts(ArrayList<BankAccount> accounts) {
        String jdbcUrl = "jdbc:sqlite:src/main/resources/accounts.db";

        try {
            System.out.println("tried to get import from db");
            Connection conn = DriverManager.getConnection(jdbcUrl);

            var stat = conn.createStatement();
            ResultSet rs = stat.executeQuery( "SELECT * FROM accounts;" );

            while (rs.next()) {
                BankAccount newAcc = new BankAccount(rs.getString("full_name"),
                        rs.getInt("account_number"), rs.getBigDecimal("balance"));
                accounts.add(newAcc);
            }

            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void getAll() {
        String jdbcUrl = "jdbc:sqlite:src/main/resources/accounts.db";

        try {
            Connection conn = DriverManager.getConnection(jdbcUrl);

            var stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM accounts;");
            while (rs.next()) {
                System.out.println("------------------------------------");
                System.out.println("name: " + rs.getString("full_name"));
                System.out.println("number: " + rs.getInt("account_number"));
                System.out.println("balance: " + rs.getDouble("balance"));
            }
            rs.close();

            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}

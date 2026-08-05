package bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC CRUD for bank_account table
 */
public class AccountDAO {

    public boolean addAccount(Account account, String type) {
        String sql = "INSERT INTO bank_account "
                + "(account_num, account_owner, balance, account_type, password, bonus_point, account_limit) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, account.getAccountNum());
            ps.setString(2, account.getAccountOwner());
            ps.setDouble(3, account.getBalance());
            ps.setString(4, type);

            if (account instanceof DebitAccount) {
                DebitAccount d = (DebitAccount) account;
                ps.setString(5, d.getPassword());
                ps.setInt(6, 0);
                ps.setObject(7, null);
            } else if (account instanceof CreditAccount) {
                CreditAccount c = (CreditAccount) account;
                ps.setString(5, null);
                ps.setInt(6, c.getBonusPoint());
                ps.setDouble(7, c.getLimit());
            } else {
                ps.setString(5, null);
                ps.setInt(6, 0);
                ps.setObject(7, null);
            }

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Add failed: " + e.getMessage());
            return false;
        }
    }

    public List<Account> getAllAccounts() {
        List<Account> list = new ArrayList<>();
        String sql = "SELECT * FROM bank_account ORDER BY account_num";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (Exception e) {
            System.out.println("Display all failed: " + e.getMessage());
        }
        return list;
    }

    public Account getAccountById(String accountNum) {
        String sql = "SELECT * FROM bank_account WHERE account_num = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, accountNum);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (Exception e) {
            System.out.println("Find failed: " + e.getMessage());
        }
        return null;
    }

    public boolean updateAccount(Account account) {
        String sql = "UPDATE bank_account SET balance = ?, bonus_point = ? WHERE account_num = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDouble(1, account.getBalance());
            if (account instanceof CreditAccount) {
                ps.setInt(2, ((CreditAccount) account).getBonusPoint());
            } else {
                ps.setInt(2, 0);
            }
            ps.setString(3, account.getAccountNum());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Update failed: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteAccount(String accountNum) {
        String sql = "DELETE FROM bank_account WHERE account_num = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, accountNum);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Delete failed: " + e.getMessage());
            return false;
        }
    }

    private Account mapRow(ResultSet rs) throws Exception {
        String type = rs.getString("account_type");
        String num = rs.getString("account_num");
        String owner = rs.getString("account_owner");
        double balance = rs.getDouble("balance");

        if ("DEBIT".equalsIgnoreCase(type)) {
            return new DebitAccount(num, owner, balance, rs.getString("password"));
        }
        if ("CREDIT".equalsIgnoreCase(type)) {
            return new CreditAccount(num, owner, balance, rs.getInt("bonus_point"), rs.getDouble("account_limit"));
        }
        return new Account(num, owner, balance);
    }
}

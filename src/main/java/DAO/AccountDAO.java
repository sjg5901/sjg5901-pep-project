package DAO;

import Model.Account;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.*;

public class AccountDAO {
    

    // helper function to ensure username is unique upon creation
    public Account getAccountByUsername(String username) {
        Connection connection = ConnectionUtil.getConnection();

        try {

            String sql = "SELECT * FROM account WHERE username = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }


    public Account registerAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection();

        try {

            String sql = "INSERT INTO account (username, password) VALUES (?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                int db_generated_id = (int) rs.getLong(1);
                return new Account(db_generated_id, account.getUsername(), account.getPassword());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        return null;
    }



}

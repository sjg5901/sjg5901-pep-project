package DAO;

import Model.Message;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.*;

public class MessageDAO {
    

    public Message addMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();

        // check to assert message_text not blank and within varchar parameters (254 characters)
        if (message.getMessage_text().isBlank() || message.getMessage_text().length() > 254) return null;

        try {

            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();

            if (rs.next()) {
                int DB_generated_id = rs.getInt(1);
                return new Message(DB_generated_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }



    public List<Message> getAllMessages() {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> list = new ArrayList<>();

        try {

            String sql = "SELECT * FROM message";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), 
                    rs.getString("message_text"), rs.getLong("time_posted_epoch"));

                list.add(message);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        return list;
    }

    public Message getMessageById(int msg_id) {
        Connection connection = ConnectionUtil.getConnection();

        try {

            String sql = "SELECT * FROM message WHERE message_id = ?";
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, msg_id);

            ResultSet rs = preparedStatement.executeQuery();
            
            while (rs.next()) {
                return new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), 
                    rs.getLong("time_poster_epoch"));
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}

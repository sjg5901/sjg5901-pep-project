package DAO;

import Model.Message;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.*;

import org.h2.command.ddl.PrepareProcedure;

public class MessageDAO {
    

    public Message addMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();

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

    public Message getMessageById(int id) {
        Connection connection = ConnectionUtil.getConnection();

        try {

            String sql = "SELECT * FROM message WHERE message_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            
            while (rs.next()) {
                return new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), 
                    rs.getLong("time_posted_epoch"));
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public void deleteMessageById(int id) {
        Connection connection = ConnectionUtil.getConnection();

        try {

            String sql = "DELETE FROM message WHERE message_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateMessageById(int id, Message message) {
        Connection connection = ConnectionUtil.getConnection();

        try {

            String sql = "UPDATE message SET message_text = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
 
            preparedStatement.setString(1, message.getMessage_text());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Message> getAllMessagesByAccountId(int id) {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> list = new ArrayList<>();

        try {

            String sql = "SELECT * FROM message WHERE posted_by = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

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
}

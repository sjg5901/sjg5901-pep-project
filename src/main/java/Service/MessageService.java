package Service;

import Model.Message;
import DAO.MessageDAO;
import java.util.*;

public class MessageService {
    MessageDAO messageDAO;

    public MessageService() {
        this.messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }


    public Message addMessage(Message message) {

        // check message values are valid
        if (message.getMessage_text().isBlank() || message.getMessage_text().length() > 254) return null;

        return messageDAO.addMessage(message);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }


    public Message getMessageById(int msg_id) {
        return messageDAO.getMessageById(msg_id);
    }

    public Message deleteMessageById(int id) {

        // whether id exists or not, DELETE is idempotent thus we can merely delete
        // return getMessageById for display purposes to user. If id doesn't exist handled in other methods

        Message temp = messageDAO.getMessageById(id);
        messageDAO.deleteMessageById(id);
        return temp;
    }
    
    public Message updateMessageById(int id, Message message) {

        // first check to see if message exists in database
        // if exists, update existing entry
        // check for valid update values here due to void method in DAO


        if (messageDAO.getMessageById(id) != null) { 
            if (message.getMessage_text().isBlank() || message.getMessage_text().length() > 254) return null;

            messageDAO.updateMessageById(id, message);
        }

        return messageDAO.getMessageById(id);
    }

    public List<Message> getAllMessagesByAccountId(int id) {
        return messageDAO.getAllMessagesByAccountId(id);
    }
}

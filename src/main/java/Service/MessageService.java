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
        return messageDAO.addMessage(message);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }


    public Message getMessageById(int msg_id) {
        return messageDAO.getMessageById(msg_id);
    }
}

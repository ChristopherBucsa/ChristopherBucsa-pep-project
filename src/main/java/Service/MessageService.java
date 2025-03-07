package Service;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Account;
import Model.Message;
import java.util.List;

public class MessageService {
    private MessageDAO messageDAO = new MessageDAO();
    
    public Message createMessage(int userId, String messageText, long timestamp) {
        if (messageText == null || messageText.isBlank() || messageText.length() > 255) {
            return null;
        }
        return messageDAO.createMessage(new Message(0, userId, messageText, timestamp));
    }
    
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }
    
    public Message getMessageById(int messageId) {
        return messageDAO.getMessageById(messageId);
    }
    
    public Message deleteMessage(int messageId) {
        return messageDAO.deleteMessage(messageId);
    }
    
    public boolean updateMessage(int messageId, String newText) {
        if (newText == null || newText.isBlank() || newText.length() > 255) {
            return false;
        }
        return messageDAO.updateMessage(messageId, newText);
    }
    
    public List<Message> getMessagesByUser(int accountId) {
        return messageDAO.getMessagesByUser(accountId);
    }
}
package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Service.AccountService;
import Service.MessageService;
import Model.Account;
import Model.Message;
import java.util.List;

public class SocialMediaController {
    private AccountService accountService = new AccountService();
    private MessageService messageService = new MessageService();
    
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        
        app.post("/register", this::registerUser);
        app.post("/login", this::loginUser);
        app.post("/messages", this::createMessage);
        app.get("/messages", this::getAllMessages);
        app.get("/messages/{message_id}", this::getMessageById);
        app.delete("/messages/{message_id}", this::deleteMessage);
        app.patch("/messages/{message_id}", this::updateMessage);
        app.get("/accounts/{account_id}/messages", this::getMessagesByUser);
        
        return app;
    }
    
    private void registerUser(Context context) {
        Account account = context.bodyAsClass(Account.class);
        Account createdAccount = accountService.registerUser(account.getUsername(), account.getPassword());
        if (createdAccount != null) {
            context.json(createdAccount);
        } else {
            context.status(400);
        }
    }
    
    private void loginUser(Context context) {
        Account account = context.bodyAsClass(Account.class);
        Account loggedInAccount = accountService.loginUser(account.getUsername(), account.getPassword());
        if (loggedInAccount != null) {
            context.json(loggedInAccount);
        } else {
            context.status(401);
        }
    }
    
    private void createMessage(Context context) {
        Message message = context.bodyAsClass(Message.class);
        Message createdMessage = messageService.createMessage(message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
        if (createdMessage != null) {
            context.json(createdMessage);
        } else {
            context.status(400);
        }
    }
    
    private void getAllMessages(Context context) {
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
    }
    
    private void getMessageById(Context context) {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessageById(messageId);
        if (message != null) {
            context.json(message);
        } else {
            context.status(200).json("");
        }
    }
    
    private void deleteMessage(Context context) {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message deletedMessage = messageService.deleteMessage(messageId);
        if (deletedMessage != null) {
            context.json(deletedMessage);
        } else {
            context.status(200);
        }
    }
    
    private void updateMessage(Context context) {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message message = context.bodyAsClass(Message.class);
        boolean updated = messageService.updateMessage(messageId, message.getMessage_text());
        if (updated) {
            context.json(messageService.getMessageById(messageId));
        } else {
            context.status(400);
        }
    }
    
    private void getMessagesByUser(Context context) {
        int accountId = Integer.parseInt(context.pathParam("account_id"));
        List<Message> messages = messageService.getMessagesByUser(accountId);
        context.json(messages);
    }
}
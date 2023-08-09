package Controller;

import Model.*;
import Service.*;
import io.javalin.Javalin;
import io.javalin.http.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postNewAccountHandler);


        app.post("/login", this::verifyLoginHandler);
        app.post("/messages", this::postNewMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByAccountIdHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postNewAccountHandler(Context context) throws JsonProcessingException {
        ObjectMapper obj = new ObjectMapper();
        Account account = obj.readValue(context.body(), Account.class);
        Account newAccount = accountService.addAccount(account);

        if (newAccount == null) {
            context.status(400);
        } else {
            context.json(obj.writeValueAsString(newAccount));
        }
        
    }



    
    private void verifyLoginHandler(Context context) throws JsonProcessingException {
        ObjectMapper obj = new ObjectMapper();
        Account account = obj.readValue(context.body(), Account.class);
        Account newLogin = accountService.verifyLogin(account);

        if (newLogin == null) {
            context.status(401);
        } else {
            context.json(obj.writeValueAsString(newLogin));
        }
    }

     
    private void postNewMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper obj = new ObjectMapper();
        Message message = obj.readValue(context.body(), Message.class);
        Message newMessage = messageService.addMessage(message);

        if (newMessage == null) {
            context.status(400);
        } else {
            context.json(obj.writeValueAsString(newMessage));
        }
    }

    
    private void getAllMessagesHandler(Context context) {
        context.json(messageService.getAllMessages());
    }


     
    private void getMessageByIdHandler(Context context) {    

        Message message = messageService.getMessageById(Integer.parseInt(context.pathParam("message_id")));

        if (message == null) {
            context.json("");
        } else {
            context.json(message);
        }
        
    }

    
    private void deleteMessageByIdHandler(Context context) {

        Message message = messageService.deleteMessageById(Integer.parseInt(context.pathParam("message_id")));

        if (message == null) {
            context.json("");
        } else {
            context.json(message);
        }
        
    }

    
    private void updateMessageByIdHandler(Context context) throws JsonProcessingException {
        ObjectMapper obj = new ObjectMapper();
        Message message = obj.readValue(context.body(), Message.class);
        int id = Integer.parseInt(context.pathParam("message_id"));
        Message updatedMessage = messageService.updateMessageById(id, message);

        if (updatedMessage == null) {
            context.status(400);
        } else {
            context.json(obj.writeValueAsString(updatedMessage));
        }
    }
    

    private void getAllMessagesByAccountIdHandler(Context context) {
        context.json(messageService.getAllMessagesByAccountId(Integer.parseInt(context.pathParam("account_id"))));
    }


}
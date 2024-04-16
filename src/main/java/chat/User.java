package chat;

import chat.exception.UserNotRegisteredException;

import java.util.Iterator;

public class User implements IterableByUser {
    private final String username;
    private ChatServer registeredServer;
    private final ChatHistory chatHistory;

    public User(String username) {
        this.username = username;
        this.chatHistory = new ChatHistory();
    }

    public void receiveMessage(Message message) {
        System.out.println(message.toString() + " (received by " + username + ")");
        chatHistory.addReceivedMessage(message);
    }

    public void sendMessage(Message message) {
        checkHasRegisteredServer();
        registeredServer.sendMessage(message);
        chatHistory.addSentMessage(message);
    }

    public void undoLastMessage() {
        getLastMessage().undo();
    }

    public Message getLastMessage() {
        return chatHistory.getLastMessageSent();
    }

    public void blockUser(User blocked) {
        checkHasRegisteredServer();
        registeredServer.blockUser(this, blocked);
    }

    public void viewChatHistoryWith(User user) {
        Iterator<Message> messages = chatHistory.iterator(user);
        System.out.printf("Chat History with %s:\n", user.getUsername());

        while (messages.hasNext()) {
            System.out.println(messages.next().toString());
        }
    }

    public String getUsername() {
        return username;
    }

    private void checkHasRegisteredServer() {
        if (registeredServer == null) throw new UserNotRegisteredException(this);
    }

    public void setRegisteredServer(ChatServer server) {
        this.registeredServer = server;
    }

    public ChatServer getRegisteredServer() {
        return registeredServer;
    }

    @Override
    public Iterator<Message> iterator(User userToSearchWith) {
        return chatHistory.iterator(userToSearchWith);
    }

}

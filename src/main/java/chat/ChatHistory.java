package chat;

import java.util.*;

public class ChatHistory implements IterableByUser {
    private final Map<User, List<Message>> messageHistories;
    private Message lastMessageSent;

    public ChatHistory() {
        this.messageHistories = new HashMap<>();
    }

    public void addReceivedMessage(Message message) {
        addMessage(message.getSender(), message);
    }

    public void addSentMessage(Message message) {
        for (User recipient : message.getRecipients()) {
            addMessage(recipient, message);
        }
        lastMessageSent = message;
    }

    private void addMessage(User correspondent, Message message) {
        if (!messageHistories.containsKey(correspondent))
            messageHistories.put(correspondent, new ArrayList<>());

        List<Message> messages = messageHistories.get(correspondent);
        messages.add(message);
    }

    public Message getLastMessageSent() {
        return lastMessageSent;
    }

    @Override
    public Iterator<Message> iterator(User userToSearchWith) {
        List<Message> messages = messageHistories.get(userToSearchWith);

        return new SearchMessagesByUser(Objects.requireNonNullElseGet(messages, ArrayList::new));
    }
}

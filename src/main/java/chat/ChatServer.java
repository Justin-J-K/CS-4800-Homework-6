package chat;

import chat.exception.UserNotRegisteredException;

import java.util.*;

public class ChatServer {
    private final Set<User> users;
    private final Map<User, Set<User>> userBlocks;

    public ChatServer() {
        users = new HashSet<>();
        userBlocks = new HashMap<>();
    }

    public void registerUser(User user) {
        users.add(user);
        userBlocks.put(user, new HashSet<>());
        user.setRegisteredServer(this);
    }

    public void unregisterUser(User user) {
        users.remove(user);
        userBlocks.remove(user);
        user.setRegisteredServer(null);
    }

    public void blockUser(User blocker, User blocked) {
        checkUserIsRegistered(blocker);
        checkUserIsRegistered(blocked);

        Set<User> blocks = userBlocks.get(blocker);
        blocks.add(blocked);
    }

    public void sendMessage(Message message) {
        User sender = message.getSender();
        checkUserIsRegistered(sender);

        for (User recipient : message.getRecipients()) {
            checkUserIsRegistered(recipient);

            Set<User> blocks = userBlocks.get(recipient);
            if (blocks.contains(sender)) continue;

            recipient.receiveMessage(message);
        }
    }

    private void checkUserIsRegistered(User user) {
        if (!users.contains(user)) throw new UserNotRegisteredException(user);
    }
}

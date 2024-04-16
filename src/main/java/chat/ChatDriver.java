package chat;

import java.time.LocalDateTime;
import java.util.List;

public class ChatDriver {
    public static void main(String[] args) {
        ChatServer server = new ChatServer();
        User bob = new User("bob");
        User alice = new User("alice");
        User mallory = new User("mallory");

        server.registerUser(bob);
        server.registerUser(alice);
        server.registerUser(mallory);

        // Users can send messages to one or more other users through the chat server.
        System.out.println("Basic messaging functionality demo:");

        Message bobMessage = new Message(bob, List.of(alice), LocalDateTime.now(), "Hello, alice");
        bob.sendMessage(bobMessage);

        Message aliceMessage = new Message(alice, List.of(mallory, bob), LocalDateTime.now(), "Hello, mallory and bob");
        alice.sendMessage(aliceMessage);

        Message malloryMessage = new Message(mallory, List.of(bob), LocalDateTime.now(), "Hello, bob");
        mallory.sendMessage(malloryMessage);

        System.out.println();

        // Users can undo the last message they sent using the Memento design pattern.
        // Print initial last message
        Message bobLastMessage = bob.getLastMessage();
        System.out.printf("Before modifications:\n%s\n", bobLastMessage);

        // Then modify the message
        bobLastMessage.setContent("Goodbye, alice");
        bobLastMessage.setTimestamp(LocalDateTime.now());
        System.out.printf("After modifications:\n%s\n", bobLastMessage);

        // Then undo the message
        bob.undoLastMessage();
        System.out.printf("After undo:\n%s\n", bobLastMessage);

        System.out.println();

        // Users can block messages from specific users using the Mediator design pattern.
        System.out.println("Bob blocking alice demo:");

        bob.blockUser(alice);
        Message aliceToBobMessage = new Message(alice, List.of(bob, mallory), LocalDateTime.now(), "Are you there?");
        alice.sendMessage(aliceToBobMessage);

        System.out.println();

        // Users can receive messages from other users and view the chat history for a specific user.
        System.out.println("Viewing chat history of alice demo:");

        Message malloryToAlice = new Message(mallory, List.of(alice), LocalDateTime.now(), "Hello, alice");
        mallory.sendMessage(malloryToAlice);
        alice.viewChatHistoryWith(mallory);
        alice.viewChatHistoryWith(bob);
    }
}

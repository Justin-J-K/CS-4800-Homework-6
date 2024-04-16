import chat.ChatServer;
import chat.Message;
import chat.User;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ChatServerTest extends BaseTest {
    @Test
    public void canRegisterUsers() throws IllegalAccessException, NoSuchFieldException {
        registerUsers();

        Set<User> users = getRegisteredUsersFromServer();

        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
    }

    @Test
    public void canUnregisterUsers() throws IllegalAccessException, NoSuchFieldException {
        registerUsers();
        unregisterUsers();

        Set<User> users = getRegisteredUsersFromServer();

        assertFalse(users.contains(user1));
        assertFalse(users.contains(user2));
    }

    @Test
    public void canSendMessagesForUsers() {
        registerUsers();
        server.sendMessage(message1);
        assertEquals("2024-01-01T00:00 [user1] content (received by user2)", out.toString().trim());
    }

    @Test
    public void sentMessagesShowUpInChatHistory() {
        registerUsers();
        server.sendMessage(message1);

        Iterator<Message> messages = user2.iterator(user1);
        assertEquals(message1, messages.next());
    }

    @Test
    public void messagesCanBeBlocked() {
        registerUsers();
        server.blockUser(user1, user2);
        server.sendMessage(message2);
        assertEquals("", out.toString().trim());
    }

    private Set<User> getRegisteredUsersFromServer() throws IllegalAccessException, NoSuchFieldException {
        Field usersField = ChatServer.class.getDeclaredField("users");
        usersField.setAccessible(true);

        Set<User> users = (Set<User>) usersField.get(server);
        return users;
    }

    private void unregisterUsers() {
        server.unregisterUser(user1);
        server.unregisterUser(user2);
    }
}

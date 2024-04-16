import chat.Message;
import chat.exception.UserNotRegisteredException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest extends BaseTest {
    @Test
    public void userPrintsReceivedMessage() {
        user2.receiveMessage(message1);
        assertEquals("2024-01-01T00:00 [user1] content (received by user2)", out.toString().trim());
    }

    @Test
    public void userCannotSendWithoutServer() {
        assertThrows(UserNotRegisteredException.class, () -> user1.sendMessage(message1));
    }

    @Test
    public void userCanSendWithServer() {
        registerUsers();
        user1.sendMessage(message1);
        assertEquals("2024-01-01T00:00 [user1] content (received by user2)", out.toString().trim());
    }

    @Test
    public void userCanUndoLastMessage() {
        registerUsers();
        user1.sendMessage(message1);

        user1.getLastMessage().setContent("message");
        user1.getLastMessage().setTimestamp(LocalDateTime.of(2020, Month.JANUARY, 1, 0, 0));

        assertEquals("2020-01-01T00:00 [user1] message", user1.getLastMessage().toString());

        user1.undoLastMessage();

        assertEquals("2024-01-01T00:00 [user1] content", user1.getLastMessage().toString());
    }

    @Test
    public void userCanGetLastMessageSent() {
        registerUsers();
        user1.sendMessage(message1);
        assertEquals(message1, user1.getLastMessage());
    }

    @Test
    public void userCanBLockUser() {
        registerUsers();
        user2.blockUser(user1);
        user1.sendMessage(message1);
        assertEquals("", out.toString().trim());
    }

    @Test
    public void userCanViewChatHistory() {
        registerUsers();
        user1.sendMessage(message1);
        user2.sendMessage(message2);
        user1.viewChatHistoryWith(user2);
        assertEquals("""
                2024-01-01T00:00 [user1] content (received by user2)
                2018-01-01T00:00 [user2] the content (received by user1)
                Chat History with user2:
                2024-01-01T00:00 [user1] content
                2018-01-01T00:00 [user2] the content""", out.toString().trim());
    }

    @Test
    public void canGetUsername() {
        assertEquals("user1", user1.getUsername());
    }

    @Test
    public void canGetRegisteredServer() {
        registerUsers();
        assertEquals(server, user1.getRegisteredServer());
    }

    @Test
    public void canGetIteratorOfChatHistory() {
        registerUsers();
        user1.sendMessage(message1);
        user2.sendMessage(message2);

        Iterator<Message> messages = user1.iterator(user2);

        assertNotNull(messages.next());
    }

}

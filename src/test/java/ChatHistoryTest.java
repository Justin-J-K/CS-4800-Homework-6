import chat.ChatHistory;
import chat.Message;
import chat.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ChatHistoryTest extends BaseTest {
    private ChatHistory history;

    @BeforeEach
    public void setupHistory() {
        history = new ChatHistory();
    }

    @Test
    public void canAddReceivedMessages() {
        history.addReceivedMessage(message1);
        assertHistoryHasMessageForUser(message1, user1);
    }

    @Test
    public void canAddSentMessages() {
        history.addSentMessage(message1);
        assertHistoryHasMessageForUser(message1, user2);
    }

    @Test
    public void canGetLastMessageSent() {
        history.addSentMessage(message1);
        assertEquals(message1, history.getLastMessageSent());
    }

    @Test
    public void canGetIteratorOfMessagesForUser() {
        history.addSentMessage(message1);
        assertNotNull(history.iterator(user2).next());
    }

    private void assertHistoryHasMessageForUser(Message message, User user) {
        Iterator<Message> messages = history.iterator(user);
        assertEquals(message, messages.next());
    }
}

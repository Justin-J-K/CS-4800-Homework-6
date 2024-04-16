import chat.MessageMemento;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageTest extends BaseTest {
    @Test
    public void canGetSender() {
        assertEquals(user1, message1.getSender());
    }

    @Test
    public void canGetRecipients() {
        assertEquals(1, message1.getRecipients().size());
    }

    @Test
    public void canGetTimestamp() {
        assertEquals(LocalDateTime.of(2024, Month.JANUARY,1, 0, 0), message1.getTimestamp());
    }

    @Test
    public void canGetContent() {
        assertEquals("content", message1.getContent().trim());
    }

    @Test
    public void canSetTimestamp() {
        changeTimestamp();
        assertEquals(LocalDateTime.of(2020, Month.JANUARY, 15, 0, 0), message1.getTimestamp());
    }

    @Test
    public void canSetContent() {
        changeContent();
        assertEquals("content two", message1.getContent().trim());
    }

    @Test
    public void canUndoChangesToTimestampAndContent() {
        changeTimestampAndContent();
        message1.undo();

        assertEquals(LocalDateTime.of(2024, Month.JANUARY, 1, 0, 0), message1.getTimestamp());
        assertEquals("content", message1.getContent().trim());
    }

    @Test
    public void canSaveChangesToTimestampAndContent() {
        changeTimestampAndContent();
        message1.save();
        message1.undo();

        assertEquals(LocalDateTime.of(2020, Month.JANUARY, 15, 0, 0), message1.getTimestamp());
        assertEquals("content two", message1.getContent().trim());
    }

    @Test
    public void messageToStringReturnsFormatted() {
        assertEquals("2024-01-01T00:00 [user1] content", message1.toString().trim());
    }

    @Test
    public void canInstantiateMessageMemento() {
        MessageMemento memento = new MessageMemento(LocalDateTime.MAX, "content");
        assertEquals("content", memento.getContent().trim());
        assertEquals(LocalDateTime.MAX, memento.getTimestamp());
    }

    private void changeTimestampAndContent() {
        changeTimestamp();
        changeContent();
    }

    private void changeTimestamp() {
        message1.setTimestamp(LocalDateTime.of(2020, Month.JANUARY, 15, 0, 0));
    }

    private void changeContent() {
        message1.setContent("content two");
    }
}

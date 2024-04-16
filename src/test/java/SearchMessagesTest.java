import chat.Message;
import chat.SearchMessagesByUser;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchMessagesTest extends BaseTest {
    @Test
    public void iteratorContainsAllMessages() {
        SearchMessagesByUser searchMessagesByUser = new SearchMessagesByUser(List.of(message1, message2));

        Set<Message> messages = new HashSet<>(List.of(message1, message2));
        int totalSize = 0;

        while (searchMessagesByUser.hasNext()) {
            Message message = searchMessagesByUser.next();
            messages.remove(message);
            totalSize++;
        }

        assertEquals(2, totalSize);
        assertEquals(0, messages.size());
    }
}

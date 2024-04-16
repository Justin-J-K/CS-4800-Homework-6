import chat.ChatServer;
import chat.Message;
import chat.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

public abstract class BaseTest {
    protected final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final PrintStream standardOut = System.out;
    protected User user1, user2;
    protected Message message1, message2;
    protected ChatServer server;

    @BeforeEach
    public void setup() {
        System.setOut(new PrintStream(out));
        user1 = new User("user1");
        user2 = new User("user2");
        message1 = new Message(user1, List.of(user2),
                LocalDateTime.of(2024, Month.JANUARY,1, 0, 0),
                "content");
        message2 = new Message(user2, List.of(user1),
                LocalDateTime.of(2018, Month.JANUARY, 1, 0, 0),
                "the content");
        server = new ChatServer();
    }

    @AfterEach
    public void teardown() {
        System.setOut(standardOut);
    }

    protected void registerUsers() {
        server.registerUser(user1);
        server.registerUser(user2);
    }
}

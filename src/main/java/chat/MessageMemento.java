package chat;

import java.time.LocalDateTime;

public class MessageMemento {
    private LocalDateTime timestamp;
    private String content;

    public MessageMemento(LocalDateTime timestamp, String content) {
        this.timestamp = timestamp;
        this.content = content;
    }

    public void setState(LocalDateTime timestamp, String content) {
        this.timestamp = timestamp;
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getContent() {
        return content;
    }
}

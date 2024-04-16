package chat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Message {
    private final User sender;
    private final List<User> recipients;
    private LocalDateTime timestamp;
    private String content;
    private final MessageMemento messageMemento;

    public Message(User sender, List<User> recipients, LocalDateTime timestamp, String content) {
        this.sender = sender;
        this.recipients = new ArrayList<>(recipients);
        this.timestamp = timestamp;
        this.content = content;
        this.messageMemento = new MessageMemento(timestamp, content);
    }

    public User getSender() {
        return sender;
    }

    public List<User> getRecipients() {
        return recipients;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void save() {
        messageMemento.setState(timestamp, content);
    }

    public void undo() {
        timestamp = messageMemento.getTimestamp();
        content = messageMemento.getContent();
    }

    @Override
    public String toString() {
        return String.format("%s [%s] %s", timestamp.toString(), sender.getUsername(), content);
    }
}

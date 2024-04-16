package chat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SearchMessagesByUser implements Iterator<Message> {
    private int indexInMessages;
    private final int messagesSize;
    private final List<Message> messages;

    public SearchMessagesByUser(List<Message> messages) {
        this.messages = new ArrayList<>(messages);
        this.indexInMessages = 0;
        this.messagesSize = this.messages.size();
    }

    @Override
    public boolean hasNext() {
        return indexInMessages < messagesSize;
    }

    @Override
    public Message next() {
        int currentIndex = indexInMessages;
        indexInMessages++;
        return messages.get(currentIndex);
    }
}

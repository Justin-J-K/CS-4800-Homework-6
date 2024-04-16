package chat.exception;

import chat.User;

public class UserNotRegisteredException extends IllegalStateException {
    public UserNotRegisteredException(User user) {
        super(String.format("User %s is not registered", user.getUsername()));
    }
}

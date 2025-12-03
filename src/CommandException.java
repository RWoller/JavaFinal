
// ** custom exception **
public class CommandException extends Exception {

    // Simple constructor that accepts the error message
    public CommandException(String message) {
        super(message);
    }
}
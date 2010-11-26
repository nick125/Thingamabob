package simulation.parser;

/**
 * An error in parsing
 *
 * @author kampernj. Created Oct 30, 2010.
 */
public class ParsingError extends Exception {

    private String message;

    /**
     * Creates a new error instance with the given message
     *
     * @param message
     */
    public ParsingError(String message) {
        this.message = message;
    }

    /**
     * Returns the error message.
     *
     * @return Returns the message.
     */
    @Override
    public String getMessage() {
        return this.message;
	}

}

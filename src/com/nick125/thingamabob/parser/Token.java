package simulation.parser;

/**
 * A token generated by the Lexer.
 *
 * @author kampernj. Created Oct 30, 2010.
 */
public class Token {
    /**
     * A token of an unknown type. You should never get an unknown token.
     */
    public static final int TOKEN_UNKNOWN = 1;
    /**
     * An "if" token, representing an if statement.
     */
    public static final int TOKEN_IF = 2;
    /**
     * An "endif" token, representing the end of an if statement.
     */
    public static final int TOKEN_ENDIF = 4;
    /**
     * A "define" token, representing the start of an instruction.
     */
    public static final int TOKEN_DEFINE = 8;
    /**
     * An "enddefine" token, representing the end of an instruction.
     */
    public static final int TOKEN_ENDDEFINE = 16;
    /**
     * A "goto" token, representing a goto statement.
     */
    public static final int TOKEN_GOTO = 32;
    /**
     * A "write" token, representing a tape write call.
     */
    public static final int TOKEN_WRITE = 64;
    /**
     * A "move" token, representing a tape move.
     */
    public static final int TOKEN_MOVE = 128;
    /**
     * A "left" token, representing a left direction.
     */
    public static final int TOKEN_LEFT = 256;
    /**
     * A "right" token, representing a right direction.
     */
    public static final int TOKEN_RIGHT = 512;
    /**
     * A word that isn't one of the other tokens.
     */
    public static final int TOKEN_WORD = 1024;
    /**
     * A "halt" token, representing when the machine should halt.
     */
    public static final int TOKEN_HALT = 2048;
    /**
     * A token representing what the machine's first instruction is.
     */
    public static final int TOKEN_DEFAULT = 4096;
    /**
     * When the parser interprets a "null".
     */
    public static final int TOKEN_NULL = 8192;

    private int type;
    private String value;

    /**
     * Creates a new Token with the given type and value.
     *
     * @param type
     * @param value
     */
    public Token(int type, String value) {
        this.type = type;
        this.value = value;
    }

    /**
     * Returns the type of this Token. Should be one of the constants defined in
     * Token.
     *
     * @return Returns the type.
     */
    public int getType() {
        return this.type;
    }

    /**
     * Returns the value of the Token.
     *
     * @return Returns the value.
     */
    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
		return this.value.toString();
	}
}
package simulation.parser;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A basic lexer for the MIDL file format
 *
 * @author kampernj. Created Oct 30, 2010.
 */
public class Lexer {
    private StreamTokenizer reader;
    private ArrayList<Token> tokens;

    private static final HashMap<String, Integer> DEFINITIONS = new HashMap<String, Integer>();

    static {
        DEFINITIONS.put("if", Token.TOKEN_IF);
        DEFINITIONS.put("endif", Token.TOKEN_ENDIF);
        DEFINITIONS.put("define", Token.TOKEN_DEFINE);
        DEFINITIONS.put("enddefine", Token.TOKEN_ENDDEFINE);
        DEFINITIONS.put("move", Token.TOKEN_MOVE);
        DEFINITIONS.put("left", Token.TOKEN_LEFT);
        DEFINITIONS.put("right", Token.TOKEN_RIGHT);
        DEFINITIONS.put("goto", Token.TOKEN_GOTO);
        DEFINITIONS.put("write", Token.TOKEN_WRITE);
        DEFINITIONS.put("halt", Token.TOKEN_HALT);
        DEFINITIONS.put("default", Token.TOKEN_DEFAULT);
        DEFINITIONS.put("null", Token.TOKEN_NULL);
    }

    /**
     * Creates a new Lexer
     *
     * @param r
     */
    public Lexer(Reader r) {
        this.reader = new StreamTokenizer(r);
        this.tokens = new ArrayList<Token>();
    }

    /**
     * Parses the file
     *
     * @throws IOException
     */
    public void parseStream() throws IOException {
        int token = this.reader.nextToken();
        while (token != StreamTokenizer.TT_EOF) {
            this.tokens.add(this.parseToken(token));
            token = this.reader.nextToken();
        }
    }

    /**
     * Returns the tokens after parsing.
     *
     * @return An ArrayList of Token objects.
     */
    public ArrayList<Token> getTokens() {
        return this.tokens;
    }

    /**
     * Parses a token
     *
     * @param token Token type from StreamTokenizer.nextToken()
     * @return New Token object
     */
    public Token parseToken(int token) {
        if (token == StreamTokenizer.TT_WORD) {
            String l_value = this.reader.sval.toLowerCase();
            if (DEFINITIONS.containsKey(l_value)) {
                return new Token(DEFINITIONS.get(l_value), this.reader.sval);
            } else {
                return new Token(Token.TOKEN_WORD, this.reader.sval);
            }
        } else if (token == StreamTokenizer.TT_NUMBER) {
            return new Token(Token.TOKEN_WORD, ((Integer) Double.valueOf(
                    this.reader.nval).intValue()).toString());
        } else {
            return new Token(Token.TOKEN_WORD, Character.valueOf((char) token)
					.toString());
		}
	}
}

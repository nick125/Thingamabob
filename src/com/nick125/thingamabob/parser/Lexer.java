/*
 * Thingamabob - A Java-based Turing Machine Emulator
 * Copyright (c) 2010 Nicholas Kamper, Drew Hill, Travis Baumbaugh
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */

package com.nick125.thingamabob.parser;

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

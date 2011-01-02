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

import com.nick125.thingamabob.machine.core.SimpleInst;
import com.nick125.thingamabob.machine.core.SimpleInst.InstructionEntry;
import com.nick125.thingamabob.machine.core.SimpleInstSet;
import com.nick125.thingamabob.machine.core.Tape;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

/**
 * Parses a file (or other type of stream) into an instruction set
 *
 * @author kampernj. Created Oct 30, 2010.
 */
public class Parser {

	/**
	 * Parses the given string
	 *
	 * @param r
	 * @return New instruction set
	 * @throws IOException
	 * @throws ParsingError
	 */
	public SimpleInstSet<String> parseStream(Reader r) throws IOException,
	       ParsingError {
		       SimpleInstSet<String> instructionSet = new SimpleInstSet<String>();
		       Lexer lexer = new Lexer(r);
		       lexer.parseStream();
		       ArrayList<Token> tokens = lexer.getTokens();
		       return this.createInstructionSet(instructionSet, tokens);
	       }

	/**
	 * Handles actually parsing the tokens into an instruction set.
	 *
	 * @param instructionSet
	 * @param tokens
	 * @return
	 */
	private SimpleInstSet<String> createInstructionSet(
			SimpleInstSet<String> instructionSet, ArrayList<Token> tokens)
		throws ParsingError {
			SimpleInst<String> currentInstruction = null;
			SimpleInst.InstructionEntry<String> currentInstructionEntry = null;

			for (Integer i = 0; i < tokens.size(); i++) {
				Token token = tokens.get(i);
				switch (token.getType()) {
					case Token.TOKEN_DEFAULT:
						// Grab the next token
						Token instruction = this.nextToken(i, tokens, "mnemonic");
						i++;

						instructionSet.setDefaultInstruction(instruction.getValue());
						break;

					case Token.TOKEN_DEFINE:
						if (currentInstruction != null) {
							throw new ParsingError(
									"Expecting ENDDEFINE, got DEFINE instead");
						}
						// Grab the next token word
						Token name = this.nextTokenWord(i, tokens, "mnemonic");
						i++;

						currentInstruction = new SimpleInst<String>(name.getValue());
						instructionSet.addInstruction(currentInstruction);
						break;

					case Token.TOKEN_ENDDEFINE:
						if (currentInstruction == null) {
							throw new ParsingError("Got an unexpected ENDDEFINE");
						}
						currentInstruction = null;
						break;

					case Token.TOKEN_IF:
						if (currentInstructionEntry != null) {
							throw new ParsingError("Expecting ENDIF, got an IF instead");
						}
						if (currentInstruction == null) {
							throw new ParsingError(
									"Unexpected IF outside of a DEFINE block");
						}
						// Grab the condition
						Token condition = this.nextTokenWord(i, tokens, "condition",
								true);
						i++;
						currentInstructionEntry = new SimpleInst.InstructionEntry<String>();
						currentInstruction.add(condition.getValue(),
								currentInstructionEntry);
						break;

					case Token.TOKEN_ENDIF:
						if (currentInstructionEntry == null) {
							throw new ParsingError("Got an unexpected ENDIF");
						}
						currentInstructionEntry = null;
						break;

					case Token.TOKEN_GOTO:
						this.checkNullInstEntry(currentInstruction,
								currentInstructionEntry, "GOTO");
						Token target = this.nextTokenWord(i, tokens, "mnemonic");
						i++;

						currentInstructionEntry.setNextInstruction(target.getValue());
						break;

					case Token.TOKEN_MOVE:
						this.checkNullInstEntry(currentInstruction,
								currentInstructionEntry, "MOVE");
						Token direction = this.nextToken(i, tokens, "direction");
						i++;

						if (direction.getType() != Token.TOKEN_LEFT
								&& direction.getType() != Token.TOKEN_RIGHT) {
							throw new ParsingError(String.format(
										"Expecting direction, got %s instead",
										direction.getValue()));
						}
						currentInstructionEntry
							.setNextDirection(((direction.getType() == Token.TOKEN_LEFT) ? Tape.LEFT
										: Tape.RIGHT));
						break;

					case Token.TOKEN_WRITE:
						this.checkNullInstEntry(currentInstruction,
								currentInstructionEntry, "WRITE");
						Token tapeValue = this.nextTokenWord(i, tokens, "symbol", true);
						i++;

						currentInstructionEntry.setNewTapeValue(tapeValue.getValue());
						break;

					case Token.TOKEN_WORD:
						throw new ParsingError("Unexpected token");
					case Token.TOKEN_HALT:
						this.checkNullInstEntry(currentInstruction,
								currentInstructionEntry, "HALT");

						currentInstructionEntry.setNextInstruction(null);
						break;
					default:
						throw new ParsingError("Unknown token encountered");
				}
			}
			// Build the mnemonic map
			instructionSet.buildMnemonicMap();
			return instructionSet;
		}

	/**
	 * Grabs the next word or null
	 *
	 * @param i
	 * @param tokens
	 * @param caller
	 * @param allowNull
	 * @return
	 * @throws ParsingError
	 */
	private Token nextTokenWord(Integer i, ArrayList<Token> tokens,
			String caller, boolean allowNull) throws ParsingError {
		Token newToken = this.nextToken(i, tokens, caller);
		if (!allowNull) {
			if (newToken.getType() != Token.TOKEN_WORD) {
				throw new ParsingError(String.format(
							"Expecting %s, got %s instead", caller,
							newToken.getValue()));
			}
		} else {
			if (!(newToken.getType() == Token.TOKEN_WORD || newToken.getType() == Token.TOKEN_NULL)) {
				throw new ParsingError(String.format(
							"Expecting %s, got %s instead", caller,
							newToken.getValue()));
			}
		}
		return newToken;
	}

	private Token nextToken(Integer i, ArrayList<Token> tokens, String caller)
		throws ParsingError {
			if (++i > tokens.size()) {
				throw new ParsingError(String.format(
							"Expecting %s, got EOF instead", caller));
			}
			return tokens.get(i);
		}

	private Token nextTokenWord(Integer i, ArrayList<Token> tokens,
			String caller) throws ParsingError {

		Token newToken = this.nextToken(i, tokens, caller);
		if (newToken.getType() != Token.TOKEN_WORD) {
			throw new ParsingError(
					String.format("Expecting %s, got %s instead", caller,
						newToken.getValue()));
		}
		return newToken;
	}

	private void checkNullInstEntry(SimpleInst<String> instruction,
			InstructionEntry<String> instructionEntry, String function)
		throws ParsingError {
			if (instruction == null) {
				throw new ParsingError(String.format(
							"Unexpected %s outside of a DEFINE block", function));

			}
			if (instructionEntry == null) {
				throw new ParsingError(String.format(
							"Unexpected %s outside of an IF block", function));

			}
		}
}

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

package com.nick125.thingamabob.machine.core;

import java.util.HashMap;
import java.util.Map;

/**
 * A wrapper around the raw Instruction interface.
 *
 * @author baumbata. Created Oct 20, 2010.
 * @param <T>
 */
public class SimpleInst<T> extends Instruction<T> {
    /**
     * An entry in the instruction.
     *
     * @author kampernj. Created Oct 30, 2010.
     * @param <T>
     */
    public static final class InstructionEntry<T> {
        private String nextInstruction;
        private int nextDirection;
        private T newTapeValue;

        /**
         * Creates an empty instruction entry
         */
        public InstructionEntry() {
            this(null, 0, null);
        }

        /**
         * Creates a new instruction entry.
         *
         * @param nextInstruction
         * @param nextDirection
         * @param newTapeValue
         */
        public InstructionEntry(String nextInstruction, int nextDirection,
                                T newTapeValue) {
            this.nextInstruction = nextInstruction;
            this.nextDirection = nextDirection;
            this.newTapeValue = newTapeValue;
        }

        /**
         * Returns the value of the field called 'nextInstruction'.
         *
         * @return Returns the nextInstruction.
         */
        public String getNextInstruction() {
            return this.nextInstruction;
        }

        /**
         * Sets the field called 'nextInstruction' to the given value.
         *
         * @param nextInstruction The nextInstruction to set.
         */
        public void setNextInstruction(String nextInstruction) {
            this.nextInstruction = nextInstruction;
        }

        /**
         * Returns the value of the field called 'nextDirection'.
         *
         * @return Returns the nextDirection.
         */
        public int getNextDirection() {
            return this.nextDirection;
        }

        /**
         * Sets the field called 'nextDirection' to the given value.
         *
         * @param nextDirection The nextDirection to set.
         */
        public void setNextDirection(int nextDirection) {
            this.nextDirection = nextDirection;
        }

        /**
         * Returns the value of the field called 'newTapeValue'.
         *
         * @return Returns the newTapeValue.
         */
        public T getNewTapeValue() {
            return this.newTapeValue;
        }

        /**
         * Sets the field called 'newTapeValue' to the given value.
         *
         * @param newTapeValue The newTapeValue to set.
         */
        public void setNewTapeValue(T newTapeValue) {
            this.newTapeValue = newTapeValue;
        }

    }

    private Map<T, InstructionEntry<T>> instructions;

    /**
     * Gives the Lines that are in the instruction.
     *
     * @return The map of lines
     */

    public Map<T, InstructionEntry<T>> getLines() {
        return this.instructions;
    }

    /**
     * Creates a simple instruction.
     *
     * @param mnemonic
     */
    public SimpleInst(String mnemonic) {
        this.mnemonic = mnemonic;
        this.instructions = new HashMap<T, InstructionEntry<T>>();
    }

    /**
     * Adds a new instructionEntry to the mapper.
     *
     * @param tapeValue
     * @param instructionEntry
     */
    public void add(T tapeValue, InstructionEntry<T> instructionEntry) {
        this.instructions.put(tapeValue, instructionEntry);
    }

    /**
     * This adds a line to this instruction for a particular symbol.
     *
     * @param tapeValue
     * @param nextValue
     * @param direction
     * @param nextInstruction
     */

    public void addLine(T tapeValue, T nextValue, int direction,
                        String nextInstruction) {
        this.instructions.put(tapeValue, new InstructionEntry<T>(
                nextInstruction, direction, nextValue));
    }

    @Override
    public Instruction<T> operation(Machine<T> machine,
                                    InstructionSet<T> instructionSet) {
        InstructionEntry<T> currentInstruction;
        // Read the current value from the tape
        T value = machine.read();
        // Find the instruction
        if (this.instructions.containsKey(null)) {
            currentInstruction = this.instructions.get(null);
        } else {
            if (value == null) {
                currentInstruction = this.instructions.get("null");
            } else {
                currentInstruction = this.instructions.get(value);
            }
        }
        if (currentInstruction != null) {
            // Do stuff
            if (currentInstruction.newTapeValue != null) {
                if (currentInstruction.newTapeValue.equals("null")) {
                    machine.write(null);
                } else {
                    machine.write(currentInstruction.newTapeValue);
                }
            }
            if (currentInstruction.nextDirection != 0) {
				machine.tickTape(currentInstruction.nextDirection);
			}

			return instructionSet
					.getInstruction(currentInstruction.nextInstruction);
		} else {
			// No instruction..halt the machine
			machine.halt(-1);
			return null;
		}
	}

}

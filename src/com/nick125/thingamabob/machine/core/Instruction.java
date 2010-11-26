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

/**
 * Represents a single instruction.
 *
 * @author kampernj. Created Oct 20, 2010.
 * @param <T>
 * The type of object used as a symbol
 */
public abstract class Instruction<T> {
    /**
     * The Mnemonic for this particular instruction. Used by the InstructionSet
     * to map mnemonics to individual instructions.
     */
    protected String mnemonic;

    /**
     * Returns a mnemonic for this state.
     *
     * @return mnemonic
     */
    public String getMnemonic() {
        return this.mnemonic;
    }

    /**
     * Sets the mnemonic for this state.
     *
     * @param mnemonic The mnemonic to set.
     */
    public void setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
    }

    /**
     * Performs the operation that this particular instruction performs when it
     * is called. It should return the next instruction
     *
     * @param machine
     * @param instructionSet
     * @return next instruction
     */
    public abstract Instruction<T> operation(Machine<T> machine,
			InstructionSet<T> instructionSet);

}

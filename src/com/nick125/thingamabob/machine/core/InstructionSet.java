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
 * An instruction set.
 * <p/>
 * An instruction set stores instructions and allows the machine to access them
 * through getInstruction(index) and getInstruction(mnemonic).
 *
 * @author kampernj, Travis Baumbaugh. Created Oct 20, 2010.
 * @param <T>
 * The type of symbol used in instructions
 */
public interface InstructionSet<T> {

	/**
	 * This accesses the given instruction.
	 *
	 * @param index the index of the instruction to get
	 * @return The instruction that is wanted
	 */
	public Instruction<T> getInstruction(int index);

	/**
	 * This accesses the instruction with the given mnemonic.
	 *
	 * @param mnemonic The mnemonic that is used for the instruction
	 * @return The instruction that is wanted
	 */
	public Instruction<T> getInstruction(String mnemonic);

	/**
	 * Returns the instruction the machine should start on.
	 *
	 * @return instruction
	 */
	public Instruction<T> getDefaultInstruction();

	/**
	 * Returns the number of instructions in the set.
	 *
	 * @return Number of Instructions.
	 */
	public int size();

}

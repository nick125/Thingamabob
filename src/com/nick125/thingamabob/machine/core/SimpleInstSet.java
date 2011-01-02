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

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This contains simple instructions for the machine to use.
 *
 * @author Travis Baumbaugh. Created Oct 24, 2010.
 * @param <T>
 * The type of symbol being used on the tape.
 */
public class SimpleInstSet<T> implements InstructionSet<T> {
	private ArrayList<Instruction<T>> instructions;
	private HashMap<String, Instruction<T>> mnemonicMap;
	private String defaultInstruction;

	/**
	 * Constructs the initial instruction set.
	 */
	public SimpleInstSet() {
		this.instructions = new ArrayList<Instruction<T>>();
		this.mnemonicMap = new HashMap<String, Instruction<T>>();
	}

	/**
	 * Adds an instruction to the set. Remember after adding instructions to the
	 * set to build the Mnemonic map using buildMnemonicMap!
	 *
	 * @param newInstruction The instruction to be added
	 */
	public void addInstruction(Instruction<T> newInstruction) {
		this.instructions.add(newInstruction);
	}

	/**
	 * Rebuilds the Mnemonic mapper. If you fail to call this before starting
	 * execution, mnemonic calls will fail.
	 */
	public void buildMnemonicMap() {
		this.mnemonicMap.clear();
		for (Instruction<T> instruction : this.instructions) {
			this.mnemonicMap.put(instruction.getMnemonic(), instruction);
		}
	}

	@Override
		public Instruction<T> getInstruction(int index) {
			return this.instructions.get(index);
		}

	@Override
		public Instruction<T> getInstruction(String mnemonic) {
			return this.mnemonicMap.get(mnemonic);
		}

	@Override
		public Instruction<T> getDefaultInstruction() {
			if (this.defaultInstruction != null) {
				return this.getInstruction(this.defaultInstruction);
			} else {
				return this.getInstruction(0);
			}
		}

	@Override
		public int size() {
			return this.instructions.size();
		}

	/**
	 * Sets the machine's default instruction.
	 *
	 * @param value
	 */
	public void setDefaultInstruction(String value) {
		this.defaultInstruction = value;
	}

}

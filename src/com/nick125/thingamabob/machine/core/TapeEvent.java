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
 * An event fired by the tape.
 *
 * @author kampernj. Created Oct 29, 2010.
 */
public class TapeEvent {
	//
	public static final int NEW_HEAD = 2;
	public static final int ELEMENT_ADDED = 4;
	public static final int ELEMENT_REMOVED = 8;
	public static final int ELEMENT_MODIFIED = 16;
	//
	private int eventType;
	private Cell caller;

	/**
	 * Creates a new tape event with the given eventType, which should be one of
	 * the constants defined in TapeEvent.
	 *
	 * @param eventType
	 */
	public TapeEvent(int eventType) {
		this.eventType = eventType;
	}

	/**
	 * Creates a new tape event with the given changed Cell and eventType, which
	 * should be one of the constants defined in TapeEvent.
	 *
	 * @param eventType
	 * @param caller
	 */
	public TapeEvent(int eventType, Cell caller) {
		this.eventType = eventType;
		this.caller = caller;
	}

	/**
	 * Returns the value of the field called 'eventType'.
	 *
	 * @return Returns the eventType.
	 */
	public int getEventType() {
		return this.eventType;
	}

	/**
	 * Returns the value of the field called 'caller'.
	 *
	 * @return Returns the caller.
	 */
	public Cell getCaller() {
		return this.caller;
	}

}

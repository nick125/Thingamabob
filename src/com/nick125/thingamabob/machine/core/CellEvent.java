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
 * An object describing a Cell event.
 *
 * @author kampernj. Created Oct 29, 2010.
 */
public class CellEvent {
	/**
	 * The value of the cell has changed.
	 */
	public static final int NEW_VALUE = 2;
	/**
	 * The cell is now the head of the tape.
	 */
	public static final int SET_AS_HEAD = 4;
	/**
	 * The cell is no longer the head of the tape.
	 */
	public static final int NO_LONGER_HEAD = 8;

	private int eventType;
	private Cell<?> caller;

	/**
	 * Creates a new cell event with the given caller and event type. The event
	 * type should be one of the constants defined in CellEvent.
	 *
	 * @param eventType
	 * @param caller
	 */
	public CellEvent(int eventType, Cell<?> caller) {
		this.eventType = eventType;
		this.caller = caller;
	}

	/**
	 * Returns type of the event. This should be one of the CellEvent constants.
	 *
	 * @return Returns the eventType.
	 */
	public int getEventType() {
		return this.eventType;
	}

	/**
	 * Returns the cell that generated this event.
	 *
	 * @return Returns the caller.
	 */
	public Cell<?> getCaller() {
		return this.caller;
	}

}

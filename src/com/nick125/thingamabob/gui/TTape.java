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

package com.nick125.thingamabob.gui;

import com.nick125.thingamabob.machine.core.Cell;
import com.nick125.thingamabob.machine.core.Tape;
import com.nick125.thingamabob.machine.core.TapeEvent;
import com.nick125.thingamabob.machine.core.TapeListener;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.HashMap;

/**
 * Represents the Tape in a GUI element.
 *
 * @author kampernj. Created Oct 26, 2010.
 * @param <T>
 */
public class TTape<T> extends JPanel implements TapeListener {

	private Tape<T> tape;
	private HashMap<Cell<T>, TCell<T>> cellMap;

	/**
	 * Creates a new tape display
	 *
	 * @param tape
	 */
	public TTape(Tape<T> tape) {
		super();
		// Set a nice border
		this.setBorder(BorderFactory.createTitledBorder(
					BorderFactory.createLineBorder(Color.black), "Turing Tape"));

		// Reset the TTape and then set the tape.
		this.resetTTape();
		this.setTape(tape);

		// Set the tape as visible
		this.setVisible(true);
	}

	/**
	 * Creates a TTape GUI element without a tape set.
	 */
	public TTape() {
		this(null);
	}

	/**
	 * Sets the tape that the TTape uses. Note that this expects the tape to be
	 * clean. Call resetTTape() before calling setTape.
	 *
	 * @param tape
	 */
	public void setTape(Tape<T> tape) {
		// Deregister the old tape.
		if (this.tape != null) {
			this.tape.removeTapeListener(this);
		}
		// Set the new tape

		this.tape = tape;
		if (this.tape != null) {
			// Register with the new tape
			this.tape.addTapeListener(this);
			// Generate the current state of the tape;
			for (Cell<T> cell : this.tape) {
				this.add(cell);
			}
		}
	}

	/**
	 * Resets the TTape
	 *
	 * @param tape
	 */
	public void resetTTape() {
		this.removeAll();
		this.cellMap = new HashMap<Cell<T>, TCell<T>>();
	}

	/**
	 * Adds a new cell to the tape
	 *
	 * @param newCell
	 */
	public void add(Cell<T> newCell) {
		TCell<T> newTCell = new TCell<T>(newCell);
		this.cellMap.put(newCell, newTCell);
		this.add(newTCell);
	}

	/**
	 * Adds a new cell to the tape at a particular index.
	 *
	 * @param newCell
	 * @param index
	 */
	public void add(Cell<T> newCell, int index) {
		TCell<T> newTCell = new TCell<T>(newCell);
		this.cellMap.put(newCell, newTCell);
		this.add(newTCell, index);
	}

	/**
	 * Removes a cell from the tape
	 *
	 * @param toRemove
	 */
	public void remove(Cell<T> toRemove) {
		TCell<T> TCToRemove = this.cellMap.get(toRemove);
		if (TCToRemove != null) {
			this.remove(this.cellMap.get(toRemove));
			this.cellMap.remove(toRemove);
		}
	}

	@Override
		public void tapeEvent(TapeEvent event) {
			if (event.getEventType() == TapeEvent.ELEMENT_ADDED) {
				this.add(event.getCaller(), this.tape.indexOf(event.getCaller()));
			} else if (event.getEventType() == TapeEvent.ELEMENT_REMOVED) {
				this.remove((event.getCaller()));
			}
		}
}

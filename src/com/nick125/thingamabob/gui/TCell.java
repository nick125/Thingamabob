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
import com.nick125.thingamabob.machine.core.CellEvent;
import com.nick125.thingamabob.machine.core.CellListener;

import javax.swing.*;
import java.awt.*;

/**
 * A GUI cell representation
 *
 * @author kampernj. Created Oct 26, 2010.
 * @param <T>
 */

public class TCell<T> extends JPanel implements CellListener {
	private Cell<T> parentCell;
	private JLabel cellValue;
	private boolean isHead = false;

	/* Constants */
	private static final int CELL_WIDTH = 40; // px;
	private static final int CELL_HEIGHT = 40; // px;
	private static final Color NULL_COLOR = Color.LIGHT_GRAY;
	private static final Color DEFAULT_COLOR = Color.WHITE;
	private static final Color HEAD_COLOR = Color.ORANGE;

	/**
	 * Creates a new Cell GUI
	 *
	 * @param parentCell
	 */
	public TCell(Cell<T> parentCell) {
		super();
		// Set the parent of this cell
		this.parentCell = parentCell;
		// Construct the element
		this.setPreferredSize(new Dimension(CELL_WIDTH, CELL_HEIGHT));
		this.cellValue = new JLabel();
		this.add(this.cellValue);
		// Set ourself as the parentCell's event handler
		this.parentCell.addCellListener(this);
		// Set the element as visible
		this.setVisible(true);
	}

	@Override
		public void cellEvent(CellEvent event) {
			T value;

			switch (event.getEventType()) {
				case CellEvent.NEW_VALUE:
					value = this.parentCell.getValue();
					this.cellValue.setText((value == null) ? " " : value.toString());
					this.setBackground(this.isHead ? HEAD_COLOR : (value == null) ? NULL_COLOR : DEFAULT_COLOR);
					break;
				case CellEvent.SET_AS_HEAD:
					this.isHead = true;
					this.setBackground(HEAD_COLOR);
					break;
				case CellEvent.NO_LONGER_HEAD:
					value = this.parentCell.getValue();
					this.isHead = false;
					this.setBackground((value == null) ? NULL_COLOR : DEFAULT_COLOR);
					break;
				default:
					break;
			}
			// Force a repaint
			this.repaint();
		}

}

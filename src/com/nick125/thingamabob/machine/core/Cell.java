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

/**
 * Represents a single cell.
 *
 * @author Nicholas Kamper, Drew Hill, Travis Baumbaugh Created Oct 20, 2010.
 * @param <T>
 * Type of the data being stored in the cell
 */
public class Cell<T> {
    private T data;
    // Event framework
    private ArrayList<CellListener> listeners;

    /**
     * Creates a new cell with no initial value.
     */
    public Cell() {
        this(null);
    }

    /**
     * Creates a new cell with the given initial value
     *
     * @param data
     */
    public Cell(T data) {
        this.data = data;
        this.listeners = new ArrayList<CellListener>();
    }

    /**
     * Returns the value of this cell.
     *
     * @return The data contained in this cell.
     */
    public T getValue() {
        return this.data;
    }

    /**
     * Sets the value of the cell.
     *
     * @param value
     */
    public void setValue(T value) {
        this.data = value;
        this.notifyHandlers(new CellEvent(CellEvent.NEW_VALUE, this));
    }

    /**
     * Notifies all of the registered handlers about an event that has occurred
     * with the cell.
     *
     * @param event
     */
    private void notifyHandlers(CellEvent event) {
        for (CellListener listener : this.listeners) {
            listener.cellEvent(event);
        }
    }

    /**
     * Adds a CellListener to listen to events from this cell.
     *
     * @param listener
     */
    public void addCellListener(CellListener listener) {
        this.listeners.add(listener);
    }

    /**
     * Removes the given CellListener from getting event notification from this
     * cell.
     *
     * @param listener
     */
    public void removeCellListener(CellListener listener) {
        this.listeners.remove(listener);
    }

    /**
     * Fires an event saying this cell has been set as the head.
     */
    public void fireHeadEvent() {
        this.notifyHandlers(new CellEvent(CellEvent.SET_AS_HEAD, this));
    }

    /**
     * Fires an event saying this cell has been unset as the head.
     */
    public void fireNotHeadEvent() {
        this.notifyHandlers(new CellEvent(CellEvent.NO_LONGER_HEAD, this));
    }

    @Override
    public String toString() {
		if (this.data != null) {
			return this.data.toString();
		}
		return "null";
	}

}

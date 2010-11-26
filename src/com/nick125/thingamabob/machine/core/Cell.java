package simulation.machine.core;

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

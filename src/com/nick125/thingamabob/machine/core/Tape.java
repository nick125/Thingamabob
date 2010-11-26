package simulation.machine.core;

import java.util.ArrayList;

/**
 * Represents the tape on a turing machine
 *
 * @author kampernj. Created Oct 20, 2010.
 * @param <T>
 * type
 */
public class Tape<T> extends ArrayList<Cell<T>> {
    /**
     * Move to the left
     */
    public static final int LEFT = -1;
    /**
     * Move to the right
     */
    public static final int RIGHT = 1;
    /* Operational settings */
    // Whether we should create new objects when we encounter a null.
    private boolean AddNewOnNull = true;
    // What we should add to the cells as the default value.
    private T defaultNew = null;
    private int headPosition;
    private ArrayList<TapeListener> listeners;

    /**
     * Creates a fresh new tape.
     */
    public Tape() {
        this(null);
    }

    /**
     * Creates a fresh tape that will have default values.
     *
     * @param defaultNew the default value for new cells.
     */
    public Tape(T defaultNew) {
        super();
        this.defaultNew = defaultNew;
        this.listeners = new ArrayList<TapeListener>();
    }

    private void notifyListeners(TapeEvent event) {
        for (TapeListener listener : this.listeners) {
            listener.tapeEvent(event);
        }
    }

    /**
     * Appends a new cell in a position specified by the position integer, which
     * should be one of the constants Tape.LEFT or Tape.RIGHT based on the given
     * position. If there is no current cell, this will create a new cell and
     * set the current head position to that cell. Note that this does not
     * change the position of the head to the newly created cell if the current
     * head is set. If this is desired behavior, you will have to call either a
     * .previous() or .next() after appending the value.
     *
     * @param value
     * @param position
     */
    public synchronized void append(T value, int position) {
        if (this.size() == 0 || this.headPosition < 0) {
            Cell<T> newCell = new Cell<T>(value);
            this.add(newCell);
            this.setHead(this.indexOf(newCell));
        } else {
            Cell<T> newCell = new Cell<T>(value);
            this.add(this.headPosition + position, newCell);
        }
    }

    /**
     * Returns the value stored in the current head cell. If there is no current
     * cell, this will return null.
     *
     * @return current head value
     */
    public synchronized T read() {
        if (this.headPosition < 0 || this.size() == 0) {
            return null;
        } else {
            return this.get(this.headPosition).getValue();
        }
    }

    /**
     * Writes the given value to the tape.
     *
     * @param newValue The value to write
     */
    public synchronized void write(T newValue) {
        if (this.size() == 0 || this.headPosition < 0) {
            Cell<T> newCell = new Cell<T>();
            this.add(newCell);
            newCell.setValue(newValue);
            this.setHead(this.indexOf(newCell));
        } else {
            this.get(this.headPosition).setValue(newValue);
        }
    }

    @Override
    public synchronized boolean add(Cell<T> e) {
        boolean toReturn = super.add(e);
        this.notifyListeners(new TapeEvent(TapeEvent.ELEMENT_ADDED, e));
        return toReturn;
    }

    @Override
    public synchronized void add(int index, Cell<T> element) {
        super.add(index, element);
        this.notifyListeners(new TapeEvent(TapeEvent.ELEMENT_ADDED, element));
    }

    @Override
    public synchronized Cell<T> remove(int index) {
        Cell<T> toReturn = super.remove(index);
        if (toReturn != null) {
            this.notifyListeners(new TapeEvent(TapeEvent.ELEMENT_REMOVED,
                    toReturn));
        }
        return toReturn;
    }

    @Override
    public synchronized boolean remove(Object o) {
        boolean toReturn = super.remove(o);
        if (toReturn) {
            if (o instanceof Cell<?>) {
                this.notifyListeners(new TapeEvent(TapeEvent.ELEMENT_REMOVED,
                        (Cell<?>) o));
            }
        }
        return toReturn;
    }

    @Override
    public synchronized Cell<T> set(int index, Cell<T> element) {
        // TODO Auto-generated method stub.
        Cell<T> toRemove = super.set(index, element);
        if (toRemove != null) {
            this.notifyListeners(new TapeEvent(TapeEvent.ELEMENT_REMOVED,
                    toRemove));
        }
        this.notifyListeners(new TapeEvent(TapeEvent.ELEMENT_ADDED, element));
        return toRemove;
    }

    /**
     * Ticks the position of the head to the left or the right based on the
     * position given, which should be one of the constants Tape.LEFT or
     * Tape.RIGHT.
     *
     * @param position
     */
    public synchronized void tick(int position) {
        if (this.headPosition < 0 || this.size() == 0) {
            Cell<T> newCell = new Cell<T>();
            this.add(newCell);
            newCell.setValue(this.defaultNew);
            this.setHead(this.indexOf(newCell));
        } else {
            int nHeadPosition = this.headPosition + position;
            if (0 > nHeadPosition || nHeadPosition > (this.size() - 1)) {
                // Check if we can add null;
                if (this.AddNewOnNull) {
                    Cell<T> newCell = new Cell<T>(this.defaultNew);
                    this.add((nHeadPosition < 0) ? 0 : nHeadPosition, newCell);
                    if (this.headPosition > nHeadPosition) {
                        this.headPosition += 1;
                    }
                    this.setHead(this.indexOf(newCell));
                } else {
                    // Undo the headPosition change
                    this.setHead(nHeadPosition - position);
                }
            } else {
                this.setHead(nHeadPosition);
            }
        }
    }

    private synchronized void setHead(int position) {
        try {
            Cell<T> oldHead = this.get(this.headPosition);
            oldHead.fireNotHeadEvent();
        } catch (IndexOutOfBoundsException e) {
            // ignore it
        }
        this.notifyListeners(new TapeEvent(TapeEvent.NEW_HEAD));
        this.headPosition = position;
        try {
            Cell<T> newHead = this.get(this.headPosition);
            newHead.fireHeadEvent();
        } catch (IndexOutOfBoundsException e) {
            // ignore it
        }
    }

    /**
     * Loads the value of a string into the tape after clearing the tape.
     * Introduces a delay into each tape operation.
     *
     * @param value
     * @param delay
     */
    public synchronized void loadFromString(String value, int delay) {
        // Reset the tape
        this.clear();
        // Load each character from the string
        int cellHead = 0;
        int cellCount = 0;
        for (int i = 0; i < value.length(); i++) {
            //
            String character = Character.valueOf(value.charAt(i)).toString();
            if (character.equals("[")) {
                cellHead = cellCount + 1;
            } else if (character.equals("]")) {
                // Do nothing
            } else {
                try {
                    this.tick(Tape.RIGHT);
                    this.write((T) character);
                    cellCount++;
                    Thread.sleep(1000 / delay);
                } catch (InterruptedException exception) {
                    // Ignore
                }
            }
        }
        // Now tick back to the head
        for (int i = this.headPosition; (i + 1) > cellHead; i--) {
            try {
                this.tick(Tape.LEFT);
                Thread.sleep(1000 / delay);
            } catch (InterruptedException exception) {
                // Ignore
            }
        }
    }

    @Override
    public synchronized void clear() {
        while (!this.isEmpty()) {
            this.remove(0);
        }
    }

    /**
     * Registers a class to receive events from this tape.
     *
     * @param listener
     */
    public void addTapeListener(TapeListener listener) {
        this.listeners.add(listener);
    }

    /**
     * Removes a listener from receving events from this tape.
     *
     * @param listener
     */
	public void removeTapeListener(TapeListener listener) {
		this.listeners.remove(listener);
	}
}

package simulation.machine.core;

/**
 * An object describing a Cell event.
 *
 * @author kampernj. Created Oct 29, 2010.
 */
public class CellEvent {
    //
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

    //
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

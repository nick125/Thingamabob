package simulation.machine.core;

/**
 * TODO Put here a description of what this class does.
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

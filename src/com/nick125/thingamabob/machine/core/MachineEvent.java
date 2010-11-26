package simulation.machine.core;

/**
 * TODO Put here a description of what this class does.
 *
 * @author kampernj. Created Oct 29, 2010.
 */
public class MachineEvent {
    // Event types
    /**
     * Fired when the machine starts running.
     */
    public static final int MACHINE_STATE_RUNNING = 2;
    /**
     * Fired when the machine has halted
     */
    public static final int MACHINE_STATE_HALTED = 4;
    /**
     * Fired when the machine has thrown an error
     */
    public static final int MACHINE_ERROR = 8;
    /**
     * Fired when the machine performs an operation.
     */
    public static final int MACHINE_OPERATION = 16;
    //
    private int eventType;
    private Object argument;

    /**
     * Creates a new MachineEvent with the given event type
     *
     * @param eventType
     */
    public MachineEvent(int eventType) {
        this(eventType, null);
    }

    /**
     * Creates a new MachineEvent with the given event type and argument
     *
     * @param eventType
     * @param argument
     */
    public MachineEvent(int eventType, Object argument) {
        this.eventType = eventType;
        this.argument = argument;
    }

    /**
     * Returns the value of the field called 'argument'.
     *
     * @return Returns the argument.
     */
    public Object getArgument() {
        return this.argument;
    }

    /**
     * Returns the value of the field called 'eventType'.
     *
     * @return Returns the eventType.
     */
    public int getEventType() {
		return this.eventType;
	}
}

package simulation.machine.core;

/**
 * TODO Put here a description of what this class does.
 *
 * @author kampernj. Created Oct 29, 2010.
 */
public interface MachineListener {
    /**
     * Handles an event from the machine.
     *
     * @param event
     */
    public void machineEvent(MachineEvent event);
}

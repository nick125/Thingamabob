package simulation.machine.core;

/**
 * TODO Put here a description of what this class does.
 *
 * @author kampernj. Created Oct 29, 2010.
 */
public interface TapeListener {
    /**
     * Handles a tape event
     *
     * @param event
     */
    public void tapeEvent(TapeEvent event);
}

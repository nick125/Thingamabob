package simulation.machine.core;

/**
 * A class that can listen to Cell events.
 *
 * @author kampernj. Created Oct 29, 2010.
 */
public interface CellListener {
    /**
     * Handles a cell event
     *
     * @param event
     */
    public void cellEvent(CellEvent event);
}

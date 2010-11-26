package simulation.gui;

import simulation.machine.core.Cell;
import simulation.machine.core.Tape;
import simulation.machine.core.TapeEvent;
import simulation.machine.core.TapeListener;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.HashMap;

/**
 * Represents the Tape in a GUI element.
 *
 * @author kampernj. Created Oct 26, 2010.
 * @param <T>
 */
public class TTape<T> extends JPanel implements TapeListener {

    private Tape<T> tape;
    private HashMap<Cell<T>, TCell<T>> cellMap;

    /**
     * Creates a new tape display
     *
     * @param tape
     */
    public TTape(Tape<T> tape) {
        super();
        // Set a nice border
        this.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.black), "Turing Tape"));

        // Reset the TTape and then set the tape.
        this.resetTTape();
        this.setTape(tape);
        // Set visible
        this.setVisible(true);
    }

    /**
     * Creates a TTape GUI element without a tape set.
     */
    public TTape() {
        this(null);
    }

    /**
     * Sets the tape that the TTape uses. Note that this expects the tape to be
     * clean. Call resetTTape() before calling setTape.
     *
     * @param tape
     */
    public void setTape(Tape<T> tape) {
        // Deregister this as a tape listener
        if (this.tape != null) {
            this.tape.removeTapeListener(this);
        }
        this.tape = tape;
        if (this.tape != null) {
            // Register with the new tape
            this.tape.addTapeListener(this);
            // Generate the current state of the tape;
            for (Cell<T> cell : this.tape) {
                this.add(cell);
            }
        }
    }

    /**
     * Resets the TTape
     *
     * @param tape
     */
    public void resetTTape() {
        this.removeAll();
        this.cellMap = new HashMap<Cell<T>, TCell<T>>();
    }

    /**
     * Adds a new cell to the tape
     *
     * @param newCell
     */
    public void add(Cell<T> newCell) {
        TCell<T> newTCell = new TCell<T>(newCell);
        this.cellMap.put(newCell, newTCell);
        this.add(newTCell);
    }

    /**
     * Adds a new cell to the tape at a particular index.
     *
     * @param newCell
     * @param index
     */
    public void add(Cell<T> newCell, int index) {
        TCell<T> newTCell = new TCell<T>(newCell);
        this.cellMap.put(newCell, newTCell);
        this.add(newTCell, index);
    }

    /**
     * Removes a cell from the tape
     *
     * @param toRemove
     */
    public void remove(Cell<T> toRemove) {
        TCell<T> TCToRemove = this.cellMap.get(toRemove);
        if (TCToRemove != null) {
            this.remove(this.cellMap.get(toRemove));
            this.cellMap.remove(toRemove);
        }
    }

    @Override
    public void tapeEvent(TapeEvent event) {
        if (event.getEventType() == TapeEvent.ELEMENT_ADDED) {
            this.add(event.getCaller(), this.tape.indexOf(event.getCaller()));
            // this.validate();
        } else if (event.getEventType() == TapeEvent.ELEMENT_REMOVED) {
			this.remove((event.getCaller()));
		}
	}
}

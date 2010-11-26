package simulation.gui;

import simulation.machine.core.Cell;
import simulation.machine.core.CellEvent;
import simulation.machine.core.CellListener;

import javax.swing.*;
import java.awt.*;

/**
 * A GUI cell representation
 *
 * @author kampernj. Created Oct 26, 2010.
 * @param <T>
 */
public class TCell<T> extends JPanel implements CellListener {
    private Cell<T> parentCell;
    private JLabel cellValue;
    private boolean isHead = false;
    //
    private static final int CELL_WIDTH = 40; // px;
    private static final int CELL_HEIGHT = 40; // px;
    private static final Color NULL_COLOR = Color.LIGHT_GRAY;
    private static final Color DEFAULT_COLOR = Color.WHITE;
    private static final Color HEAD_COLOR = Color.ORANGE;

    /**
     * Creates a new Cell GUI
     *
     * @param parentCell
     */
    public TCell(Cell<T> parentCell) {
        super();
        this.parentCell = parentCell;
        // Construct the element
        this.setPreferredSize(new Dimension(CELL_WIDTH, CELL_HEIGHT));
        this.cellValue = new JLabel();
        this.add(this.cellValue);
        // Set ourself as the parentCell's event handler
        this.parentCell.addCellListener(this);
        // Set visible
        this.setVisible(true);
    }

    @Override
    public void cellEvent(CellEvent event) {
        if (event.getEventType() == CellEvent.NEW_VALUE) {
            if (this.parentCell.getValue() == null) {
                if (!this.isHead) {
                    this.setBackground(NULL_COLOR);
                }
                this.cellValue.setText("");
            } else {
                if (!this.isHead) {
                    this.setBackground(DEFAULT_COLOR);
                }
                this.cellValue.setText(this.parentCell.getValue().toString());
            }
        } else if (event.getEventType() == CellEvent.SET_AS_HEAD) {
            this.isHead = true;
            this.setBackground(HEAD_COLOR);
        } else if (event.getEventType() == CellEvent.NO_LONGER_HEAD) {
            this.isHead = false;
            if (this.parentCell.getValue() == null) {
                this.setBackground(NULL_COLOR);
            } else {
                this.setBackground(DEFAULT_COLOR);
            }
        }
        this.repaint();
	}

}

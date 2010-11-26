package simulation.gui;

import javax.swing.*;
import java.awt.*;

/**
 * This is a panel which contains the components necessary for a line.
 *
 * @author Travis Baumbaugh.
 *         Created October 27, 2010.
 */
public class LinePanel extends JPanel {
    private static final Dimension SIZE = new Dimension(330, 35);
    private JTextField mnemonic;
    private JTextField symbolEncountered;
    private JTextField symbolWrite;
    private JComboBox nextDirection;
    private JTextField nextInstruction;

    /**
     * Creates the actual panel.
     */
    public LinePanel() {
        this.setPreferredSize(LinePanel.SIZE);
        this.setBackground(Color.GREEN);
        this.setLayout(new FlowLayout());
        this.mnemonic = new JTextField("Mnemonic");
        this.symbolEncountered = new JTextField("Symbol");
        this.symbolWrite = new JTextField("Mnemonic");
        String[] choiceString = {"N", "L", "R"};
        this.nextDirection = new JComboBox(choiceString);
        this.nextInstruction = new JTextField("Instruction");
        this.add(this.mnemonic);
        this.add(this.symbolEncountered);
        this.add(this.symbolWrite);
        this.add(this.nextDirection);
        this.add(this.nextInstruction);
        this.setVisible(true);
    }

    /**
     * Returns the value of the field called 'mnemonic'.
     *
     * @return Returns the mnemonic.
     */
    public JTextField getMnemonic() {
        return this.mnemonic;
    }

    /**
     * Sets the field called 'mnemonic' to the given value.
     *
     * @param mnemonic The mnemonic to set.
     */
    public void setMnemonic(JTextField mnemonic) {
        this.mnemonic = mnemonic;
    }

    /**
     * Returns the value of the field called 'symbolEncountered'.
     *
     * @return Returns the symbolEncountered.
     */
    public JTextField getSymbolEncountered() {
        return this.symbolEncountered;
    }

    /**
     * Sets the field called 'symbolEncountered' to the given value.
     *
     * @param symbolEncountered The symbolEncountered to set.
     */
    public void setSymbolEncountered(JTextField symbolEncountered) {
        this.symbolEncountered = symbolEncountered;
    }

    /**
     * Returns the value of the field called 'symbolWrite'.
     *
     * @return Returns the symbolWrite.
     */
    public JTextField getSymbolWrite() {
        return this.symbolWrite;
    }

    /**
     * Sets the field called 'symbolWrite' to the given value.
     *
     * @param symbolWrite The symbolWrite to set.
     */
    public void setSymbolWrite(JTextField symbolWrite) {
        this.symbolWrite = symbolWrite;
    }

    /**
     * Returns the value of the field called 'nextDirection'.
     *
     * @return Returns the nextDirection.
     */
    public JComboBox getNextDirection() {
        return this.nextDirection;
    }

    /**
     * Sets the field called 'nextDirection' to the given value.
     *
     * @param nextDirection The nextDirection to set.
     */
    public void setNextDirection(JComboBox nextDirection) {
        this.nextDirection = nextDirection;
    }

    /**
     * Returns the value of the field called 'nextInstruction'.
     *
     * @return Returns the nextInstruction.
     */
    public JTextField getNextInstruction() {
        return this.nextInstruction;
    }

    /**
     * Sets the field called 'nextInstruction' to the given value.
     *
     * @param nextInstruction The nextInstruction to set.
     */
	public void setNextInstruction(JTextField nextInstruction) {
		this.nextInstruction = nextInstruction;
	}
}

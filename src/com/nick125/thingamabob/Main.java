package simulation;

import simulation.gui.TWindow;

import javax.swing.*;

/**
 * This runs the stuff.
 *
 * @author Nick Kamper, Travis Baumbaugh, Drew Hill - Created Oct 11, 2010.
 */
public class Main {

    /**
     * Starts here.
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            // Set the native L&F as default..
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Yes, I know this is bad..but if this fails, I don't particularly
            // care. It should remain at Metal if this fails.
        }

        TWindow myWindow = new TWindow();
        myWindow.setVisible(true);
	}

}

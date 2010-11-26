package simulation.gui;

import simulation.machine.core.MachineEvent;
import simulation.machine.core.MachineListener;

import javax.swing.*;
import java.awt.*;

/**
 * A simple status bar
 *
 * @author kampernj. Created Oct 31, 2010.
 */
public class TStatusBar extends JPanel implements MachineListener {
    private JLabel machineStatus;
    private JLabel notificationArea;

    /**
     * Creates a new status bar
     */
    public TStatusBar() {
        this.machineStatus = new JLabel("Machine: ");
        this.notificationArea = new JLabel();
        this.setLayout(new GridLayout(1, 2));
        this.add(this.machineStatus);
        this.add(this.notificationArea);
    }

    /**
     * Sets the value of the machine portion of the status bar
     *
     * @param value
     */
    public void setMachineValue(final String value) {
        if (SwingUtilities.isEventDispatchThread()) {
            this.machineStatus.setText(String.format("Machine: %s", value));
        } else {
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    TStatusBar.this.machineStatus.setText(String.format(
                            "Machine: %s", value));
                }
            });
        }
    }

    /**
     * Sets the value of the notificaiton area of the status bar.
     *
     * @param value
     */
    public void setNotificationArea(final String value) {
        if (SwingUtilities.isEventDispatchThread()) {
            this.notificationArea.setText(value);
        } else {
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    TStatusBar.this.notificationArea.setText(value);
                }
            });
        }
    }

    @Override
    public void machineEvent(MachineEvent event) {
        if (event.getEventType() == MachineEvent.MACHINE_STATE_HALTED) {
            this.setMachineValue("Halted");
        } else if (event.getEventType() == MachineEvent.MACHINE_STATE_RUNNING) {
            this.setMachineValue("Running");
        } else if (event.getEventType() == MachineEvent.MACHINE_ERROR) {
            this.setMachineValue("Error");
		}
	}
}

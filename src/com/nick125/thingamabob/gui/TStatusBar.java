/*
 * Thingamabob - A Java-based Turing Machine Emulator
 * Copyright (c) 2010 Nicholas Kamper, Drew Hill, Travis Baumbaugh
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */

package com.nick125.thingamabob.gui;

import com.nick125.thingamabob.machine.core.MachineEvent;
import com.nick125.thingamabob.machine.core.MachineListener;

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
			switch (event.getEventType()) {
				case MachineEvent.MACHINE_STATE_HALTED:
					this.setMachineValue("Halted");
					break;
				case MachineEvent.MACHINE_STATE_RUNNING:
					this.setMachineValue("Running");
					break;
				case MachineEvent.MACHINE_ERROR:
					this.setMachineValue("Error");
					break;
				default:
					this.setMachineValue(" ");
					break;
			}
		}
}

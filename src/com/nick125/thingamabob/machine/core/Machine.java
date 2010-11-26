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

package com.nick125.thingamabob.machine.core;

import java.util.ArrayList;

/**
 * The machine "glue" code. It glues together the tape and instruction set.
 *
 * @author kampernj. Created Oct 20, 2010.
 * @param <T>
 * The type of symbol being used for the program
 */
public class Machine<T> {
    private Tape<T> tape;
    private InstructionSet<T> instructionSet;
    private Instruction<T> currentInstruction;
    private boolean running;
    private ArrayList<MachineListener> listeners;

    /**
     * Creates a new machine
     *
     * @param instructionSet
     * @param tape
     */
    public Machine(InstructionSet<T> instructionSet, Tape<T> tape) {
        this.tape = tape;
        this.instructionSet = instructionSet;
        this.listeners = new ArrayList<MachineListener>();
        if (this.instructionSet != null) {
            this.currentInstruction = instructionSet.getDefaultInstruction();
        }
    }

    /**
     * Creates a "blank" machine.
     */
    public Machine() {
        this(null, null);
    }

    /**
     * Performs a single execution. Note that this does not check if we're
     * running or not before executing -- that's YOUR responsibility.
     */
    public void execute() {
        if (this.currentInstruction != null) {
            // Execute the instruction
            this.currentInstruction = this.currentInstruction.operation(this,
                    this.instructionSet);
        } else {
            this.halt(0);
        }
    }

    /**
     * Halts the machine with the given status (negative means an error
     * occurred)
     *
     * @param status
     */
    public void halt(int status) {
        this.setState(false);
        if (status < 0) {
            this.notifyListeners(new MachineEvent(MachineEvent.MACHINE_ERROR));
        }
    }

    /**
     * Halts the machine with the given status (negative means an error
     * occurred) and a message, potentially describing the error condition.
     *
     * @param status
     * @param message
     */
    public void halt(int status, String message) {
        this.setState(false);
        if (status < 0) {
            this.notifyListeners(new MachineEvent(MachineEvent.MACHINE_ERROR,
                    message));
        }

    }

    /**
     * Emits an event to all registered listeners
     *
     * @param machineEvent
     */
    private void notifyListeners(MachineEvent machineEvent) {
        for (MachineListener listener : this.listeners) {
            listener.machineEvent(machineEvent);
        }
    }

    /**
     * Adds an event listener to this machine.
     *
     * @param listener
     */
    public void addEventListener(MachineListener listener) {
        this.listeners.add(listener);
    }

    /**
     * Removes an event listener from receiving events from this machine.
     *
     * @param listener
     */
    public void removeEventListener(MachineListener listener) {
        this.listeners.remove(listener);
    }

    /**
     * Returns the value of the field called 'tape'.
     *
     * @return Returns the tape.
     */
    public Tape<T> getTape() {
        return this.tape;
    }

    /**
     * Sets the field called 'tape' to the given value.
     *
     * @param tape The tape to set.
     */
    public void setTape(Tape<T> tape) {
        this.tape = tape;
    }

    /**
     * Returns the value of the field called 'instructionSet'.
     *
     * @return Returns the instructionSet.
     */
    public InstructionSet<T> getInstructionSet() {
        return this.instructionSet;
    }

    /**
     * Sets the instruction set of the machine.
     *
     * @param instructionSet The instructionSet to set.
     */
    public void setInstructionSet(InstructionSet<T> instructionSet) {
        this.instructionSet = instructionSet;
        this.currentInstruction = instructionSet.getDefaultInstruction();
    }

    /**
     * Returns whether the machine is still running.
     *
     * @return boolean whether the machine is still running.
     */
    public boolean isRunning() {
        return this.running;
    }

    /**
     * Reads the symbol off of the tape.
     *
     * @return The symbol on the tape
     */
    public T read() {
        return this.tape.read();
    }

    /**
     * Writes the given symbol to the tape.
     *
     * @param newSymbol The new symbol to write
     */
    public void write(T newSymbol) {
        this.tape.write(newSymbol);
    }

    /**
     * Moves the tape over to the given direction.
     *
     * @param direction The direction to move the tape. Should be either Tape.LEFT or
     *                  Tape.RIGHT.
     */
    public void tickTape(int direction) {
        this.tape.tick(direction);
    }

    /**
     * Sets whether the machine is running or not.
     *
     * @param state
     */
    public void setState(boolean state) {
        this.running = state;
        if (this.running) {
            this.notifyListeners(new MachineEvent(
                    MachineEvent.MACHINE_STATE_RUNNING));
        } else {
            this.notifyListeners(new MachineEvent(
                    MachineEvent.MACHINE_STATE_HALTED));
		}
	}

	/**
     * Resets the machine.
     */
	public void reset() {
		if (this.instructionSet != null) {
			this.currentInstruction = this.instructionSet
					.getDefaultInstruction();
		}
	}
}

package simulation.machine.core;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO Put here a description of what this class does.
 *
 * @author baumbata. Created Oct 20, 2010.
 * @param <T>
 */
public class SimpleInst<T> extends Instruction<T> {
    /**
     * An entry in the instruction.
     *
     * @author kampernj. Created Oct 30, 2010.
     * @param <T>
     */
    public static final class InstructionEntry<T> {
        private String nextInstruction;
        private int nextDirection;
        private T newTapeValue;

        /**
         * Creates an empty instruction entry
         */
        public InstructionEntry() {
            this(null, 0, null);
        }

        /**
         * Creates a new instruction entry.
         *
         * @param nextInstruction
         * @param nextDirection
         * @param newTapeValue
         */
        public InstructionEntry(String nextInstruction, int nextDirection,
                                T newTapeValue) {
            this.nextInstruction = nextInstruction;
            this.nextDirection = nextDirection;
            this.newTapeValue = newTapeValue;
        }

        /**
         * Returns the value of the field called 'nextInstruction'.
         *
         * @return Returns the nextInstruction.
         */
        public String getNextInstruction() {
            return this.nextInstruction;
        }

        /**
         * Sets the field called 'nextInstruction' to the given value.
         *
         * @param nextInstruction The nextInstruction to set.
         */
        public void setNextInstruction(String nextInstruction) {
            this.nextInstruction = nextInstruction;
        }

        /**
         * Returns the value of the field called 'nextDirection'.
         *
         * @return Returns the nextDirection.
         */
        public int getNextDirection() {
            return this.nextDirection;
        }

        /**
         * Sets the field called 'nextDirection' to the given value.
         *
         * @param nextDirection The nextDirection to set.
         */
        public void setNextDirection(int nextDirection) {
            this.nextDirection = nextDirection;
        }

        /**
         * Returns the value of the field called 'newTapeValue'.
         *
         * @return Returns the newTapeValue.
         */
        public T getNewTapeValue() {
            return this.newTapeValue;
        }

        /**
         * Sets the field called 'newTapeValue' to the given value.
         *
         * @param newTapeValue The newTapeValue to set.
         */
        public void setNewTapeValue(T newTapeValue) {
            this.newTapeValue = newTapeValue;
        }

    }

    private Map<T, InstructionEntry<T>> instructions;

    /**
     * Gives the Lines that are in the instruction.
     *
     * @return The map of lines
     */

    public Map<T, InstructionEntry<T>> getLines() {
        return this.instructions;
    }

    /**
     * Creates a simple instruction.
     *
     * @param mnemonic
     */
    public SimpleInst(String mnemonic) {
        this.mnemonic = mnemonic;
        this.instructions = new HashMap<T, InstructionEntry<T>>();
    }

    /**
     * Adds a new instructionEntry to the mapper.
     *
     * @param tapeValue
     * @param instructionEntry
     */
    public void add(T tapeValue, InstructionEntry<T> instructionEntry) {
        this.instructions.put(tapeValue, instructionEntry);
    }

    /**
     * This adds a line to this instruction for a particular symbol.
     *
     * @param tapeValue
     * @param nextValue
     * @param direction
     * @param nextInstruction
     */

    public void addLine(T tapeValue, T nextValue, int direction,
                        String nextInstruction) {
        this.instructions.put(tapeValue, new InstructionEntry<T>(
                nextInstruction, direction, nextValue));
    }

    @Override
    public Instruction<T> operation(Machine<T> machine,
                                    InstructionSet<T> instructionSet) {
        InstructionEntry<T> currentInstruction;
        // Read the current value from the tape
        T value = machine.read();
        // Find the instruction
        if (this.instructions.containsKey(null)) {
            currentInstruction = this.instructions.get(null);
        } else {
            if (value == null) {
                currentInstruction = this.instructions.get("null");
            } else {
                currentInstruction = this.instructions.get(value);
            }
        }
        if (currentInstruction != null) {
            // Do stuff
            if (currentInstruction.newTapeValue != null) {
                if (currentInstruction.newTapeValue.equals("null")) {
                    machine.write(null);
                } else {
                    machine.write(currentInstruction.newTapeValue);
                }
            }
            if (currentInstruction.nextDirection != 0) {
				machine.tickTape(currentInstruction.nextDirection);
			}

			return instructionSet
					.getInstruction(currentInstruction.nextInstruction);
		} else {
			// No instruction..halt the machine
			machine.halt(-1);
			return null;
		}
	}

}

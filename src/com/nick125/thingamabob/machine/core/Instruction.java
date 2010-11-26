package simulation.machine.core;

/**
 * Represents a single instruction.
 *
 * @author kampernj. Created Oct 20, 2010.
 * @param <T>
 * The type of object used as a symbol
 */
public abstract class Instruction<T> {
    /**
     * The Mnemonic for this particular instruction. Used by the InstructionSet
     * to map mnemonics to individual instructions.
     */
    protected String mnemonic; // A mnemonic for the state, if any.

    /**
     * Returns a mnemonic for this state.
     *
     * @return mnemonic
     */
    public String getMnemonic() {
        return this.mnemonic;
    }

    /**
     * Sets the mnemonic for this state.
     *
     * @param mnemonic The mnemonic to set.
     */
    public void setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
    }

    /**
     * Performs the operation that this particular instruction performs when it
     * is called. It should return the next instruction
     *
     * @param machine
     * @param instructionSet
     * @return next instruction
     */
    public abstract Instruction<T> operation(Machine<T> machine,
			InstructionSet<T> instructionSet);

}

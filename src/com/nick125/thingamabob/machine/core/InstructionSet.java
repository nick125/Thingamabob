package simulation.machine.core;

/**
 * An instruction set.
 * <p/>
 * An instruction set stores instructions and allows the machine to access them
 * through getInstruction(index) and getInstruction(mnemonic).
 *
 * @author kampernj, Travis Baumbaugh. Created Oct 20, 2010.
 * @param <T>
 * The type of symbol used in instructions
 */
public interface InstructionSet<T> {

    /**
     * This accesses the given instruction.
     *
     * @param index the index of the instruction to get
     * @return The instruction that is wanted
     */
    public Instruction<T> getInstruction(int index);

    /**
     * This accesses the instruction with the given mnemonic.
     *
     * @param mnemonic The mnemonic that is used for the instruction
     * @return The instruction that is wanted
     */
    public Instruction<T> getInstruction(String mnemonic);

    /**
     * Returns the instruction the machine should start on.
     *
     * @return instruction
     */
    public Instruction<T> getDefaultInstruction();

    /**
     * Returns the number of instructions in the set.
     *
     * @return Number of Instructions.
     */
	public int size();

}

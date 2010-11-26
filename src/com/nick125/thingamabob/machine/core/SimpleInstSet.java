package simulation.machine.core;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This contains simple instructions for the machine to use.
 *
 * @author Travis Baumbaugh. Created Oct 24, 2010.
 * @param <T>
 * The type of symbol being used on the tape.
 */
public class SimpleInstSet<T> implements InstructionSet<T> {
    private ArrayList<Instruction<T>> instructions;
    private HashMap<String, Instruction<T>> mnemonicMap;
    private String defaultInstruction;

    /**
     * Constructs the initial instruction set.
     */
    public SimpleInstSet() {
        this.instructions = new ArrayList<Instruction<T>>();
        this.mnemonicMap = new HashMap<String, Instruction<T>>();
    }

    /**
     * Adds an instruction to the set. Remember after adding instructions to the
     * set to build the Mnemonic map using buildMnemonicMap!
     *
     * @param newInstruction The instruction to be added
     */
    public void addInstruction(Instruction<T> newInstruction) {
        this.instructions.add(newInstruction);
    }

    /**
     * Rebuilds the Mnemonic mapper. If you fail to call this before starting
     * execution, mnemonic calls will fail.
     */
    public void buildMnemonicMap() {
        this.mnemonicMap.clear();
        for (Instruction<T> instruction : this.instructions) {
            this.mnemonicMap.put(instruction.getMnemonic(), instruction);
        }
    }

    @Override
    public Instruction<T> getInstruction(int index) {
        return this.instructions.get(index);
    }

    @Override
    public Instruction<T> getInstruction(String mnemonic) {
        return this.mnemonicMap.get(mnemonic);
    }

    @Override
    public Instruction<T> getDefaultInstruction() {
        if (this.defaultInstruction != null) {
            return this.getInstruction(this.defaultInstruction);
        } else {
            return this.getInstruction(0);
        }
    }

    @Override
    public int size() {
        return this.instructions.size();
    }

    /**
     * Sets the machine's default instruction.
     *
     * @param value
     */
    public void setDefaultInstruction(String value) {
        this.defaultInstruction = value;
	}

}

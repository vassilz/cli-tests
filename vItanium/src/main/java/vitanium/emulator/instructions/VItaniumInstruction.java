package vitanium.emulator.instructions;

import vitanium.emulator.Instruction;
import vitanium.emulator.Program;

/**
 * Common {@link Instruction} supertype, defining some execution lifecycle logic
 * 
 * @author vassi_000
 *
 */
public abstract class VItaniumInstruction implements Instruction {
	
	private final int index;
	
	protected VItaniumInstruction(int sourceIndex) {
		if (sourceIndex < 0) {
			throw new ArrayIndexOutOfBoundsException(sourceIndex);
		}
		index = sourceIndex;
	}

	@Override
	public void parse(String sourceInstruction) {
		// TODO Auto-generated method stub

	}

	public void afterExecute(Program program) {
		System.out.println(program.toString() + " " + this.toString() + " executed successfully.");
	}

	public void beforeExecute(Program program) {
		System.out.println(program.toString() + " Begin executing : " + this.toString() + "...");
	}
	
	@Override
	public int getIndex() {
		return index;
	}
	
	@Override
	public String toString() {
		return getCode().toString();
	}

}

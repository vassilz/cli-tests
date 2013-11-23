package vitanium.emulator.instructions;

import org.apache.log4j.Logger;

import vitanium.emulator.Instruction;
import vitanium.emulator.Program;

/**
 * Common {@link Instruction} supertype, defining some execution lifecycle logic
 *
 */
public abstract class VItaniumInstruction implements Instruction {
	
	protected final Logger log = Logger.getLogger(getClass());
	
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
		log.info(program.toString() + " " + this.toString() + " executed successfully.");
	}

	public void beforeExecute(Program program) {
		log.info(program.toString() + " Begin executing : " + this.toString() + "...");
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

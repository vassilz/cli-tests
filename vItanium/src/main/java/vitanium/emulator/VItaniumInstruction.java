package vitanium.emulator;

import org.apache.log4j.Logger;

import vitanium.emulator.execution.Instruction;
import vitanium.emulator.execution.Program;

/**
 * Common {@link Instruction} supertype, defining some execution lifecycle logic
 *
 */
public abstract class VItaniumInstruction implements Instruction {
	
	// use this to log execution lifecycle events as part of the CPU emulator log
	private final Logger parentLogger = Logger.getLogger(VItaniumInstruction.class);
	
	// use in subclasses for specific logic logging
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
		parentLogger.info(program.toString() + " " + this.toString() + " executed successfully.");
	}

	public void beforeExecute(Program program) {
		parentLogger.info(program.toString() + " Begin executing : " + this.toString() + "...");
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

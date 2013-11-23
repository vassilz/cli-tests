package vitanium.emulator;

import org.apache.log4j.Logger;

import vitanium.emulator.exceptions.VItaniumExecutionException;

public final class VItaniumRunner {
	
	private final Logger log = Logger.getLogger(getClass());
	
	private final int instructionLimit;
	
	public VItaniumRunner(int instructionLimit) {
		this.instructionLimit = instructionLimit;
	}

	public void executeProgram(Program program) throws VItaniumExecutionException {
		log.info("Executing program "+program.toString()+"...");

		try {
			program.execute(instructionLimit);
		} catch (VItaniumExecutionException e) {
			program.dumpState();
			throw e;
		}

		log.info("Execution finished successfully");
	}
}

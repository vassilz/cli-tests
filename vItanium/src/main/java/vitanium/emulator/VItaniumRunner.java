package vitanium.emulator;

import vitanium.emulator.exceptions.VItaniumExecutionException;

public final class VItaniumRunner {
	
	private final int instructionLimit;
	private final boolean isDebugEnabled;
	private final boolean isTraceEnabled;
	
	public VItaniumRunner(boolean isDebugEnabled, boolean isTraceEnabled, int instructionLimit) {
		this.instructionLimit = instructionLimit;
		this.isDebugEnabled = isDebugEnabled;
		this.isTraceEnabled = isTraceEnabled;
	}

	public void executeProgram(Program program) throws VItaniumExecutionException {
		System.out.println("Executing vItanium code...");

		try {
			program.execute(instructionLimit);
		} catch (VItaniumExecutionException e) {
			if (isDebugEnabled) {
				program.dumpState(isTraceEnabled);
			}

			throw e;
		}

		System.out.println("Execution finished successfully");
	}
}

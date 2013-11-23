package vitanium.emulator.execution;

import vitanium.emulator.exceptions.VItaniumExecutionException;

public interface Instruction {

	void parse(String sourceInstruction);
	
	void doExecute(Program program, Stack stack) throws VItaniumExecutionException;
	
	void beforeExecute(Program program);
	
	void afterExecute(Program program);
	
	OpCode getCode();
	
	int getIndex();
	
	static enum OpCode {
		LOC, LDINT, STLOC, LDLOC, CVTS, PRINT, SUM, JMP, JL, HALT;
	}
}

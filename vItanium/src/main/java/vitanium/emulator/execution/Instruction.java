package vitanium.emulator.execution;

import vitanium.emulator.exceptions.VItaniumExecutionException;
import vitanium.emulator.exceptions.VItaniumParseException;

public interface Instruction {

	void parse(String[] arguments) throws VItaniumParseException;
	
	void doExecute(Program program, Stack stack) throws VItaniumExecutionException;
	
	void beforeExecute(Program program) throws VItaniumExecutionException;
	
	void afterExecute(Program program) throws VItaniumExecutionException;
	
	OpCode getCode();
	
	int getIndex();
	
	static enum OpCode {
		LOC, LDINT, STLOC, LDLOC, CVTS, PRINT, SUM, JMP, JL, HALT;
	}
}

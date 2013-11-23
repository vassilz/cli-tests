package vitanium.emulator.execution.opcodes;

import vitanium.emulator.VItaniumInstruction;
import vitanium.emulator.exceptions.VItaniumExecutionException;
import vitanium.emulator.exceptions.VItaniumParseException;
import vitanium.emulator.execution.Program;
import vitanium.emulator.execution.Stack;

public class Sum extends VItaniumInstruction {
	
	public Sum(int sourceIndex) {
		super(sourceIndex, 0);
	}
	
	@Override
	protected void doParse(String[] arguments) throws VItaniumParseException {
		// not used
	}

	@Override
	public void doExecute(Program program, Stack stack) throws VItaniumExecutionException {
		int firstInt = stack.popInt();
		int secondInt = stack.popInt();
		
		// XXX recheck how int sum is computed
		stack.pushInt(firstInt + secondInt);
	}

	@Override
	public OpCode getCode() {
		return OpCode.SUM;
	}

}

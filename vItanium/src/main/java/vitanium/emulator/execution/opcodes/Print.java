package vitanium.emulator.execution.opcodes;

import vitanium.emulator.VItaniumInstruction;
import vitanium.emulator.exceptions.VItaniumExecutionException;
import vitanium.emulator.exceptions.VItaniumParseException;
import vitanium.emulator.execution.Program;
import vitanium.emulator.execution.Stack;

public class Print extends VItaniumInstruction {

	public Print(int sourceIndex) {
		super(sourceIndex, 0);
	}

	@Override
	protected void doParse(String[] arguments) throws VItaniumParseException {
		// not used
	}

	@Override
	public void doExecute(Program program, Stack stack)
			throws VItaniumExecutionException {
		String stackTop = stack.popString();

		System.out.println(stackTop);
	}

	@Override
	public OpCode getCode() {
		return OpCode.PRINT;
	}

}

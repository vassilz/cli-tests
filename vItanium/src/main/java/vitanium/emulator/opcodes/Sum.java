package vitanium.emulator.opcodes;

import java.util.EmptyStackException;

import vitanium.emulator.VItaniumInstruction;
import vitanium.emulator.exceptions.VItaniumExecutionException;
import vitanium.emulator.execution.Program;
import vitanium.emulator.execution.Stack;

public class Sum extends VItaniumInstruction {
	
	public Sum(int sourceIndex) {
		super(sourceIndex);
	}

	@Override
	public void doExecute(Program program, Stack stack) throws VItaniumExecutionException {
		try {
			int firstInt = stack.popInt();
			int secondInt = stack.popInt();
			stack.pushInt(firstInt + secondInt);
			
		} catch (ClassCastException | EmptyStackException e) {
			throw new VItaniumExecutionException(e);
		}
	}

	@Override
	public OpCode getCode() {
		return OpCode.SUM;
	}

}

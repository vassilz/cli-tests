package vitanium.emulator.instructions;

import java.util.EmptyStackException;

import vitanium.emulator.Program;
import vitanium.emulator.Stack;
import vitanium.emulator.exceptions.VItaniumExecutionException;

public class Sum extends VItaniumInstruction {
	
	Sum(int sourceIndex) {
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

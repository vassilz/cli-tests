package vitanium.emulator.instructions;

import java.util.EmptyStackException;

import vitanium.emulator.Program;
import vitanium.emulator.Stack;
import vitanium.emulator.exceptions.VItaniumExecutionException;

public class Cvts extends VItaniumInstruction {

	Cvts(int sourceIndex) {
		super(sourceIndex);
	}

	@Override
	public void doExecute(Program program, Stack stack)
			throws VItaniumExecutionException {
		try {
			int value = stack.popInt();

			stack.pushString(String.valueOf(value));

		} catch (ClassCastException | EmptyStackException e) {
			throw new VItaniumExecutionException(e);
		}
	}

	@Override
	public OpCode getCode() {
		return OpCode.CVTS;
	}

}

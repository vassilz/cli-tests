package vitanium.emulator.execution.opcodes;

import java.text.MessageFormat;
import java.util.EmptyStackException;

import vitanium.emulator.VItaniumInstruction;
import vitanium.emulator.exceptions.VItaniumExecutionException;
import vitanium.emulator.execution.Program;
import vitanium.emulator.execution.Stack;

public class Cvts extends VItaniumInstruction {

	public Cvts(int sourceIndex) {
		super(sourceIndex);
	}

	@Override
	public void doExecute(Program program, Stack stack)
			throws VItaniumExecutionException {
		try {
			int value = stack.popInt();
			
			log.trace(MessageFormat.format("Converted {0} and pushed back as string.", value));

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

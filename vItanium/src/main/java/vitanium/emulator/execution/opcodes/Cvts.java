package vitanium.emulator.execution.opcodes;

import java.text.MessageFormat;
import vitanium.emulator.VItaniumInstruction;
import vitanium.emulator.exceptions.VItaniumExecutionException;
import vitanium.emulator.exceptions.VItaniumParseException;
import vitanium.emulator.execution.Program;
import vitanium.emulator.execution.Stack;

public class Cvts extends VItaniumInstruction {

	public Cvts(int sourceIndex) {
		super(sourceIndex, 0);
	}

	@Override
	protected void doParse(String[] arguments) throws VItaniumParseException {
		// not used
	}

	@Override
	public void doExecute(Program program, Stack stack)
			throws VItaniumExecutionException {
		int value = stack.popInt();

		log.trace(MessageFormat.format(
				"Converted {0} and pushed back as string.", value));

		stack.pushString(String.valueOf(value));
	}

	@Override
	public OpCode getCode() {
		return OpCode.CVTS;
	}

}

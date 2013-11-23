package vitanium.emulator.execution.opcodes;

import java.text.MessageFormat;
import java.util.EmptyStackException;

import vitanium.emulator.VItaniumInstruction;
import vitanium.emulator.exceptions.VItaniumExecutionException;
import vitanium.emulator.exceptions.VItaniumParseException;
import vitanium.emulator.execution.Program;
import vitanium.emulator.execution.Stack;

public class JL extends VItaniumInstruction {

	private String label;

	// to be recalculated each time the JL instance is executed
	private boolean condition;

	public JL(int sourceIndex) {
		super(sourceIndex, 1);
	}
	
	@Override
	protected void doParse(String[] arguments) throws VItaniumParseException {
		label = arguments[0];
	}

	@Override
	public void doExecute(Program program, Stack stack)
			throws VItaniumExecutionException {
		try {
			int firstInt = stack.popInt();
			int secondInt = stack.popInt();

			condition = secondInt < firstInt;

			log.trace(MessageFormat.format("Comparing {0} < {1}: {2} to {3}",
					secondInt, firstInt,
					condition ? "true - execution will JUMP"
							: "false - execution will not JUMP", label));

		} catch (ClassCastException | EmptyStackException e) {
			throw new VItaniumExecutionException(e);
		}
	}

	@Override
	public OpCode getCode() {
		return OpCode.JL;
	}

	public String getLabel() {
		return label;
	}

	public boolean getCondition() {
		return condition;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(super.toString());
		builder.append(" ").append(label);
		return builder.toString();
	}

}

package vitanium.emulator.execution.opcodes;

import java.text.MessageFormat;

import vitanium.emulator.VItaniumInstruction;
import vitanium.emulator.exceptions.VItaniumParseException;
import vitanium.emulator.execution.Program;
import vitanium.emulator.execution.Stack;

public class LdInt extends VItaniumInstruction {
	
	private int integer; // XXX what int-s do we work with?
	
	public LdInt(int sourceIndex) {
		super(sourceIndex, 1);
	}
	
	@Override
	protected void doParse(String[] arguments) throws VItaniumParseException {
		try {
			integer = Integer.parseInt(arguments[0]);
		} catch (NumberFormatException e) {
			throw new VItaniumParseException(e);
		}
	}

	@Override
	public void doExecute(Program program, Stack stack) {
		stack.pushInt(integer);
		log.trace(MessageFormat.format("Pushed {0} as int to the top of execution stack.", integer));
	}

	@Override
	public OpCode getCode() {
		return OpCode.LDINT;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(super.toString());
		builder.append(" ").append(integer);
		return builder.toString();
	}

}

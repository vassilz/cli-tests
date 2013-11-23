package vitanium.emulator.opcodes;

import java.text.MessageFormat;

import vitanium.emulator.VItaniumInstruction;
import vitanium.emulator.execution.Program;
import vitanium.emulator.execution.Stack;

public class LdInt extends VItaniumInstruction {
	
	private final int integer; // what int-s do we work with?
	
	public LdInt(int sourceIndex, int value) {
		super(sourceIndex);
		integer = value;
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

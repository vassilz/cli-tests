package vitanium.emulator.instructions;

import vitanium.emulator.Program;
import vitanium.emulator.Stack;

public class LdInt extends VItaniumInstruction {
	
	private final int integer; // what int-s do we work with?
	
	public LdInt(int sourceIndex, int value) {
		super(sourceIndex);
		integer = value;
	}

	@Override
	public void doExecute(Program program, Stack stack) {
		stack.pushInt(integer);
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

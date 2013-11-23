package vitanium.emulator.instructions;

import vitanium.emulator.Program;
import vitanium.emulator.Stack;
import vitanium.emulator.exceptions.VItaniumExecutionException;

public class Loc extends VItaniumInstruction {
	
	private final String variable;
	
	public Loc(int sourceIndex, String varName) {
		super(sourceIndex);
		variable = varName;
	}
	
	@Override
	public void doExecute(Program program, Stack stack) throws VItaniumExecutionException {
		program.createNamedVariable(variable);
	}

	@Override
	public OpCode getCode() {
		return OpCode.LOC;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(super.toString());
		builder.append(" ").append(variable);
		return builder.toString();
	}

}

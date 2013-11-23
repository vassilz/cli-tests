package vitanium.emulator.execution.opcodes;

import vitanium.emulator.VItaniumInstruction;
import vitanium.emulator.exceptions.VItaniumExecutionException;
import vitanium.emulator.execution.Program;
import vitanium.emulator.execution.Stack;

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

package vitanium.emulator.execution.opcodes;

import vitanium.emulator.VItaniumInstruction;
import vitanium.emulator.exceptions.VItaniumExecutionException;
import vitanium.emulator.exceptions.VItaniumParseException;
import vitanium.emulator.execution.Program;
import vitanium.emulator.execution.Stack;

public class Loc extends VItaniumInstruction {
	
	private String variable;
	
	public Loc(int sourceIndex) {
		super(sourceIndex, 1);
	}
	
	@Override
	protected void doParse(String[] arguments) throws VItaniumParseException {
		variable = arguments[0];
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

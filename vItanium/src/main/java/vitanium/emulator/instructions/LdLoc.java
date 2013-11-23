package vitanium.emulator.instructions;

import vitanium.emulator.Program;
import vitanium.emulator.Stack;
import vitanium.emulator.exceptions.VItaniumExecutionException;

public class LdLoc extends VItaniumInstruction {
	
	private final String variable;
	
	public LdLoc(int sourceIndex, String varName) {
		super(sourceIndex);
		variable = varName;
	}

	@Override
	public void doExecute(Program program, Stack stack) throws VItaniumExecutionException {
		Object value = program.loadNamedVariableValue(variable);
		
		stack.push(value);
	}

	@Override
	public OpCode getCode() {
		return OpCode.LDLOC;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(super.toString());
		builder.append(" ").append(variable);
		return builder.toString();
	}

}

package vitanium.emulator.instructions;

import java.util.EmptyStackException;

import vitanium.emulator.Program;
import vitanium.emulator.Stack;
import vitanium.emulator.exceptions.VItaniumExecutionException;

public class StLoc extends VItaniumInstruction {

	private final String variable;

	StLoc(int sourceIndex, String varName) {
		super(sourceIndex);
		variable = varName;
	}

	@Override
	public void doExecute(Program program, Stack stack)
			throws VItaniumExecutionException {
		try {
			Object stackTop = stack.pop();
			program.storeNamedVariableValue(variable, stackTop);
		} catch (EmptyStackException e) {
			throw new VItaniumExecutionException(e);
		}
	}

	@Override
	public OpCode getCode() {
		return OpCode.STLOC;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(super.toString());
		builder.append(" ").append(variable);
		return builder.toString();
	}

}

package vitanium.emulator.execution.opcodes;

import java.util.EmptyStackException;

import vitanium.emulator.VItaniumInstruction;
import vitanium.emulator.exceptions.VItaniumExecutionException;
import vitanium.emulator.exceptions.VItaniumParseException;
import vitanium.emulator.execution.Program;
import vitanium.emulator.execution.Stack;

public class StLoc extends VItaniumInstruction {

	private String variable;

	public StLoc(int sourceIndex) {
		super(sourceIndex, 1);
	}
	
	@Override
	protected void doParse(String[] arguments) throws VItaniumParseException {
		variable = arguments[0];
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

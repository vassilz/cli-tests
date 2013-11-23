package vitanium.emulator.instructions;

import java.util.EmptyStackException;

import vitanium.emulator.Program;
import vitanium.emulator.Stack;
import vitanium.emulator.exceptions.VItaniumExecutionException;

public class JL extends VItaniumInstruction {
	
	private final String label;
	
	// to be recalculated each time the JL instance is executed
	private boolean condition;
	
	public JL(int sourceIndex, String jumpTo) {
		super(sourceIndex);
		label = jumpTo;
	}
	
	@Override
	public void doExecute(Program program, Stack stack) throws VItaniumExecutionException {
		try {
			int firstInt = stack.popInt();
			int secondInt = stack.popInt();
			
			condition = secondInt < firstInt;
			
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

package vitanium.emulator.execution.opcodes;

import java.util.EmptyStackException;

import vitanium.emulator.VItaniumInstruction;
import vitanium.emulator.exceptions.VItaniumExecutionException;
import vitanium.emulator.exceptions.VItaniumParseException;
import vitanium.emulator.execution.Program;
import vitanium.emulator.execution.Stack;

public class Print extends VItaniumInstruction {
	
	public Print(int sourceIndex) {
		super(sourceIndex, 0);
	}
	
	@Override
	protected void doParse(String[] arguments) throws VItaniumParseException {
		// not used
	}

	@Override
	public void doExecute(Program program, Stack stack) throws VItaniumExecutionException {
		try {
			String stackTop = stack.popString();
			
			System.out.println(stackTop);
			
		} catch (ClassCastException | EmptyStackException e) {
			throw new VItaniumExecutionException(e);
		}
		
	}

	@Override
	public OpCode getCode() {
		return OpCode.PRINT;
	}

}

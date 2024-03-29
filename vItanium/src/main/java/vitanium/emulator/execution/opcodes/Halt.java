package vitanium.emulator.execution.opcodes;

import vitanium.emulator.VItaniumInstruction;
import vitanium.emulator.exceptions.VItaniumParseException;
import vitanium.emulator.execution.Program;
import vitanium.emulator.execution.Stack;

public class Halt extends VItaniumInstruction {
	
	public Halt(int sourceIndex) {
		super(sourceIndex, 0);
	}
	
	@Override
	protected void doParse(String[] arguments) throws VItaniumParseException {
		// not used
	}

	@Override
	public void doExecute(Program program, Stack stack) {
		log.trace("Halting execution...");
	}

	@Override
	public OpCode getCode() {
		return OpCode.HALT;
	}

}

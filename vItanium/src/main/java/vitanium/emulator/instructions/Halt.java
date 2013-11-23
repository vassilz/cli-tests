package vitanium.emulator.instructions;

import vitanium.emulator.Program;
import vitanium.emulator.Stack;

public class Halt extends VItaniumInstruction {
	
	Halt(int sourceIndex) {
		super(sourceIndex);
	}

	@Override
	public void doExecute(Program program, Stack stack) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public OpCode getCode() {
		return OpCode.HALT;
	}

}

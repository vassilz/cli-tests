package vitanium.emulator.instructions;

import vitanium.emulator.Program;
import vitanium.emulator.Stack;

public class Jmp extends VItaniumInstruction {
	
	private final String label;
	
	Jmp(int sourceIndex, String jumpTo) {
		super(sourceIndex);
		label = jumpTo;
	}

	@Override
	public void doExecute(Program program, Stack stack) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public OpCode getCode() {
		return OpCode.JMP;
	}
	
	public String getLabel() {
		return label;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(super.toString());
		builder.append(" ").append(label);
		return builder.toString();
	}

}

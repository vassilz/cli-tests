package vitanium.emulator.execution.opcodes;

import java.text.MessageFormat;

import vitanium.emulator.VItaniumInstruction;
import vitanium.emulator.exceptions.VItaniumParseException;
import vitanium.emulator.execution.Program;
import vitanium.emulator.execution.Stack;

public class Jmp extends VItaniumInstruction {
	
	private String label;
	
	public Jmp(int sourceIndex) {
		super(sourceIndex, 1);
	}
	
	@Override
	protected void doParse(String[] arguments) throws VItaniumParseException {
		label = arguments[0];
	}

	@Override
	public void doExecute(Program program, Stack stack) {
		log.trace(MessageFormat.format("Unconditional JUMP to {0}", label));
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

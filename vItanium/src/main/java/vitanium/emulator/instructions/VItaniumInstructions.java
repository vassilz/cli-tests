package vitanium.emulator.instructions;

import vitanium.emulator.Instruction;
import vitanium.emulator.Instruction.OpCode;
import vitanium.emulator.exceptions.VItaniumParseException;

public final class VItaniumInstructions {

	public static Instruction parse(int sourceIndex, String instruction) throws VItaniumParseException {
		if (instruction == null) {
			throw new VItaniumParseException("null instruction"); // runtime exception maybe
		}
		
		String[] parts = instruction.trim().split("\\s");
		if (parts.length == 0 || parts[0] == null || parts[0].isEmpty()) {
			throw new VItaniumParseException("null instruction: " + instruction);
		}
		
		String opCode = parts[0];
		try {
			if (OpCode.LOC.name().equalsIgnoreCase(opCode)) {
				return new Loc(sourceIndex, parts[1]);
			}
			if (OpCode.LDINT.name().equalsIgnoreCase(opCode)) {
				return new LdInt(sourceIndex, Integer.parseInt(parts[1]));
			}
			if (OpCode.STLOC.name().equalsIgnoreCase(opCode)) {
				return new StLoc(sourceIndex, parts[1]);
			}
			if (OpCode.LDLOC.name().equalsIgnoreCase(opCode)) {
				return new LdLoc(sourceIndex, parts[1]);
			}
			if (OpCode.CVTS.name().equalsIgnoreCase(opCode)) {
				return new Cvts(sourceIndex);
			}
			if (OpCode.PRINT.name().equalsIgnoreCase(opCode)) {
				return new Print(sourceIndex);
			}
			if (OpCode.SUM.name().equalsIgnoreCase(opCode)) {
				return new Sum(sourceIndex);
			}
			if (OpCode.JMP.name().equalsIgnoreCase(opCode)) {
				return new Jmp(sourceIndex, parts[1]);
			}
			if (OpCode.JL.name().equalsIgnoreCase(opCode)) {
				return new JL(sourceIndex, parts[1]);
			}
			if (OpCode.HALT.name().equalsIgnoreCase(opCode)) {
				return new Halt(sourceIndex);
			}
		} catch (Exception e) {
			throw new VItaniumParseException(e);
		}
		
		throw new VItaniumParseException("Unrecognized vItanium instruction OpCode: " + instruction);
	}
	
	private VItaniumInstructions() {
		// do not instantiate directly
	}
}

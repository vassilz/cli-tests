package vitanium.emulator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import vitanium.emulator.Instruction.OpCode;
import vitanium.emulator.exceptions.VItaniumParseException;
import vitanium.emulator.instructions.Cvts;
import vitanium.emulator.instructions.Halt;
import vitanium.emulator.instructions.JL;
import vitanium.emulator.instructions.Jmp;
import vitanium.emulator.instructions.LdInt;
import vitanium.emulator.instructions.LdLoc;
import vitanium.emulator.instructions.Loc;
import vitanium.emulator.instructions.Print;
import vitanium.emulator.instructions.StLoc;
import vitanium.emulator.instructions.Sum;

public final class VItaniumCompiler {
	
	private final Logger log = Logger.getLogger(getClass());

	public Program compileFromSourceFile(String programPath) throws VItaniumParseException {
		// TODO log debug & trace messages

		String programName = FilenameUtils.getName(programPath);

		Program vItaniumProgram = new Program(programName);

		try {
			URI programURI = CPU.class.getResource("/" + programPath).toURI();

			try (BufferedReader bReader = new BufferedReader(new FileReader(
					new File(programURI)))) {
				String nextLine = null;
				int lineNumber = 0;

				while ((nextLine = bReader.readLine()) != null) {
					String instruction = nextLine;
					String label = null;

					if (instruction.contains(":")
							&& (instruction.indexOf(":") == instruction
									.lastIndexOf(":"))) {
						int labelSeparatorIndex = instruction.indexOf(":");
						label = instruction.substring(0, labelSeparatorIndex); // .trim().toUpperCase();

						log.debug("Label found: " + label);

						instruction = nextLine.substring(
								labelSeparatorIndex + 1).trim();
					}

					log.debug("Instruction found: " + instruction);

					Instruction vItaniumInstruction = parse(lineNumber, instruction);

					vItaniumProgram.appendInstruction(label,
							vItaniumInstruction);

					lineNumber++;
				}

			} catch (FileNotFoundException e) {
				throw new RuntimeException(
						"Source program not found. vItanium emulator will now exit.",
						e);
			} catch (IOException e) {
				throw new RuntimeException(
						"IO error. vItanium emulator will now exit.", e);
			}

		} catch (URISyntaxException e1) {
			throw new RuntimeException(e1);
		}

		vItaniumProgram.readyForExecution();

		return vItaniumProgram;
	}
	
	public Instruction parse(int sourceIndex, String instruction) throws VItaniumParseException {
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
}

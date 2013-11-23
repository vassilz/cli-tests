package vitanium.emulator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import vitanium.emulator.exceptions.VItaniumParseException;
import vitanium.emulator.exceptions.VItaniumSystemException;
import vitanium.emulator.execution.Instruction;
import vitanium.emulator.execution.Program;
import vitanium.emulator.execution.Instruction.OpCode;
import vitanium.emulator.execution.opcodes.Cvts;
import vitanium.emulator.execution.opcodes.Halt;
import vitanium.emulator.execution.opcodes.JL;
import vitanium.emulator.execution.opcodes.Jmp;
import vitanium.emulator.execution.opcodes.LdInt;
import vitanium.emulator.execution.opcodes.LdLoc;
import vitanium.emulator.execution.opcodes.Loc;
import vitanium.emulator.execution.opcodes.Print;
import vitanium.emulator.execution.opcodes.StLoc;
import vitanium.emulator.execution.opcodes.Sum;

public final class VItaniumCompiler {

	private final Logger log = Logger.getLogger(getClass());

	// make sure all Loc instructions go at the beginning of the program
	private boolean acceptDeclarations = true;

	public Program compileFromSourceFile(File programFile)
			throws VItaniumParseException, VItaniumSystemException {
		// TODO log debug & trace messages

		String programName = FilenameUtils.getName(programFile
				.getAbsolutePath());

		Program vItaniumProgram = new Program(programName);

		try (BufferedReader bReader = new BufferedReader(new FileReader(
				programFile))) {
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

					instruction = nextLine.substring(labelSeparatorIndex + 1)
							.trim();
				}

				log.debug("Instruction found: " + instruction);

				Instruction vItaniumInstruction = parse(lineNumber, instruction);

				vItaniumProgram.appendInstruction(label, vItaniumInstruction);

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

		vItaniumProgram.readyForExecution();

		return vItaniumProgram;
	}

	public Instruction parse(int sourceIndex, String instruction)
			throws VItaniumParseException, VItaniumSystemException {
		if (instruction == null) {
			throw new VItaniumSystemException("null instruction");
		}

		String[] parts = instruction.trim().split("\\s");
		if (parts.length == 0 || parts[0] == null || parts[0].isEmpty()) {
			throw new VItaniumSystemException("null instruction: "
					+ instruction);
		}

		String sCode = parts[0];
		OpCode opCode = OpCode.valueOf(sCode.toUpperCase());
		switch (opCode) {
		case LOC: {
			if (acceptDeclarations) {
				return new Loc(sourceIndex, parts[1]);
			} else {
				throw new VItaniumParseException(
						"All local variable declarations (LOC <var>) must go in the beginning of the program.");
			}
		}
		case LDINT: {
			acceptDeclarations = false;

			return new LdInt(sourceIndex, Integer.parseInt(parts[1]));
		}
		case STLOC: {
			acceptDeclarations = false;

			return new StLoc(sourceIndex, parts[1]);
		}
		case LDLOC: {
			acceptDeclarations = false;

			return new LdLoc(sourceIndex, parts[1]);
		}
		case CVTS: {
			acceptDeclarations = false;

			return new Cvts(sourceIndex);
		}
		case PRINT: {
			acceptDeclarations = false;

			return new Print(sourceIndex);
		}
		case SUM: {
			acceptDeclarations = false;

			return new Sum(sourceIndex);
		}
		case JMP: {
			acceptDeclarations = false;

			return new Jmp(sourceIndex, parts[1]);
		}
		case JL: {
			acceptDeclarations = false;

			return new JL(sourceIndex, parts[1]);
		}
		case HALT: {
			acceptDeclarations = false;

			return new Halt(sourceIndex);
		}
		default: {
			throw new VItaniumParseException(
					"Unrecognized vItanium instruction OpCode: " + instruction);
		}
		}
	}
}

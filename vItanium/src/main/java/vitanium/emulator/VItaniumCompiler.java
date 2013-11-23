package vitanium.emulator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.regex.Pattern;

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

	private static final String LABEL_SEPARATOR = ":";
	private static final Pattern LABEL_PATTERN = Pattern.compile("[a-zA-Z]+",
			Pattern.CASE_INSENSITIVE);

	private static final String COMMENT_SYMBOL = "!";

	private final Logger log = Logger.getLogger(getClass());

	// make sure all Loc instructions go at the beginning of the program
	private boolean acceptDeclarations = true;

	private final boolean enableComments;

	public VItaniumCompiler() {
		this(false);
	}

	public VItaniumCompiler(boolean enableComments) {
		this.enableComments = enableComments;
	}

	public Program compileFromSourceFile(File programFile)
			throws VItaniumParseException, VItaniumSystemException {
		// TODO log debug & trace messages

		String programName = FilenameUtils.getName(programFile
				.getAbsolutePath());

		Program vItaniumProgram = new Program(programName);

		try (BufferedReader bReader = new BufferedReader(new FileReader(
				programFile))) {
			String nextLine = null;
			int sourceIndex = 0;

			while ((nextLine = bReader.readLine()) != null) {
				String instruction = nextLine.trim();
				String label = null;

				if (enableComments && instruction.startsWith(COMMENT_SYMBOL)) {
					// moving on
					log.warn(MessageFormat
							.format("Instruction line {0} skipped from the assembly, due to debug flag set!!",
									instruction));
					continue;
				}

				if (instruction.contains(LABEL_SEPARATOR)
						&& (instruction.indexOf(LABEL_SEPARATOR) == instruction
								.lastIndexOf(LABEL_SEPARATOR))) {
					int labelSeparatorIndex = instruction
							.indexOf(LABEL_SEPARATOR);
					label = instruction.substring(0, labelSeparatorIndex);

					if (!LABEL_PATTERN.matcher(label).matches()) {
						throw new VItaniumParseException(
								MessageFormat
										.format("Unparsable label found {0}; valid labels are {1}.",
												label, LABEL_PATTERN.pattern()));
					}

					log.debug("Label found: " + label);

					instruction = nextLine.substring(labelSeparatorIndex + 1)
							.trim();
				}

				log.debug("Instruction found: " + instruction);

				Instruction vItaniumInstruction = parseInstruction(sourceIndex,
						instruction);

				vItaniumProgram.appendInstruction(label, vItaniumInstruction);

				sourceIndex++;
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

	public Instruction parseInstruction(int sourceIndex, String instruction)
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
		Instruction parsedInstruction = null;
		switch (opCode) {
		case LOC: {
			if (acceptDeclarations) {
				parsedInstruction = new Loc(sourceIndex);
				break;
			} else {
				throw new VItaniumParseException(
						"All local variable declarations (LOC <var>) must go in the beginning of the program.");
			}
		}
		case LDINT: {
			acceptDeclarations = false;
			parsedInstruction = new LdInt(sourceIndex);
			break;
		}
		case STLOC: {
			acceptDeclarations = false;
			parsedInstruction = new StLoc(sourceIndex);
			break;
		}
		case LDLOC: {
			acceptDeclarations = false;
			parsedInstruction = new LdLoc(sourceIndex);
			break;
		}
		case CVTS: {
			acceptDeclarations = false;
			parsedInstruction = new Cvts(sourceIndex);
			break;
		}
		case PRINT: {
			acceptDeclarations = false;
			parsedInstruction = new Print(sourceIndex);
			break;
		}
		case SUM: {
			acceptDeclarations = false;
			parsedInstruction = new Sum(sourceIndex);
			break;
		}
		case JMP: {
			acceptDeclarations = false;
			parsedInstruction = new Jmp(sourceIndex);
			break;
		}
		case JL: {
			acceptDeclarations = false;
			parsedInstruction = new JL(sourceIndex);
			break;
		}
		case HALT: {
			acceptDeclarations = false;
			parsedInstruction = new Halt(sourceIndex);
			break;
		}
		default: {
			throw new VItaniumParseException(
					"Unrecognized vItanium instruction OpCode: " + instruction);
		}
		}

		if (parts.length == 1) {
			// no-arg instruction
			parsedInstruction.parse(new String[] {});
		} else {
			String[] arguments = Arrays.copyOfRange(parts, 1, parts.length);
			parsedInstruction.parse(arguments);
		}

		return parsedInstruction;
	}
}

package vitanium.emulator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.io.FilenameUtils;

import vitanium.emulator.exceptions.VItaniumParseException;
import vitanium.emulator.instructions.VItaniumInstructions;

public final class VItaniumCompiler {

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

						System.out.println("Label found: " + label);

						instruction = nextLine.substring(
								labelSeparatorIndex + 1).trim();
					}

					System.out.println("Instruction found: " + instruction);

					Instruction vItaniumInstruction = VItaniumInstructions
							.parse(lineNumber, instruction);

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
}

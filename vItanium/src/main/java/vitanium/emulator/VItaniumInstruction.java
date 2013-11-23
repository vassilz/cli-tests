package vitanium.emulator;

import java.text.MessageFormat;

import org.apache.log4j.Logger;

import vitanium.emulator.exceptions.VItaniumParseException;
import vitanium.emulator.execution.Instruction;
import vitanium.emulator.execution.Program;

/**
 * Common {@link Instruction} supertype, defining some execution lifecycle logic
 * 
 */
public abstract class VItaniumInstruction implements Instruction {

	// use this to log execution lifecycle events as part of the CPU emulator
	// log
	private final Logger parentLogger = Logger
			.getLogger(VItaniumInstruction.class);

	// use in subclasses for specific logic logging
	protected final Logger log = Logger.getLogger(getClass());

	private final int index;

	// convenience flag; equivalent to argumentCount == 0
	private final boolean takesArguments;

	// how many arguments does this OpCode require
	private final int argumentCount;

	protected VItaniumInstruction(int sourceIndex, int argumentCount) {
		if (sourceIndex < 0) {
			throw new ArrayIndexOutOfBoundsException(sourceIndex);
		}
		index = sourceIndex;
		if (argumentCount < 0) {
			// how did we get here...? halt program
			throw new IllegalArgumentException("illegal argumentCount: "
					+ argumentCount);
		}

		takesArguments = argumentCount > 0;
		this.argumentCount = argumentCount;
	}

	@Override
	public void parse(String[] arguments) throws VItaniumParseException {
		validateArguments(arguments);

		doParse(arguments);
	}

	private void validateArguments(String[] arguments)
			throws VItaniumParseException {
		if (arguments == null || arguments.length == 0) {
			if (takesArguments) {
				throw new VItaniumParseException(MessageFormat.format(
						"OpCode {0} expects {1} arguments; received none.",
						getCode(), argumentCount));
			}

			// we're good to go
			return;
		}

		if (!takesArguments) {
			// this time we have extra input
			throw new VItaniumParseException(MessageFormat.format(
					"OpCode {0} does not take arguments; received {1}.",
					getCode(), arguments.length));
		}

		if (argumentCount != arguments.length) {
			throw new VItaniumParseException(MessageFormat.format(
					"OpCode {0} expects {1} arguments; received {2}.",
					getCode(), argumentCount, arguments.length));
		}
	}

	/**
	 * We are now guaranteed that the number of arguments is exactly what we
	 * need.
	 * <p>
	 * Their types may still be incompatible though.
	 * 
	 * @param arguments
	 * @throws VItaniumParseException
	 */
	protected abstract void doParse(String[] arguments)
			throws VItaniumParseException;

	public void afterExecute(Program program) {
		parentLogger.info(program.toString() + " " + this.toString()
				+ " executed successfully.");
		
		program.incrementExecutedInstructions();
	}

	public void beforeExecute(Program program) {
		parentLogger.info(program.toString() + " Begin executing : "
				+ this.toString() + "...");
	}

	@Override
	public int getIndex() {
		return index;
	}

	@Override
	public String toString() {
		return getCode().toString();
	}

}

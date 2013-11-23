package vitanium.emulator;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.RollingFileAppender;

import vitanium.emulator.exceptions.VItaniumExecutionException;
import vitanium.emulator.exceptions.VItaniumParseException;

/**
 * Main entry point to the console application
 * 
 */
public class CPU {
	
	private static final Logger LOG = Logger.getLogger(CPU.class);

	public static void main(String[] args) {
		// create the command line parser
		CommandLineParser parser = new DefaultParser();

		// create the Options
		Options options = new Options();
		options.addOption("e", "execute", true,
				"emulate execution of the given source assembly code");
		options.addOption(
				"l",
				"instructionlimit",
				true,
				"maximum allowed number of instructions to execute per program. does not affect number of declared instruction in the source code");
		options.addOption("h", "help", false, "print usage help message");
		
		// bootstrap log4j
		try {
			configureLogging();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		try {
			// parse the command line arguments
			CommandLine line = parser.parse(options, args);

			if (line.getOptions() == null
					|| (line.hasOption('h') && line.getOptions().length != 1)) {
				LOG.fatal("Unparsable arguments: " + Arrays.asList(line.getOptions()));
				printUsage(options);
				return;
			}

			if (line.hasOption('e')) {
				String sourceFilePath = line.getOptionValue('e');

				int instructionLimit = -1;
				if (line.hasOption('l')) {
					String sValue = line.getOptionValue('l');
					try {
						instructionLimit = Integer.parseInt(sValue);
					} catch (NumberFormatException e) {
						// handle below
						throw new ParseException(e.getMessage());
					}
				}

				Program program = compileProgram(sourceFilePath);
				VItaniumRunner runner = new VItaniumRunner(instructionLimit);
				executeProgram(program, runner);

			} else if (line.hasOption('h')) {
				printUsage(options);
			} else {
				LOG.fatal("Unparsable arguments: " + Arrays.asList(line.getOptions()));
				printUsage(options);
			}
		} catch (ParseException e) {
			LOG.error(e.getMessage());
			printUsage(options);
			
		} catch (VItaniumParseException e) {
			LOG.error(e.getMessage(), e);
		} catch (VItaniumExecutionException e) {
			LOG.error(e.getMessage(), e);
		}
	}

	private static void configureLogging() throws IOException {
		
		String log4jProperties = System.getProperty("log4j.configuration");
		if (log4jProperties != null) {
			PropertyConfigurator.configure(log4jProperties);
		} else {
			// use default logging configuration
			URL log4jConfigUrl = CPU.class.getResource("/log4j.properties");
			PropertyConfigurator.configure(log4jConfigUrl);
		}
		
		Logger rootLogger = LogManager.getRootLogger();
		if (System.getProperty("vItanium.debug") != null) {
			AppenderSkeleton mainAppender = (AppenderSkeleton) rootLogger.getAppender("main");
			if (mainAppender == null) {
				PatternLayout layout = new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n");
				RollingFileAppender appender = new RollingFileAppender(layout, "vItanium.log");
				appender.setMaxFileSize("10MB");
				appender.setMaxBackupIndex(1);
				appender.setThreshold(Level.ALL);
				appender.setName("main");
				rootLogger.addAppender(appender);
			} else {
				mainAppender.setThreshold(Level.ALL);
			}
		}
		if (System.getProperty("vItanium.execution.debug") != null) {
			AppenderSkeleton executionAppender = (AppenderSkeleton) rootLogger.getAppender("execution");
			if (executionAppender == null) {
				PatternLayout layout = new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n");
				RollingFileAppender appender = new RollingFileAppender(layout, "vItanium-execution.log");
				appender.setMaxFileSize("10MB");
				appender.setMaxBackupIndex(1);
				appender.setThreshold(Level.ALL);
				appender.setName("execution");
				rootLogger.addAppender(appender);
			} else {
				executionAppender.setThreshold(Level.ALL);
			}
		}
	}

	private static void executeProgram(Program program, VItaniumRunner runner)
			throws VItaniumExecutionException {
		runner.executeProgram(program);
	}

	private static Program compileProgram(String programPath)
			throws VItaniumParseException {
		return new VItaniumCompiler().compileFromSourceFile(programPath);
	}

	private static void printUsage(Options expectedOpts) {
		String header = "Do something useful with an input file\n\n";
		String footer = "\nPlease report issues at http://example.com/issues";

		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("vItanium emulator", header, expectedOpts, footer,
				true);
	}

}

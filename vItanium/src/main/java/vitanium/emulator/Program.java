package vitanium.emulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import vitanium.emulator.Instruction.OpCode;
import vitanium.emulator.exceptions.VItaniumExecutionException;
import vitanium.emulator.exceptions.VItaniumParseException;
import vitanium.emulator.exceptions.VariableNotFoundException;
import vitanium.emulator.instructions.JL;
import vitanium.emulator.instructions.Jmp;

public final class Program {
	
	private final Logger log;
	
	// Human-readable name of the program. Derived from the source file.
	private final String name;

	private List<Instruction> instructions;
	
	private Map<String, Instruction> labeledInstructions;
	
	private Map<String, Object> namedVariables;
	
	private Stack executionStack;
	
	private boolean readyToExecute = false;
	
	private int executedInstructions = 0; // LONG..?
	
	public Program(String programName) {
		name = programName;
		log = Logger.getLogger(programName);
		
		instructions = new ArrayList<>();
		labeledInstructions = new HashMap<>();
		namedVariables = new HashMap<>();
	}
	
	public void readyForExecution() {
		readyToExecute = true;
	}
	
	public void appendInstruction(String label, Instruction instruction) throws VItaniumParseException {
		if (readyToExecute) {
			throw new IllegalStateException("Program is now in state AWAIT_EXECUTION. No more modifications allowed.");
		}
		
		// TODO do we care for duplication here?
		instructions.add(instruction);
		
		if (label != null) {
			String ucLabel = label.toUpperCase();
			if (labeledInstructions.containsKey(ucLabel)) {
				throw new VItaniumParseException("Unparsable vItanium program: Label duplication is not allowed (at "+label+"; "+instruction.toString()+"). Program will now exit.");
			}
			
			labeledInstructions.put(ucLabel, instruction);
		}
	}
	
	public final void execute(int instructionLimit) throws VItaniumExecutionException {
		if (!readyToExecute) {
			throw new IllegalStateException("Program is still in state COMPILING. In order to be executed, readyForExecution() must be called first.");
		}
		
		executionStack = new Stack();
		
		Instruction currentInstruction = instructions.get(0); // begin execution
		while (currentInstruction != null) {
			
			if (instructionLimit > 0 && executedInstructions >= instructionLimit) {
				throw new VItaniumExecutionException("Program terminated abnormally due to hit limit of executed instructions");
			}
			
			Instruction nextInstruction = null;
			
			currentInstruction.beforeExecute(this);
			
			if (OpCode.HALT.equals(currentInstruction.getCode())) {
				break;
			} else if (OpCode.JMP.equals(currentInstruction.getCode())) {
				Jmp jmp = (Jmp) currentInstruction;
				
				// currently this is a no-op
				jmp.doExecute(this, executionStack);
				
				String jumpToLabel = jmp.getLabel();
				
				nextInstruction = findLabeledInstruction(jumpToLabel);
				
			} else if (OpCode.JL.equals(currentInstruction.getCode())) {
				JL jl = (JL) currentInstruction;
				
				// let JL calculate its condition
				jl.doExecute(this, executionStack);
				
				boolean willJump = jl.getCondition();
				if (willJump) {
					nextInstruction = findLabeledInstruction(jl.getLabel());
				} else {
					nextInstruction = findInstructionByIndex(jl.getIndex() + 1);
				}
			} else {
				// all other instructions have no special effect on the execution order
				currentInstruction.doExecute(this, executionStack);
				
				nextInstruction = findInstructionByIndex(currentInstruction.getIndex() + 1);
			}
			
			currentInstruction.afterExecute(this);
			
			currentInstruction = nextInstruction;
		}
	}
	
	public Instruction findLabeledInstruction(String label) throws VItaniumExecutionException {
		if (labeledInstructions.containsKey(label.toUpperCase())) {
			return labeledInstructions.get(label.toUpperCase());
		}
		throw new VItaniumExecutionException("Instruction labeled with " + label + " was not found in the source tree.");
	}
	
	public Instruction findInstructionByIndex(int sourceIndex) throws ArrayIndexOutOfBoundsException {
		return instructions.get(sourceIndex);
	}
	
	public void createNamedVariable(String varName) throws VItaniumExecutionException {
		String ucName = varName.toUpperCase();
		if (namedVariables.containsKey(ucName)) {
			throw new VItaniumExecutionException("Duplicate-named local variables cannot be created: " + varName);
		}
		namedVariables.put(ucName, null); // unassigned yet. XXX What is this "zero" assignment in the reqs..?
	}
	
	public void storeNamedVariableValue(String varName, Object value) throws VariableNotFoundException {
		String ucName = varName.toUpperCase();
		if (!namedVariables.containsKey(ucName)) {
			throw new VariableNotFoundException(ucName);
		}
		namedVariables.put(ucName, value);
	}
	
	public Object loadNamedVariableValue(String varName) throws VariableNotFoundException {
		String ucName = varName.toUpperCase();
		if (namedVariables.containsKey(ucName)) {
			return namedVariables.get(ucName);
		}
		throw new VariableNotFoundException(ucName);
	}
	
	/**
	 * Used to debug vItanium programs that fail upon execution.
	 */
	void dumpState() {
		log.debug("Stack: " + executionStack.toString());
		log.trace("Local variables: " + namedVariables);
		log.trace("Defined labels: " + labeledInstructions.keySet());
	}
	
	@Override
	public String toString() {
		return new StringBuilder("[").append(name).append("]").toString();
	}
	
	// FIXME add validate() logic
}

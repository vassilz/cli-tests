package vitanium.emulator.exceptions;

public class VariableNotFoundException extends VItaniumExecutionException {
	
	public VariableNotFoundException(String varName) {
		super("Local variable named: "+varName+" does not exist.");
	}

}

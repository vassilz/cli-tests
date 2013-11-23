package vitanium.emulator.exceptions;

public class VItaniumExecutionException extends VItaniumException {
	
	public VItaniumExecutionException(String message) {
		super(message);
	}
	
	public VItaniumExecutionException(Throwable cause) {
		super(cause);
	}

	public VItaniumExecutionException(String message, Throwable cause) {
		super(message, cause);
	}

}

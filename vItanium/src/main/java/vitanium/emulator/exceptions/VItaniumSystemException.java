package vitanium.emulator.exceptions;

public class VItaniumSystemException extends Exception {

	public VItaniumSystemException(String message) {
		super(message);
	}
	
	public VItaniumSystemException(Throwable cause) {
		super(cause);
	}
	
	public VItaniumSystemException(String message, Throwable cause) {
		super(message, cause);
	}
}

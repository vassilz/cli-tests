package vitanium.emulator.exceptions;

/**
 * Supertype for all exceptions, produced and/or handled by the vItanium CPU Emulator.
 * 
 * @author vassi_000
 *
 */
public class VItaniumException extends Exception {

	public VItaniumException(String message) {
		super(message);
	}
	
	public VItaniumException(Throwable cause) {
		super(cause);
	}
	
	public VItaniumException(String message, Throwable cause) {
		super(message, cause);
	}
}

package vitanium.emulator;

import java.util.EmptyStackException;

import vitanium.emulator.exceptions.VItaniumExecutionException;

public final class Stack {
	
	private final java.util.Stack<Object> theStack = new java.util.Stack<>();
	
	public void push(Object value) throws VItaniumExecutionException {
		if (value == null) {
			throw new VItaniumExecutionException(new IllegalArgumentException("Cannot push null value to the execution stack."));
		}
		
		if (String.class.isAssignableFrom(value.getClass())) {
			pushString((String) value);
		} else if (Integer.class.isAssignableFrom(value.getClass())) {
			pushInt((Integer) value);
		} else {
			throw new VItaniumExecutionException("Attempted push of unsupported value type to execution stack: " + value.getClass());
		}
	}
	
	public void pushInt(int value) {
		theStack.push(new Integer(value));
	}
	
	public void pushString(String value) {
		theStack.push(value);
	}
	
	public Object pop() throws EmptyStackException, VItaniumExecutionException {
		Object top = theStack.pop();
		if (String.class.isAssignableFrom(top.getClass())) {
			return (String) top;
		} else if (Integer.class.isAssignableFrom(top.getClass())) {
			return (Integer) top;
		}
		
		throw new VItaniumExecutionException("Unsupported value type found in execution stack: " + top.getClass());
	}
	
	public String popString() throws ClassCastException, EmptyStackException, VItaniumExecutionException {
		return (String) pop();
	}
	
	public int popInt() throws ClassCastException, EmptyStackException, VItaniumExecutionException {
		return (int) pop();
	}
	
	@Override
	public String toString() {
		return theStack.toString();
	}

}

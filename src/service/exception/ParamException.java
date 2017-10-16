package service.exception;

public class ParamException extends Exception {
	
	private String error;

	public ParamException() {
		
	}
	
	public ParamException(String error) {
		this.error = error;
	}
	
	@Override
	public String toString() {
		return error;
	}
}

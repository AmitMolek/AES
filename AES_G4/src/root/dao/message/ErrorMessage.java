package root.dao.message;

public class ErrorMessage extends AbstractMessage {
	private Exception errorException;
	
	public ErrorMessage(Exception exception) {
		super("error");
		this.errorException = exception;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "ErrorMesage";
	}

	@Override
	public String toString() {
		return "ErrorMessage [errorException=" + errorException + "]";
	}

	public Exception getErrorException() {
		return errorException;
	}

	public void setErrorException(Exception errorException) {
		this.errorException = errorException;
	}
	

}

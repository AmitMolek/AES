package root.dao.message;

public class LogoutErrorMessage extends AbstractMessage{
	private static final long serialVersionUID = 1L;
	private Exception errorException;
	
	public LogoutErrorMessage(Exception exception) {
		super("error-loggedout");
		this.errorException = exception;
	}

	@Override
	public String getType() {
		return "LogoutErrorMessage";
	}

	@Override
	public String toString() {
		return errorException.getMessage();
	}

	public Exception getErrorException() {
		return errorException;
	}

	public void setErrorException(Exception errorException) {
		this.errorException = errorException;
	}
}

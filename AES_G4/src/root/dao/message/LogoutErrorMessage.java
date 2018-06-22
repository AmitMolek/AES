package root.dao.message;

/**
 * The object message that the client and sever are talking about the logout error action
 * @author Amit Molek
 *
 */

public class LogoutErrorMessage extends AbstractMessage{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * The exception the was thrown
	 */
	private Exception errorException;
	
	/**
	 * Class constructor
	 * @param exception the exception that was thrown
	 */
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

	/**
	 * Returns the exception that was thrown
	 * @return
	 */
	public Exception getErrorException() {
		return errorException;
	}

	/**
	 * Sets the exception that was thrown
	 * @param errorException the thrown exception
	 */
	public void setErrorException(Exception errorException) {
		this.errorException = errorException;
	}
}

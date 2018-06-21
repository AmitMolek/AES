package root.dao.message;

/**
 * A class that transmits messages between the server and the client when there
 * is exception in the server
 * @author gal
 *	this dao message class used by the server, when there's a problem in client's request. 
 */
public class ErrorMessage extends AbstractMessage {
	
	// Instance variables **********************************************
	
	/**
	 * The exception that occurred on the server side
	 */
	private Exception errorException;

	
	// CONSTRUCTORS *****************************************************
	
	/**
	 * Constructs the CourseMessage
	 * @param exception �he exception that occurred on the server side
	 */
	public ErrorMessage(Exception exception) {
		super("error");
		this.errorException = exception;
	}

	// CLASS METHODS *************************************************
	
	/**
	 * 
	 * Returns the type of message
	 */
	@Override
	public String getType() {
		return "ErrorMesage";
	}

	/**
	 * Prints the exception message
	 */
	@Override
	public String toString() {
		return errorException.getMessage();
	}

	/**
	 * A method that returns the exception
	 * 
	 * @return the exception
	 */
	public Exception getErrorException() {
		return errorException;
	}

	/**
	 * A method that set exception
	 * @param errorException the exception
	 */
	public void setErrorException(Exception errorException) {
		this.errorException = errorException;
	}
	

}

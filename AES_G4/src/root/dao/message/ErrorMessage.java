package root.dao.message;

/**
 * 
 * @author gal
 *	this dao message class used by the server, when there's a problem in client's request. 
 */
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
		return errorException.getMessage();
	}

	public Exception getErrorException() {
		return errorException;
	}

	public void setErrorException(Exception errorException) {
		this.errorException = errorException;
	}
	

}

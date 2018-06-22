package root.dao.message;
/**
 * 
 * @author Alon Ben-yosef
 * A class containing an SQL query for the server to run, used by PrincipalGradesController
 */
public class QueryMessage extends AbstractMessage {
	
	private static final long serialVersionUID = 1L;
	/**
	 * The query for the server to run
	 */
	private String query;
	
	/**
	 * Creates a QueryMessage
	 * @param msg the message
	 * @param query the query for the server to run
	 */
	public QueryMessage(String msg, String query) {
		super(msg);
		this.query = query;
	}

	@Override
	public String getType() {
		return "Query";
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

}

package root.dao.message;

public class QueryMessage extends AbstractMessage {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String query;
	
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

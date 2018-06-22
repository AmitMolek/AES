package root.dao.message;
import root.dao.app.Statistic;
/**
 * 
 * @author Alon Ben-yosef
 * A message with a statistics field to be used by Histograms
 */
public class StatsMessage extends AbstractMessage {

	private static final long serialVersionUID = 1L;
	/**
	 * Statistics the server returns
	 */
	private Statistic stats;
	
	/**
	 * Creates a StatsMessage
	 * @param msg the message
	 * @param payload an instance of Statistics
	 */
	public StatsMessage(String msg, Statistic payload) {
		this.setMsg(msg);;
		this.setStats(payload);
	}

	@Override
	public String getType() {
		return "Stats";
	}

	public Statistic getStats() {
		return stats;
	}

	public void setStats(Statistic stats) {
		this.stats = stats;
	}

}
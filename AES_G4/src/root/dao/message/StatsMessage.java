package root.dao.message;
import java.util.ArrayList;
import root.dao.app.Statistic;

@SuppressWarnings("serial")
public class StatsMessage extends AbstractMessage {

	private Statistic stats;
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
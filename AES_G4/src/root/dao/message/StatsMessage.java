package root.dao.message;
import java.util.ArrayList;
import root.dao.app.Statistic;

@SuppressWarnings("serial")
public class StatsMessage extends AbstractMessage {

	private ArrayList<Statistic> stats;
	public StatsMessage(ArrayList<Statistic> payload) {
		this.setStats(payload);
	}

	@Override
	public String getType() {
		return "Stats";
	}

	public ArrayList<Statistic> getStats() {
		return stats;
	}

	public void setStats(ArrayList<Statistic> stats) {
		this.stats = stats;
	}

}

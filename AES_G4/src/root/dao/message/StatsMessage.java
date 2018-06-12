package root.dao.message;
import java.util.ArrayList;
import root.dao.app.Statistic;

@SuppressWarnings("serial")
public class StatsMessage extends AbstractMessage {

	private ArrayList<Statistic> stats;
	public StatsMessage(ArrayList<Statistic> payload) {
		this.stats=payload;
	}

	@Override
	public String getType() {
		return "Stats";
	}

}

package root.client.controllers;
import java.text.DecimalFormat;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import root.client.managers.DataKeepManager;
import root.dao.app.Statistic;
/**
 * 
 * @author Alon Ben-yosef
 * A controller for histogram screen, the controller pulls relevant data from DataKeepManager and presents it as graphs
 */
public class HistogramsScreenController {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private Text titleText;

    @FXML
    private Text medianText;

    @FXML
    private Text averageText;

    /**
     * Used for building the graph's Y axis
     */
    private CategoryAxis xAxis;
    
    /**
     * Used for building the graph's X axis
     */
    private NumberAxis yAxis;
    
    /**
     * Is called as JavaFX loads the scene, it will pull the data from DataKeepManager if available or it will present an epty graph otherwise
     */
	public void initialize() {
		Statistic stat=(Statistic) DataKeepManager.getInstance().getObject("statsForHistogram");
		String title=(String) DataKeepManager.getInstance().getObject("titleForHistogram");
		if(stat==null||title==null) {
		XYChart.Series dataSeries=new XYChart.Series();
		dataSeries.setName("No Data");
		averageText.setText("null");
		medianText.setText("null");
		dataSeries.getData().add(new XYChart.Data("0-10",0));
		dataSeries.getData().add(new XYChart.Data("11-20",0));
		dataSeries.getData().add(new XYChart.Data("21-30",0));
		dataSeries.getData().add(new XYChart.Data("31-40",0));
		dataSeries.getData().add(new XYChart.Data("41-50",0));
		dataSeries.getData().add(new XYChart.Data("51-60",0));
		dataSeries.getData().add(new XYChart.Data("61-70",0));
		dataSeries.getData().add(new XYChart.Data("71-80",0));
		dataSeries.getData().add(new XYChart.Data("81-90",0));
		dataSeries.getData().add(new XYChart.Data("91-100",0));
		barChart.getData().add(dataSeries);
		}
		else {
			updateHistograms(title, stat);
		}
	}
	/**
	 * Will update the histograms with data from DataKeepManager
	 * @param title the title for the graph
	 * @param stat the statisitic to show
	 */
	public void updateHistograms(String title,Statistic stat) {
		DecimalFormat numberFormat = new DecimalFormat("#.00");
		barChart.getData().removeAll();
		XYChart.Series dataSeries=new XYChart.Series();
		barChart.setTitle(title);
		dataSeries.setName("Student Grades");
		dataSeries.getData().add(new XYChart.Data("0-10",stat.getGrade_derivative_0_10()));
		dataSeries.getData().add(new XYChart.Data("11-20",stat.getGrade_derivative_11_20()));
		dataSeries.getData().add(new XYChart.Data("21-30",stat.getGrade_derivative_21_30()));
		dataSeries.getData().add(new XYChart.Data("31-40",stat.getGrade_derivative_31_40()));
		dataSeries.getData().add(new XYChart.Data("41-50",stat.getGrade_derivative_41_50()));
		dataSeries.getData().add(new XYChart.Data("51-60",stat.getGrade_derivative_51_60()));
		dataSeries.getData().add(new XYChart.Data("61-70",stat.getGrade_derivative_61_70()));
		dataSeries.getData().add(new XYChart.Data("71-80",stat.getGrade_derivative_71_80()));
		dataSeries.getData().add(new XYChart.Data("81-90",stat.getGrade_derivative_81_90()));
		dataSeries.getData().add(new XYChart.Data("91-100",stat.getGrade_derivative_91_100()));
		medianText.setText("Median: "+(int)stat.getExams_median());
		averageText.setText("Average: "+numberFormat.format(stat.getExams_avg()));
		barChart.getData().add(dataSeries);
	}
	
}
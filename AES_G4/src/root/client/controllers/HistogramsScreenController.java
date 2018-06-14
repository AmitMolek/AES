package root.client.controllers;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import root.client.managers.DataKeepManager;
import root.dao.app.Statistic;

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

    private CategoryAxis xAxis;
    private NumberAxis yAxis;
    
    public static final String HISTOGRAM_CONTROLLER_NAME="HistogramController";
    
	public void initialize() {
		DataKeepManager.getInstance().keepObject(HISTOGRAM_CONTROLLER_NAME, this);
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
	
	public void updateHistograms(String title,Statistic stat) {
		barChart.getData().removeAll();
		titleText.setText(title);
		XYChart.Series dataSeries=new XYChart.Series();
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
		barChart.getData().add(dataSeries);
	}
}

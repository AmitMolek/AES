package root.client.controllers;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.layout.AnchorPane;
import ocsf.client.ObservableClient;
import root.client.managers.DataKeepManager;
import root.dao.app.Exam;
import root.dao.app.Question;
import root.dao.message.AbstractMessage;
import root.dao.message.ExamMessage;
import root.dao.message.MessageFactory;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;

public class TestGradesTeacherController implements Observer {
	@FXML
	private AnchorPane anchorPane;
	@FXML
	private TableView<Exam> table;
	@FXML
	private TableColumn<Exam,String> examNumCol;
	@FXML
	private TableColumn dateCol;
	@FXML
	private TableColumn subjectCol;
	@FXML
	private Button viewReportButton;
	
	private ObservableClient client;	
	private ObservableList<Exam> examsList;

	
	@FXML
	public void initialize() throws IOException {
		client = (ObservableClient)DataKeepManager.getInstance().getObject_NoRemove("client");// get the client from DataKeep, but dont remove it from there, for later use.
    	client.addObserver(this);
    	AbstractMessage msg=MessageFactory.getInstance().getMessage("get-exams",""); //Temporary bad solution
    	client.sendToServer(msg);
    	examNumCol.setCellValueFactory(
    		    new PropertyValueFactory<>("examId"));
    	//dateCol.setCellValueFactory(
    	//	    new PropertyValueFactory<Exam,String>("date"));
    	//subjectCol.setCellValueFactory(
    	//		new PropertyValueFactory<Exam,String>("subject"));
    		
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof ExamMessage && ((ExamMessage) arg).getMsg().equals("ok-get-exams")) {
			ExamMessage examMessage=(ExamMessage) arg;
			ObservableList<Exam> examsList=FXCollections.observableArrayList();
			examsList.addAll(examMessage.getExams());
			table.setItems(examsList);
			
		}
	}
	
}

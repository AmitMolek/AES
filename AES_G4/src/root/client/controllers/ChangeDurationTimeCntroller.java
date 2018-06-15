package root.client.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.util.converter.IntegerStringConverter;
import ocsf.client.ObservableClient;
import root.client.managers.DataKeepManager;
import root.dao.app.Exam;
import root.dao.app.ExecuteExam;
import root.dao.app.QuestionInExam;
import root.dao.message.ChangeTimeDurationRequest;
import root.dao.message.ExecutedExamsMessage;
import root.dao.message.MessageFactory;

public class ChangeDurationTimeCntroller implements Observer{
	ObservableClient client;
		
	@FXML
	private TextArea  txtNotes;

    @FXML
    private TableColumn<ExecuteExam, Integer> durationTime;

    @FXML
    private TableColumn<ExecuteExam, String> examId;

    @FXML
    private TableColumn<ExecuteExam, String> examType;

    @FXML
    private TableColumn<ExecuteExam, String> startTime;

    @FXML
    private TableColumn<ExecuteExam, String> examPassword;
    
    @FXML
    private TableView<ExecuteExam> exeTable;
    
    MessageFactory messageFact;
    
    
    
    @FXML
	public void initialize() {  	
    	exeTable.setEditable(true);
    	messageFact = MessageFactory.getInstance();
    	durationTime.setCellValueFactory(new PropertyValueFactory("durationTime"));
    	examId.setCellValueFactory(new PropertyValueFactory("examId"));
    	examType.setCellValueFactory(new PropertyValueFactory("examType"));
    	startTime.setCellValueFactory(new PropertyValueFactory("startTime"));
    	examPassword.setCellValueFactory(new PropertyValueFactory("examPassword"));
		client = (ObservableClient) DataKeepManager.getInstance().getObject_NoRemove("client");
		client.addObserver(this);
		try {
			client.openConnection();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		durationTime.setCellFactory(
				TextFieldTableCell.<root.dao.app.ExecuteExam, Integer>forTableColumn(new IntegerStringConverter()));

		ExecutedExamsMessage executedMsg = (ExecutedExamsMessage) messageFact.getMessage("get-executed", null);
		try {
			client.sendToServer(executedMsg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg1 instanceof ExecutedExamsMessage)
		{
			ExecutedExamsMessage msg = (ExecutedExamsMessage)arg1;
			ObservableList<ExecuteExam> observerseExecuteExams = FXCollections.observableArrayList();
			ArrayList<ExecuteExam> arr = msg.getExecutedExams();
			for(ExecuteExam e:arr) {
				observerseExecuteExams.add(e);
			}
			exeTable.setItems(observerseExecuteExams);
		}

		}
	
    @FXML
    void updateTime(TableColumn.CellEditEvent<ExecuteExam, Integer> durationEditEvent) {
    	ExecuteExam executed = exeTable.getSelectionModel().getSelectedItem();
		ChangeTimeDurationRequest cht = new ChangeTimeDurationRequest();
		Integer newValue = durationEditEvent.getNewValue();
		executed.setDurationTime(newValue);
		cht.setMessageFromTeacher(txtNotes.getText());
		cht.setExamId(executed.getExamId());
		cht.setNewTime(newValue);
		
		SendRequests se = new SendRequests();
		try {
			se.send(cht);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
}

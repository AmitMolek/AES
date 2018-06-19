package root.client.controllers;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ocsf.client.ObservableClient;
import root.client.managers.DataKeepManager;
import root.client.managers.ScreensManager;
import root.dao.message.AbstractMessage;
import root.dao.message.ErrorMessage;
import root.dao.message.MessageFactory;
import root.dao.message.StatsMessage;
import javafx.scene.control.CheckBox;

public class TestGradesPrincipalController implements Observer{
	@FXML
	private AnchorPane anchorPane;
	@FXML
	private ComboBox<String> queriesComboBox;
	@FXML
	private Button showRawTablesButton;
	@FXML
	private Button executeButton;
	@FXML
	private CheckBox checkBox1;
	@FXML
	private TextField value1Field;
	@FXML
	private CheckBox checkBox2;
	@FXML
	private TextField value2Field;
	@FXML
	private CheckBox checkBox3;
	@FXML
	private TextField value3Field;
	 @FXML
	private Text errorText;

	private ObservableClient client;	

	public void initialize() {
		client = (ObservableClient)DataKeepManager.getInstance().getObject_NoRemove("client");// get the client from DataKeep, but dont remove it from there, for later use.
    	client.addObserver(this);
		String[] queryNameArray= {"Exams By Approving Teacher","Exams By Student","Exams By Course"};
		ObservableList<String> queries=FXCollections.observableArrayList(queryNameArray);
		queriesComboBox.setItems(queries);
	}
	
    @FXML
    void executeQuery(ActionEvent event) {
    	String query="";
    	try {
	    	switch(queriesComboBox.getValue()) {
			case "Exams By Approving Teacher":
				query=examsByTeacherQueryBuilder();
				break;
			case "Exams By Student":
				query=examsByStudentQueryBuilder();
	    		break;
			case "Exams By Course":
				query=examsByCourseQueryBuilder();
	    		break;
	    	default:
	    		return;
	    	}
	    	System.out.println(query);
	    	AbstractMessage msg= MessageFactory.getInstance().getMessage("get-query", query);
			client.sendToServer(msg);
    	}
	    catch (IOException e) {
				showAlert("Error Communicating With AES Sever","Please contact system administrator");
			}
    	catch (Exception e) {
    		showAlert("Query Error",e.getMessage());
			}
    	
    }
    
    private void showAlert(String header,String errorMessage) {
    	errorText.setText(header+" - "+errorMessage);
    	errorText.setVisible(true);
    }

    private String examsByStudentQueryBuilder() throws Exception {
		String query=null;
		if(checkBox1.isSelected()) {
    		if(value1Field.getText().isEmpty()) throw new Exception("Fields cannot be empty");
			query="SELECT se.solved_exam_grade \r\n" + 
					"FROM aes.`solved exams` se \r\n" + 
					"WHERE se.User_ID='"+value1Field.getText()+"' AND se.grade_approval_by_teacher='approved';";
		}
		else if(checkBox2.isSelected()) {
			if(value2Field.getText().isEmpty()) throw new Exception("Fields cannot be empty");
			String[] name=value2Field.getText().split(" ");
			if(name.length!=2) throw new Exception("A full name is required");
			name[0]=toStandardNameConvention(name[0]);
			name[1]=toStandardNameConvention(name[1]);
			query="SELECT se.solved_exam_grade \r\n" + 
					"FROM aes.`solved exams` se, aes.`users` u " + 
					"WHERE se.User_ID=u.Users_ID AND u.User_FIrst_Name='"+name[0]+"' "+
					"AND u.User_Last_Name='"+name[1]+"' AND se.grade_approval_by_teacher='approved';";
		}
		return query;
	}

	private String examsByTeacherQueryBuilder() throws Exception {
    	String query=null;
    	if(checkBox1.isSelected()) {
    		if(value1Field.getText().isEmpty()) throw new Exception("Fields cannot be empty");
			query="SELECT se.solved_exam_grade " + 
					"FROM aes.`solved exams` se, aes.exams e "+
					"WHERE se.exam_ID=e.exam_id AND se.approving_teacher_id='"+value1Field.getText()+"' "+
					"AND se.grade_approval_by_teacher='approved';";
		}
		else if(checkBox2.isSelected()) {
			if(value2Field.getText().isEmpty()) throw new Exception("Fields cannot be empty");
			String[] name=value2Field.getText().split(" ");
			if(name.length!=2) throw new Exception("A full name is required");
			name[0]=toStandardNameConvention(name[0]);
			name[1]=toStandardNameConvention(name[1]);
			query="SELECT se.solved_exam_grade \r\n" + 
					"FROM aes.`solved exams` se, aes.`users` u \r\n" + 
					"WHERE se.approving_teacher_id=u.Users_ID AND User_FIrst_Name='"+name[0]+"' AND User_Last_Name='"+name[1]+"' AND se.grade_approval_by_teacher='approved';";
		}
    	return query;
	}

	private String examsByCourseQueryBuilder() {
		return null;
	}
	
	private String toStandardNameConvention(String name) {
    	String fullname=name.toLowerCase();
		String prefix=fullname.substring(0, 1);
		prefix=prefix.toUpperCase();
		return prefix+fullname.substring(1);
	}
	
	/***
	 * @author Alon Ben-yosef
 	* Calls perparePageForQuery()
 	* @param event thrown by JavaFX
 	*/
	@FXML
    void queryChosen(ActionEvent event) {
    	preparePageForQuery();
    }
	/***
     * @author Alon Ben-yosef
     * Changes the field names for the relevant query
     */
    private void preparePageForQuery() {
    	switch(queriesComboBox.getValue()) {
    		case "Exams By Approving Teacher":
    				checkBox1.setText("Approving Teacher ID");
    				checkBox1.setVisible(true);
    				value1Field.setVisible(true);
    				checkBox2.setText("Approving Teacher Name");
    				checkBox2.setVisible(true);
    				value2Field.setVisible(true);
    			break;
    		case "Exams By Student":
	    			checkBox1.setText("Student ID");
					checkBox1.setVisible(true);
					value1Field.setVisible(true);
					checkBox2.setText("Student Name");
					checkBox2.setVisible(true);
					value2Field.setVisible(true);
        		break;
    		case "Exams By Course":
	    			checkBox1.setText("Course ID");
					checkBox1.setVisible(true);
					value1Field.setVisible(true);
					checkBox2.setText("Course Name");
					checkBox2.setVisible(true);
					value2Field.setVisible(true);
        		break;
    	}
    	resetCheckBoxes();
	}

    /***
     * @author Alon Ben-yosef
     * Resets the checkboxs states to default
     */
	private void resetCheckBoxes() {
		errorText.setVisible(false);
		value1Field.setEditable(true);
		value1Field.setText("");
    	value2Field.setEditable(false);
		value2Field.setText("");
    	value3Field.setEditable(false);
		value3Field.setText("");
    	checkBox1.setSelected(true);
    	checkBox2.setSelected(false);
    	checkBox3.setSelected(false);
    	executeButton.setVisible(true);		
	}

	@FXML
    void showRawData(ActionEvent event) {

    }
	
	/***
     * @author Alon Ben-yosef
     * Makes only the first text field editable and cleans the rest if first checkbox is selected, otherwise disables it
     * @param event thrown by JavaFX
     */
    @FXML
    void set1Editable(ActionEvent event) {
    	errorText.setVisible(false);
    	if(checkBox1.isSelected()) {
    		value1Field.setEditable(true);
        	value2Field.setEditable(false);
        	value2Field.setText("");
        	value3Field.setEditable(false);
        	value3Field.setText("");
        	checkBox2.setSelected(false);
        	checkBox3.setSelected(false);
    	}
    	else {
    		value1Field.setEditable(false);   
    	}
    }
    
    /***
     * @author Alon Ben-yosef
     * Makes only the second text field editable and cleans the rest if second checkbox is selected, otherwise disables it
     * @param event thrown by JavaFX
     */
    @FXML
    void set2Editable(ActionEvent event) {
    	errorText.setVisible(false);
    	if(checkBox2.isSelected()) {
    		value2Field.setEditable(true);
        	value1Field.setEditable(false);
        	value1Field.setText("");
        	value3Field.setEditable(false);
        	value3Field.setText("");
        	checkBox1.setSelected(false);
        	checkBox3.setSelected(false);
    	}
    	else {
    		value2Field.setEditable(false);   
    	}
    }
    /***
     * @author Alon Ben-yosef
     * Makes only the third text field editable and cleans the rest if third checkbox is selected, otherwise disables it
     * @param event thrown by JavaFX
     */
    @FXML
    void set3Editable(ActionEvent event) {
    	errorText.setVisible(false);
    	if(checkBox3.isSelected()) {
    		value3Field.setEditable(true);
        	value2Field.setEditable(false);
        	value2Field.setText("");
        	value1Field.setEditable(false);
        	value1Field.setText("");
        	checkBox2.setSelected(false);
        	checkBox1.setSelected(false);
    	}
    	else {
    		value3Field.setEditable(false);   
    	}
    }

	@Override
	public void update(Observable arg0, Object msg) {
		if(msg instanceof AbstractMessage) {
			if(((AbstractMessage) msg).getMsg().equals("ok-get-query")) {
				StatsMessage statsMessage=(StatsMessage)msg;
				DataKeepManager.getInstance().updateObject("statsForHistogram", statsMessage.getStats());
				DataKeepManager.getInstance().updateObject("titleForHistogram", buildTitleForHistogram());
				Platform.runLater(() -> {				// In order to run javaFX thread.(we recieve from server a java thread)
						try {
							ScreensManager.getInstance().activate("histograms");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					});
			}
			if(((AbstractMessage) msg).getMsg().equals("error")) {
				ErrorMessage errmsg=(ErrorMessage)msg;
				showAlert("Error occurred in AES server", errmsg.getErrorException().getMessage());
			}
		}		
	}

	private String buildTitleForHistogram() {
		String queryName=queriesComboBox.getValue();
		switch (queryName) {
		case "Exams By Approving Teacher":
			return queryName+" For "+getTeacherTitle();
		case "Exams By Student":
			return queryName+" For "+getStudentTitle();
		case "Exams By Course":
			return queryName+" For "+getCourseTitle();
		}
		return "";
	}

	private String getCourseTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	private String getStudentTitle() {
		if(checkBox1.isSelected())	return "ID = '"+value1Field.getText()+"'";
		else if (checkBox2.isSelected()) {
			String[] name=value2Field.getText().split(" ");
			name[0]=toStandardNameConvention(name[0]);
			name[1]=toStandardNameConvention(name[1]);
			return "Name = '"+name[0]+" "+name[1]+"'";
		}
		else return "";
	}

	/**
	 * @author Alon Ben-yosef
	 * 
	 * @return
	 */
	private String getTeacherTitle() {
		if(checkBox1.isSelected())	return "ID = '"+value1Field.getText()+"'";
		else if (checkBox2.isSelected()) {
			String[] name=value2Field.getText().split(" ");
			name[0]=toStandardNameConvention(name[0]);
			name[1]=toStandardNameConvention(name[1]);
			return "Name = '"+name[0]+" "+name[1]+"'";
		}
		else return "";
	}

}

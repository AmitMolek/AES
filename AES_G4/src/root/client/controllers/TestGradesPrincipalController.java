package root.client.controllers;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
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
import ocsf.client.ObservableClient;
import root.client.managers.DataKeepManager;
import root.client.managers.ScreensManager;
import root.dao.message.AbstractMessage;
import root.dao.message.ErrorMessage;
import root.dao.message.MessageFactory;
import root.dao.message.StatsMessage;
import javafx.scene.control.CheckBox;
/**
 * 
 * @author Alon Ben-yosef
 * TestGradesPrincipalController is a controller class for TestGradesPrincipal which allows the principal to view customized reports
 * and queries.
 */

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
	/**
	 * An instance of client, used for observing
	 */
	private ObservableClient client;	

    
    /**
     * Is called as JavaFX loads the scene, this will define the queries in the combobox.
     */
	public void initialize() {
		client = (ObservableClient)DataKeepManager.getInstance().getObject_NoRemove("client");// get the client from DataKeep, but dont remove it from there, for later use.
    	client.addObserver(this);
		String[] queryNameArray= {"Exams By Approving Teacher","Exams By Student","Exams By Course"};
		ObservableList<String> queries=FXCollections.observableArrayList(queryNameArray);
		queriesComboBox.setItems(queries);
	}
	
    @FXML
    /**
     * Is called once the user clicks 'Execute Query'
     * @param event thrown by JavaFX on click on the 'Execute Query' and call the relevant QueryBuilder.
     */
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
    
    /**
     * This will show a temporary warning text for invalid queries or server errors
     * @param header the head of the warning
     * @param errorMessage the content of thrown exception
     */
    private void showAlert(String header,String errorMessage) {
        	Platform.runLater(() -> {								
                Alert alert = new Alert(AlertType.ERROR);
                alert.initOwner(ScreensManager.getInstance().getPrimaryStage());
                alert.setTitle("Error");
                alert.setHeaderText(header);
                alert.setContentText(errorMessage);
                alert.showAndWait();       
    		});
    }

    /**
     * Builds a query for student
     * @return a query to run in the server
     * @throws Exception on case of invalid input
     */
    private String examsByStudentQueryBuilder() throws Exception {
		String query=null;
		if(checkBox1.isSelected()) {
			String text=value1Field.getText().trim();
    		if(text.isEmpty()) throw new Exception("Fields cannot be empty");
			if(!isNumber(text)) throw new Exception("Enter numeric values only"); 
			query="SELECT se.solved_exam_grade " + 
					"FROM aes.`solved exams` se " + 
					"WHERE se.User_ID='"+text+"' AND se.grade_approval_by_teacher='approved';";
		}
		else if(checkBox2.isSelected()) {
			String text=value2Field.getText().trim();
			if(text.isEmpty()) throw new Exception("Fields cannot be empty");
			String[] name=text.split(" ", 2);
			if(name.length<2) throw new Exception("A full name is required");
			name[0]=toStandardNameConvention(name[0]);
			name[1]=toStandardNameConvention(name[1]);
			query="SELECT se.solved_exam_grade \r\n" + 
					"FROM aes.`solved exams` se, aes.`users` u " + 
					"WHERE se.User_ID=u.Users_ID AND u.User_FIrst_Name='"+name[0]+"' "+
					"AND u.User_Last_Name='"+name[1]+"' AND se.grade_approval_by_teacher='approved';";
		}
		return query;
	}

    /**
     * Builds a query for teacher
     * @return a query to run in the server
     * @throws Exception on case of invalid input
     */
	private String examsByTeacherQueryBuilder() throws Exception {
    	String query=null;
    	if(checkBox1.isSelected()) {
    		String text=value1Field.getText().trim();
    		if(text.isEmpty()) throw new Exception("Fields cannot be empty");
			if(!isNumber(text)) throw new Exception("Enter numeric values only"); 
			query="SELECT se.solved_exam_grade " + 
					"FROM aes.`solved exams` se, aes.exams e "+
					"WHERE se.exam_ID=e.exam_id AND se.approving_teacher_id='"+value1Field.getText()+"' "+
					"AND se.grade_approval_by_teacher='approved';";
		}
		else if(checkBox2.isSelected()) {
			String[] name=value2Field.getText().trim().split(" ",2);
    		if(name.length==0) throw new Exception("Fields cannot be empty");
			if(name.length<2) throw new Exception("A full name is required");
			name[0]=toStandardNameConvention(name[0]);
			name[1]=toStandardNameConvention(name[1]);
			query="SELECT se.solved_exam_grade \r\n" + 
					"FROM aes.`solved exams` se, aes.`users` u \r\n" + 
					"WHERE se.approving_teacher_id=u.Users_ID AND User_FIrst_Name='"+name[0]+"' AND User_Last_Name='"+name[1]+"' AND se.grade_approval_by_teacher='approved';";
		}
    	return query;
	}

	/**
	 * Checks is a string is a number
	 * @param s a String
	 * @return true if s is a number, false otherwise
	 */
	private boolean isNumber(String s) {
		try {
			Integer.parseInt(s);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
	
	/**
     * Builds a query for course
     * @return a query to run in the server
     * @throws Exception on case of invalid input
     */
	private String examsByCourseQueryBuilder() throws Exception {
		String query=null;
		if(checkBox1.isSelected()) {
			String text=value1Field.getText().trim();
    		if(text.isEmpty()) throw new Exception("Fields cannot be empty");
			if(text.length()!=4) throw new Exception("Enter subject code and course code only");
			if(!isNumber(text)) throw new Exception("Enter numeric values only"); 
			String subjectId, courseId;
			subjectId=text.substring(0,2);
			courseId=text.substring(2,4);
			query="SELECT se.solved_exam_grade " + 
					"FROM aes.`solved exams` se, aes.`courses` c, aes.`subjects` s, aes.`courses in subject` cis " + 
					"WHERE s.subject_id=SUBSTR(se.exam_ID,1,2) AND c.course_id=SUBSTR(se.exam_ID,3,2) AND cis.course_id=c.course_id " + 
					"AND cis.subject_id=s.subject_id AND s.subject_id = '"+subjectId+"' AND c.course_id='"+courseId+"' AND se.grade_approval_by_teacher='approved';";
		}
		else if(checkBox2.isSelected()) {
			String text=value2Field.getText().trim();
    		if(text.isEmpty()) throw new Exception("Fields cannot be empty");
			String[] splitInp=text.split(" ", 2);
			if(splitInp.length!=2) throw new Exception("Enter course subject and course name");
			String subject=toStandardNameConvention(splitInp[0]);
			String course=toStandardNameConvention(splitInp[1]);
			query="SELECT se.solved_exam_grade\r\n" + 
					"FROM aes.`solved exams` se, aes.`courses` c, aes.`subjects` s, aes.`courses in subject` cis " + 
					"WHERE s.subject_id=SUBSTR(se.exam_ID,1,2) AND c.course_id=SUBSTR(se.exam_ID,3,2) AND cis.course_id=c.course_id " + 
					"AND cis.subject_id=s.subject_id AND s.subject_name = '"+subject+"' AND c.course_name='"+course+"' AND se.grade_approval_by_teacher='approved';";
		}
		return query;
	}
	/**
	 * Converts a string to a name format (Uppercase letter, rest is lowercase)
	 * @param name The name to convert
	 * @return a name in "Name" format
	 */
	private String toStandardNameConvention(String name) {
    	String fullname=name.toLowerCase();
		String prefix=fullname.substring(0, 1);
		prefix=prefix.toUpperCase();
		return prefix+fullname.substring(1);
	}
	
	/**
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
	    			checkBox1.setText("SubjectID + Course ID");
					checkBox1.setVisible(true);
					value1Field.setVisible(true);
					checkBox2.setText("Subject Name + Course Name");
					checkBox2.setVisible(true);
					value2Field.setVisible(true);
        		break;
    	}
    	resetCheckBoxes();
	}

    /**
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
		Platform.runLater(() -> {				// In order to run javaFX thread.(we recieve from server a java thread)
			try {
				ScreensManager.getInstance().activate("rawDataView");
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
    }
	
	/**
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
    
    /**
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
    /**
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
				ScreensManager.getInstance().switchScreen("histograms");
			}
			if(((AbstractMessage) msg).getMsg().equals("error")) {
				ErrorMessage errmsg=(ErrorMessage)msg;
				showAlert("Error occurred in AES server", errmsg.getErrorException().getMessage());
			}
		}		
	}
	
	/**
	 * Builds a title for graphs depends on chosen query
	 * @return a relevant title for the histogram
	 */
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

	/**
	 * Builds a title for course queries
	 * @return an appropriate title
	 */
	private String getCourseTitle() {
		if(checkBox1.isSelected()) {
			String text=value1Field.getText().trim();
			String subjectId, courseId;
			subjectId=text.substring(0,2);
			courseId=text.substring(2,4);
			return "Subject ID='"+subjectId+"', Course ID='"+courseId+"'";
		}
		else if(checkBox2.isSelected()) {
			String text=value2Field.getText().trim();
			String[] splitInp=text.split(" ", 2);
			String course=toStandardNameConvention(splitInp[1]);
			return "Course Name='"+course+"'";
		}
		return "";
	}

	/**
	 * Builds a title for student queries
	 * @return an appropriate title
	 */
	private String getStudentTitle() {
		if(checkBox1.isSelected())	return "ID = '"+value1Field.getText()+"'";
		else if (checkBox2.isSelected()) {
			String[] name=value2Field.getText().split(" ",2);
			name[0]=toStandardNameConvention(name[0]);
			name[1]=toStandardNameConvention(name[1]);
			return "Name = '"+name[0]+" "+name[1]+"'";
		}
		else return "";
	}

	/**
	 * Builds a title for teacher queries
	 * @return an appropriate title
	 */
	private String getTeacherTitle() {
		if(checkBox1.isSelected())	return "ID = '"+value1Field.getText()+"'";
		else if (checkBox2.isSelected()) {
			String[] name=value2Field.getText().split(" ",2);
			name[0]=toStandardNameConvention(name[0]);
			name[1]=toStandardNameConvention(name[1]);
			return "Name = '"+name[0]+" "+name[1]+"'";
		}
		else return "";
	}

}

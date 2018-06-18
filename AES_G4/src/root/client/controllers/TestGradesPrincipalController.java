package root.client.controllers;

import java.util.Observable;
import java.util.Observer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import javafx.scene.control.ComboBox;

import javafx.scene.layout.AnchorPane;

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
	
	public void initialize() {
		String[] queryNameArray= {"Exams By Teacher","Exams By Student","Exams By Course"};
		ObservableList<String> queries=FXCollections.observableArrayList(queryNameArray);
		queriesComboBox.setItems(queries);
	}
	
    @FXML
    void executeQuery(ActionEvent event) {
    	
    	StringBuilder sb=new StringBuilder("SELECT * FROM aes.`solved exams` se,");
    	switch(queriesComboBox.getValue()) {
		case "Exams By Teacher":
			if(checkBox1.isSelected()) {
				sb.append("aes.'users' u,aes.'exams' e ");
				sb.append("WHERE ");
			}
			break;
		case "Exams By Student":
    		
    		break;
		case "Exams By Course":
    	
    		break;
    	}
    }

    @FXML
    void queryChosen(ActionEvent event) {
    	preparePageForQuery();
    	resetCheckBoxes();
    }

    private void preparePageForQuery() {
    	switch(queriesComboBox.getValue()) {
    		case "Exams By Teacher":
    				checkBox1.setText("Teacher ID");
    				checkBox1.setVisible(true);
    				value1Field.setVisible(true);
    				checkBox2.setText("Teacher Name");
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

	private void resetCheckBoxes() {
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
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
    
}

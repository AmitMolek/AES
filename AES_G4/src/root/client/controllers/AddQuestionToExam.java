package root.client.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import root.client.Main;

public class AddQuestionToExam extends AnchorPane {
	private static int count = 0;
	private String id;
	
	@FXML
    private FlowPane MyFlow;

    @FXML
    private AnchorPane rootPane;
    
    public AddQuestionToExam()
    {
    	FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("resources/view/AddQuestionToAnExam.fxml"));
    	fxmlLoader.setRoot(this);
    	fxmlLoader.setController(this);
    	id = Integer.toString(count);
    	count++;
    	try {
			fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public String getID() {
    	return id;
    }
    
    

}
package root.client.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import root.client.Main;

public class AddQuestionToExam extends AnchorPane {
	private static int count = 0;
	private String id;
	
	@FXML
    private FlowPane MyFlow;

    @FXML
    private AnchorPane rootPane;
    
    public AddQuestionToExam()
    {
    	FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("resources/view/AddQuestionToAnExam.fxml"));
    	fxmlLoader.setRoot(this);
    	fxmlLoader.setController(this);
    	id = Integer.toString(count);
    	count++;
    	try {
			fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public String getID() {
    	return id;
    }
    
    

}

package root.client.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import root.client.Main;

public class AddQuestionToExam extends AnchorPane {
	private static int count = 0;
	private String id;
	
	@FXML
    private FlowPane MyFlow;

    @FXML
    private AnchorPane rootPane;
    
    public AddQuestionToExam()
    {
    	FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("resources/view/AddQuestionToAnExam.fxml"));
    	fxmlLoader.setRoot(this);
    	fxmlLoader.setController(this);
    	id = Integer.toString(count);
    	count++;
    	try {
			fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public String getID() {
    	return id;
    }
    
    

}

package root.client.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import root.client.Main;

public class AddQuestionToExam extends AnchorPane {
	private static int count = 0;
	private String id;
	
	@FXML
    private FlowPane MyFlow;

    @FXML
    private AnchorPane rootPane;
    
    public AddQuestionToExam()
    {
    	FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("resources/view/AddQuestionToAnExam.fxml"));
    	fxmlLoader.setRoot(this);
    	fxmlLoader.setController(this);
    	id = Integer.toString(count);
    	count++;
    	try {
			fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public String getID() {
    	return id;
    }
    
    

}

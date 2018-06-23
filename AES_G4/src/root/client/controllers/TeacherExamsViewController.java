package root.client.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.util.Duration;
import ocsf.client.ObservableClient;
import root.client.managers.DataKeepManager;
import root.client.managers.ScreensManager;
import root.dao.app.Course;
import root.dao.app.CsvDetails;
import root.dao.app.Question;
import root.dao.app.SolvedExams;
import root.dao.app.Subject;
import root.dao.app.User;
import root.dao.message.CourseMessage;
import root.dao.message.CsvMessage;
import root.dao.message.MessageFactory;
import root.dao.message.QuestionsMessage;
import root.dao.message.SolvedExamBySubjectCourseMessage;
import root.dao.message.UpdateSolvedExam;
import root.dao.message.UserSubjectMessage;
import root.util.log.Log;
import root.util.log.LogLine;

/**
 * The controller for the Teacher view solved exams
 * @author Amit Molek
 *
 */

public class TeacherExamsViewController {

    @FXML
    private AnchorPane rootPane;

    @FXML
    protected TableView<SolvedExams> tbl_viewExams;


    @FXML
    private TableColumn<SolvedExams, Boolean> tb_approve;
    
    @FXML
    protected TableColumn<SolvedExams, String> tb_examid;

    @FXML
    protected TableColumn<SolvedExams, String> tb_userid;

    @FXML
    protected TableColumn<SolvedExams, String> tb_course;

    @FXML
    protected TableColumn<SolvedExams, String> tb_executingDate;

    @FXML
    protected TableColumn<SolvedExams, String> tb_grade;

    @FXML
    protected TableColumn<SolvedExams, String> tb_explanation;

    @FXML
    protected TableColumn<SolvedExams, String> tb_suspected;

    @FXML
    protected TableColumn<SolvedExams, String> tb_download;

    @FXML
    protected Button submit_btn;

    @FXML
    private Text errorTxt;

	private DataKeepManager dkm = DataKeepManager.getInstance();

	/**
	 * Called when the submit button is pressed
	 * used to call the child Updater submit function
	 * @param event the event that happened
	 */
    @FXML
    void btn_SubmitChanges(MouseEvent event) {
    	Platform.runLater(() -> {
    		Updater.getInstance().sumbitClicked();
    	});
    }

    private static class Updater implements Observer{

    	/**
    	 * A workaround to the controller system,
    	 * it is a problem to sends messages to the server from different
    	 * threads so this singleton class is used to work around this problem
    	 */
    	
    	private ArrayList<Subject> teacherSubject;
    	private ArrayList<Course> teacherCourses;
    	private ArrayList<SolvedExams> solvedExams;
    	private ArrayList<SolvedExams> changedExams;
    	private Map<String, String> coursesNamesMap;
    	
    	private ObservableList<SolvedExams> observableSolvedExams;
    	
    	private TableView<SolvedExams> viewExamsTable;
    	private TableColumn<SolvedExams, Boolean> tb_approve;
    	private TableColumn<SolvedExams, String> tb_examid;
    	private TableColumn<SolvedExams, String> tb_userid;
    	private TableColumn<SolvedExams, String> tb_course;
    	private TableColumn<SolvedExams, String> tb_executingDate;
    	private TableColumn<SolvedExams, String> tb_grade;
    	private TableColumn<SolvedExams, String> tb_explanation;
    	private TableColumn<SolvedExams, String> tb_suspected;
    	private TableColumn<SolvedExams, String> tb_download;
    	private Text errorTxt;
    	private SolvedExams solvedExam;
    	private ArrayList<String[]> csvData;
    	private ArrayList<Question> solvedExamQuestions;
    	
    	private ObservableClient client;
    	private User user;
    	
    	private Log log = Log.getInstance();
    	private ScreensManager screenManager = ScreensManager.getInstance();
    	private MessageFactory message = MessageFactory.getInstance();
    	private static Updater up;
    	private DataKeepManager dkm = DataKeepManager.getInstance();
    	
    	private static final int ERROR_DISPLAY_TIME = 5;
    	
    	/**
    	 * Private contrutor, used to init vars
    	 */
    	private Updater() {
    		teacherSubject = new ArrayList<>();
    		teacherCourses = new ArrayList<>();
    		solvedExams = new ArrayList<>();
    		changedExams = new ArrayList<>();
    		coursesNamesMap = new HashMap<String, String>();
    		observableSolvedExams = FXCollections.observableArrayList();
    		
    	}
    	
    	/**
    	 * Observable update function
    	 */
		@Override
		public void update(Observable o, Object arg) {
			if (arg instanceof UserSubjectMessage) {
				teacherSubject = ((UserSubjectMessage)arg).getSubjects();
				getTeacherCourses();
			}else if (arg instanceof CourseMessage) {
				ArrayList<Course> courses = ((CourseMessage) arg).getCourses();

				teacherCourses.clear();
				for (Course c: courses) {
					teacherCourses.add(c);
					coursesNamesMap.put(c.getCourseId(), c.getCourseName());
				}
				
				getSolvedExams();
			}else if (arg instanceof SolvedExamBySubjectCourseMessage) {
				ArrayList<SolvedExams> se = ((SolvedExamBySubjectCourseMessage) arg).getSolvedExams(); 
				
				for (SolvedExams e : se) {
					if (checkIfToShowSolvedExam(e)) {
						String course_id = e.getExamID().substring(2, 4);
						String courseName = getCourseName(course_id);
						e.setCourseName(courseName);
						solvedExams.add(e);
					}
				}
				
				updateTableWithData(solvedExams);
			}else if (arg instanceof CsvMessage) {
				this.csvData = ((CsvMessage) arg).getCsvDetailofSolvedExam();
				getExamQuestions();
			}else if (arg instanceof QuestionsMessage) {
				this.solvedExamQuestions = ((QuestionsMessage) arg).getQuestions();
				createWord();
			}
		}
		
		/**
		 * Checks if the solved exam should be displayed to the teacher
		 * @param e the exam you want to check
		 * @return true if the exam should be displayed to the teacher
		 */
		private boolean checkIfToShowSolvedExam(SolvedExams e) {
			if (e.getApprovingTeacherID() != null) return false;
			if (e.getCheatingFlag() == null) return false;
			return true;
		}
		
		/**
		 * Updates the table with data
		 * @param solved the SolvedExams you want to populate the table with
		 */
		private void updateTableWithData(ArrayList<SolvedExams> solved) {
			Platform.runLater(() -> {
				getInstance().observableSolvedExams.clear();
				getInstance().observableSolvedExams.setAll(solved);
				getInstance().viewExamsTable.setItems(observableSolvedExams);
			});
		}
		
		/**
		 * Gets the name of the course
		 * @param course_id the id of the course you want to get the name of
		 * @return the name of the course
		 */
		private String getCourseName(String course_id) {
			for (Entry<String, String> entry : coursesNamesMap.entrySet()) {
				if (entry.getKey().equals(course_id)) {
					return entry.getValue();
				}
			}
			return "";
		}
		
		/**
		 * Sends a message to the server to get the subjects
		 */
		private void getTeacherSubjects() {
			UserSubjectMessage usm = (UserSubjectMessage) message.getMessage("get-UserSubjects", user);
			try {
				client.sendToServer(usm);
			} catch (IOException e) {
				e.printStackTrace();
				log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
			}
		}
		
		/**
		 * Sends a message to the server to get the courses
		 */
		private void getTeacherCourses() {
			for(Subject s : teacherSubject) {
				CourseMessage cm = (CourseMessage) message.getMessage("get-courses", s);
				
				try {
					client.sendToServer(cm);
				} catch (IOException e) {
					e.printStackTrace();
					log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
				}
			}
		}
		
		/**
		 * Sends a message to the server to get the solved exams
		 */
		private void getSolvedExams() {
			for (Course c : teacherCourses) {
				SolvedExamBySubjectCourseMessage cMsg = new SolvedExamBySubjectCourseMessage(null, c);

				try {
					client.sendToServer(cMsg);
				} catch (IOException e) {
					e.printStackTrace();
					log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
				}
			}
		}
		
		/**
		 * this method get all solvedExam's questions
		 */
		private void getExamQuestions() {
			ArrayList<String> questionIDList = new ArrayList<String>();
			for (String[] csvLine: csvData) {
				String questionID = csvLine[0];
				if (questionID.replace("\"", "").length() == 5) {// do this check to pass the first line of the csvData file.
					questionIDList.add(questionID);
				}
			}
			QuestionsMessage newQuestionMessage = (QuestionsMessage) message.getMessage("get-Questions",questionIDList);
			try {
				client.sendToServer(newQuestionMessage);
			} catch (IOException e) {
				e.printStackTrace();
				log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
			}
		}
		
		/**
		 * Must be called, used to init the table
		 */
		private void initExamsViewTable() {
			viewExamsTable.setEditable(true);
			tb_examid.setCellValueFactory(new PropertyValueFactory<SolvedExams, String>("examID"));
			tb_userid.setCellValueFactory(new PropertyValueFactory<SolvedExams, String>("sovingStudentID"));
			tb_course.setCellValueFactory(new PropertyValueFactory<SolvedExams, String>("courseName"));
			tb_executingDate.setCellValueFactory(new PropertyValueFactory<SolvedExams, String>("examDateTime"));
			
			tb_explanation.setCellValueFactory(new PropertyValueFactory<SolvedExams, String>("gradeAlturationExplanation"));
			tb_explanation.setEditable(true);
			tb_explanation.setCellFactory(TextFieldTableCell.forTableColumn());
			
			tb_explanation.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<SolvedExams,String>>() {
				@Override
				public void handle(CellEditEvent<SolvedExams, String> event) {
					event.getTableView().getItems().get(event.getTablePosition().getRow()).setGradeAlturationExplanation(event.getNewValue());
				}
			});
			
			tb_grade.setCellValueFactory(new PropertyValueFactory<SolvedExams, String>("strGrade"));
			tb_grade.setEditable(true);
			tb_grade.setCellFactory(TextFieldTableCell.forTableColumn());
			
			tb_grade.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<SolvedExams,String>>() {
				@Override
				public void handle(CellEditEvent<SolvedExams, String> event) {
					try {
						if (!isInteger(event.getNewValue(), 10)) {
							setErrorLabelText("Grade not a number", ERROR_DISPLAY_TIME);
							return;
						}
						int newGrade = Integer.parseInt(event.getNewValue());
						if (!isInputGradeValid(newGrade)) {
							setErrorLabelText("Grade not valid", ERROR_DISPLAY_TIME);
							return;
						}
						SolvedExams se = event.getTableView().getItems().get(event.getTablePosition().getRow());
						se.setExamGrade(newGrade);
						if (!isChangedExamExistAlready(se)) {
							se.setGradeChanged(true);
							changedExams.add(se);
						}
						
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
				}
			});
			
			tb_suspected.setCellValueFactory(new PropertyValueFactory<SolvedExams, String>("cheatingFlag"));
			tb_download.setCellValueFactory(new PropertyValueFactory<SolvedExams,String>("action"));
			tb_approve.setCellValueFactory(new PropertyValueFactory<SolvedExams, Boolean>("gg"));
			
	        Callback<TableColumn<SolvedExams, String>, TableCell<SolvedExams, String>> cellFactory
            = //
            new Callback<TableColumn<SolvedExams, String>, TableCell<SolvedExams, String>>() {
            @SuppressWarnings("rawtypes")
			@Override
            public TableCell call(final TableColumn<SolvedExams, String> param) {
            	
                final TableCell<SolvedExams, String> cell = new TableCell<SolvedExams, String>() {

                    final Button btn = new Button("Download copy");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {
                            	solvedExam = getTableView().getItems().get(getIndex());
                            	perapeDownload(solvedExam);
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }

                };
                return cell;
            }
    	};
    	
        Callback<TableColumn<SolvedExams, Boolean>, TableCell<SolvedExams, Boolean>> approveCellFactory
        = //
        new Callback<TableColumn<SolvedExams, Boolean>, TableCell<SolvedExams, Boolean>>() {
        @SuppressWarnings("rawtypes")
		@Override
        public TableCell call(final TableColumn<SolvedExams, Boolean> param) {
        	
            final TableCell<SolvedExams, Boolean> cell = new TableCell<SolvedExams, Boolean>() {

				final CheckBox checkBox = new CheckBox();
				
				@Override
                public void updateItem(Boolean item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                    	SolvedExams se = getTableView().getItems().get(getIndex());
                    	checkBox.setOnAction(event -> {
                        	if (checkBox.isSelected()) {
                        		se.setApproved(true);
                        	}else {
                        		se.setApproved(false);
                        	}
                    	});
                        setGraphic(checkBox);
                        setText(null);
                    }
                }
			};
			return cell;
        }};

	    tb_download.setCellFactory(cellFactory);
	    tb_approve.setCellFactory(approveCellFactory);
		}
		
		/**
		 * The function that the parent controller calls when the submit button is pressed
		 */
		public void sumbitClicked() {
			for (SolvedExams se : solvedExams) {
				if (se.isApproved()) {
					if ((se.getGradeAlturationExplanation() == null || se.getGradeAlturationExplanation() == "") && se.isGradeChanged()) {
						setErrorLabelText("Missing explanation", ERROR_DISPLAY_TIME);
						return;
					}
					UpdateSolvedExam uExam = new UpdateSolvedExam(user.getUserID(), se);
					
					try {
						client.sendToServer(uExam);
						Platform.runLater(() -> {
							try {
								screenManager.activate("home");
							} catch (IOException e) {
								e.printStackTrace();
							}
						});
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		/**
		 * Checks if we already saved to our listing the SolvedExam se 
		 * @param se the SolvedExam you want to check
		 * @return true if exist
		 */
		private boolean isChangedExamExistAlready(SolvedExams se) {
			for (SolvedExams exam : changedExams) {
				if (exam == se) return true;
				if (exam.getExamID().equals(se.getExamID()) && exam.getSovingStudentID().equals(se.getSovingStudentID()) && exam.getExamDateTime().equals(se.getExamDateTime()))
					return true;
			}
			return false;
		}
		
		/**
		 * Checks if the inputed grade is valid
		 * @param grade the grade you want to check for validation
		 * @return true if the grade is valid
		 */
		private boolean isInputGradeValid(int grade) {
			if (grade < 0 || grade > 100) return false;
			return true;
		}
		
		/**
		 * Checks if the string s is an integer
		 * @param s the string you want to check
		 * @param radix the radix
		 * @return true if s is an integer
		 */
		public static boolean isInteger(String s, int radix) {
		    if(s.isEmpty()) return false;
		    for(int i = 0; i < s.length(); i++) {
		        if(i == 0 && s.charAt(i) == '-') {
		            if(s.length() == 1) return false;
		            else continue;
		        }
		        if(Character.digit(s.charAt(i),radix) < 0) return false;
		    }
		    return true;
		}
		
		/**
		 * Sets the error label text to indicate an error
		 * @param msg the message you want the error label to display
		 * @param displayTime for how long the message will be displayed
		 */
	    private void setErrorLabelText(String msg, int displayTime) {
			PauseTransition delay = new PauseTransition(Duration.seconds(displayTime));
			
			EventHandler<ActionEvent> e = new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					errorTxt.setText("");
				}
			};
			
			delay.setOnFinished(e);
			errorTxt.setText(msg);
			delay.play();
	    }
		
		/**
		 * this method create the solvedExam's PDF. 
		 */
		@SuppressWarnings("resource")
		private void createWord() {
			/** Word formation:
			* 
			*	SolvingDate
			*	Exam ID
			*	StudentID
			*	Approving teacher full name
			*	Exam Grade
			*
			*	question (from Questions)
			*	Question weight
			*	selected answer
			*
			*	question (from Questions)
			*	Question weight
			*	selected answer
			*		*
			*		*
			*		*
			*
			*/

			Platform.runLater(() -> {
				FileChooser fileChooser = new FileChooser();
		        fileChooser.setTitle("Save solvedExam"); 
		        fileChooser.setInitialFileName(solvedExam.getExamID()+"-"+solvedExam.getSovingStudentID() + ".docx");
		        File file = fileChooser.showSaveDialog(screenManager.getPrimaryStage());
		        if (file != null) {
		        	try {
		    			//Blank Document
		                XWPFDocument document = new XWPFDocument();
		                //Write the Document in file system
		                FileOutputStream out = new FileOutputStream(
		                		file);//new File(solvedExam.getExamID()+"-"+solvedExam.getSovingStudentID() + ".docx"));
		                /**
		                 * Printing:
		                 *	SolvingDate
		    			 *	Exam ID
		    			 *	StudentID
		    			 *	Approving teacher full name
		    			 *	Exam Grade
		                 */
		                // solving date
		                XWPFParagraph dateParagraph = document.createParagraph();
		                XWPFRun runDate = dateParagraph.createRun();
		                runDate.setBold(true);
		                runDate.setItalic(true);
		                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		                String solvedExsamDate  = dateFormat.format(solvedExam.getExamDateTime());
		                runDate.setText(solvedExsamDate);
		                // Exam ID + Exam course
		                XWPFParagraph titleParagraph = document.createParagraph();
		                titleParagraph.setAlignment(ParagraphAlignment.CENTER);
		                XWPFRun runTitle = titleParagraph.createRun();
		                runTitle.setBold(true);
		                runTitle.setItalic(true);
		                runTitle.setText("ExamID: "+ solvedExam.getExamID());
		                runTitle.addBreak();
		                runTitle.setText("Exam course: "+ solvedExam.getExamCourse());
		                runTitle.addBreak();
		                runTitle.setText("Strudet ID: "+ solvedExam.getSovingStudentID());
		                runTitle.addBreak();
		                runTitle.setText("Approving Teacher: "+ solvedExam.getApprovingTeacherName());
		                runTitle.addBreak();
		                runTitle.setText("Exam grade: "+ solvedExam.getExamGrade());
		                runTitle.addBreak();
		                /**
		                 * printing Questions and selected answeres
		                 */
		                for (Question question: solvedExamQuestions) {
		    	            //create Paragraph
		    	            XWPFParagraph questionParagraph = document.createParagraph();
		    	            XWPFRun runQuestions = questionParagraph.createRun();
		    	            runQuestions.setText(question.getQuestionText()+"\n");
		    	            runQuestions.addBreak();
		    	            runQuestions.addTab();
		    	            if(question.getIdquestionIntruction() != null) {
			    	            runQuestions.setText(question.getIdquestionIntruction()+"\n");
			    	            runQuestions.addBreak();
			    	            runQuestions.addTab();
		    	            }
		    	            runQuestions.setText("Possible answeres:");
		    	            runQuestions.addBreak();
		    	            runQuestions.addTab();
		    	            runQuestions.setText("1) "+question.getAns1());
		    	            runQuestions.addBreak();
		    	            runQuestions.addTab();
		    	            runQuestions.setText("2) "+question.getAns2());
		    	            runQuestions.addBreak();
		    	            runQuestions.addTab();
		    	            runQuestions.setText("3) "+question.getAns3());
		    	            runQuestions.addBreak();
		    	            runQuestions.addTab();
		    	            runQuestions.setText("4) "+question.getAns4());
		    	            runQuestions.addBreak();
		    	            runQuestions.addTab();
		    	            runQuestions.setText("Correct answer: " + question.getCorrectAns());
		    	            runQuestions.addBreak();
		    	            runQuestions.addTab();
		    	            // getting the selected answer from csvData.
		    	            for (String[] csvLine: csvData) {
		    	    			String slectedQuestionID = csvLine[0].replaceAll("\"", "");
		    	    			String questionID = question.getQuestionId();
		    	    			int correctAnswer = question.getCorrectAns();
		    	    			if (slectedQuestionID.equals(questionID) ) {
		    	    				runQuestions.setText("Your selected answer: "+ csvLine[1].replaceAll("\"", ""));
		    	    				runQuestions.addBreak();
		    	    				if (Integer.parseInt(csvLine[1].replaceAll("\"", "")) == correctAnswer) {
		    	    					runQuestions.setText("Question points: "+ csvLine[2].replaceAll("\"", "") +"/"+csvLine[2].replaceAll("\"", ""));
		    	    				}
		    	    				else runQuestions.setText("Recieved points: "+ "0" +"/"+csvLine[2].replaceAll("\"", ""));
		    	    			}
		    	    		}
		    	            runQuestions.addBreak();
		    			}// end of printing questions
		                document.enforceReadonlyProtection();
		                document.write(out);
		
		                //Close document
		                out.close();
		    	      
		    		}catch (IOException e) {
		    			String setTitle = "IOException";
		    			String errorHeader = "In TeacherExamView, createWord()";
		    			String errorText = e.getMessage();
		    			showErrorDialog(setTitle,errorHeader,errorText);
		    		}catch (Exception e) {
		    			String setTitle = "Exception";
		    			String errorHeader = "In TeacherExamView, createWord()";
		    			String errorText = e.getMessage();
		    			showErrorDialog(setTitle,errorHeader,errorText);
		    		}
		        }
			});
		}
		
		/**
		 * this method is called when student pressed the "Download copy" button.
		 * @param solvedExam
		 */
		private void perapeDownload(SolvedExams solvedExam) {
			/**
			 * get CSV of this solved exam, from server 
			 */
			CsvDetails csv = new CsvDetails(solvedExam, solvedExam.getSovingStudentID());
			CsvMessage newMessage = (CsvMessage) message.getMessage("get-csvFromServer", csv);
			try {
				client.sendToServer(newMessage);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				String setTitle = "IOException";
				String errorHeader = "In TeacherExamView, perapeDownload()";
				String errorText = e.getMessage();
				showErrorDialog(setTitle,errorHeader,errorText);
			}
			
		}
		
	    /**
	     * This method is called when theres a need to ErrrorDialog
	     * @param HeaderTitle
	     * @param HeaderText
	     * @param Errormessage
	     */
	    private void showErrorDialog(String HeaderTitle,String HeaderText,String Errormessage){
	    	Platform.runLater(() -> {								// In order to run javaFX thread.(we recieve from server a java thread)
				// Show the error message.
	            Alert alert = new Alert(AlertType.ERROR);
	            alert.initOwner(screenManager.getPrimaryStage());
	            alert.setTitle(HeaderTitle);//"ServerIP error");
	            alert.setHeaderText(HeaderText);//"Please contact system administrator");
	            alert.setContentText(Errormessage);
	            alert.showAndWait();       
	        	log.writeToLog(LogLine.LineType.ERROR,Errormessage);
			});
	    }
		
		/**
		 * The Main function of the Updater class
		 */
		public void updaterMain() {
			initExamsViewTable();
			getTeacherSubjects();
		}
		
		/**
		 * Must be called to setup the updater
		 * @param client the observable client
		 * @param user the current user logged in
		 */
		public void setup(ObservableClient client, User user) {
			this.client  = client;
			this.user = user;
			
			String dkm_key = "View Solved Exams Observer";
			Observer observer = (Observer)dkm.getObject_NoRemove(dkm_key);
			if (observer == null) {
				dkm.keepObject(dkm_key, (Observer)this);
			}
			this.client.addObserver((Observer)dkm.getObject(dkm_key));
			
			updaterMain();
		}
		
		/**
		 * Must be called before "setup" function
		 * Gets the FXML object from the parent controller
		 * @param tbl the table
		 * @param exam_id the exam_id column
		 * @param user_id the user_id column
		 * @param course the course column
		 * @param date the date column
		 * @param grade the grade column
		 * @param expl the explanation column
		 * @param cheating the is cheating column
		 * @param download the download copy column
		 * @param errorTxt the error txt you want errors displayed to
		 */
		public void setup_table(TableView<SolvedExams> tbl, TableColumn<SolvedExams, String> exam_id, TableColumn<SolvedExams, String> user_id, TableColumn<SolvedExams, String> course,
				TableColumn<SolvedExams, String> date, TableColumn<SolvedExams, String> grade, TableColumn<SolvedExams, String> expl, TableColumn<SolvedExams, String> cheating, TableColumn<SolvedExams, String> download,
				Text errorTxt, TableColumn<SolvedExams, Boolean> tb_approve) {
			this.viewExamsTable = tbl;
			this.tb_examid = exam_id;
			this.tb_userid = user_id;
			this.tb_course = course;
			this.tb_executingDate = date;
			this.tb_grade = grade;
			this.tb_explanation = expl;
			this.tb_suspected = cheating;
			this.tb_download = download;
			this.errorTxt = errorTxt;
			this.tb_approve = tb_approve;
		}
		
		/**
		 * Creates a new singleton instance
		 */
		public static void createUpdater() {
			up = new Updater();
		}
		
		/**
		 * Returns the current instance of Updater 
		 * @return the current instance of Updater
		 */
		public static Updater getInstance() {
			return up;
		}
    	
    }
    
    /**
     * Init, used to create an Updater 'singleton'
     */
    @FXML
	public void initialize() {
    	
    	Platform.runLater(() -> rootPane.requestFocus());

    	ObservableClient client = (ObservableClient) dkm.getObject_NoRemove("client");
    	Updater.createUpdater();
    	Updater updater = Updater.getInstance();
    	updater.setup_table(tbl_viewExams, tb_examid, tb_userid, tb_course, tb_executingDate, tb_grade, tb_explanation, tb_suspected, tb_download, errorTxt, tb_approve);
    	updater.setup(client, dkm.getUser());
    }
	
}

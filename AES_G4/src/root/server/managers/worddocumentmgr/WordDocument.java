package root.server.managers.worddocumentmgr;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import root.dao.app.Exam;
import root.dao.app.Question;
import root.dao.app.QuestionInExam;
import root.server.managers.ServerMessageManager;

public class WordDocument {

	private Exam exam;
	
	public WordDocument(Exam exam) {
		super();
		this.exam = exam;
	}

	public void createDocument() {
		XWPFDocument document = new XWPFDocument();
		int i =0;
		try {
			
			File file = new File(ServerMessageManager.PATH + exam.getExamId()+ ".docx");
			FileOutputStream out = new FileOutputStream(file);
			// create Paragraph
			XWPFParagraph paragraph = document.createParagraph();
			paragraph.setAlignment(ParagraphAlignment.LEFT);
			XWPFRun run = paragraph.createRun();
			run.setText("Name:_________________");
			run.addBreak();
			run.addBreak();
			run.setText("Id:___________________");
			run.addBreak();
			run.addBreak();
			run.addBreak();
			ArrayList<QuestionInExam> questionInExam = exam.getExamQuestions();
			for(QuestionInExam q: questionInExam) {
				i++;
				Question question = q.getQuestion();
				run.setText("Question number "+ i +  ": " + question.getIdquestionIntruction());
				run.addTab();
				run.setText("(" + q.getQuestionGrade() +" points)");
				run.addBreak();
				run.addBreak();
				run.setText(question.getQuestionText());
				run.addBreak();
				if(q.getFreeTextForStudent() != null) {
				run.setText("note: " + q.getFreeTextForStudent());
				run.addBreak();
				}
				run.addBreak();
				run.setText("The answers are:");
				run.addBreak();
				run.setText("1. " + question.getAns1());
				run.addTab();
				run.setText("2. " + question.getAns2());
				run.addBreak();
				run.addBreak();
				run.setText("3. " + question.getAns3());
				run.addTab();
				run.setText("4. " + question.getAns4());
				run.addBreak();
				run.addBreak();
				run.addBreak();
				run.addBreak();
				run.addBreak();
				run.addBreak();
				run.addBreak();		
			}
			run.setText("Good luck!");
			document.write(out);
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

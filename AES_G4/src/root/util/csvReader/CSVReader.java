package root.util.csvReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import root.client.managers.DataKeepManager;
/**
 * This singeltone class is for reading a scv, from a given path.
 * @author gal
 *
 */
public class CSVReader {

    private String csvFile;
    private BufferedReader br;
    private String line = "";
    private String csvSplitBy;
    
    private static CSVReader instace = new CSVReader();
    
    private CSVReader() {
    	this.csvFile = new String();
    	this.br = null;
    	this.line = new String();
    	this.csvSplitBy = ",";
    }
    
    public ArrayList<String[]> readCSV(){
	    try {
	    	ArrayList<String[]> solvedExamFromCSV = new ArrayList<String[]>();
	        	br = new BufferedReader(new FileReader(csvFile));
	        	while ((line = br.readLine()) != null) {
	
	            // use comma as separator
	            solvedExamFromCSV.add(line.split(csvSplitBy));
	        	}
	            //System.out.println(solvedExamFromCSV[4]);
	            return solvedExamFromCSV;
	        
	
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        if (br != null) {
	            try {
	                br.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    return null;
    }

	/**
	 * @return the csvFile
	 */
	public String getCsvFile() {
		return csvFile;
	}

	/**
	 * @param csvFile the csvFile to set
	 */
	public void setCsvFile(String csvFile) {
		this.csvFile = csvFile;
	}

	/**
	 * @return the instace
	 */
	public static CSVReader getInstace() {
		return instace;
	}

	/**
	 * @param instace the instace to set
	 */
	public static void setInstace(CSVReader instace) {
		CSVReader.instace = instace;
	}
    
}
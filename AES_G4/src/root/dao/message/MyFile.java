package root.dao.message;

import java.io.Serializable;

/**
 * 
 * A class that is responsible for transferring files between the server and the
 * client
 * 
 * @author Omer Haimovich
 *
 */
public class MyFile implements Serializable {

	// Instance variables **********************************************

	/**
	 * The path of the file
	 */
	private String Description = null;
	/**
	 * The name of the file
	 */
	private String fileName = null;
	/**
	 * The size of the file
	 */
	private int size = 0;
	/**
	 * The content of the file(in bytes)
	 */
	public byte[] mybytearray;

	// CONSTRUCTORS *****************************************************

	/**
	 * Constructs the MyFile
	 * 
	 * @param fileName
	 *            the name of the file
	 */
	public MyFile(String fileName) {
		this.fileName = fileName;
	}

	// CLASS METHODS *************************************************

	/**
	 * 
	 * A method that initializes the array of bytes of a file meaning its contents
	 * 
	 * @param size
	 *            the size of the file
	 */
	public void initArray(int size) {
		mybytearray = new byte[size];
	}

	/**
	 * A method that returns the name of the file
	 * 
	 * @return the name of the file
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * A method that set the name of the file
	 * 
	 * @param fileName
	 *            the name of the file
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * A method that returns the size of the file
	 * 
	 * @return the size of the file
	 */
	public int getSize() {
		return size;
	}

	/**
	 * A method that set the size of the file
	 * @param size the size of the file
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * A method that returns the content of the file(in bytes)
	 * @return The content of the file(in bytes)
	 */
	public byte[] getMybytearray() {
		return mybytearray;
	}

	/**
	 *  A method that returns the content of the file(in bytes) in specific index
	 * @param i the index in the file
	 * @return the content of the file(in bytes) in specific index
	 */
	public byte getMybytearray(int i) {
		return mybytearray[i];
	}

	/**
	 * A method that set the content of the file(in bytes)
	 * @param mybytearray the content of the file(in bytes)
	 */
	public void setMybytearray(byte[] mybytearray) {

		for (int i = 0; i < mybytearray.length; i++)
			this.mybytearray[i] = mybytearray[i];
	}

	/**
	 * A method that returns the path of the file
	 * @return the path of the file
	 */
	public String getDescription() {
		return Description;
	}

	/**
	 * A method that set the path of the file
	 * @param description the path of the file
	 */
	public void setDescription(String description) {
		Description = description;
	}
}

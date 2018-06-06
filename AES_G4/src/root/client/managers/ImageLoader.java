package root.client.managers;

import java.io.File;
import java.util.ArrayList;

import javafx.scene.image.Image;
import root.util.log.Log;
import root.util.log.LogLine.LineType;

/**
 * Loads images from files
 * @author Amit Molek
 *
 */

public class ImageLoader {

	/**
	 * Loads and returns image from file
	 * @param filePath the path of the image file
	 * @param fileName the name of the image file
	 * @return returns Image object
	 * @throws Exception throws an exception if the file could not been found
	 */
	public static Image loadImage(String filePath, String fileName) throws Exception{
		File imgFile = new File(filePath + "/" + fileName);
		if (!imgFile.exists()) {
			logError("Image could not be found");
			return null;
		}else {
			return (new Image(imgFile.toURI().toString()));
		}
	}
	
	/**
	 * Returns images objects from all the file in the folder
	 * @param folderPath the path of the folder
	 * @return returns images
	 * @throws Exception
	 */
	public static ArrayList<Image> loadImagesFromFolder(String folderPath) throws Exception{
		File folder = new File(folderPath);
		if (!folder.isDirectory()) {
			logError("Folder could not be found");
			return null;
		}else {
			ArrayList<Image> images = new ArrayList<Image>();
			File[] filesInFolder = folder.listFiles();
			for (File file : filesInFolder) {
				if (file.exists()) {
					images.add(new Image(file.toURI().toString()));
				}
			}
			return images;
		}
	}
	
	/**
	 * Logs an error to the log and throws an exception
	 * @param msg the message you want to throw and write to the log
	 * @throws Exception
	 */
	private static void logError(String msg) throws Exception{
		Log.getInstance().writeToLog(LineType.ERROR, msg);
		throw (new Exception(msg));
	}
}

package root.client.managers;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.plaf.metal.MetalIconFactory.FolderIcon16;

import com.sun.javafx.iio.ImageStorage;

import javafx.scene.image.Image;
import root.client.Main;
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
		File imgFile = new File(Main.class.getResource(filePath + "/" + fileName).toURI());
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
		List<URL> urls = getResources(folderPath);
		ArrayList<Image> images = new ArrayList<>();
		
		for (URL url : urls) {
			try {
				InputStream is = url.openStream();
				Image img = new Image(is);
				System.out.println("Images: " + img.getHeight());
				images.add(img);
			}catch (IOException e) {
				e.printStackTrace();
				logError(e.getMessage());
			}
		}
		return images;
	}
	
	/**
	 * Collects all the resources (in our case images) from the folder and creates a URL for each one of them
	 * Then returns the collection of all the URLs
	 * @param path the path of the folder
	 * @return List of files URLs inside path folder
	 * @throws IOException
	 */
	private static List<URL> getResources(final String path) throws IOException {
		List<URL> urls = new ArrayList<>();
		CodeSource src = Main.class.getProtectionDomain().getCodeSource();
		if (src != null) {
		  URL jar = src.getLocation();
		  ZipInputStream zip = new ZipInputStream(jar.openStream());
		  int firstIndex = 0;
		  for (ZipEntry e = zip.getNextEntry(); e != null; e = zip.getNextEntry()) {
			  String eName = e.getName();
			  if (eName.startsWith(path)) {
				  firstIndex++;
				  if (firstIndex != 1) {
					  System.out.println(Main.class.getResource("/" + eName));
					  URL imgUrl = Main.class.getResource("/" + eName);
					  urls.add(imgUrl);
				  }
			  }
		  }
		  return urls;

		} 
		else {
			InputStream is = Main.class.getResourceAsStream("/root/client/resources/view");
	        InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
	        BufferedReader br = new BufferedReader(isr);
	        
	        return br.lines().map(l -> path + "/" + l).map(r -> Main.class.getResource(r)).collect(Collectors.toList());
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

package root.client.managers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


import javafx.scene.image.Image;
import root.client.Main;
import root.util.log.Log;
import root.util.log.LogLine.LineType;

public class HomeScreenBGLoader {
	
	/**
	 * Used to load all the home screen backgrounds
	 * Loads on startup
	 * @author Amit Molek
	 *
	 */
	
	public enum HomeScreenStages{
		MORNING("Morning"),
		NOON("Noon"),
		AFTERNOON("Afternoon"),
		EVENING("Evening");
		
		private final String text;
		
		HomeScreenStages(final String text) {
			this.text = text;
		}
				
		@Override
		public String toString() {
			return text;
		}
	}
	
	private HashMap<String, ArrayList<Image>> images;
	
	private Log log = Log.getInstance();
	private static HomeScreenBGLoader instance = new HomeScreenBGLoader();
	
	private final static String imgsPath = "root/client/resources/images/bg/home/";
	
	/**
	 * Private constructor, used to init the singleton
	 */
	private HomeScreenBGLoader() {
		images = new HashMap<>();
		init_Map();
		try {
			loadBGs();
		} catch (Exception e) {
			try {
				logError(e.getMessage());
			} catch (Exception e1) {
				e1.printStackTrace();
				log.writeToLog(LineType.ERROR, e1.getMessage());
			}
		}
	}
	
	/**
	 * Adds all the stages of the day to the map
	 */
	private void init_Map() {
		init_addToMap(HomeScreenStages.MORNING);
		init_addToMap(HomeScreenStages.NOON);
		init_addToMap(HomeScreenStages.AFTERNOON);
		init_addToMap(HomeScreenStages.EVENING);
	}
	
	/**
	 * Used to init the map (lazy writing alot)
	 * @param stage the stage you want to put in the map
	 */
	private void init_addToMap(HomeScreenStages stage) {
		images.put(stage.toString().toLowerCase(), null);
	}
	
	/**
	 * Returns a random image from the stage images
	 * @param stage the stage of the day you want to get a image for
	 * @return image for the stage you want
	 */
	public Image getRandomImage(HomeScreenStages stage) {
		ArrayList<Image> stageImages = images.get(stage.toString().toLowerCase());
		if (stageImages.size() <= 0) return null;
		Random rnd = new Random();
		return stageImages.get(rnd.nextInt(stageImages.size()));
	}
	
	/**
	 * Loads to the images map the list with all the images
	 * @param key the key of the stage you want to load the images to
	 * @param urlImages the URLs of the images
	 * @throws Exception if urls could not be opened
	 */
	private void loadImagesFromURL(String key, ArrayList<URL> urlImages) throws Exception {
		ArrayList<Image> finalImages = new ArrayList<>();
		for (URL url : urlImages) {
			try {
				InputStream is = url.openStream();
				if (is == null) continue;
				Image img = new Image(is);
				if (img.isError()) continue;
				finalImages.add(img);
			}catch (IOException e) {
				e.printStackTrace();
				logError(e.getMessage());
			}
		}
		images.put(key, finalImages);
	}
	
	/**
	 * Loads the images if the environment is IDE
	 * @throws Exception if could'nt find the folder or open the file
	 */
	private void loadBGs_IDE() throws Exception{
		for (Entry<String, ArrayList<Image>> entry : images.entrySet()) {
			String stage = entry.getKey();
			File imgFolder = new File(Main.class.getResource("/" + imgsPath + stage).toURI());
			if (!imgFolder.isDirectory()) {
				logError("Folder could not be found");
			}else {
				ArrayList<Image> folderImages = new ArrayList<>();
				File[] filesInFolder = imgFolder.listFiles();
				for (File f : filesInFolder) {
					if (f.exists()) {
						folderImages.add(new Image(f.toURI().toString()));
					}
				}
				entry.setValue(folderImages);
			}
		}
	}
	
	/**
	 * Loads the images if the environment is jar
	 * @param jarLocation the URL location of the jar
	 * @throws Exception if couldn't open stream
	 */
	private void loadBGs_Jar(URL jarLocation) throws Exception{
		for(Entry<String, ArrayList<Image>> entry : images.entrySet()) {
			ZipInputStream zippedJar = new ZipInputStream(jarLocation.openStream());
			ArrayList<URL> urls = new ArrayList<>();
			boolean breakLoop = false;
			for (ZipEntry zEntry = zippedJar.getNextEntry(); zEntry != null; zEntry = zippedJar.getNextEntry()) {
				String zeName = zEntry.getName();
				if (zeName.startsWith(imgsPath + entry.getKey().toLowerCase())) {
					URL imgUrl = Main.class.getResource("/" + zeName);
					urls.add(imgUrl);
					breakLoop = true;
				}else {
					if (breakLoop) break;
				}
			}
			loadImagesFromURL(entry.getKey(), urls);
		}
	}
	
	/**
	 * Loads the background images for the home screen
	 * If you are in the IDE (eclipse, net-beans...) it will load with the File system
	 * If you are running a jar it will iterate through the jar to find the images .... (thanks java -_-)
	 * @throws Exception if the files could not be found
	 */
	private void loadBGs() throws Exception{
		CodeSource src = Main.class.getProtectionDomain().getCodeSource();
		
		URL jarLocation = src.getLocation();
		if (jarLocation.toString().startsWith("jar") || jarLocation.toString().endsWith("jar")) {
			loadBGs_Jar(jarLocation);

		} else {
			/* SHOULD ONLY BE HERE IF IN IDE */
			loadBGs_IDE();
		}
	}
	
	/**
	 * Logs an error to the log and throws an exception
	 * @param msg the message you want to throw and write to the log
	 * @throws Exception
	 */
	private void logError(String msg) throws Exception{
		log.writeToLog(LineType.ERROR, msg);
		throw (new Exception(msg));
	}
	
	/**
	 * Returns the instance of the singleton
	 * @return the instanceof this class
	 */
	public static HomeScreenBGLoader getInstance() {
		return instance;
	}
	
}

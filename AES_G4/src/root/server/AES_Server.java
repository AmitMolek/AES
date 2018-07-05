package root.server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Calendar.Builder;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Set;
import java.sql.PreparedStatement;
import ocsf.server.*;
import root.client.controllers.WaitForPirncipleMessage;
import root.dao.app.CheatingExamTest;
import root.dao.app.Question;
import root.dao.app.User;
import root.dao.message.*;
import root.server.managers.*;
import root.server.managers.dbmgr.GetFromDB;
import root.util.log.Log;
import root.util.log.LogLine;
import root.util.properties.PropertiesFile;

/**
 * 
 * This Class is implements the server side The class extends the AbstractServer
 * class Super class is implemented all the connection methods The connection
 * with the server is used with message class.
 */
public class AES_Server extends AbstractServer {

	// Instance variables **********************************************
	
	/**
	 * 
	 * Default port where the server is going to run
	 */
	final public static int DEFAULT_PORT = 8000;
	/**
	 * 
	 * Responsible for handle with messages sent to the server from the client
	 */
	private ServerMessageManager smm;
	/**
	 * 
	 * Generates new messages that link the server to the client
	 */
	private MessageFactory msgFactory;
	/**
	 * 
	 * Connection between the database and the server
	 */
	private static Connection conn;
	/**
	 * 
	 * A log file that is responsible for documenting the actions performed in the
	 * application
	 */
	private static Log log;
	/**
	 * 
	 */
	public static ConnectionToClient CLIENT;

	/**
	 * The default database username
	 */
	final private static String DEFAULT_DB_USER = "root";
	
	/**
	 * The default database password
	 */
	final private static String DEFAULT_DB_PASSWORD = "204403257";
	
	/**
	 * The default database server ip
	 */
	final private static String DEFAULT_DB_IP = "localhost";
	
	/**
	 * The name of the config property of database username
	 */
	final private static String CONFIG_DB_USER = "db_user";
	
	/**
	 * The name of the config property of database password
	 */
	final private static String CONFIG_DB_PASSWORD = "db_password";
	
	/**
	 * The name of the config property of database server ip
	 */
	final private static String CONFIG_DB_IP = "db_server_ip";
	
	/**
	 * The instance of the config manager
	 */
	private static PropertiesFile properties = PropertiesFile.getInstance();
	
	// CONSTRUCTORS *****************************************************
	
	/**
	 * Constructs the AES_Server
	 * @param port the port where the server is going to run
	 */
	public AES_Server(int port) {
		super(port);
		smm = ServerMessageManager.getInstance();
		msgFactory = MessageFactory.getInstance();
		log = Log.getInstance();
	}

	// CLASS methods ************************************************
	
	/**
	 *  Get the server connection
	 * @return the server connection
	 */
	public static Connection getConnection() {
		return conn;
	}
	
	/**
	 * This method handles any messages received from the client.s
	 */
	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
		this.CLIENT = client;
		if (msg instanceof AbstractMessage) {
			AbstractMessage msgToHandle = (AbstractMessage) msg;
			AbstractMessage msgToReturn = smm.handleMessage(msgToHandle);
			try {
				client.sendToClient(msgToReturn);
			} catch (IOException e) {
				e.printStackTrace();
				log.writeToLog(LogLine.LineType.ERROR, e.getMessage());
			}

		}
	}

	/**
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections.
	 */
	protected void serverStarted() {
		System.out.println("Server listening for connections on port " + getPort());
	}

	/**
	 * This method overrides the one in the superclass. Called when the server stops
	 * listening for connections.
	 */
	protected void serverStopped() {
		System.out.println("Server has stopped listening for connections.");
	}

	// Class methods ***************************************************

	/**
	 * The main method creates mysql driver The main method creates connection with
	 * the client
	 * 
	 */
	public static void main(String[] args) {
		int port = 0; // Port to listen on
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception ex) {
			log.writeToLog(LogLine.LineType.ERROR, ex.getMessage());
			/* handle the error */}
		try {
			String db_user = properties.getFromConfig(CONFIG_DB_USER);
			String db_password = properties.getFromConfig(CONFIG_DB_PASSWORD);
			String db_ip = properties.getFromConfig(CONFIG_DB_IP);
			
			if(db_user == null) {
				db_user = DEFAULT_DB_USER;
				properties.writeToConfig(CONFIG_DB_USER, DEFAULT_DB_USER);
			}
			if(db_password == null) {
				db_password = DEFAULT_DB_PASSWORD;
				properties.writeToConfig(CONFIG_DB_PASSWORD, DEFAULT_DB_PASSWORD);
			}
			if(db_ip == null) {
				db_ip = DEFAULT_DB_IP;
				properties.writeToConfig(CONFIG_DB_IP, DEFAULT_DB_IP);
			}
			
			conn = DriverManager.getConnection("jdbc:mysql://" + db_ip + "/aes", db_user, db_password);

			System.out.println("SQL connection succeed");
		} catch (SQLException ex) {/* handle any errors */
			// log.writeToLog(LogLine.LineType.ERROR, ex.getMessage());
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}

		try {
			port = Integer.parseInt(args[0]); // Get port from command line
		} catch (Throwable t) {
			port = DEFAULT_PORT;
		}

		AES_Server sv = new AES_Server(port);
		log.writeToLog(LogLine.LineType.INFO, "The server is connected");

		try {
			sv.listen(); // Start listening for connections
		} catch (Exception ex) {
			log.writeToLog(LogLine.LineType.ERROR, ex.getMessage());
			System.out.println("ERROR - Could not listen for clients!");
		}
	}

}
// End of EchoServer class
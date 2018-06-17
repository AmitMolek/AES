package root.server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.sql.PreparedStatement;
import ocsf.server.*;
import root.client.controllers.WaitForPirncipleMessage;
import root.dao.app.Question;
import root.dao.app.User;
import root.dao.message.*;
import root.server.managers.*;
import root.util.log.Log;
import root.util.log.LogLine;

/**
 * 
 * This Class is implements the server side The class extends the AbstractServer
 * class Super class is implemented all the connection methods
 * The connection with the server is used with message class.
 */
public class AES_Server extends AbstractServer {

	final public static int DEFAULT_PORT = 8000;
	private ServerMessageManager smm;
	private MessageFactory msgFactory;
	String[] recivedMSG;
	private ArrayList<Question> dataBase;
	private static Connection conn;
	private static Log log;
	public static ConnectionToClient CLIENT;
	public AES_Server(int port) {
		super(port);
		smm=ServerMessageManager.getInstance();
		msgFactory=MessageFactory.getInstance();
		log = Log.getInstance();
	}
	/**
	 *  Get the server connection
	 */
	public static Connection getConnection() {
		return conn;
	}
	// Instance methods ************************************************

	/**
	 * This method handles any messages received from the client.
	 */
	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
		this.CLIENT = client;
		if(msg instanceof AbstractMessage) {
			AbstractMessage msgToHandle = (AbstractMessage) msg;
			AbstractMessage msgToReturn=smm.handleMessage(msgToHandle);
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
	 * The main method creates mysql driver
	 * The main method creates connection with the client
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
			conn = DriverManager.getConnection("jdbc:mysql://localhost/aes", "root", "123456");



			System.out.println("SQL connection succeed");
		} catch (SQLException ex) {/* handle any errors */
			//log.writeToLog(LogLine.LineType.ERROR, ex.getMessage());
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
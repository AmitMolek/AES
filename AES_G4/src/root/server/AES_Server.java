package root.server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.sql.PreparedStatement;
import ocsf.server.*;
import root.dao.app.Question;
import root.dao.message.*;
import root.server.managers.*;

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

	public AES_Server(int port) {
		super(port);
		smm=ServerMessageManager.getInstance();
		msgFactory=MessageFactory.getInstance();
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
		AbstractMessage msgToHandle = (AbstractMessage) msg;
		AbstractMessage msgToReturn=smm.handleMessage(msgToHandle);
		try {
			client.sendToClient(msgToReturn);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		System.out.println("Message received: " + handleMsg.getMsg() + " from " + client);
		recivedMSG = handleMsg.getMsg().split("-");
		dataBase = new ArrayList<Question>();
		if (recivedMSG[0].equals("get")) {
			getObj(handleMsg);
		}
		if (recivedMSG[0].equals("set"))
			setObj(handleMsg);
			*/
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
			/* handle the error */}

		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/aes", "root", "204403257");
			System.out.println("SQL connection succeed");
		} catch (SQLException ex) {/* handle any errors */
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

		try {
			sv.listen(); // Start listening for connections
		} catch (Exception ex) {
			System.out.println("ERROR - Could not listen for clients!");
		}
	}
	/**
	 * getObj method give to client for an asked question
	 */
	
	public void getObj(Message msg) {
		recivedMSG = msg.getMsg().split("-");
		if (recivedMSG[1].equals("questions")) {
			Statement stmt;
			try {
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM questions;");
				while (rs.next()) {
					dataBase.add(new Question(rs.getString(1), rs.getInt(2), rs.getString(3), rs.getString(4),
							rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9)));
				}
				Message replyMsg = new Message("ok-arraylist", dataBase);
				this.sendToAllClients(replyMsg);
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	 /**
	  *  setObj method give to client for an asked question
	  * @param msg
	  * 	The changed objects from the client
	  */

	public void setObj(Message msg) {
		recivedMSG = msg.getMsg().split("-");
		String updateString = "UPDATE questions set correctans=? where idquestions=?";
		if (recivedMSG[1].equals("questions") && recivedMSG[2].equals("map")) {
			Map<String, Integer> updateMap = msg.getCorrectAns();
			PreparedStatement stmt;
			try {
				stmt = conn.prepareStatement(updateString);
				Set <String> ids = updateMap.keySet();
				for(String id:ids) {
					stmt.setInt(1, updateMap.get(id));
					stmt.setString(2, id);
					stmt.executeUpdate();
				}
				Message replyMsg = new Message("ok-map");
				this.sendToAllClients(replyMsg);
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}
}
// End of EchoServer class
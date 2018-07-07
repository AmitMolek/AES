package root.server.stubs;

import java.util.ArrayList;

import root.dao.app.User;
import root.server.managers.dbmgr.GetFromDB;

public class GetFromDbStub extends GetFromDB {
	
	private ArrayList<User> fakeUsers;
	

	public GetFromDbStub(ArrayList<User> fakeUsers) {
		super();
		this.fakeUsers = fakeUsers;
	}



	public ArrayList<User> users(String... str){
		return checkIfUserExist(str);
	}
	
	public ArrayList<User> checkIfUserExist(String... id){
		ArrayList<User> checkUser = new ArrayList<User>();
		for(User u: fakeUsers) {
			if(u.getUserID().equals(id[0]))
			{
				checkUser.add(u);
			}
		}
		return checkUser;
	}

}

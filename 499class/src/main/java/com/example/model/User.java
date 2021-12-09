package com.example.model;

import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.List;
import com.example.model.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
@Document(collection = "users_DB")
public class User {
	    @Id
		private String userName;
	 	private String firstName;
	    private String lastName;
	    private List<ScanHistory> scanHistoryArray = new ArrayList<ScanHistory>();
	    private Login login;
	    
	    @ConstructorProperties({"firstName", "lastName", "userName", "password"})
	    public User(String firstName, String lastName, String userName, String password) {
	    	this.firstName = firstName;
	    	this.lastName = lastName;
	    	this.userName = userName;
	    	login = new Login(userName, password);
	    }
	    
	    public User() {
	    	
	    }
	    
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		
		public String getUserName() {
			return userName;
		}
		public List<ScanHistory> getScanHistoryArray() {
			return scanHistoryArray;
		}
		
		public void addScanHistory(ScanHistory scan) {
			scanHistoryArray.add(scan);
		}

		public Login getLogin() {
			return login;
		}

}

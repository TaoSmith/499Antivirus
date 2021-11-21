package com.example.model;

import java.beans.ConstructorProperties;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.*;

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
	    private Login login;
	    @DBRef
	    private List<UploadedFile> scanHistoryArray;
	    
	    @ConstructorProperties({"firstName", "lastName", "userName", "password"})
	    public User(String firstName, String lastName, String userName, String password) {
	    	this.firstName = firstName;
	    	this.lastName = lastName;
	    	this.userName = userName;
	    	login = new Login(userName, password);
	    	scanHistoryArray = new ArrayList<UploadedFile>();
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

		public Login getLogin() {
			return login;
		}
		
		public List<UploadedFile> getScanHistoryArray() {
			return scanHistoryArray;
		}
		
		public void addFile(File file) {
			scanHistoryArray.add(new UploadedFile(file));
		}
}


package com.example.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
@Document(collection = "users_DB")
public class User {
		@Id
		private int id;
	 	private String firstName;
	    private String lastName;
	    //private List<ScanHistory> scanHistoryArray;
	    private Login login;
	    
	    public User(String FirstName, String LastName, String UserName, String Password) {
	    	this.firstName = FirstName;
	    	this.lastName = LastName;
	    	login = new Login(UserName, Password);
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
//		public List<ScanHistory> getScanHistoryArray() {
//			return scanHistoryArray;
//		}
//		public void setScanHistoryArray(List<ScanHistory> scanHistoryArray) {
//			this.scanHistoryArray = scanHistoryArray;
//		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public Login getLogin() {
			return login;
		}

}

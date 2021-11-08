package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.mindrot.jbcrypt.*;

@Data
@AllArgsConstructor
public class Login {
	private String passwordHash;
	private String userName;
	
	public Login(String userName, String password) {
		this.userName = userName;
		this.passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
	}
	
	public Login() {
		
	}
	
	public void changePassword(String newPassword) {
		this.passwordHash = BCrypt.hashpw(newPassword, BCrypt.gensalt());
	}
	public String getUserName() {
		return userName;
	}
	
	public boolean loginCheck(String username, String password) {
		boolean passCheck = BCrypt.checkpw(password, passwordHash);
		//Console returns for Username and Password checking
		if(passCheck==true) {
			System.out.println("Password: True");
		} else System.out.println("Password: False");
		if(username.compareTo(this.userName)==0) {
			System.out.println("Username: True");
		}  else System.out.println("Username: False");

		if(username.compareTo(this.userName)==0 && BCrypt.checkpw(password, passwordHash)) {
			return true;
		} else {
			return false;
		}
	}

}
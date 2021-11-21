package com.example.demo;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.*;
import com.example.repository.Data499Repository;
import com.kanishka.virustotal.dto.FileScanReport;


@RestController
@RequestMapping("")
public class DataUserController {
	
	@Autowired
	private Data499Repository repository;
	
	@PostMapping("/addUserNow")
	public User addUser(@RequestBody User user) {
		User test_user = repository.findByUserName(user.getUserName());
		if(test_user == null) {
			repository.save(user);
			return user;
		}
		else {
			return null;
		}
	}
	
	@PutMapping("/update")
	public User updateUser(@RequestBody User user) {
		System.out.println(user.getFirstName());
		System.out.println(user.getLastName());
		System.out.println(user.getUserName());
		repository.save(user);
		return user;
	}
	
	@PostMapping("/login")
	public User login(@RequestBody Map<String, String> json) {		
		User user = repository.findByUserName(json.get("username"));
		Login login = user.getLogin();
		boolean logincheck = login.loginCheck(json.get("username"), json.get("password"));
		if(logincheck) {
			return user;
		}
		else {
			return null;
		}
	}

	@GetMapping("/getUserByFirstName/{name}")
	public List<User> getUserbyFirstName(String name){
		return repository.findByFirstName(name);
	}
	
	//Simple hello world test
	@GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
    return String.format("Hello %s!", name);
	}
	
	@PostMapping("/scan")
	public String scanFileUpload(@RequestBody String filePath) {
		File fileToUpload = new File(filePath);
		UploadedFile newFile = new UploadedFile(fileToUpload);
		newFile.scanFile();
		newFile.retrieveFileReport();
		return newFile.BasicReturnInfo();
	}
	
/*	@PostMapping("/uploadFile")
	public FileScanReport addFile(@RequestBody UploadedFile file) {
		System.out.println(file.getFileName());
		file.scanFile();
		file.getFileScanReport();
		repository.save(file);
		return file.getFileScanReport();
	}*/
		
		//Tests eicar text file (this file might be caught by antivirus)
		//Limited submissions of this test
	@GetMapping("/test")
	public String uploadedFileFormat() {
		File fileToUpload = new File("src/main/java/com/example/model/eicar.txt");
		UploadedFile newFile = new UploadedFile(fileToUpload);
		newFile.scanFile();
		newFile.retrieveFileReport();
		String returnFormat = newFile.BasicReturnInfo();
		System.out.println(returnFormat);
		return returnFormat;
	}

}

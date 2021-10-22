package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.APITesting;
import com.example.model.User;
import com.example.repository.Data499Repository;

@RestController
@RequestMapping("")
public class DataUserController {
	
	@Autowired
	private Data499Repository repository;
	
	@PostMapping("/addUserNow")
	public String addUser(@RequestBody final User user) {
		repository.save(user);
		return "User added successfully.";
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
	
	//Tests eicar text file (this file might be caught by antivirus)
	//Limited submissions of this test
	@GetMapping("/test")
	public String report() {
		APITesting scan = new APITesting();
		String fileString = null;
		String fileScan = scan.scanFile();
		String fileReport = scan.getFileScanReport();
		fileString = fileScan +"\n"+ fileReport;
		return fileString;
	}

}

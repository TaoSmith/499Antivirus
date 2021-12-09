package com.example.demo;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.model.APITesting;
import com.example.model.Login;
import com.example.model.ScanHistory;
import com.example.model.UploadedFile;
import com.example.model.User;
import com.example.repository.Data499Repository;

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
	
	@PostMapping("/upload")
	@ResponseBody
	public ScanHistory upload(@RequestBody MultipartFile file, String name) throws IllegalStateException, IOException {
		User cur = repository.findByUserName(name);
		String fname = file.getOriginalFilename();
		File newFile = multipartToFile(file, fname);
		UploadedFile upload = new UploadedFile(newFile, newFile.getName());
		upload.scanFile();
		upload.retrieveFileReport();
		Boolean bool = false;
		if(upload.isReportDone() == false) {
			ScanHistory scan = new ScanHistory(fname, bool);
			cur.addScanHistory(scan);
			repository.save(cur);
			return scan;
		}
		upload.BasicReturnInfo();
		double positives = 0;
		if(upload.getFileReport().getScans() != null) {
			positives = upload.getFileReport().getPositives();
			double total = upload.getFileReport().getTotal();
			double percent = ((double)positives/total)*100;
			if(percent > 10) {
				bool = true;
			}
		}
		ScanHistory scan = new ScanHistory(fname, bool);
		cur.addScanHistory(scan);
		repository.save(cur);
		return scan;
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
	
	@GetMapping("/scan")
	public String scanFileUpload() {
		APITesting scan = new APITesting();
		String fileString = null;
		String fileScan = scan.scanFile();
		String fileReport = scan.getFileScanReport();
		fileString = fileScan + "\n" + fileReport;
		return fileString;
	}
	
	@PostMapping("/getScanHistory")
	public List<ScanHistory> getUserScanHistory(String name){
		User cur = repository.findByUserName(name);
		return cur.getScanHistoryArray();
	}
	
	public static File multipartToFile(MultipartFile multipart, String fileName) throws IllegalStateException, IOException {
	    File convFile = new File(System.getProperty("java.io.tmpdir")+"/"+fileName);
	    multipart.transferTo(convFile);
	    return convFile;
	}

}

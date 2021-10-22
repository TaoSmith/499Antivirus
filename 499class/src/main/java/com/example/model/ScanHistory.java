package com.example.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScanHistory {
	private String fileName;
    private boolean infectedStatus;
    private Date date;
    
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public boolean isInfectedStatus() {
		return infectedStatus;
	}
	public void setInfectedStatus(boolean infectedStatus) {
		this.infectedStatus = infectedStatus;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

}

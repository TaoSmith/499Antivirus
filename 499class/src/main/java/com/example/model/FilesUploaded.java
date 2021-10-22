package com.example.model;

import java.io.File;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FilesUploaded {
	private File file;
	private String pathName;
	private boolean report;
	
	public FilesUploaded(File file, String pathName, boolean report) {
		super();
		this.file = file;
		this.pathName = pathName;
		this.report = report;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getPathName() {
		return pathName;
	}
	public void setPathName(String pathName) {
		this.pathName = pathName;
	}
	public boolean isReport() {
		return report;
	}
	public void setReport(boolean report) {
		this.report = report;
	}

}

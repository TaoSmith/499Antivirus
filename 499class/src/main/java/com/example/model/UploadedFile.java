package com.example.model;

import java.beans.ConstructorProperties;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.*;

import com.kanishka.virustotal.dto.FileScanReport;
import com.kanishka.virustotal.dto.ScanInfo;
import com.kanishka.virustotal.dto.VirusScanInfo;
import com.kanishka.virustotal.exception.APIKeyNotFoundException;
import com.kanishka.virustotal.exception.UnauthorizedAccessException;
import com.kanishka.virustotalv2.VirusTotalConfig;
import com.kanishka.virustotalv2.VirustotalPublicV2;
import com.kanishka.virustotalv2.VirustotalPublicV2Impl;

@Data
@AllArgsConstructor
@Document(collection = "files_DB")
public class UploadedFile {
	private File file;
	@Id
	private String fileName;
	@Field(name = "reportDone")
	private boolean reportDone;
	@Field(name = "scanDone")
	private boolean scanDone;
	@Field(name = "scan")
	private ScanInfo scanInformation;
	@Field(name = "report")
	private FileScanReport report;
	private VTapiKey VTkey;
	
	@ConstructorProperties({"file"})
	public UploadedFile(File file) {
		super();
		this.file = file;
		this.fileName = file.getName();
		this.reportDone = false;
		this.scanDone = false;
		this.scanInformation = null;
		this.report = null;
		this.VTkey = new VTapiKey();
	}
	public File getFile() {
		return file;
	}

	public String getFileName() {
		return fileName;
	}
	
	public Boolean isScanDone() {
		return scanDone;
	}
	public boolean isReportDone() {
		return reportDone;
	}
	public void scanFile() {
        try {
            VirusTotalConfig.getConfigInstance().setVirusTotalAPIKey(VTkey.getVTKey());
            VirustotalPublicV2 virusTotalRef = new VirustotalPublicV2Impl();
            scanInformation = virusTotalRef.scanFile(this.file);
            this.scanDone = true;
        } catch (APIKeyNotFoundException ex) {
            System.err.println("API Key not found! " + ex.getMessage());
        } catch (UnsupportedEncodingException ex) {
            System.err.println("Unsupported Encoding Format!" + ex.getMessage());
        } catch (UnauthorizedAccessException ex) {
            System.err.println("Invalid API Key " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("Something Bad Happened! " + ex.getMessage());
        }
	}
	
	public ScanInfo getScannedInfo() {
		return scanInformation;
	}
	
	public void retrieveFileReport() {
        try {
            VirusTotalConfig.getConfigInstance().setVirusTotalAPIKey(VTkey.getVTKey());
            VirustotalPublicV2 virusTotalRef = new VirustotalPublicV2Impl();

            //String resource="275a021bbfb6489e54d471899f7db9d1663fc695ec2fe2a2c4538aabf651fd0f";
            String resource = scanInformation.getResource();
            report = virusTotalRef.getScanReport(resource);
            this.reportDone = true;
            
        } catch (APIKeyNotFoundException ex) {
            System.err.println("API Key not found! " + ex.getMessage());
        } catch (UnsupportedEncodingException ex) {
            System.err.println("Unsupported Encoding Format!" + ex.getMessage());
        } catch (UnauthorizedAccessException ex) {
            System.err.println("Invalid API Key " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("Something Bad Happened! " + ex.getMessage());
        } 
    }
	
	public FileScanReport getFileScanReport() {
		return report;
	}
	
	public String BasicReturnInfo() {
		String reportInfo = "___REPORT INFORMATION___\n"
				+ "Name: "+ fileName +"\n"
        		+ "MD5 :\t" + report.getMd5() +"\n"
        		+ "Perma link :\t" + report.getPermalink() +"\n"
        		+ "Resourve :\t" + report.getResource() +"\n"
        		+ "Positives :\t" + report.getPositives() +"\n"
        		+ "Total :\t" + report.getTotal() +"\n";
		String mapReturn = null;
		Map<String, VirusScanInfo> scans = report.getScans();
        for (String key : scans.keySet()) {
            VirusScanInfo virusInfo = scans.get(key);
            mapReturn += "Scanner : " + key +"\n"
            + "\t\t Result : " + virusInfo.getResult() +"\n"
            + "\t\t Update : " + virusInfo.getUpdate() +"\n"
            + "\t\t Version :" + virusInfo.getVersion()+"\n";
        }
        String returnString = reportInfo + mapReturn;
        return returnString;
	}
}

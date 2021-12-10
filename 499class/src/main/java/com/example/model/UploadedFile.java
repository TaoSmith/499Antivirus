package com.example.model;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
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
public class UploadedFile {
	private File file;
	private String pathName;
	private boolean reportDone;
	private boolean scanDone;
	private ScanInfo scanInformation;
	private FileScanReport report;
	private VTapiKey VTkey;
	
	public UploadedFile(File file, String pathName) {
		super();
		this.file = file;
		this.pathName = pathName;
		this.reportDone = false;
		this.scanDone = false;
		this.scanInformation = null;
		this.report = null;
		this.VTkey = new VTapiKey();
	}
	
	public UploadedFile(File file) {
		super();
		this.file = file;
		this.reportDone = false;
		this.scanDone = false;
		this.scanInformation = null;
		this.report = null;
		this.VTkey = new VTapiKey();
	}
	
	public UploadedFile() {
		
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
            String resource = scanInformation.getResource();
            report = virusTotalRef.getScanReport(resource);
            while(report == null) {
            	wait(2000);
            	report = virusTotalRef.getScanReport(resource);
            }
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
	public FileScanReport getFileReport() {
		return report;
	}

	public String getName() {
		return file.getName();
	}
	
	public String BasicReturnInfo() {
		String reportInfo = "___REPORT INFORMATION___\n"
				+ "Name: "+ file.getName() +"\n"
        		+ "MD5 :\t" + report.getMd5() +"\n"
        		+ "Perma link :\t" + report.getPermalink() +"\n"
        		+ "Resourve :\t" + report.getResource() +"\n"
        		+ "Positives :\t" + report.getPositives() +"\n"
        		+ "Total :\t" + report.getTotal() +"\n";
		String mapReturn = null;
		Map<String, VirusScanInfo> scans = report.getScans();
		if(scans != null) {
        for (String key : scans.keySet()) {
            VirusScanInfo virusInfo = scans.get(key);
            mapReturn += "Scanner : " + key +"\n"
            + "\t\t Result : " + virusInfo.getResult() +"\n"
            + "\t\t Update : " + virusInfo.getUpdate() +"\n"
            + "\t\t Version :" + virusInfo.getVersion()+"\n";
        }
		}
        String returnString = reportInfo + mapReturn;
        return returnString;
	}
	
}

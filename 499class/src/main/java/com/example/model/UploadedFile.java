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
	public void getFileScanReport() {
        try {
            VirusTotalConfig.getConfigInstance().setVirusTotalAPIKey(VTkey.getVTKey());
            VirustotalPublicV2 virusTotalRef = new VirustotalPublicV2Impl();

            String resource="275a021bbfb6489e54d471899f7db9d1663fc695ec2fe2a2c4538aabf651fd0f";
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
	public FileScanReport getFileReport() {
		return report;
	}
	
}

package com.example.model;

import com.kanishka.virustotal.dto.FileScanReport;
import com.kanishka.virustotal.dto.ScanInfo;
import com.kanishka.virustotal.dto.VirusScanInfo;
import com.kanishka.virustotal.exception.APIKeyNotFoundException;
import com.kanishka.virustotal.exception.UnauthorizedAccessException;
import com.kanishka.virustotalv2.VirusTotalConfig;
import com.kanishka.virustotalv2.VirustotalPublicV2;
import com.kanishka.virustotalv2.VirustotalPublicV2Impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class APITesting {
	
	private VTapiKey VTkey;
	private ScanInfo scanInformation;
	private FileScanReport report;
	
	public APITesting() {
		VTkey = new VTapiKey();
		this.scanInformation = null;
		this.report = null;
	}
	
	@SuppressWarnings("finally")
	public String scanFile() {
		String scanReturn = null;
        try {
            VirusTotalConfig.getConfigInstance().setVirusTotalAPIKey(VTkey.getVTKey());
            VirustotalPublicV2 virusTotalRef = new VirustotalPublicV2Impl();

            scanInformation = virusTotalRef.scanFile(new File("src/main/java/com/example/model/eicar.txt"));

            scanReturn = "___SCAN INFORMATION___\n"
            		+ "MD5 :\t" + scanInformation.getMd5() +"\r\n"
            		+ "Perma Link :\t" + scanInformation.getPermalink() +"\r\n"
            		+ "Resource :\t" + scanInformation.getResource() +"\r\n"
            		+ "Scan Date :\t" + scanInformation.getScanDate() +"\r\n"
            		+ "Scan Id :\t" + scanInformation.getScanDate() +"\r\n"
            		+ "SHA1 :\t" + scanInformation.getSha1() +"\r\n"
            		+ "SHA256 :\t" + scanInformation.getSha256() +"\r\n"
            		+ "Verbose Msg :\t" + scanInformation.getVerboseMessage() +"\r\n"
            		+ "Response Code :\t" + scanInformation.getResponseCode() +"\r\n";
        } catch (APIKeyNotFoundException ex) {
            System.err.println("API Key not found! " + ex.getMessage());
        } catch (UnsupportedEncodingException ex) {
            System.err.println("Unsupported Encoding Format!" + ex.getMessage());
        } catch (UnauthorizedAccessException ex) {
            System.err.println("Invalid API Key " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("Something Bad Happened! " + ex.getMessage());
        } finally { 
        	String scanFinal = scanReturn + "Scan Done";
        	return scanFinal;
        	}
    }
	@SuppressWarnings("finally")
	public String getFileScanReport() {
		String reportReturn = null;
		String mapReturn = "";
        try {
            VirusTotalConfig.getConfigInstance().setVirusTotalAPIKey(VTkey.getVTKey());
            VirustotalPublicV2 virusTotalRef = new VirustotalPublicV2Impl();

            //String resource="275a021bbfb6489e54d471899f7db9d1663fc695ec2fe2a2c4538aabf651fd0f";
            String resource = scanInformation.getResource();
            report = virusTotalRef.getScanReport(resource);

            reportReturn =  "___REPORT INFORMATION___\n"
            		+ "MD5 :\t" + report.getMd5() +"\n"
            		+ "Perma link :\t" + report.getPermalink() +"\n"
            		+ "Resourve :\t" + report.getResource() +"\n"
            		+ "Scan Date :\t" + report.getScanDate() +"\n"
            		+ "Scan Id :\t" + report.getScanDate() +"\n"
            		+ "SHA1 :\t" + report.getSha1() +"\n"
            		+ "SHA256 :\t" + report.getSha256() +"\n"
            		+ "Verbose Msg :\t" + report.getVerboseMessage() +"\n"
            		+ "Response Code :\t" + report.getResponseCode() +"\n"
            		+ "Positives :\t" + report.getPositives() +"\n"
            		+ "Total :\t" + report.getTotal() +"\n";

            Map<String, VirusScanInfo> scans = report.getScans();
            for (String key : scans.keySet()) {
                VirusScanInfo virusInfo = scans.get(key);
                mapReturn += "Scanner : " + key +"\n"
                + "\t\t Resut : " + virusInfo.getResult() +"\n"
                + "\t\t Update : " + virusInfo.getUpdate() +"\n"
                + "\t\t Version :" + virusInfo.getVersion();
            }

        } catch (APIKeyNotFoundException ex) {
            System.err.println("API Key not found! " + ex.getMessage());
        } catch (UnsupportedEncodingException ex) {
            System.err.println("Unsupported Encoding Format!" + ex.getMessage());
        } catch (UnauthorizedAccessException ex) {
            System.err.println("Invalid API Key " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("Something Bad Happened! " + ex.getMessage());
        } finally { 
        	String reportFinal = reportReturn + mapReturn + "\nReport Done";
        	return reportFinal;
        	}
    }
	
	
}

/*********************** AIFC-Validator **************************************
 (C) Copyright 2014 Elham Rezvani,  Nidhi Solanki, Wei Zhu @UT-Dallas
 
 All rights reserved. Use of this software is permitted for non-commercial
 research purposes, and it may be copied only for that use.  All copies must
 include this copyright message.  This software is made available AS IS, and
 neither the authors nor UTD make any warranty about the software or its
 performance.
 *************************************************************************/
package utd.aifc.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RequestRecord {
	
	

	private String accessResource;
	private String accessDomain;
	private String resourceACRN;

	private List<String> contributor;
	private List<String> contributorDomain;
	private List<Integer> contributorVer;
	
	private String sourceUserName;
	private String sourceDomain;

	private String sourceService;
	private String sourceCred;
	private String sourceRole;
	
	private String doAction;
	
	



	public RequestRecord() {
		super();
		this.contributor = new ArrayList<String>();
		this.contributorDomain = new ArrayList<String>();
		this.contributorVer = new ArrayList<Integer>();
		// TODO Auto-generated constructor stub
	}
	
/*	public RequestRecord(RequestRecord req) {
		this.accessResource = req.accessResource;
		this.accessDomain = req.accessDomain;
		this.resourceACRN=req.resourceACRN;
		// TODO Auto-generated constructor stub
	}*/
	
	public String getAccessDomain() {
		return accessDomain;
	}
	
	public void setAccessDomain(String accessDomain) {
		this.accessDomain = accessDomain;
	}
	public String getSourceDomain() {
		return sourceDomain;
	}
	public void setSourceDomain(String sourceDomain) {
		this.sourceDomain = sourceDomain;
	}
	public String getSourceRole() {
		return sourceRole;
	}
	public void setSourceRole(String sourceRole) {
		this.sourceRole = sourceRole;
	}
	public String getDoAction() {
		return doAction;
	}
	public void setDoAction(String doAction) {
		this.doAction = doAction;
	}
	public String getAccessResource() {
		return accessResource;
	}
	public void setAccessResource(String accessResource) {
		this.accessResource = accessResource;
	}
	public String getSourceUserName() {
		return sourceUserName;
	}
	public void setSourceUserName(String sourceUserId) {
		this.sourceUserName = sourceUserId;
	}
	public List<String> getContributor() {
		return contributor;
	}

	public void setContributor(List<String> contributor) {
		this.contributor = contributor;
	}
	
	public List<String> getContributorDomain() {
		return contributorDomain;
	}

	public void setContributorDomain(List<String> contributorDomain) {
		this.contributorDomain = contributorDomain;
	}
	
	public List<Integer> getContributorVer() {
		return contributorVer;
	}

	public void setContributorVer( List<Integer> contributorVer) {
		this.contributorVer = contributorVer;
	}
	public String getResourceACRN() {
		return resourceACRN;
	}

	public void setResourceACRN(String resourceACRN) {
		this.resourceACRN = resourceACRN;
	}
	
	public String getSourceService() {
		return sourceService;
	}

	public void setSourceService(String sourceService) {
		this.sourceService = sourceService;
	}

	public String getSourceCred() {
		return sourceCred;
	}

	public void setSourceCred(String sourceCred) {
		this.sourceCred = sourceCred;
	}
	
	public String extractServiceRole(){
		String role = this.sourceCred.split(":")[3];
		return role;
	}
	
	public HashMap<String, String> extractSourceServiceList(){
		HashMap<String,String> servceList = new HashMap<String,String>();
		String [] temp = this.sourceCred.split(":");
		int i =4;
		while(i<temp.length){
			servceList.put(temp[i], temp[i+1]);
			i=i+3;
		}
		
		return servceList;
	}


	public void printRequest() {
		System.out.println("Request Printer\n ***************************************\n"
		+"\n accessResource= "+this.getAccessResource()
		+"\n accessDomain=  "+this.getAccessDomain()
		+"\n resourceHierarchy=  "+this.getResourceACRN()
		+"\n sourceUserName=  "+this.getSourceUserName()
		+"\n sourceDomain=  "+this.getSourceDomain()
		+"\n sourceRole=  "+this.getSourceRole()
		+"\n doAction=  "+this.getDoAction()
		+"\n contributor=  "+this.getContributor()
		+"\n contributorDomain=  "+this.getContributorDomain()+ "\n***************************************\n" );
		// TODO Auto-generated method stub	
	}


}

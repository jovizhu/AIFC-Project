/*********************** AIFC-Validator **************************************
 (C) Copyright 2014 Elham Rezvani,  Nidhi Solanki, Wei Zhu @UT-Dallas
 
 All rights reserved. Use of this software is permitted for non-commercial
 research purposes, and it may be copied only for that use.  All copies must
 include this copyright message.  This software is made available AS IS, and
 neither the authors nor UTD make any warranty about the software or its
 performance.
 *************************************************************************/

package utd.aifc.robotclient;

public class RequestRecord {
	
	

	private String accessResource;
	private String accessDomain;
	private String resourceHierarchy;

	private String contributor;


	private String contributorDomain;
	private String sourceUserName;
	private String sourceDomain;
	private String sourceRole;
	
	private String doAction;
	
	
	public int getContributorVer() {
		return contributorVer;
	}

	public void setContributorVer(int contributorVer) {
		this.contributorVer = contributorVer;
	}

	private int contributorVer;


	public RequestRecord() {
		super();
		this.contributor = null;
		// TODO Auto-generated constructor stub
	}
	
	public RequestRecord(RequestRecord req) {
		this.accessResource = req.accessResource;
		this.accessDomain = req.accessDomain;
		this.resourceHierarchy=req.resourceHierarchy;
		// TODO Auto-generated constructor stub
	}
	
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
	public String getContributor() {
		return contributor;
	}

	public void setContributor(String contributor) {
		this.contributor = contributor;
	}
	
	public String getContributorDomain() {
		return contributorDomain;
	}

	public void setContributorDomain(String contributorDomain) {
		this.contributorDomain = contributorDomain;
	}
	
	public String getResourceHierarchy() {
		return resourceHierarchy;
	}

	public void setResourceHierarchy(String resourceHierarchy) {
		this.resourceHierarchy = resourceHierarchy;
	}
	public void printRequest() {
		System.out.println("Request Printer\n ***************************************\n"
		+"accessResource= "+this.getAccessResource()
		+"accessDomain=  "+this.getAccessDomain()
		+"resourceHierarchy=  "+this.getResourceHierarchy()
		+"sourceUserName=  "+this.getSourceUserName()
		+"sourceDomain=  "+this.getSourceDomain()
		+"sourceRole=  "+this.getSourceRole()
		+"doAction=  "+this.getDoAction()
		+"contributor=  "+this.getContributor()
		+"contributorDomain=  "+this.getContributorDomain() );
		// TODO Auto-generated method stub	
	}

}

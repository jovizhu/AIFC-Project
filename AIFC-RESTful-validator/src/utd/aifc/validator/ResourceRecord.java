/*********************** AIFC-Validator **************************************
 (C) Copyright 2014 Elham Rezvani,  Nidhi Solanki, Wei Zhu @UT-Dallas
 
 All rights reserved. Use of this software is permitted for non-commercial
 research purposes, and it may be copied only for that use.  All copies must
 include this copyright message.  This software is made available AS IS, and
 neither the authors nor UTD make any warranty about the software or its
 performance.
 *************************************************************************/
package utd.aifc.validator;

public class ResourceRecord {
	
	int id;
	String resourceName;
	String rescourceDescription;
	String resourceDomain;
	String resourceType;
	int ResourceNo;
	public ResourceRecord() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getRescourceDescription() {
		return rescourceDescription;
	}
	public void setRescourceDescription(String rescourceDescription) {
		this.rescourceDescription = rescourceDescription;
	}
	public String getResourceDomain() {
		return resourceDomain;
	}
	public void setResourceDomain(String domainName) {
		this.resourceDomain = domainName;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public int getResourceNo() {
		return ResourceNo;
	}
	public void setResourceNo(int resourceNo) {
		ResourceNo = resourceNo;
	}

}

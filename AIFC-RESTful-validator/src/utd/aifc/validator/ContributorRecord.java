/*********************** AIFC-Validator **************************************
 (C) Copyright 2014 Elham Rezvani,  Nidhi Solanki, Wei Zhu @UT-Dallas
 
 All rights reserved. Use of this software is permitted for non-commercial
 research purposes, and it may be copied only for that use.  All copies must
 include this copyright message.  This software is made available AS IS, and
 neither the authors nor UTD make any warranty about the software or its
 performance.
 *************************************************************************/
package utd.aifc.validator;

public class ContributorRecord {

	public ContributorRecord(int id, int provenanceRef, String resourceName,
			String predDomain, String predResourceName,
			int predResourceVersion, int resourceVersion) {
		super();
		this.id = id;
		this.provenanceRef = provenanceRef;
		this.resourceName = resourceName;
		this.predDomain = predDomain;
		this.predResourceName = predResourceName;
		this.predResourceVersion = predResourceVersion;
		this.resourceVersion = resourceVersion;
	}
	
	public ContributorRecord() {
		super();

	}

	private int id;
	private int provenanceRef; // Current Domain's Provenance Reference id
	private String resourceName; // Current Domain's Resource ID

	private String resourceDomain;
	private String predDomain; // Domain of Predecessor may be same or different

	private String predResourceName;    // Predecessor resource version may be in
										// same domain or different domain //not
										// required but we are fetching for more
										// understandability
	private int predResourceVersion;    // Predecessor resource version may be in								// same domain or different domain
	private int resourceVersion;        // which version of the resource is

	public int getId() {
		return id;
	}

	public int getProvenanceRef() {
		return provenanceRef;
	}

	public String getResourceName() {
		return resourceName;
	}

	public String getPredDomain() {
		return predDomain;
	}

	public String getPredResourceName() {
		return predResourceName;
	}

	public int getPredResourceVersion() {
		return predResourceVersion;
	}

	public int getResourceVersion() {
		return resourceVersion;
	}

	public void setId(int i) {
		id = i;
	}

	public void setPredVersion(int i) {
		predResourceVersion = i;
	}

	public void setResourceVersion(int i) {
		resourceVersion = i;
	}

	public void setProvenanceRef(int i) {
		provenanceRef = i;
	}

	public void setResourceName(String s) {
		resourceName = s;
	}

	public void setPredDomainName(String s) {
		predDomain = s;
	}

	public void setPredResourceName(String s) {
		predResourceName = s;
	}
	public String getResourceDomain() {
		return resourceDomain;
	}

	public void setResourceDomain(String resourceDomain) {
		this.resourceDomain = resourceDomain;
	}

	public void setPredDomain(String predDomain) {
		this.predDomain = predDomain;
	}

	public void setPredResourceVersion(int predResourceVersion) {
		this.predResourceVersion = predResourceVersion;
	}

}

/*********************** AIFC-Validator **************************************
 (C) Copyright 2014 Elham Rezvani,  Nidhi Solanki, Wei Zhu @UT-Dallas
 
 All rights reserved. Use of this software is permitted for non-commercial
 research purposes, and it may be copied only for that use.  All copies must
 include this copyright message.  This software is made available AS IS, and
 neither the authors nor UTD make any warranty about the software or its
 performance.
 *************************************************************************/

package utd.aifc.validator;

public class LDAPUserInfo {

	private String userName;
	private String userDomain;
	private String userRole;
	private String ldapSignature;
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserDomain() {
		return userDomain;
	}
	public void setUserDomain(String userDomain) {
		this.userDomain = userDomain;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public String getLdapSignature() {
		return ldapSignature;
	}
	public void setLdapSignature(String ldapSignature) {
		this.ldapSignature = ldapSignature;
	}
	
	public String extractRole()
	{
		return userRole;
	}

}

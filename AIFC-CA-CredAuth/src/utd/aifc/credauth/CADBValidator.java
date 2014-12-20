/*********************** AIFC-Validator **************************************
 (C) Copyright 2014 Elham Rezvani,  Nidhi Solanki, Wei Zhu @UT-Dallas
 
 All rights reserved. Use of this software is permitted for non-commercial
 research purposes, and it may be copied only for that use.  All copies must
 include this copyright message.  This software is made available AS IS, and
 neither the authors nor UTD make any warranty about the software or its
 performance.
 *************************************************************************/
package utd.aifc.credauth;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

/**
 * @author DomainB
 *
 */
public class CADBValidator {

	/**
	 * 
	 */
	public CADBValidator() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String username ="Person1";
		String password ="person1";
		String rolename ="R_D_Director";
		String domainname ="DomainB";
		
		
		try {
		String credential=loginCA(username, password, rolename, domainname);
		System.out.print("The Credential are " + credential);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error in main: " + e.getMessage());
		}
	}
	
	
	public static String loginCA(String userName, String passWord, String roleName,
			String domainName) throws SQLException {
		
		String credential  = null;
		
		try {

		credential  = authenticateUser(userName, passWord, roleName, domainName);		
		
		} catch (Exception Ex) {

			System.out.println("Error Occured: " + Ex.getMessage());			
			return null;
		} 		
		return credential;
	}

	

	public static String authenticateUser(String username, String password, String rolename, String domainname) {

		String  authenticationResult = null;
		java.sql.Connection conn;

		conn = CADBConnector.getConnectionToDB(domainname);

		ResultSet rs = null;
		String sqlStatement = "";
		Statement s;
		try {
			
			// Connection conn = getMySqlConnection();
		    // Step-2: identify the stored procedure
		    //String simpleProc = "{ call simpleproc(?) }";
		    String spAuthentication = "{ call authenticationCA(?,?,?,?,?) }";
		    // Step-3: prepare the callable statement
		    //CallableStatement cs = conn.prepareCall(simpleProc);
		    CallableStatement cs = conn.prepareCall(spAuthentication);
		    // Step-4: register output parameters ...
		   
		    cs.registerOutParameter(5, java.sql.Types.VARCHAR); 
		    
		    cs.setString(1, username);
		    
		    cs.setString(2, password);
		    cs.setString(3, rolename);
		    cs.setString(4, domainname);
		    
		    // Step-5: execute the stored procedures: proc3
		    cs.execute();
		    // Step-6: extract the output parameters
		    authenticationResult = cs.getString(5);
		    //System.out.println("Authentication Result: " + authenticationResult);
		    // Step-7: get ParameterMetaData
		    //ParameterMetaData pmeta = cs.getParameterMetaData();
		    if (authenticationResult == null) {
		      System.out.println("authenticationResult is null");
		    } else {
		     // System.out.println(authenticationResult);
		    }
		    //conn.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("SqlException : " + e.getMessage());
		//	e.printStackTrace();
		}

		return authenticationResult;
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

public static String db_validate_credential (String credential) throws SQLException{
		
		String role = null;
		String mapcredential = null;
		String currentDomain = CAConfiguration.domain;
		
		String crossDomain = extractDomain(credential);
		String crossRole = extractRole(credential);
		
		
		if(extractDomain(credential).equals(currentDomain)){
			// credential is in the same domain
			role =  extractRole(credential);
			return "true:"+role;
		}else{
			// not in the same domain
			role = getDomainRole(crossDomain,crossRole, currentDomain);
								
			return "true:"+extractRole(credential)+":10-24-2014:300:"+role+":"+currentDomain;
		}
		
	}

public static String getDomainRole(String crossDomain,
		String crossDomainRole, String currentDomain) {
	System.out.println("getDomainRole( "+ crossDomain + crossDomainRole+currentDomain +")");
	ResultSet rs = null;
	String sqlStatement = "";
	Statement s;
	String accessDomainRole = null;
	
	
	try {

		java.sql.Connection con = CADBConnector.getConnectionToDB(currentDomain);

		if (con == null) {
			System.err
					.println("Error: ops~~ Access Control Role mapping: could not connect to DB");
			return null;
		}

		s = con.createStatement();

		sqlStatement = "SELECT M.Parent_Domain_Role FROM Cross_Domain_Role_Mapping as M "
				+ " WHERE M.Cross_Domain_Name ='"
				+ crossDomain
				+ "' AND M.Cross_Domain_Role = '" + crossDomainRole + "'";

		System.out.println("SQL statement "+ sqlStatement);
		
		s.execute(sqlStatement);
		rs = s.getResultSet();

		if (rs != null && rs.next()) {
			accessDomainRole = rs.getString(1);
			//System.out.println("Match in role mapping for current request role is "+ accessDomainRole);
		} else {
			System.err
					.println("No match in role mapping for current request role: "
							+ crossDomainRole
							+ " from domain: "
							+ crossDomain);
			return null;

		}

	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	System.out.println("CrossDomain Match updated for "+ accessDomainRole);
	return accessDomainRole;
}

public static String extractRole(String credential){
	String [] credlist = credential.split(":");
	return credlist[2];
}

public static String extractDomain(String credential){
	String [] credlist = credential.split(":");
	return credlist[3];
}


}

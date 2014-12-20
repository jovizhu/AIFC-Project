/*********************** AIFC-Validator **************************************
 (C) Copyright 2014 Elham Rezvani,  Nidhi Solanki, Wei Zhu @UT-Dallas
 
 All rights reserved. Use of this software is permitted for non-commercial
 research purposes, and it may be copied only for that use.  All copies must
 include this copyright message.  This software is made available AS IS, and
 neither the authors nor UTD make any warranty about the software or its
 performance.
 *************************************************************************/
package utd.aifc.credauth;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * @author DomainA
 *
 */
public class CARoleMapping {

	/**
	 * 
	 */
	public CARoleMapping() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		getDomainRole("DomainC", "Developer", "DomainB");
	}
	public static String getDomainRole(String crossDomain,
			String crossDomainRole, String domainName) {
		System.out.println("getDomainRole( "+ crossDomain+" " + crossDomainRole+" "+domainName +")");
		ResultSet rs = null;
		String sqlStatement = "";
		Statement s;
		String accessDomainRole = null;
		java.sql.Connection con;
		
		try {

			con = CADBConnector.getConnectionToDB(domainName);

			//java.sql.Connection con = DBConnector.getConnectionToDB(currentDomain);

			if (con == null) {
				System.err.println("Error: ops~~ Access Control Role mapping: could not connect to DB");
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
				System.out.println("Match in role mapping for current request role is "+ accessDomainRole);
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

		//Logger.printLog("CrossDomain Match updated for "+ accessDomainRole, 2);
		return accessDomainRole;
	}


}

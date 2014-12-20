/*********************** AIFC-Validator **************************************
 (C) Copyright 2014 Elham Rezvani,  Nidhi Solanki, Wei Zhu @UT-Dallas
 
 All rights reserved. Use of this software is permitted for non-commercial
 research purposes, and it may be copied only for that use.  All copies must
 include this copyright message.  This software is made available AS IS, and
 neither the authors nor UTD make any warranty about the software or its
 performance.
 *************************************************************************/
package utd.aifc.validator;

import java.sql.ResultSet;
import java.util.ArrayList;


public class ContributorOperations {

	public static java.sql.Connection con = null;

	public boolean SaveContributors(ArrayList<ContributorRecord> cl) {
		
		con = DBConnector.getConnectionToDB(Configuration.prop
				.getProperty("current-domain"));
		
		try {
			for (int i = 0; i < cl.size(); i++) {
				ContributorRecord c = cl.get(i);
				String insertQuery = "insert into resource_operation_contributor (Provenance_Ref, Resource_Name, Pred_Domain_Name, Pred_Resource_Name, Pred_Resource_Version, Current_Version) values (?,?,?,?,?,?,?)";
				java.sql.PreparedStatement preparedStmt = con
						.prepareStatement(insertQuery);

				preparedStmt.setInt(1, c.getId());
				preparedStmt.setInt(2, c.getProvenanceRef());
				preparedStmt.setString(3, c.getResourceName());
				preparedStmt.setString(4, c.getPredDomain());
				preparedStmt.setString(5, c.getPredResourceName());
				preparedStmt.setInt(6, c.getPredResourceVersion());
				preparedStmt.setInt(7, c.getResourceVersion());
				//System.out.println(insertQuery);
				Logger.printLog("SQL execute "+insertQuery, 4);
				int result = preparedStmt.executeUpdate();
				if (result == 0)
					return false;

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return true;
	}

	public static boolean saveContributor(ContributorRecord c) {
		try {
			con = DBConnector.getConnectionToDB(c.getResourceDomain());
			
			String insertQuery = "insert into resource_operation_contributor (Resource_Name, Resource_Domain, Version, Provenance_Ref,  Pred_Resource_Name, Pred_Domain_Name, Pred_Resource_Version) values (?,?,?,?,?,?,?)";
			java.sql.PreparedStatement preparedStmt = con
					.prepareStatement(insertQuery);
			
			preparedStmt.setString(1, c.getResourceName());
			preparedStmt.setString(2, c.getResourceDomain());
			preparedStmt.setInt(3, c.getResourceVersion());
			preparedStmt.setInt(4, c.getProvenanceRef());
			preparedStmt.setString(5, c.getPredResourceName());
			preparedStmt.setString(6, c.getPredDomain());
			preparedStmt.setInt(7, c.getPredResourceVersion());
			
		//	System.out.println(insertQuery);
			Logger.printLog("SQL execute "+insertQuery, 4);
			int result = preparedStmt.executeUpdate();
			if (result == 0 )
				return false;

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return true;
	}

	public static ArrayList<ContributorRecord> fetchContributors(
			String resource_Name, String domainName) {

		ArrayList<ContributorRecord> clist = new ArrayList<ContributorRecord>();
		try {
			
			con = DBConnector.getConnectionToDB(domainName);
			
			
			String theQuery = new String(
					"select roc.* from resource_operation_contributor roc where roc.Resource_Name=?;");

			java.sql.PreparedStatement preparedStmt = con
					.prepareStatement(theQuery);

			preparedStmt.setString(1, resource_Name);
			
			Logger.printLog("SQL execute "+theQuery, 4);
			Logger.printLog("Resource_Name  "+resource_Name, 4);
			ResultSet rs = preparedStmt.executeQuery();

			//System.out.println(rs.getFetchSize());
			
			Logger.printLog("contributor list size is "+rs.getFetchSize(), 4);
			
			while (rs.next()) {
				ContributorRecord cont = new ContributorRecord();
				cont.setId(rs.getInt("Id"));
				cont.setProvenanceRef(rs.getInt("Provenance_Ref"));
				cont.setResourceName(rs.getString("Resource_Name"));
				cont.setPredDomainName(rs.getString("Pred_Domain_Name"));
				cont.setPredResourceName(rs.getString("Pred_Resource_Name"));
				cont.setPredVersion(rs.getInt("Pred_Resource_Version"));
				cont.setResourceVersion(rs.getInt("Version"));

				clist.add(cont);
			}// end while
			
			rs.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return clist;

	}

}

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
import java.sql.SQLException;

public class ResourceOperation {
	
	public static java.sql.Connection con = null;

	public static int getResourceId(String rescoureName, String accessDomain) {

		int resoureId = -1;
				con = DBConnector.getConnectionToDB(accessDomain);
		

		String selectQuery = "select Resource_Id from resource where Resource_Name = ?";
		java.sql.PreparedStatement preparedStmt;
		try {
			preparedStmt = con.prepareStatement(selectQuery);
			preparedStmt.setString(1, rescoureName);

			//System.out.println(preparedStmt.toString());
			ResultSet rs = preparedStmt.executeQuery();
			while (rs.next()) {

				resoureId = rs.getInt("Resource_Id");
			}// end while
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resoureId;
	}
	
	public static String getACRN(String resourceName, String accessDomain) {

		String resoureACRN = null;

		con = DBConnector.getConnectionToDB(accessDomain);
	

		String selectQuery = "select ACRN from resource where Resource_Name = ?";
		java.sql.PreparedStatement preparedStmt;
		try {
			preparedStmt = con.prepareStatement(selectQuery);
			preparedStmt.setString(1, resourceName);

			//System.out.println(preparedStmt.toString());
			ResultSet rs = preparedStmt.executeQuery();
			while (rs.next()) {

				resoureACRN = rs.getString("ACRN");
			}// end while
			
			if(resoureACRN == null)
			{
				resoureACRN = resourceName;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//System.out.println("Resource Hier get "+resoureHierarchy);
		return resoureACRN;
	}
	public static int getResourceVersion(String rescoureName, String accessDomain) {

		int resoureNo = -1;
		try {
			// get connection to domain DB
			if (con == null) {
				con = DBConnector.getConnectionToDB(accessDomain);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		String selectQuery = "select Resource_Version from resource where Resource_Name = ?";
		java.sql.PreparedStatement preparedStmt;
		try {
			preparedStmt = con.prepareStatement(selectQuery);
			preparedStmt.setString(1, rescoureName);

			//System.out.println(preparedStmt.toString());
			ResultSet rs = preparedStmt.executeQuery();
			while (rs.next()) {
				resoureNo = rs.getInt("Resource_Version");
			}// end while
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resoureNo;
	}
	
	public static boolean UpdateResouceDB(RequestRecord req) {
		
		Logger.printLog("UpdateResourceDB "+req.getAccessResource()+ " " +req.getAccessDomain(), 3);
		
		boolean result = false;
		try {
			// get connection to domain DB
		
				con = DBConnector.getConnectionToDB(req.getAccessDomain());
		
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		if (req.getDoAction().equals("CREATE")) {
			int insertResult = 0;
			String insertQuery = "insert into resource (Resource_Name, Domain_Name, Resource_Version, ACRN) VALUES (?,?,?,?)";
			java.sql.PreparedStatement preparedStmt;
			try {
				preparedStmt = con.prepareStatement(insertQuery);
				preparedStmt.setString(1, req.getAccessResource());
				preparedStmt.setString(2, req.getAccessDomain());
				preparedStmt.setInt(3, 1);
				preparedStmt.setString(4, req.getResourceACRN());
				

				//System.out.println(preparedStmt.toString());
				Logger.printLog("SQL execute "+preparedStmt.toString(), 4);
				
				insertResult = preparedStmt.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(insertResult == 0)
			{
				result = false;
			}
			else
			{
				result = true;
			}

			return result;
		}else if (req.getDoAction().equals("UPDATE")) {
			int updateResult = 0;
			String insertQuery = "update resource set Resource_Version =? where Resource_Name = ?";
			java.sql.PreparedStatement preparedStmt;
			try {
				preparedStmt = con.prepareStatement(insertQuery);
				
				int newVersion = 1+ ResourceOperation.getResourceVersion(req.getAccessResource(), req.getAccessDomain());
				//System.out.println("New Version is "+ newVersion);
				preparedStmt.setInt(1, newVersion);
				preparedStmt.setString(2, req.getAccessResource());
				
				
				Logger.printLog("SQL execute "+preparedStmt.toString(), 4);
				updateResult = preparedStmt.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(updateResult == 0)
			{
				result = false;
			}
			else
			{
				result = true;
			}

			return result;
		
			
		}else if (req.getDoAction().equals("DELETE")) {

			int delResult = 0;
			
			try {
				String delQuery = "delete from resource where Resource_Name = ?";
				java.sql.PreparedStatement preparedStmt;
				preparedStmt = con.prepareStatement(delQuery);
				preparedStmt.setString(1, req.getAccessResource());
				//System.out.println(preparedStmt.toString());
				delResult = preparedStmt.executeUpdate();
				
				
				String delQuery2 = "delete from resource_operation_contributor where Resource_Name = ?";
				java.sql.PreparedStatement preparedStmt2;
				preparedStmt2 = con.prepareStatement(delQuery2);
				preparedStmt2.setString(1, req.getAccessResource());
				
				Logger.printLog("SQL execute "+preparedStmt.toString(), 4);
				preparedStmt2.executeUpdate();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(delResult == 0)
			{
				result = false;
			}
			else
			{
				result = true;
			}

			return result;
		
			
		}else if (req.getDoAction().equals("READ")) {
			
		}
		else
		{
			System.out.println("Non supported operation for resource");
		}
		
		return result;
	}

}

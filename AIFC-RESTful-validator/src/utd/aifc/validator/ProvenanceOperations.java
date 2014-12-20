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
import java.util.Calendar;
import java.util.Date;


public class ProvenanceOperations {

	public static java.sql.Connection con = null;

	public static boolean RecordProvenance(ProvenanceRecord p, String accessDomain) {
		try {

			// get connection to domain DB
			con = DBConnector
						.getConnectionToDB(accessDomain);
			

			String insertQuery = "insert into provenance_track (User_Name, User_Role, Operation_Type, Resource_Domain, Cur_Domain_Resource_Id, Time_Stamp) VALUES (?,?,?,?,?,?)";
			java.sql.PreparedStatement preparedStmt = con
					.prepareStatement(insertQuery);

			//preparedStmt.setInt(1, p.getId());
			preparedStmt.setString(1, p.getUserName());
			preparedStmt.setString(2, p.getUserRole());
			preparedStmt.setString(3, p.getOperationType());
			preparedStmt.setString(4, p.getResourceDomain());
			//preparedStmt.setString(6, p.getResourceId());
			preparedStmt.setString(5, "1");
			// delete this note:check this
			preparedStmt.setTimestamp(6, p.getProvenanceTime());
			//preparedStmt.setString(7, p.getSessionNo().toString());
			
			//System.out.println(preparedStmt.toString());

			boolean result = preparedStmt.execute();
			if (!result)
				return false;

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return true;
	}
	
	// if return 0 ,false
	public static int RecordProvenance(RequestRecord req) {
		int provenaceId = -1;
		try {

			// get connection to domain DB
				con = DBConnector
						.getConnectionToDB(req.getAccessDomain());
			
/*				ProvenanceRecord p = new ProvenanceRecord();

				p.setUserName(req.getSourceUserName());
				p.setUserRole(req.getSourceRole());
				p.setOperationType(req.getDoAction());
				p.setResourceDomain(req.getAccessDomain());
				p.setResourceName(req.getAccessResource());*/
				
			String insertQuery = "insert into provenance_track (User_Name, User_Role, Operation_Type, Resource_Domain, Resource_Name, Time_Stamp) VALUES (?,?,?,?,?,?)";
			java.sql.PreparedStatement preparedStmt = con
					.prepareStatement(insertQuery);

			Date today = Calendar.getInstance().getTime();
		    java.sql.Timestamp timestamp = new java.sql.Timestamp(today.getTime());
		    
			preparedStmt.setString(1, req.getSourceUserName()); // User_Name
			preparedStmt.setString(2, req.getSourceRole()); // User_Role
			preparedStmt.setString(3, req.getDoAction()); //Operation_Type
			preparedStmt.setString(4,req.getAccessDomain());  // Resource_Domain
			preparedStmt.setString(5,req.getAccessResource());  // Resource_Domain
			preparedStmt.setTimestamp(6, timestamp); // Time_stamp
			
			
			//System.out.println(preparedStmt.toString());

			int result = preparedStmt.executeUpdate();
			if (result==0){
				return provenaceId;
			}else
			{
				String selectQuery = "select Id from provenance_track where User_Name = ? and Resource_Name = ? and Time_Stamp = ?";
				java.sql.PreparedStatement selectStmt = con
						.prepareStatement(selectQuery);
				
				selectStmt.setString(1, req.getSourceUserName());
				selectStmt.setString(2, req.getAccessResource());
				selectStmt.setTimestamp(3, timestamp);
				
				//System.out.println(selectQuery);
				ResultSet rs = selectStmt.executeQuery();

				while (rs.next()) {
					
					 provenaceId = rs.getInt("Id");

				}// end while
				//System.out.println("Provenance Id is " + provenaceId);
				return provenaceId;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return provenaceId;
	}

/*	public static boolean validateProvenanceBasedAccess(
			ArrayList<ContributorRecord> contributors, RequestRecord requestRecord) {
		
		RequestRecord req = new RequestRecord();
		boolean res = true;

		try {
			for (int i = 0; i < contributors.size(); i++) {
				ContributorRecord con = contributors.get(i);
				// change the request to contain new access resource
				// i need to change this
				if (!requestRecord.getAccessResource().equals(con.getPredResourceName())) {
					req.setAccessResource(con.getPredResourceName());
					req.setAccessDomain(con.getPredDomain());
					req.setResourceACRN(ResourceOperation
							.getACRN(req.getAccessResource(),
									req.getAccessDomain()));
					req.setDoAction(requestRecord.getDoAction());
					req.setSourceDomain(requestRecord.getSourceDomain());
					req.setSourceRole(requestRecord.getSourceRole());
					req.setSourceUserName(requestRecord.getSourceUserName());
					
					
					String currentDomain = Configuration.prop.getProperty("current-domain");
					if (!req.getAccessDomain().equals(currentDomain)) {
						String validatorURL = Configuration.prop.getProperty(req.getAccessDomain() + "-contributor-validator");
						try {

							URL url = new URL(validatorURL 
									+ "accessResource=" + req.getAccessResource()
									+ "&&accessDomain=" + req.getAccessDomain()
									+ "&&resourceACRN=" + req.getResourceACRN()
									+ "&&sourceUserName=" + req.getSourceUserName()
									+ "&&sourceDomain=" + req.getSourceDomain()
									+ "&&sourceRole=" + req.getSourceRole()
									+ "&&doAction=" + req.getDoAction());
							HttpURLConnection conn = (HttpURLConnection) url
									.openConnection();
							conn.setRequestMethod("GET");
							conn.setRequestProperty("Accept",
									"application/json");

							if (conn.getResponseCode() != 200) {
								throw new RuntimeException(
										"Failed : HTTP error code : "
												+ conn.getResponseCode());
							}

							BufferedReader br = new BufferedReader(
									new InputStreamReader(
											(conn.getInputStream())));

							String output;
							System.out.println("Output from Server .... \n");
							while ((output = br.readLine()) != null) {
								System.out.println(output);
								if(output.equals("true")) {
									res = res && true;
								}
								else {
									res = false;
								}
							}
							conn.disconnect();

						} catch (MalformedURLException e) {
							e.printStackTrace();
							return false;
						} catch (IOException e) {
							e.printStackTrace();
							return false;
						}
					} else {
					res = res && AccessControl.validateRequestRecurive(req);
					}
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return res;
	}*/
	
	
	
/*	private static boolean validateBatchRequestAgainstLocalContributorPolicy(
			ArrayList<RequestRecord> reqList) {
		boolean res = false;
		try {

			for (int i = 0; i < reqList.size(); i++) {
				res = res && AccessControl.validateRequest(reqList.get(i));
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return res;
	}*/

/*	private static boolean validateBatchRequestAgainstRemoteContributorPolicy(
			ArrayList<RequestRecord> reqList, String domain) {
		boolean res = false;
		try {
			Iterator<RequestRecord> rrIr = reqList.iterator();
			while (rrIr.hasNext()) {
				RequestRecord rr = rrIr.next();
				res =  AccessControl.validateRequest(rr);
				if (res == false) break;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return res;
	}*/
}

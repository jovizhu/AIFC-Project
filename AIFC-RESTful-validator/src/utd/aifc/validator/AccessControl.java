/*********************** AIFC-Validator **************************************
 (C) Copyright 2014 Elham Rezvani,  Nidhi Solanki, Wei Zhu @UT-Dallas
 
 All rights reserved. Use of this software is permitted for non-commercial
 research purposes, and it may be copied only for that use.  All copies must
 include this copyright message.  This software is made available AS IS, and
 neither the authors nor UTD make any warranty about the software or its
 performance.
 *************************************************************************/
package utd.aifc.validator;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.sun.xacml.ctx.RequestCtx;


public class AccessControl {
	
	public static long totalValiTime =0;
	public static long totalProTime =0;
	
	/*private static String getDomainRole(String crossDomain,
			String crossDomainRole, String currentDomain) {
		Logger.printLog("getDomainRole( "+ crossDomain + crossDomainRole+currentDomain +")", 3);
		ResultSet rs = null;
		String sqlStatement = "";
		Statement s;
		String accessDomainRole = null;
		
		
		try {

			java.sql.Connection con = DBConnector.getConnectionToDB(currentDomain);

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

			Logger.printLog("SQL statement "+ sqlStatement, 4);
			
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

		Logger.printLog("CrossDomain Match updated for "+ accessDomainRole, 2);
		return accessDomainRole;
	}
*/
	// receive read request
	/*
	 * (String sourceDomain, String sourceRole, String doAction, Integer
	 * accessResource, String sourceUserId, String sourceSessionId)
	 */
	public static boolean validateRequest(RequestRecord req) { /* validate_access*/
		
		Logger.printLog("validateRequest ( "+ req.getAccessResource() + " "+ req.getAccessDomain() +")", 3);
		long stime = System.currentTimeMillis();
		boolean decision = false;
		boolean provenanceDecision = true;
		String currentDomain = null;
		String domainRole = null;
		String readContributorList = "";

		try {
			if(Configuration.prop == null) {	
				 //Configuration conf = new Configuration();
				 //conf.load();
				Configuration.load();
			}
				
			currentDomain = Configuration.prop.getProperty("current-domain");

			if (!req.getSourceDomain().equals(currentDomain)) {
				//domainRole = CARoleMapping.getDomainRole(req.getSourceDomain(),req.getSourceRole(), currentDomain);
				
				////////////////////////////////////////////////////////
				
				
				String requestString =  "crossDomain="+req.getSourceDomain()
							+"crossDomainRole=" + req.getSourceRole()
							+"domainname="+currentDomain;
				
				URL url;
				try {
					String roleMappingURL = Configuration.prop.getProperty("DomainB-RoleMapping");
					url = new URL(roleMappingURL+ requestString);
				
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");
				//System.out.println("url Stirng " + url.toString());
				
				if (conn.getResponseCode() != 200) {
					throw new RuntimeException(
							"Failed : HTTP error code : "
									+ conn.getResponseCode());
				}

				BufferedReader br2 = new BufferedReader(
						new InputStreamReader((conn.getInputStream())));

				System.out.println("Output from Server ....");
				
				if ((domainRole = br2.readLine()) != null) {
					System.out.println("These is the result from the getDomainRole "+domainRole);
				
				}

				conn.disconnect();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				}
				
				
				
				
				
				
				//////////////////////////////////////////////////////////
				
				
				
				
				
				
				
				
				
				
				
				
				
				Logger.printLog("request domain role has been updated to current domain role "+domainRole, 1);
			} else {
				domainRole = req.getSourceRole();
			}
			
			RequestCtx request = null; // this request will be filled and newed

			if (req.getResourceACRN() == null) {
				req.setResourceACRN(ResourceOperation.getACRN(
						req.getAccessResource(), req.getAccessDomain()));
				if(req.getResourceACRN() == null ) {
					req.setResourceACRN(req.getAccessResource());
				}
				Logger.printLog("request resource hier has been updated to "+req.getResourceACRN(), 1);
				
			}			
			Logger.printLog("Generate Request "+req.getResourceACRN(), 1);
				
			request = Request.generateRequest(domainRole, req.getResourceACRN(), req.getDoAction());

			long s = System.currentTimeMillis();
			long e = s;
			if (PolicyDecisionPoint.validateRequest(request, req.getAccessDomain()) == 0) {
				e = System.currentTimeMillis();
				decision = true;
				Logger.printLog("Decsion is true", 4);
			}

			// Step3.5: insert into the request into resource database
			if (decision
					&& !Configuration.prop.getProperty(
							"provenance-accesscontrol").equals("true") ) {
				//System.out.println("step 4.5");
			
				ResourceOperation.UpdateResouceDB(req);
			}
			else if (decision
					&& Configuration.prop.getProperty(
							"provenance-accesscontrol").equals("true")) {
				Logger.printLog("provenance-accesscontrol check " +req.getDoAction(), 4);
				
				if (req.getDoAction().equals("READ")) {
					ArrayList<ContributorRecord> cons = ContributorOperations
							.fetchContributors(req.getAccessResource(),
									req.getAccessDomain());

					Iterator<ContributorRecord> itr_forcreatereadcontributorlist = cons
							.iterator();

					while (itr_forcreatereadcontributorlist.hasNext()) {
						ContributorRecord contributorRecord = itr_forcreatereadcontributorlist
								.next();

						readContributorList = readContributorList + ":"
								+ contributorRecord.getPredResourceName() + ":"
								+ contributorRecord.getPredDomain();
					
						Logger.printLog("contributorRecord  " + contributorRecord.getPredResourceName() + ":"
								+ contributorRecord.getPredDomain(), 1);
					}

					HashMap<String, ArrayList<ContributorRecord>> contributorMapBasedDomain = new HashMap<String, ArrayList<ContributorRecord>>();

					Iterator<String> itr = Configuration.domainList.iterator();
					while (itr.hasNext()) {

						String tempdomain = (String) itr.next();
						Iterator<ContributorRecord> itr2 = cons.iterator();
						ArrayList<ContributorRecord> contributorRecordList = new ArrayList<ContributorRecord>();
						String resourceList = "";
						while (itr2.hasNext()) {
							ContributorRecord contributorRecord = itr2.next();
							if (contributorRecord.getPredDomain().equals(
									tempdomain)) {
								contributorRecordList.add(contributorRecord);
								resourceList = resourceList
										+ "accessResource="
										+ contributorRecord
												.getPredResourceName() + "&&";
							}
						}

						Logger.printLog("resourceList size is is "
								+ resourceList.length(), 1);
						Logger.printLog("resourceList  is " + resourceList, 1);
						if (resourceList.length() == 0)
							continue;
						contributorMapBasedDomain.put(tempdomain,
								contributorRecordList);

						// Create the list of RESTful request
						String contributorValidatorURL = Configuration.prop
								.getProperty(tempdomain
										+ "-contributor-validator");
						try {

							URL url = new URL(contributorValidatorURL
									+ resourceList + "sourceDomain="
									+ req.getSourceDomain() + "&&sourceRole="
									+ req.getSourceRole() + "&&doAction="
									+ req.getDoAction());
							Logger.printLog("url  is " + url.toString(), 1);
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
							Logger.printLog("Output from Server .... \n",1);
							while ((output = br.readLine()) != null) {
								Logger.printLog(output, 1);
								if (output.equals("true")) {
									provenanceDecision = provenanceDecision && true;
								} else {
									provenanceDecision = false;
								}
							}
							conn.disconnect();

						} catch (MalformedURLException me) {
							me.printStackTrace();
							return false;
						} catch (IOException ie) {
							ie.printStackTrace();
							return false;
						}

					}

				}
				
				if (req.getDoAction().equals("CREATE")) {}
				
				
				if (provenanceDecision) {

					ResourceOperation.UpdateResouceDB(req);
					int provenanceRef = ProvenanceOperations.RecordProvenance(req);
					
					if ( (req.getDoAction().equals("CREATE")
							|| req.getDoAction().equals("UPDATE") ) && (req.getContributor().size() >=0 )) {

						// Create the list of RESTful request
						List<String> allContributor = new ArrayList<String>();
						List<String> allContributorDomain = new ArrayList<String>();
						List<String> allContributorVer = new ArrayList<String>();
						
						
						
						List<String> contributor = req.getContributor();
						List<String> contributorDomian = req.getContributorDomain();
						List<Integer> contributorVer = req.getContributorVer();
						
						Iterator<String> itr_domain = contributorDomian.iterator();
						Iterator<String> itr_con = contributor.iterator();
						Iterator<Integer> itr_conVer = contributorVer.iterator();
						
						while(itr_domain.hasNext() &&  itr_con.hasNext() && itr_conVer.hasNext()){
							
							String tempdomain = itr_domain.next();
							String tempcon = itr_con.next();
							Integer tempver = itr_conVer.next();
							
							allContributor.add(tempcon);
							allContributorDomain.add(tempdomain);
							allContributorVer.add(String.valueOf(tempver));
							
							String getContributorURL = Configuration.prop
									.getProperty(tempdomain
											+ "-getContributor");
							try {
								URL url = new URL(getContributorURL + "resource="
										+ tempcon + "&&resourceDomain="
										+ tempdomain);

								Logger.printLog("url  is " + url.toString(), 1);
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
								Logger.printLog("Output from Server .... \n", 1);
								while ((output = br.readLine()) != null) {
									Logger.printLog(output ,1);
									String [] getConResult = output.split(":");
									int i = 1;
									while(i< getConResult.length){
										allContributor.add(getConResult[i]);
										allContributorDomain.add(getConResult[i+1]);
										allContributorVer.add(getConResult[i+2]);
										i = i+3;
									}

								}
								conn.disconnect();

							}catch (MalformedURLException me) {
								me.printStackTrace();
								return false;
							} catch (IOException ie) {
								ie.printStackTrace();
								return false;
							}
						}
					
						
						Iterator<String> itr1 = allContributor.iterator();
						Iterator<String> itr2 = allContributorDomain.iterator();
						Iterator<String> itr3 = allContributorVer.iterator();
						
						while (itr1.hasNext() && itr2.hasNext()
								&& itr3.hasNext()) {
							String tempcontributor = (String) itr1.next();
							String tempcontributorDomain = (String) itr2.next();
							String tempcontributorVersion = (String) itr3.next();

							ContributorRecord conRec = new ContributorRecord();
							conRec.setResourceName(req.getAccessResource());
							conRec.setResourceDomain(req.getAccessDomain());
							conRec.setResourceVersion(ResourceOperation
									.getResourceVersion(
											req.getAccessResource(),
											req.getAccessDomain()));
							conRec.setProvenanceRef(provenanceRef);
							conRec.setPredResourceName(tempcontributor);
							conRec.setPredDomainName(tempcontributorDomain);
							conRec.setPredVersion(Integer.parseInt(tempcontributorVersion));
							ContributorOperations.saveContributor(conRec);
						}
					}
					
				}

			}

			long etime = System.currentTimeMillis();
			System.out.println("Request Validation "+decision+ " etime: " + e + " stime: " + s + " time: " +(e - s));
			System.out.println("Provenance Validation " + (decision && provenanceDecision) + " etime: " + etime + " stime: " + stime + " time: " + (etime - stime));
			totalValiTime = totalValiTime + e-s ;
			System.out.println("Total Request Validation  time is " + totalValiTime );
			totalProTime = totalProTime + etime - stime;
			System.out.println("Total Request Validation  time is " + totalProTime );
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if( decision && provenanceDecision){
			return true;
			
			
		}else{
			return false;
		}
	}
/*
	public static boolean validateRequestRecurive(RequestRecord req)
	{
		boolean decision = false;
		boolean provenanceDecision = true;
		String currentDomain = null;
		String domainRole = null;

		try {
			if(Configuration.prop == null) {					 
				//Configuration conf = new Configuration();
				//conf.load();
				 Configuration.load();
			}
				
			currentDomain = Configuration.prop.getProperty("current-domain");

			if (!req.getAccessDomain().equals(currentDomain)) {
				//domainRole = CARoleMapping.getDomainRole(req.getAccessDomain(),req.getSourceRole(), currentDomain);
				Logger.printLog("request domain role has been updated to current domain role "+domainRole, 1);
			} else {
				domainRole = req.getSourceRole();
			}

			//Step2: generate request
			//System.out.println("step 2");
			RequestCtx request = null; // this request will be filled and newew

			request = Request.generateRequest(domainRole, req.getResourceACRN(), req.getDoAction());


			if (PolicyDecisionPoint.validateRequest(request, req.getAccessDomain()) == 0) {
				decision = true;
			}

		
			if (decision) {
				ArrayList<ContributorRecord> cons = ContributorOperations
						.fetchContributors(req.getAccessResource(), req.getAccessDomain());
				
				HashMap<String, ArrayList<ContributorRecord> > contributorMapBasedDomain = new HashMap<String, ArrayList<ContributorRecord> >();
				
				Iterator<String> itr =  Configuration.domainList.iterator();
				while (itr.hasNext()) {

					String tempdomain = (String) itr.next();
					Iterator<ContributorRecord> itr2 = cons.iterator();
					ArrayList<ContributorRecord> contributorRecordList = new ArrayList<ContributorRecord>();
					String resourceList = "";
					while (itr2.hasNext()) {
						ContributorRecord contributorRecord = itr2.next();
						if (contributorRecord.getPredDomain()
								.equals(tempdomain)) {
							contributorRecordList.add(contributorRecord);
							resourceList = resourceList + "accessResource="
									+ contributorRecord.getPredResourceName()
									+ "&&";
						}
					}

					Logger.printLog("resourceList is "+resourceList, 1);
					contributorMapBasedDomain.put(tempdomain,
							contributorRecordList);

					// Create the list of RESTful request
					String contributorValidatorURL = Configuration.prop
							.getProperty(tempdomain + "-contributor-validator");
					try {

						URL url = new URL(contributorValidatorURL
								+ resourceList 
								+ "sourceDomain="+ req.getSourceDomain() 
								+ "&&sourceRole="+ req.getSourceRole() 
								+ "&&doAction="+ req.getDoAction());
						HttpURLConnection conn = (HttpURLConnection) url
								.openConnection();
						conn.setRequestMethod("GET");
						conn.setRequestProperty("Accept", "application/json");

						if (conn.getResponseCode() != 200) {
							throw new RuntimeException(
									"Failed : HTTP error code : "
											+ conn.getResponseCode());
						}

						BufferedReader br = new BufferedReader(
								new InputStreamReader((conn.getInputStream())));

						String output;
						System.out.println("Output from Server .... \n");
						while ((output = br.readLine()) != null) {
							System.out.println(output);
							if (output.equals("true")) {
								provenanceDecision = provenanceDecision && true;
							} else {
								provenanceDecision = false;
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

				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		//System.out.println("res: " + decision + " for " + req.getAccessResource()+" with hier "+req.getResourceHierarchy()+" "+req.getDoAction());
		return decision && provenanceDecision;
	
	}
	*/
	
	public static boolean validateContributor(List<String> contributorList, String userName, String userRole, String userDomain, String action){
		long stime = System.currentTimeMillis();
		boolean decision = true;
		
		String currentDomain = null;
		String domainRole = null;

		try {
			if(Configuration.prop == null) {	

				Configuration.load();
			}
				
			currentDomain = Configuration.prop.getProperty("current-domain");

			if (! userDomain.equals(currentDomain)) {
				//domainRole = CARoleMapping.getDomainRole(userDomain,userRole, currentDomain);
				////////////////////////////////////////////

				String requestString =  "crossDomain="+userDomain
							+"crossDomainRole=" + userRole
							+"domainname="+currentDomain;
				
				URL url;
				try {
					
					String roleMappingURL = Configuration.prop.getProperty("DomainB-RoleMapping");
					url = new URL(roleMappingURL+ requestString);
				
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");
				//System.out.println("url Stirng " + url.toString());
				
				if (conn.getResponseCode() != 200) {
					throw new RuntimeException(
							"Failed : HTTP error code : "
									+ conn.getResponseCode());
				}

				BufferedReader br2 = new BufferedReader(
						new InputStreamReader((conn.getInputStream())));

				System.out.println("Output from Server ....");
				
				if ((domainRole = br2.readLine()) != null) {
					System.out.println("These is the result from the getDomainRole "+domainRole);
				
				}

				conn.disconnect();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				}
				/////////////////////////////////////////////
				Logger.printLog("request domain role has been updated to current domain role "+domainRole, 1);
			} else {
				domainRole = userRole;
			}

			Logger.printLog("contributorList size is  "+contributorList.size(), 1);
			Iterator<String> itr = contributorList.iterator();
			while(itr.hasNext() && (decision == true)){
				String contributor = itr.next();
				
				RequestCtx request = null; // this request will be filled and newed
				String acrn = ResourceOperation.getACRN(contributor, currentDomain);
				
				if (acrn ==null) {
					acrn = contributor;
				}
				
				Logger.printLog("request resource hier has been updated to "+acrn, 1);
				
				request = Request.generateRequest(domainRole, acrn, action);
			
				if (PolicyDecisionPoint.validateRequest(request, currentDomain) == 0) {
					decision = true;
				}else {
					decision = false;
				}

			}
			
					
			long etime = System.currentTimeMillis();
			System.out.println("Request Validation "+decision+ " time: " + (etime - stime));
			
			
		}catch (Exception ex) {
			decision = false;
			ex.printStackTrace();
		}
		
		return decision;
	}
}

/*********************** AIFC-Validator **************************************
 (C) Copyright 2014 Elham Rezvani,  Nidhi Solanki, Wei Zhu @UT-Dallas
 
 All rights reserved. Use of this software is permitted for non-commercial
 research purposes, and it may be copied only for that use.  All copies must
 include this copyright message.  This software is made available AS IS, and
 neither the authors nor UTD make any warranty about the software or its
 performance.
 *************************************************************************/

package utd.aifc.restvalidator;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import utd.aifc.validator.AccessControl;
import utd.aifc.validator.Configuration;
import utd.aifc.validator.ContributorOperations;
import utd.aifc.validator.ContributorRecord;
import utd.aifc.validator.Logger;
import utd.aifc.validator.RequestRecord;

@Path("RESTfulAccessControl")
public class RESTfulAccessControl {

	
	// @GET here defines, this method will method will process HTTP GET
	// requests.
	@GET
	// @Path here defines method level path. Identifies the URI path that a
	// resource class method will serve requests for.
	@Path("getContributor")
	// @Produces here defines the media type(s) that the methods
	// of a resource class can produce.
	@Produces(MediaType.APPLICATION_JSON)
	// @PathParam injects the value of URI parameter that defined in @Path
	// expression, into the method.
	public static String getContributor(
			@QueryParam("resource") String resource,

			@QueryParam("resourceDomain") String resourceDomain) {

		if (resource != null) {
			Logger.printLog("resource "+resource, 4);
		}
		if (resourceDomain != null) {
			Logger.printLog("resourceDomain "+resourceDomain, 4);
		}

		ArrayList<ContributorRecord> cons = ContributorOperations
				.fetchContributors(resource,resourceDomain);
		
		String contributorList = resource;
		Iterator <ContributorRecord>itr = cons.iterator();
		while(itr.hasNext()){
			ContributorRecord conRec = itr.next();
			contributorList =contributorList +":"+conRec.getPredResourceName()+":"+conRec.getPredDomain()+":"+conRec.getPredResourceVersion();
		}
		return contributorList;
	}
	
	// @GET here defines, this method will method will process HTTP GET
	// requests.
	@GET
	// @Path here defines method level path. Identifies the URI path that a
	// resource class method will serve requests for.
	@Path("validateContributor")
	// @Produces here defines the media type(s) that the methods
	// of a resource class can produce.
	@Produces(MediaType.APPLICATION_JSON)
	// @PathParam injects the value of URI parameter that defined in @Path
	// expression, into the method.
	public static boolean validateContributor(
			@QueryParam("accessResource") List<String> accessResource,

			@QueryParam("sourceUserName") String sourceUserName,
			@QueryParam("sourceDomain") String sourceDomain,
			@QueryParam("sourceRole") String sourceRole,
			
			@QueryParam("doAction") String doAction) {

		if (sourceUserName != null) {
			Logger.printLog("sourceUserName "+sourceUserName, 4);
		}
		if (sourceDomain != null) {
			Logger.printLog("sourceDomain "+sourceDomain, 4);
		}
		if (sourceRole != null) {
			Logger.printLog("sourceRole "+sourceRole, 4);
		}
		if (doAction != null) {
			Logger.printLog("doAction "+doAction, 4);
		}
		
		boolean b = AccessControl.validateContributor(accessResource, sourceUserName, sourceRole,sourceDomain, doAction);
		
		return b;
		
	}
	// @GET here defines, this method will method will process HTTP GET
	// requests.
	@GET
	// @Path here defines method level path. Identifies the URI path that a
	// resource class method will serve requests for.
	@Path("validateRequest")
	// @Produces here defines the media type(s) that the methods
	// of a resource class can produce.
	@Produces(MediaType.APPLICATION_JSON)
	// @PathParam injects the value of URI parameter that defined in @Path
	// expression, into the method.
	public static boolean validateRequest( /* valiate flow*/
			@QueryParam("accessResource") String accessResource,
			@QueryParam("accessDomain") String accessDomain,
			@QueryParam("resourceACRN") String resourceACRN,
			@QueryParam("sourceUserName") String sourceUserName,
			@QueryParam("sourceDomain") String sourceDomain,
			@QueryParam("sourceRole") String sourceRole,
			//@QueryParam("doAction") String doAction,
			@QueryParam("contributor") List<String> contributor,
			@QueryParam("contributorDomain") List<String> contributorDomain,
			@QueryParam("contributorVersion") List<Integer> contributorVer) {
		
	
		RequestRecord req = new RequestRecord();
		String reqString = null;

		Logger.printLog("Valid Reques Service", 4);
		if (accessResource != null) {
			req.setAccessResource(accessResource);
			reqString = "accessResource=" + req.getAccessResource();
			Logger.printLog(reqString, 4);
		}
		if (accessDomain != null) {
			req.setAccessDomain(accessDomain);
			reqString = reqString+"&&accessDomain="+req.getAccessDomain();
			Logger.printLog(reqString, 4);
		}
		if (resourceACRN != null) {
			req.setResourceACRN(resourceACRN);
			reqString = reqString +"&&resourceACRN="+req.getResourceACRN();
			Logger.printLog(reqString, 4);
		}
		if (sourceUserName != null) {
			req.setSourceUserName(sourceUserName);
			reqString = reqString +"&&sourceUserName="+req.getSourceUserName();
			Logger.printLog(reqString, 4);
		}
		if (sourceDomain != null) {
			req.setSourceDomain(sourceDomain);
			reqString = reqString +"&&sourceDomain="+req.getSourceDomain();
			Logger.printLog(reqString, 4);
		}
		if (sourceRole != null) {
			req.setSourceRole(sourceRole);
			reqString = reqString +"&&sourceRole="+req.getSourceRole();
			Logger.printLog(reqString, 4);
		}
/*		if (doAction != null) {
			if(doAction.equals("REA"))
			req.setDoAction(doAction);
			reqString = reqString +"&&doAction="+req.getDoAction();
			Logger.printLog(reqString, 4);
		}*/
		if (contributor.size() > 0) {
			req.setContributor(contributor);
			//reqString = reqString+"&&contributor="+req.getContributor();
			Logger.printLog("contributor size is  "+contributor.size(), 1);
			Iterator<String> itr = contributor.iterator();
			while(itr.hasNext()){
				String temp = (String)itr.next();
				Logger.printLog(temp, 4);
				reqString = reqString+"&&contributor="+temp;
			}
			
		}
		if (contributorDomain.size() > 0) {
			req.setContributorDomain(contributorDomain);
			//reqString = reqString+"&&contributorDomain="+req.getContributorDomain();
			Logger.printLog("contributorDomain size is  "+contributorDomain.size(), 1);
			Iterator<String> itr = contributorDomain.iterator();
			while(itr.hasNext()){
				String temp = (String)itr.next();
				reqString = reqString+"&&contributorDomain="+temp;
				Logger.printLog(temp, 4);
			}
		}
		if (contributorVer.size() > 0) {
			req.setContributorVer(contributorVer);
			//reqString = reqString+"&&contributorVersion="+req.getContributorVer();
			Logger.printLog("contributorVer size is  "+contributorVer.size(), 1);
			Iterator<Integer> itr = contributorVer.iterator();
			while(itr.hasNext()){
				Integer temp = (Integer)itr.next();
				reqString = reqString+"&&contributorVersion="+temp;
				Logger.printLog(temp.toString(), 4);
			}
			
		}
		
		
		
		if(Configuration.prop == null) {	
			 //Configuration.load();
			 //Configuration conf = new Configuration();
			 Configuration.load();
			 //Configuration.loadfortesting("./afic-config.properties");
		}
		
		Logger.printLog("Debug-level is "+ Configuration.debugLevel, 4);
		Logger.printLog(" request String is "+reqString, 1);
			
		String currentDomain = Configuration.prop.getProperty("current-domain");
		boolean resultValidate = false;
		
		if(req.getAccessDomain().equals(currentDomain)) {
				req.setDoAction("READ");
				resultValidate = AccessControl.validateRequest(req);
		}
		else
		{
			if(req.getDoAction().equals("CREATE") || req.getDoAction().equals("UPDATE")) {
				System.err.println("error: AIFC validator do not allow cross domain CREATE/UPDATE");
				return false;
			}
			String validatorURL = Configuration.prop.getProperty(req.getAccessDomain()+"-validator");
			  try {
				  
					URL url = new URL( validatorURL + reqString
							);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("GET");
					conn.setRequestProperty("Accept", "application/json");
			 
					if (conn.getResponseCode() != 200) {
						throw new RuntimeException("Failed : HTTP error code : "
								+ conn.getResponseCode());
					}
			 
					BufferedReader br = new BufferedReader(new InputStreamReader(
						(conn.getInputStream())));
			 
					String output;
					Logger.printLog("Output from Server .... \n", 1);
					while ((output = br.readLine()) != null) {
						Logger.printLog(output, 1);
						
						if(output.equals("true")){
							resultValidate = true;
						} else{
							resultValidate = false;
						}
					}
			 
					conn.disconnect();
			 
				  } catch (MalformedURLException e) {
			 
					e.printStackTrace();
			 
				  } catch (IOException e) {
			 
					e.printStackTrace();
			 
				  }
			 
		}

		return resultValidate;
	}
	
}

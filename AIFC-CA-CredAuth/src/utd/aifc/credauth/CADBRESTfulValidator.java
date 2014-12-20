package utd.aifc.credauth;
/*********************** AIFC-Validator **************************************
(C) Copyright 2014 Elham Rezvani,  Nidhi Solanki, Wei Zhu @UT-Dallas

All rights reserved. Use of this software is permitted for non-commercial
research purposes, and it may be copied only for that use.  All copies must
include this copyright message.  This software is made available AS IS, and
neither the authors nor UTD make any warranty about the software or its
performance.
*************************************************************************/

import java.sql.SQLException;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import utd.aifc.credauth.CARoleMapping;

@Path("AIFC-CA")
public class CADBRESTfulValidator {


	
	// @GET here defines, this method will method will process HTTP GET
	// requests.
	@GET
	// @Path here defines method level path. Identifies the URI path that a
	// resource class method will serve requests for.
	@Path("loginCA")
	// @Produces here defines the media type(s) that the methods
	// of a resource class can produce.
	@Produces(MediaType.APPLICATION_JSON)
	// @PathParam injects the value of URI parameter that defined in @Path
	// expression, into the method.
	public static String loginCA(
			@QueryParam("username") String username,
			@QueryParam("password") String password,
			@QueryParam("rolename") String rolename,
			@QueryParam("domainname") String domainname)
			{
				System.out.println("Inside login Service");
				String cred;
				try {
					cred = utd.aifc.credauth.CADBValidator.loginCA(username, password, rolename, domainname);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
		
		return cred;
		
	
		
	}
	
	// @GET here defines, this method will method will process HTTP GET
		// requests.
		@GET
		// @Path here defines method level path. Identifies the URI path that a
		// resource class method will serve requests for.
		@Path("getDomainRole")
		// @Produces here defines the media type(s) that the methods
		// of a resource class can produce.
		@Produces(MediaType.APPLICATION_JSON)
		// @PathParam injects the value of URI parameter that defined in @Path
		// expression, into the method.
		public static boolean getDomainRole(
				@QueryParam("crossDomain") String crossDomain,
				@QueryParam("crossDomainRole") String crossDomainRole,				
				@QueryParam("domainname") String domainname)
				{
					CARoleMapping.getDomainRole(crossDomain, crossDomainRole, domainname);
			
					return false;		
			
		}
}

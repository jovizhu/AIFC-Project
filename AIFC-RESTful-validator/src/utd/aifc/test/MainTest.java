/*********************** AIFC-Validator **************************************
 (C) Copyright 2014 Wei Zhu and Nidhi Solanki
 
 All rights reserved. Use of this software is permitted for non-commercial
 research purposes, and it may be copied only for that use.  All copies must
 include this copyright message.  This software is made available AS IS, and
 neither the authors nor UTD make any warranty about the software or its
 performance.
 
 		Email: jovi.zhu@hotmail.com
 *************************************************************************/

package utd.aifc.test;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import utd.aifc.validator.AccessControl;
import utd.aifc.validator.Configuration;
import utd.aifc.validator.LDAPConnector;
import utd.aifc.validator.LDAPUserInfo;
import utd.aifc.validator.RequestRecord;
import utd.aifc.validator.ResourceRecord;

public class MainTest {

	public static void main(String [] args) throws FileNotFoundException
	{
		Configuration.loadfortesting("afic-config.properties");
		testCreate();
		//testUpdate();
		//testRead();
		//testList();
		//testDelete();
	}
	
	
	public static void testLogin()
	{
    	LDAPUserInfo user = new LDAPUserInfo();
        try {
        	user=LDAPConnector.getRole("root","nidhi","domain2");			    		
    			System.out.println("Here is the information about role:: " + user.getUserRole());
    	} catch (SQLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	System.out.println( user.getUserRole());
	}
	
	public static void testCreate() {
		// Now we create a request record:
		
		/*Random generator = new Random();
		req.setAccessResource("Jovi_"+ Integer.toString(generator.nextInt()));*/
		
		for (int i = 0; i < 30; i++) {
			System.out.println("Create Fire-"+i);
			RequestRecord req = new RequestRecord();
			req.setAccessResource("Fire-" + i);
			req.setAccessDomain("domain1");
			req.setResourceACRN("Fire");
			req.setSourceDomain("domain2");
			req.setDoAction("CREATE");
			req.setSourceRole("Department_Manager");

			// req.setSourceSessionId("123");
			req.setSourceUserName("Person1");
			ResourceRecord contributor = new ResourceRecord();
			contributor.setResourceName("Target");
			contributor.setResourceDomain("domain2");
			contributor.setResourceType("Robot");
			contributor.setRescourceDescription("this is target");
			contributor.setResourceNo(6);

			//req.setContributor(contributor);

			// Now calling access control
			if (AccessControl.validateRequest(req)) {
				System.out.println("Create permission successed!");
			} else {
				System.out.println("Create permission denied!");
			}

		}

	
	}
	
	public static void testUpdate() {
		// Now we create a request record:
		
		for(int i =0 ; i< 15; i++)
		{
		RequestRecord req = new RequestRecord();
		System.out.println("Update Fire-"+i);
		req.setAccessResource("Fire-"+i);
		req.setAccessDomain("domain2");
		req.setSourceDomain("domain2");
		req.setDoAction("UPDATE");
		req.setSourceRole("Department_Manager");
		//req.setSourceSessionId("123");
		req.setSourceUserName("Person1");

		// Now calling access control
		if (AccessControl.validateRequest(req)) {
			System.out.println("Update permission successed!");
		} else {
			System.out.println("Update permission denied!");
		}
		}
	}
	
	public static void testRead() {
		// Now we create a request record:
		
		for(int i =0 ; i< 15; i++)
		{
		RequestRecord req = new RequestRecord();
		System.out.println("Read Fire-"+i);
		req.setAccessResource("Fire-"+i);
		req.setAccessDomain("domain2");
		req.setSourceDomain("domain2");
		req.setDoAction("READ");
		req.setSourceRole("Department_Manager");
		//req.setSourceSessionId("123");
		req.setSourceUserName("Person1");

		// Now calling access control
		if (AccessControl.validateRequest(req)) {
			System.out.println("Read permission successed!");
		} else {
			System.out.println("Read permission denied!");
		}
		}
	}
	
	public static void testList() {
		// Now we create a request record:
		
		for(int i =0 ; i< 15; i++)
		{
		RequestRecord req = new RequestRecord();
		System.out.println("LIST Robot"+i);
		req.setAccessResource("Robot");
		req.setAccessDomain("domain2");
		req.setSourceDomain("domain2");
		req.setDoAction("LIST");
		req.setSourceRole("Department_Manager");
		//req.setSourceSessionId("123");
		req.setSourceUserName("Person1");

		// Now calling access control
		if (AccessControl.validateRequest(req)) {
			System.out.println("Read permission successed!");
		} else {
			System.out.println("Read permission denied!");
		}
		}
	}
	public static void testDelete() {
		// Now we create a request record:
		
		for(int i =0 ; i< 15; i++)
		{
		RequestRecord req = new RequestRecord();
		System.out.println("Delete Fire-"+i);
		req.setAccessResource("Fire-"+i);
		req.setAccessDomain("domain2");
		req.setSourceDomain("domain2");
		req.setDoAction("DELETE");
		req.setSourceRole("Department_Manager");
		//req.setSourceSessionId("123");
		req.setSourceUserName("Person1");

		// Now calling access control
		if (AccessControl.validateRequest(req)) {
			System.out.println("Delete permission successed!");
		} else {
			System.out.println("Delete permission denied!");
		}
		}
	}
	
	public static void testRemoteUpdate()
	{
		// Now we create a request record:
		RequestRecord req = new RequestRecord();

		req.setAccessResource("Target");
		req.setSourceDomain("domain1");
		req.setDoAction("UPDATE");
		req.setSourceRole("Department_Head");
		//req.setSourceSessionId("123");
		req.setSourceUserName("Person1");

		// Now calling access control
		if (AccessControl.validateRequest(req)) {
			System.out.println("Update permission successed!");
		} else {
			System.out.println("Update permission denied!");
		}

	
	}
	
	public static void testRemoteCreate()
	{
		// Now we create a request record:
		RequestRecord req = new RequestRecord();

		req.setAccessResource("Target");
		req.setAccessDomain("domain2");
		req.setDoAction("CREATE");
		req.setSourceRole("Department_Head");
		req.setSourceDomain("domain1");
		req.setSourceUserName("Person1");

		// Now calling access control
		if (AccessControl.validateRequest(req)) {
			System.out.println("Update permission successed!");
		} else {
			System.out.println("Update permission denied!");
		}

	
	}
}



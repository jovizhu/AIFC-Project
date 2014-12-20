/*********************** AIFC-Validator **************************************
 (C) Copyright 2014 Elham Rezvani,  Nidhi Solanki, Wei Zhu @UT-Dallas
 
 All rights reserved. Use of this software is permitted for non-commercial
 research purposes, and it may be copied only for that use.  All copies must
 include this copyright message.  This software is made available AS IS, and
 neither the authors nor UTD make any warranty about the software or its
 performance.
 *************************************************************************/
package utd.aifc.validator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.sun.xacml.PDP;
import com.sun.xacml.PDPConfig;
import com.sun.xacml.ParsingException;
import com.sun.xacml.ctx.RequestCtx;
import com.sun.xacml.ctx.ResponseCtx;
import com.sun.xacml.ctx.Result;
import com.sun.xacml.finder.PolicyFinder;
import com.sun.xacml.finder.impl.FilePolicyModule;


public class PolicyDecisionPoint {

	public static Map<String, PDP> pdpList = new HashMap <String, PDP>();
	/**
	 * Evaluates the given request and returns the Response that the PDP will
	 * hand back to the PEP.
	 * 
	 * @param requestFile
	 *            the name of a file that contains a Request
	 * 
	 * @return the result of the evaluation
	 * 
	 * @throws IOException
	 *             if there is a problem accessing the file
	 * @throws ParsingException
	 *             if the Request is invalid
	 */
	public static void main(String[] args) throws Exception {


		// pdp.validateRequest();

		Configuration.loadfortesting("afic-config.properties");
		// AccessControl ac = new AccessControl();
		// boolean b = ac.ValidateRquest(req);
		RequestCtx request = null; // this request will be filled and newed

		// Step2: generate request
		request = Request.generateRequest("Sculptor", "1"/*
													 * req.getAccessResource().
													 * toString()
													 */, "UPDATE");

		 long s = System.currentTimeMillis();
		PolicyDecisionPoint.validateRequest(request, "domain2");
		 long e = System.currentTimeMillis();
			System.out.println((e - s));

		 s = System.currentTimeMillis();
		PolicyDecisionPoint.validateRequest(request, "domain1");
		 e = System.currentTimeMillis();
			System.out.println((e - s));

		 s = System.currentTimeMillis();
		PolicyDecisionPoint.validateRequest(request, "domain2");
		 e = System.currentTimeMillis();
			System.out.println((e - s));

		 s = System.currentTimeMillis();
		PolicyDecisionPoint.validateRequest(request, "domain1");
		 e = System.currentTimeMillis();
		System.out.println((e - s));

	}


	// Elham: Ommitted file-read and write only include policy reads
	public static int validateRequest(RequestCtx request, String domainName)
			throws FileNotFoundException, ParsingException {
		
		Logger.printLog("validateRequest ", 4);
		int decision = -1;
		try {
			
			//PDP pdp = PolicyDecisionPoint.getDomainPolicy(domainName);
			PDP pdp = Configuration.pdp;
			// evaluate the request
			ResponseCtx response = pdp.evaluate(request);
			Set<Result> res_act = new HashSet<Result>(response.getResults());
			Iterator<Result> iter1 = res_act.iterator();
			while (iter1.hasNext()) {
				Result attr1 = (Result) iter1.next();
				decision = attr1.getDecision();
			}

		}

		catch (Exception ex) {
			ex.printStackTrace();
		}

		return decision;

	}
	
	

	public static PDP getDomainPolicy(String domainName) {
		//System.out.println(domainName);
		try {
			PDP pdp = pdpList.get(domainName);
			// Ensure the SQL Server driver class is available.
			if(Configuration.prop == null || Configuration.prop.isEmpty())
				{
				 //Configuration conf = new Configuration();
				// conf.load();
				Configuration.load();
				}
			 
			
			if(pdp == null) {
				

				long s = System.currentTimeMillis();

				String policyPath = Configuration.prop.getProperty(domainName+"-policy-path");
				System.out.println(policyPath);
				File path = new File(policyPath);
				String[] policyFiles = path.list();

				FilePolicyModule filePolicyModule = new FilePolicyModule();
				for (int i = 0; i < policyFiles.length; i++)
					filePolicyModule.addPolicy(policyPath + "//" + policyFiles[i]);

				// Step 2: Setup the PolicyFinder that this PDP will use
				PolicyFinder policyFinder = new PolicyFinder();
				Set<FilePolicyModule> policyModules = new HashSet<FilePolicyModule>();
				policyModules.add(filePolicyModule);
				policyFinder.setModules(policyModules);

				// create the PDP
				pdp = new PDP(new PDPConfig(null, policyFinder, null));
				
				pdpList.put(domainName, pdp);
				
				long e = System.currentTimeMillis();
				System.out.println("Load new policy for domain "+domainName+" time "+ (e-s));
			}
			return pdp;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;

	}

}

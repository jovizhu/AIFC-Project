/*********************** AIFC-Validator **************************************
 (C) Copyright 2014 Elham Rezvani,  Nidhi Solanki, Wei Zhu @UT-Dallas
 
 All rights reserved. Use of this software is permitted for non-commercial
 research purposes, and it may be copied only for that use.  All copies must
 include this copyright message.  This software is made available AS IS, and
 neither the authors nor UTD make any warranty about the software or its
 performance.
 *************************************************************************/
package utd.aifc.validator;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;




public class ProvenanceRecord {
	
	private int id;
	private String userName;
	private String userRole;
	private String operationType;
	private String resourceDomain;
	private String currentDomainResourceId;
	private String resourceName;
	private Timestamp provenanceTime;
	private String sessionNo;
	
	
	public ProvenanceRecord() {
		super();
		Date today = Calendar.getInstance().getTime();
	    java.sql.Timestamp s = new java.sql.Timestamp(today.getTime());
	    //Logger.printLog(("SQL Time Stamp" + s, 1);
		this.provenanceTime = s;
		// TODO Auto-generated constructor stub
	}

	public int getId()
	{
		return id;
	}
	
	public String getUserName()
	{
		return userName;
	}
	
	public String getUserRole()
	{
		return userRole;
	}
	
	public String getOperationType()
	{
		return operationType;
	}
	
	public String getResourceName()
	{
		return resourceName;
	}
	
/*	public int getResourceId(String resourceName)
	{
		int result_id = 0; 
		
		// connect to database
		try {		
		
			Connection con = null;
			Statement s;
			String sqlStatement="";
			ResultSet rs=null;
			if(con == null)
			{
				con = DBConnector.getConnectionToDB(Configuration.prop.getProperty("current-domain"));
			}
			s=con.createStatement();
			
			sqlStatement="select id from robots where robotname = '"+resourceName+"'";
			
			s.execute(sqlStatement);
			rs=s.getResultSet();
			if(rs!=null && rs.next())
			{
				result_id = rs.getInt(1);
			}
			else
			{

				System.out.println("No match in role mapping for current request role");
				result_id = 0;

			}
			
		} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
	}
		
		
		
		
		
		// get the result and store it in the result id 
		// return result id 
		
		return result_id;
	}*/
	
	public String getResourceDomain()
	{
		return resourceDomain;
	}
	
	public String getCurDomainResId()
	{
		return currentDomainResourceId;
	}
	
	public  void setProvenanceTime (Timestamp t)
	{
//		
		provenanceTime = t;
	}
	
	public Timestamp getProvenanceTime()
	{
		return provenanceTime;
	}
	
	public String getSessionNo()
	{
		return sessionNo;
	}
	
	public void setId(Integer i)
	{
		id = i;
	}
	
	public void setSessionNo(String s)
	{
		sessionNo = s;
	}
	
	public void setUserName(String s)
	{
		userName = s;
	}
	
	public void setUserRole(String s)
	{
		userRole = s;
	}
	
	public void setOperationType(String s)
	{
		operationType = s;
	}
	
	public void setResourceDomain(String s)
	{
		resourceDomain = s;
	}
	public void setResourceName(String s)
	{
		resourceName = s;
	}
	public void setCurDomainResourceId(String s)
	{
		currentDomainResourceId = s;
		
	}
	

}

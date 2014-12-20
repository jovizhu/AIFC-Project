/*********************** AIFC-Validator **************************************
 (C) Copyright 2014 Elham Rezvani,  Nidhi Solanki, Wei Zhu @UT-Dallas
 
 All rights reserved. Use of this software is permitted for non-commercial
 research purposes, and it may be copied only for that use.  All copies must
 include this copyright message.  This software is made available AS IS, and
 neither the authors nor UTD make any warranty about the software or its
 performance.
 *************************************************************************/

package utd.aifc.validator;

public class Logger {

	public static void printLog(String msg, int level)
	{
		if(Configuration.debugLevel > level )
		{
			System.out.println("Log: ("+level+") "+ msg);
		}
		
		System.out.flush();
	}
}

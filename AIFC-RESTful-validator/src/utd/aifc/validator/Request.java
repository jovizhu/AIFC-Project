/*********************** AIFC-Validator **************************************
 (C) Copyright 2014 Elham Rezvani,  Nidhi Solanki, Wei Zhu @UT-Dallas
 
 All rights reserved. Use of this software is permitted for non-commercial
 research purposes, and it may be copied only for that use.  All copies must
 include this copyright message.  This software is made available AS IS, and
 neither the authors nor UTD make any warranty about the software or its
 performance.
 *************************************************************************/
package utd.aifc.validator;/*

 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
//package org.eklavya.accesscontrol;

import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;



import com.sun.xacml.EvaluationCtx;
import com.sun.xacml.attr.StringAttribute;
import com.sun.xacml.ctx.Attribute;
import com.sun.xacml.ctx.RequestCtx;
import com.sun.xacml.ctx.Subject;

public class Request {

	public static Set<Subject> setupSubjects(String role)
			throws URISyntaxException {
		HashSet<Attribute> attributes = new HashSet<Attribute>();

		// setup the id and value for the requesting subject
		URI subjectId = new URI(
				"urn:oasis:names:tc:xacml:1.0:subject:subject-id");
		StringAttribute value = new StringAttribute(role);

		attributes.add(new Attribute(subjectId, null, null, value));
		// ...and the second with the subject's group membership

		// bundle the attributes in a Subject with the default category
		HashSet<Subject> subjects = new HashSet<Subject>();
		subjects.add(new Subject(attributes));

		return subjects;
	}

	public static Set<Attribute> setupResource(String accessResource)
			throws URISyntaxException {
		HashSet<Attribute> resource = new HashSet<Attribute>();

		// the resource being requested
		StringAttribute value = new StringAttribute(accessResource);

		// create the resource using a standard, required identifier for
		// the resource being requested
		resource.add(new Attribute(new URI(EvaluationCtx.RESOURCE_ID), null,
				null, value));

		return resource;
	}

	public static Set<Attribute> setupAction(String doAction)
			throws URISyntaxException {
		HashSet<Attribute> action = new HashSet<Attribute>();

		// this is a standard URI that can optionally be used to specify
		// the action being requested
		URI actionId = new URI("urn:oasis:names:tc:xacml:1.0:action:action-id");

		// create the action
		action.add(new Attribute(actionId, null, null, new StringAttribute(
				doAction)));

		return action;
	}
	
	// Elham: this method is called from access Control unit
	public static RequestCtx generateRequest(String role, String accessResource,
			String doAction) throws URISyntaxException, FileNotFoundException {
		HashSet<Attribute> attributes = new HashSet<Attribute>();
		Logger.printLog("Generate Request: role:" +role +" accessResource "+ accessResource+ " action "+doAction, 5);
		//System.out.flush();
		// setup the id and value for the requesting subject
		URI subjectId = new URI(
				"urn:oasis:names:tc:xacml:1.0:subject:subject-id");
		StringAttribute value = new StringAttribute(role);
		attributes.add(new Attribute(subjectId, null, null, value));
		// ...and the second with the subject's group membership
		// bundle the attributes in a Subject with the default category
		HashSet<Subject> subjects = new HashSet<Subject>();
		subjects.add(new Subject(attributes));

		HashSet<Attribute> resource = new HashSet<Attribute>();

		// the resource being requested
		StringAttribute val = new StringAttribute(accessResource);

		// create the resource using a standard, required identifier for
		// the resource being requested
		resource.add(new Attribute(new URI(EvaluationCtx.RESOURCE_ID), null,
				null, val));

		HashSet<Attribute> action = new HashSet<Attribute>();

		// this is a standard URI that can optionally be used to specify
		// the action being requested
		URI actionId = new URI("urn:oasis:names:tc:xacml:1.0:action:action-id");

		// create the action
		action.add(new Attribute(actionId, null, null, new StringAttribute(
				doAction)));

		// Scanner scan = new Scanner(System.in);

		// long startTime = System.currentTimeMillis();

		RequestCtx request = new RequestCtx(subjects, resource, action,
				new HashSet<Object>());

		// long endTime = System.currentTimeMillis();

		return request;

	}
}
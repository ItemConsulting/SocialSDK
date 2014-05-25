/*
 * � Copyright IBM Corp. 2013
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at:
 * 
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
 * implied. See the License for the specific language governing 
 * permissions and limitations under the License.
 */
package com.ibm.sbt.services.client.connections.common;

import com.ibm.commons.util.StringUtil;
import com.ibm.sbt.services.client.base.AtomXPath;
import com.ibm.sbt.services.client.base.BaseEntity;
import com.ibm.sbt.services.client.base.BaseService;
import com.ibm.sbt.services.client.base.datahandlers.DataHandler;

/**
 * @author Mario Duarte
 *
 */
public class Person extends BaseEntity {
	
	public Person() {
	}
	
	public Person(String name, String email, String userid) {
		setName(name);
		setEmail(email);
		setUserid(userid);
	}
	
	public Person(BaseService svc, DataHandler<?> dataHandler) {
		super(svc, dataHandler);
	}
	
	public String getId() {
		//author object override this. cannot change author, so no using getUserId directly here.
		String id = getAsString(AtomXPath.personUserid);
		if (StringUtil.isEmpty(id)) {
			id = getEmail();
		}
		return id;
	}
	
	public void setUserid(String userid) {
		setAsString(AtomXPath.personUserid, userid);
	}
	
	public void setName(String name) {
		setAsString(AtomXPath.personName, name);
	}

	public void setEmail(String email) {
		setAsString(AtomXPath.personEmail, email);
	}

	public void setUserState(String userState) {
		setAsString(AtomXPath.personUserState, userState);
	}
	
	public String getEmail() {
		return getAsString(AtomXPath.personEmail);
	}

	public String getName() {
		return getAsString(AtomXPath.personName);
	}
	
	/**
	 * @deprecated Use getUserState instead
	 */
	public String getState() {
		return getUserState();
	}

	public String getUserState() {
		return getAsString(AtomXPath.personUserState);
	}

	public String getUserid() {
		return getAsString(AtomXPath.personUserid);
	}
}

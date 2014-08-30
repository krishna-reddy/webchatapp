package com.web.chat.dbutill;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

public final class PMF {

	private final static PersistenceManagerFactory pmfInstance = JDOHelper.getPersistenceManagerFactory("transactions-optional");
	
	private PMF(){}
	
	public static PersistenceManagerFactory get(){
		return pmfInstance;
	}
}

package com.web.chat.servlet;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.mortbay.log.Log;

import com.web.chat.dbutill.PMF;
import com.web.chat.objects.UserRegister;

public class UserRequestSevlet {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	{
		String pageFrom = (String)request.getParameter("hpageFrom");
		String userName = (String)request.getParameter("txtUserid");
		String password = (String)request.getParameter("txtPassword");
		
		
		
		if(pageFrom.equalsIgnoreCase("LOGIN")){
			boolean status;
			status = checkUserIdPwd(userName,password);
			if(status){
				RequestDispatcher dispatcher = request.getRequestDispatcher("mainboard.html");
				try {
					dispatcher.forward(request, response);
				} catch (Exception e) {
					// TODO Auto-generated catch block
//				Log.warn("Dispatcher Exception ",e);	
				}
			}else{
				RequestDispatcher dispatcher = request.getRequestDispatcher("index.html");
				try {
					dispatcher.forward(request, response);
				} catch (Exception e) {
					// TODO Auto-generated catch block
//				Log.warn("Dispatcher Exception ",e);	
				}
			}
		}
		else if (pageFrom.equalsIgnoreCase("SIGNUP")){
			boolean status;
			status = UseridExists(userName,password);
			if(status){
				RequestDispatcher dispatcher = request.getRequestDispatcher("index.html");
				try {
					dispatcher.forward(request, response);
				} catch (Exception e) {
					// TODO Auto-generated catch block
//				Log.warn("Dispatcher Exception ",e);	
				}
			}else{
				RequestDispatcher dispatcher = request.getRequestDispatcher("signup.html");
				try {
					dispatcher.forward(request, response);
				} catch (Exception e) {
					// TODO Auto-generated catch block
//				Log.warn("Dispatcher Exception ",e);	
				}
			}
		}
		
		
		
	}
	synchronized boolean UseridExists(String UserId,String password){
		
		
		PersistenceManager pmf = PMF.get().getPersistenceManager();
//		UserRegister userRegister = 
		Query q = pmf.newQuery(UserRegister.class);
		q.setFilter("userId == hUserId ");
		q.declareParameters("String hUserId");
		boolean status = false;
		try{
			List<UserRegister> usrRegister = (List<UserRegister>) q.execute(UserId);
			if(usrRegister.size() < 0){
				status =  true;
				UserRegister usrReg = new UserRegister();
				usrReg.setUserId(UserId);
				usrReg.setPassword(password);
				pmf.makePersistent(usrReg);
				pmf.close();
			}
			else {
				status =  false;
			}
		}
		catch(Exception e){
//			Log.warn("Exception registerNewUser()",e);
		}
		
		return status;
	}
	synchronized boolean checkUserIdPwd(String UserId,String password)
	{
		//Key key = KeyFactory.createKey(UserRegister.class.g)
		PersistenceManager pmf = PMF.get().getPersistenceManager();
//		UserRegister userRegister = 
		Query q = pmf.newQuery(UserRegister.class);
		q.setFilter("userId == hUserId && password = hPassword");
		q.declareParameters("String hUserId, String hPassword");
		boolean status = false;
		try{
			List<UserRegister> usrRegister = (List<UserRegister>) q.execute(UserId,password);
			if(usrRegister == null){
				status =  true;
			}
			else {
				status =  false;
			}
		}
		catch(Exception e){
			
		}
		
			return status;	
				
		
	}
	


}

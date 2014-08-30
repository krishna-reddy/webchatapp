/**
 * 
 */
package com.web.chat.servlet;

import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.web.chat.dbutill.PMF;
import com.web.chat.objects.UserRegister;

/**
 * @author vb
 *
 */
@Controller
public class UserCommonServlet extends HttpServlet{
	
	static Logger log = Logger.getLogger("UserCommonServlet.Log");
	static JSONObject commonJsonObject = new JSONObject();
	static JSONParser commonJsonParser = new JSONParser();
	PersistenceManager pmf ;
	
	
	
	/**
	 * This method for handling user login request process.
	 * validate the Userid and password
	 * 
	 * @param loginInfo
	 * @param request
	 * @param response
	 */
	 @RequestMapping(  value="/userlogin", method = {RequestMethod.GET,RequestMethod.POST} )
	public void UserLogin (@RequestBody String loginInfo,HttpServletRequest request, HttpServletResponse response)
	{
		String userId = " " ;
		String password = " " ;
		log.info("Login Process"+loginInfo);
		JSONObject responseObject ;
		 try 
		 	{
			
			commonJsonObject = (JSONObject) commonJsonParser.parse(loginInfo);
			userId = (String) commonJsonObject.get("userId");
			password = (String) commonJsonObject.get("password");
			responseObject = new JSONObject();
			if(userId.trim().length() > 0 && password.trim().length() > 0)
			{
				pmf = PMF.get().getPersistenceManager();
				Query loginquery = pmf.newQuery(UserRegister.class);
				loginquery.setFilter("userId == loginUserId && password  == loginPassword");
//				loginquery.setFilter("password  == loginPassword");
				loginquery.declareParameters("String loginUserId, String loginPassword");
//				loginquery.declareParameters("String loginPassword");
				loginquery.setRange(0, 2);
				List<UserRegister> result = (List<UserRegister>) loginquery.execute(userId, password);
				System.out.println("login Query have value:"+result.isEmpty()+"userId:"+userId);
				log.info("login Query have value:"+result.isEmpty()+"userId:"+userId);
				if(!result.isEmpty())
				{
					
					responseObject.put("status","TRUE");
					responseObject.put("nextUrl", "mainpage.html");
				}
				else
				{
//					responseObject = new JSONObject();
					responseObject.put("status","FLASE");
					responseObject.put("error", "UserId Passord MisMatched");
				}	
			}
			
			response.getWriter().print(responseObject.toJSONString());
		 	} 
		 catch (Exception e) 
		 	{
			log.warning(e.getMessage());		 	
			}
		 finally
			 {
				pmf.close(); 
			 }
			 
		 
	}
	 
/**
 * This Method handle Signup Request process, 
 * If the userid not exist, its store into userProfile table
 * 
 * @param signupInfo
 * @param request
 * @param response
 */
	 
@RequestMapping(value="/signup",method = {RequestMethod.GET,RequestMethod.POST})
public void userSignup(@RequestBody String signupInfo,HttpServletRequest request,HttpServletResponse response)
{
	String userId = " "; 
	String password = " ";
	String firstName = " ";
	String lastName = " ";
	String emailId = " ";
	String phoneNo = " " ;
	String city = " ";
	log.info("signup Process"+signupInfo);
	JSONObject responseObject = new JSONObject();
	try
	  {
		commonJsonObject 	= (JSONObject) commonJsonParser.parse(signupInfo);
		userId 				= (String) commonJsonObject.get("userId");
		password 			= (String) commonJsonObject.get("password");
		firstName 			= (String) commonJsonObject.get("firstName");
		lastName			= (String) commonJsonObject.get("lastName");
		emailId 			= (String) commonJsonObject.get("emailId");
		phoneNo				= (String) commonJsonObject.get("phoneNo");
		city 				= (String) commonJsonObject.get("city");
		
		if(userId.trim().length() > 0)
		{
			pmf = PMF.get().getPersistenceManager();
			Query signupquery = pmf.newQuery(UserRegister.class);
			signupquery.setFilter("userId == signupUserId");
			signupquery.declareParameters("String signupUserId");
			signupquery.setRange(0, 2);
			List<UserRegister> queryResult = (List<UserRegister>) signupquery.execute(userId);
			System.out.println("signup Query have value:"+queryResult.isEmpty()+",userId:"+userId);
			log.info("signup Query have value:"+queryResult.isEmpty()+",userId:"+userId);
			
			if(queryResult.isEmpty())
			{
				UserRegister userProfile = new UserRegister();
				userProfile.setUserId(userId);
				userProfile.setPassword(password);
				userProfile.setFirstName(firstName);
				userProfile.setLastName(lastName);
				userProfile.setEmailId(emailId);
				userProfile.setMobileNo(phoneNo);
				userProfile.setCity(city);
				pmf.makePersistent(userProfile);
				responseObject = new JSONObject();
				responseObject.put("status","TRUE");
				responseObject.put("nextUrl", "login.html");
			}
			else
			{
				responseObject = new JSONObject();
				responseObject.put("status","FALSE");
				responseObject.put("error", "UserId Alread Exists");
			}
		}
		response.getWriter().print(responseObject.toJSONString());
	  }
	catch(Exception e)
	{
		System.out.println(e);
		log.warning(e.getMessage());
	}
	 finally
	 {
		pmf.close(); 
	 }
}
	
}

$(document).on( "click","#loginbtn",login );

$(document).on("click","#signupbtn",signup);

function login () {

  

	var id = $("#lUsername").val()+"" ;
	var pwd = $('#lPassword').val()+"";
	if(id.trim()!="" && pwd.trim()!="")
	{
		  var logindata = {};
		  logindata.userId=id;
		  logindata.password=pwd;
		//put your ajax here
		 $.ajax({ 
		        //url
				
				url: "/userlogin", 
				type: "POST", 
				dataType: "json", 
				contentType: "application/json",
				data: JSON.stringify(logindata), 
				success: function( response ) {
				
				//user pwd or id wrong 
				
				/*
			    if()
				{
				
			   forgetpwd();
			   
				
				}*/
		 
//				   $.mobile.changePage('whoisonline.html',  
//									   {
//										  data: {"userdata": response.userdata}
//						
//										} ); 
					}

		});
	}
	else
	{
	  if(id.trim()=='')
		{
		  $("#email").focus();
		  $('#errlogin').innerHtml='Empty UserID';
		  return;  
		}
	  else if(pwd.trim()=='')
		{
		  $("#password").focus();
		  $('#errlogin').innerHtml='Empty Password';
		  return;
		}
		
	}

}
//not used now
function signup()
{
var userId =$('#susername').val();
var emailId =$('#semail').val();
var phoneNo =$('#stel').val();
var password =$('#sPassword').val();
//$.mobile.changePage('regform.html');

var signupdata = {};
signupdata.userId = userId;
signupdata.password = password;
signupdata.emailId = emailId;
signupdata.phoneNo = phoneNo;

	$.ajax({ 
        //url
		
		url: "/signup", 
		type: "POST", 
		dataType: "json", 
		contentType: "application/json",		
		data: JSON.stringify(signupdata), 
		success: function( response ) {
		console.log(response)
		//user pwd or id wrong 
		
		/*
	    if()
		{
		
	   forgetpwd();
	   
		
		}*/
 
//		   $.mobile.changePage('whoisonline.html',  
//							   {
//								  data: {"userdata": response.userdata}
//				
//								} ); 
			}

});


}



function forgetpwd()
{
var html ="<a class='ui-btn ui-corner-all' id='forgotpwd' href='forgetpwd.html'>Forgot it</a>"
$('#login').remove();
$('#signup').prepend(html);

}

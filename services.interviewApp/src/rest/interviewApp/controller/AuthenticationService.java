package rest.interviewApp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import libraries.RESTDocs.SpringWebAppConfig;
import libraries.logging.Logger;
import libraries.logging.fact.LogFactory;
import rest.interviewApp.doc.CustomVersioningSwaggerConfig;
import rest.interviewApp.dto.UserAuthenticationDTO;
import rest.interviewApp.pojo.UserBean;
import services.interviewApp.jpa.exception.UserNotFoundException;

@RestController
@RequestMapping(value={"/api"})
@Import({SpringWebAppConfig.class, CustomVersioningSwaggerConfig.class})
public class AuthenticationService
{
    	private Logger log;
    
	/*-----------------------------------------------------------
	* private methods
	*-------------------------------------------------------------*/
	
	private Logger getLog()
	{
		if(log == null)
		{
			log = LogFactory.getInstance();
		}
		return log;
	}
	
    	@ResponseBody
	@RequestMapping(value="/v1/authenticate", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('AUTHORISED')")
    	public ResponseEntity<UserBean> userCredentials(@RequestBody UserBean user, HttpServletRequest request, HttpServletResponse response)
    	{
    	 UserBean userInfo = null;
	    UserAuthenticationDTO authenticate = new UserAuthenticationDTO();
	    getLog().debug("Preparing to authenticate user with username:"+user.getUsername());
	    
	    try
	    {
		userInfo = authenticate.authenticateUser(user);
	    }catch (UserNotFoundException e)
	    {
		userInfo = new UserBean();
		userInfo.setValidUser(false);
		getLog().error("Exception Occured: "+e.getMessage());
	    }
	    
	    return new ResponseEntity<UserBean>(userInfo, HttpStatus.OK);
	    
    	    
    	}
	
}

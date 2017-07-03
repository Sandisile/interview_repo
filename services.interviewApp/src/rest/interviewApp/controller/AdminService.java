package rest.interviewApp.controller;

import java.util.List;

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
import rest.interviewApp.dto.CandidateDTO;
import rest.interviewApp.dto.RoleDTO;
import rest.interviewApp.dto.UserDTO;
import rest.interviewApp.pojo.CandidateBean;
import rest.interviewApp.pojo.RoleBean;
import rest.interviewApp.pojo.UserBean;

@RestController
@RequestMapping(value={"/api"})
@Import({SpringWebAppConfig.class, CustomVersioningSwaggerConfig.class})
public class AdminService 
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
	
	/*************************GET User List ********************************/
        
    	@ResponseBody
	@RequestMapping(value="/v1/getAllUsers", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('AUTHORISED')")
	public ResponseEntity<List<UserBean>> user(HttpServletRequest request, HttpServletResponse response) 
	{
	    	
	    	UserDTO userDTO = new UserDTO ();
	    	List<UserBean> userList  = userDTO.getAll();
	    	getLog().debug("Get User List "+userList);
		return new ResponseEntity<List<UserBean>>(userList, HttpStatus.OK);
	}
    	
	
    	/**********************ADD User ***********************************/
    	
    	@RequestMapping(value="/v1/addUser", method = RequestMethod.POST)
    	@PreAuthorize("hasAuthority('AUTHORISED')")
    	public ResponseEntity<Boolean> addCandidate (@RequestBody UserBean user)
    	{
    	    boolean isInserted = false;
    	    UserDTO userDTO = new UserDTO();
    	    getLog().info("Preparing to add User");
    	    if(user == null)
    	    {
    		getLog().info("User input invalid or empty fields submitted");
    		return new ResponseEntity<Boolean>(isInserted ,HttpStatus.BAD_REQUEST);
    	    }
    	    userDTO.add(user);
    	    isInserted = userDTO.isInserted();
    	    
    	    return new ResponseEntity<Boolean>(isInserted ,HttpStatus.CREATED);
    	}
    	
    	
	/*************************GET User List ********************************/
        
    	@ResponseBody
	@RequestMapping(value="/v1/getAllRoles", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('AUTHORISED')")
	public ResponseEntity<List<RoleBean>> role(HttpServletRequest request, HttpServletResponse response) 
	{
	    	
	    	RoleDTO roleDTO = new RoleDTO ();
	    	List<RoleBean> roleList  = roleDTO.getAll();
	    	getLog().debug("Get Role List "+roleList);
		return new ResponseEntity<List<RoleBean>>(roleList, HttpStatus.OK);
	}
    	
}

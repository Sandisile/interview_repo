package rest.interviewApp.dto;

import java.util.ArrayList;
import java.util.List;

import rest.interviewApp.pojo.RoleBean;
import rest.interviewApp.pojo.UserBean;
import services.interviewApp.jpa.dao.QualificationDAO;
import services.interviewApp.jpa.dao.UserDAO;
import services.interviewApp.jpa.entities.Role;
import services.interviewApp.jpa.entities.User;
import services.interviewApp.jpa.exception.UserNotFoundException;
import services.interviewApp.util.authentication.ADAuthenticationHandler;

public class UserAuthenticationDTO
{
    private ADAuthenticationHandler adAuthenticationHandler;
    private UserDAO userDAO ;
    
    public UserBean authenticateUser(UserBean userBean) throws UserNotFoundException
    {
	List<RoleBean> roleList = new ArrayList();
	UserBean userCredentials =  new UserBean();    
	RoleBean roleBean = null;
	
	if(adAuthenticationHandler == null)
	{
	    adAuthenticationHandler = new ADAuthenticationHandler();
	}
	
	adAuthenticationHandler.doADAuthenticate(userBean.getUsername(), userBean.getPassword());
	
	if(adAuthenticationHandler.getAdAuthenticateStatus().equals(ADAuthenticationHandler.AD_LOGIN))
	{
	    userDAO = new UserDAO();
	    String username = userBean.getUsername().replace("metmom\\", "");
	    User user = userDAO.findUserByUsername(username);
	    List<Role> listOfRoles = user.getRoleList();
	    userCredentials.setValidUser(true);
	    userCredentials.setUsername(username);
	    
	    if(!listOfRoles.isEmpty())
	    {
		
		for(Role role : listOfRoles)
		{
		    roleBean = new RoleBean();
		    roleBean.setRole(role.getRoleName());
		    roleList.add(roleBean);
		}
		userCredentials.setUsername(user.getUsername());
		userCredentials.setRoles(roleList);
		
	    }
	}
	else
	{
	    userCredentials.setValidUser(false);
	}
	    
	
	
	return userCredentials;
	 
    }
}

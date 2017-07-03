package rest.interviewApp.dto;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.util.ArrayList;
import java.util.List;

import rest.interviewApp.dto.DTO;
import rest.interviewApp.pojo.RoleBean;
import rest.interviewApp.pojo.UserBean;
import services.interviewApp.jpa.dao.QualificationDAO;
import services.interviewApp.jpa.dao.UserDAO;
import services.interviewApp.jpa.entities.Role;
import services.interviewApp.jpa.entities.User;
import services.interviewApp.jpa.exception.NotFoundException;
import services.interviewApp.jpa.exception.UserNotFoundException;

public class UserDTO implements DTO
{
    private UserDAO userDAO;
    private boolean inserted;
    
    @Override
    public List<UserBean> getAll() 
    {
	UserBean userBean = null;
	RoleBean roleBean = null;
	//Role role = null;
	//User user = null;
	userDAO = new UserDAO();
	List<UserBean> listOfUsers = null;
	
	List<User> userList = userDAO.getAllUsers();
	
	if(!userList.isEmpty())
	{
	    listOfUsers = new ArrayList();
	    
	    for(User user : userList )
	    {
		userBean = new UserBean();
		
		userBean.setUsername(user.getUsername());
		List<Role> roleList = user.getRoleList();
		
		if(!roleList.isEmpty())
		{
		    List<RoleBean> listOfRoles = new ArrayList();
		    
		    for(Role role : roleList)
		    {
			roleBean = new RoleBean();
			
			roleBean.setRole(role.getRoleName());
			listOfRoles.add(roleBean);
		    }
		    userBean.setRoles(listOfRoles);
		}
		
		listOfUsers.add(userBean);
		
	    }
	    
	}
	
	
	
	return listOfUsers;
    }

    @Override
    public Object findById(int id) 
    {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void update(Object obj) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void delete(int obj) throws NotFoundException
    {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void add(Object obj) 
    {
	Role role = null;
	User user = null;
	User findUser = null;
	
	if(obj instanceof UserBean)
	{
	    UserBean userBean = (UserBean)obj;
	    userDAO = new UserDAO();
	    user = new User();
	    String username = userBean.getUsername().replace("metmom\\", "");
	    try
	    {
		findUser = userDAO.findUserByUsername(username);
		 
	    }catch (UserNotFoundException e)
	    {
		
	    }
	    
	    RoleBean roleBean = userBean.getRole();
	    role = new Role();
	    if(findUser == null)
	    {
		    user.setUsername(username);
		    
			    
		    if(roleBean != null)
		    {
			
			role.setRoleId(roleBean.getRoleId());
			role.addUser(user);
			user.addRole(role);
		    }
	    }
	    else
	    {
		List<Role> roleList = findUser.getRoleList();
		
		for(Role userRole : roleList)
		{
    		    if( userRole.getRoleName().equals(roleBean.getRole()) )
    		    {
    			setInserted(false);
    			return;
    		    }
		}
		
		user.setUserId(findUser.getUserId());
		role.setRoleId(roleBean.getRoleId());
		user.addRole(role);
	    }
	    
	    userDAO.addUser(user); 
	    setInserted(true);
	    
	}
	
    }
    
    public boolean isInserted()
    {
        return inserted;
    }

    public void setInserted(boolean inserted) 
    {
        this.inserted = inserted;
    }

    @Override
    public List<?> search(Object obj) {
	// TODO Auto-generated method stub
	return null;
    }
    
}

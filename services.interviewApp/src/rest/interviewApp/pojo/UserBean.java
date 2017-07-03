package rest.interviewApp.pojo;

import java.io.Serializable;
import java.util.List;

import rest.interviewApp.pojo.RoleBean;

public class UserBean implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String username;
    private List<RoleBean> roles;
    private RoleBean role;
    private boolean validUser;
    private String password;
    private int userId;
    
    
    public RoleBean getRole()
    {
	
        return role;
    }
    
    public void setRole(RoleBean role) 
    {
        this.role = role;
    }
    
    public String getUsername()
    {
        return username;
    }
    
    public void setUsername(String username) 
    {
        this.username = username;
    }
    
    public List<RoleBean> getRoles() 
    {
        return roles;
    }
    
    public void setRoles(List<RoleBean> roles)
    {
        this.roles = roles;
    }
    
    public boolean isValidUser() 
    {
        return validUser;
    }
    
    public void setValidUser(boolean validUser)
    {
        this.validUser = validUser;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public void setPassword(String password) 
    {
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
  
    
}

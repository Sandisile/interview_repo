package rest.interviewApp.pojo;

import java.io.Serializable;
import java.util.List;

import rest.interviewApp.pojo.UserBean;

public class RoleBean implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String role;
    private List<UserBean> userList;
    private int roleId;
    
    
    public String getRole()
    {
        return role;
    }
    public void setRole(String role) 
    {
        this.role = role;
    }
    public List<UserBean> getUserList() 
    {
        return userList;
    }
    public void setUserList(List<UserBean> userList) 
    {
        this.userList = userList;
    }
    
    public int getRoleId() 
    {
        return roleId;
    }
    public void setRoleId(int roleId)
    {
        this.roleId = roleId;
    }
    
}

package services.interviewApp.jpa.entities;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "IM_ROLE")
public class Role implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name = "ROLE_ID")
    private int roleId;
    
    @Column(name = "ROLE_NAME", nullable = false)
    private String roleName;
    
    @ManyToMany(mappedBy = "roleList", cascade = CascadeType.ALL)
    private List<User> userList;

    public int getRoleId()
    {
        return roleId;
    }

    public void setRoleId(int roleId)
    {
        this.roleId = roleId;
    }

    public String getRoleName() 
    {
        return roleName;
    }

    public void setRoleName(String roleName)
    {
        this.roleName = roleName;
    }

    public List<User> getUserList()
    {
        return userList;
    }

    public void setUserList(List<User> userList) 
    {
        this.userList = userList;
    }
    
    public void addUser(User user)
    {
	if(userList == null )
	{
	    userList = new ArrayList();
	}
	userList.add(user);
	
    }
    
   
}

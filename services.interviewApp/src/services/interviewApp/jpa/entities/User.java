package services.interviewApp.jpa.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "IM_USER")
@NamedQueries({
    @NamedQuery(name = "User.findByUsername", query = "select u from User u where u.username = :USERNAME")
})
public class User implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private  int userId;
    
    @Column(name = "USERNAME", unique=true , nullable = false)
    private String username;
    
    @JoinTable(name= "IM_USER_ROLE",
	    	joinColumns = {
	    		@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
	    	},
	    	inverseJoinColumns = {
	    		@JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID")
	    	})
    @ManyToMany
    private List<Role> roleList;

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username) 
    {
        this.username = username;
    }

    public List<Role> getRoleList()
    {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) 
    {
        this.roleList = roleList;
    }
    
    public void addRole(Role role)
    {
	if(roleList == null)
	{
	    roleList = new ArrayList();
	}
	roleList.add(role);
    }
    

}

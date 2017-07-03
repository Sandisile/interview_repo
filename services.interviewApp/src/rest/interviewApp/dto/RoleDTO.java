package rest.interviewApp.dto;

import java.util.ArrayList;
import java.util.List;

import rest.interviewApp.dto.DTO;
import rest.interviewApp.pojo.RoleBean;
import services.interviewApp.jpa.dao.QualificationDAO;
import services.interviewApp.jpa.dao.RoleDAO;
import services.interviewApp.jpa.entities.Role;
import services.interviewApp.jpa.exception.NotFoundException;

public class RoleDTO implements DTO
{

    private RoleDAO roleDAO ;
    
    @Override
    public List<RoleBean> getAll()
    {
	RoleBean roleBean = null;
	List<RoleBean> listOfRoles = null;
	roleDAO = new RoleDAO();
	List<Role> roleList = roleDAO.getAllRoles();
	
	if(!roleList.isEmpty())
	{
	    listOfRoles = new ArrayList();
	    for(Role role : roleList)
	    {
		roleBean = new RoleBean();
		roleBean.setRole(role.getRoleName());
		roleBean.setRoleId(role.getRoleId());
		listOfRoles.add(roleBean);
	    }
	    
	}
	return listOfRoles;
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
	// TODO Auto-generated method stub
	
    }

    @Override
    public List<?> search(Object obj) {
	// TODO Auto-generated method stub
	return null;
    }

}

package services.interviewApp.jpa.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import libraries.logging.Logger;
import libraries.logging.fact.LogFactory;
import services.interviewApp.jpa.entities.Role;
import services.interviewApp.util.Context;

public class RoleDAO 
{

	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	private Logger log = null;
	
	public RoleDAO()
	{
		
	}
	
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
	
	private EntityManager  getEntityManager()
	{
		Context ctxt= Context.getContextInstance();
		EntityManager entityManager = ctxt.getEntityManager();
		if(ctxt != null)
		{
			getLog().info(" Connection established from the Database");
		}
		
		return entityManager;
	}
	
	public List<Role> getAllRoles()
	{
	    String sql = "select r from Role r";
	    Query query = getEntityManager().createQuery(sql,Role.class);
	    getLog().info("Role List pulled successfully from the database");
	    
	    return query.getResultList();
	}
}

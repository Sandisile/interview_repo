package services.interviewApp.jpa.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import libraries.logging.Logger;
import libraries.logging.fact.LogFactory;
import services.interviewApp.jpa.entities.User;
import services.interviewApp.jpa.exception.UserNotFoundException;
import services.interviewApp.util.Context;

public class UserDAO
{
	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	private Logger log = null;
	
	public UserDAO()
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
	
	public List<User> getAllUsers()
	{
	    String sql = "select u from User u";
	    Query query =  getEntityManager().createQuery(sql,User.class);
	    getLog().info("User List pulled successfully from the database");
	    
	    return query.getResultList();
	}
	
	public void addUser(User user)
	{
	    if(!getEntityManager().getTransaction().isActive())
	    {
	    	getEntityManager().getTransaction().begin();
	    }
	    getEntityManager().merge(user);
	    getEntityManager().getTransaction().commit();
	    getLog().info("New User added to the database ");
	}

	public User findUserByUsername(String username) throws UserNotFoundException 
	{
	    User user  = null;
	    
	    Query query =  getEntityManager().createNamedQuery("User.findByUsername",User.class);
	    query.setParameter("USERNAME", username);
	    
	    try
	    {
		user = (User)query.getSingleResult();
		
	    } catch (NoResultException e) 
	    {
		throw new UserNotFoundException("Could not Found User with username :"+username);
	    }
		
	    return user;
	}
}

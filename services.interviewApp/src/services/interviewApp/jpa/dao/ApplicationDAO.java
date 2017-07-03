package services.interviewApp.jpa.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;

import libraries.logging.Logger;
import libraries.logging.fact.LogFactory;
import services.interviewApp.jpa.entities.Application;
import services.interviewApp.jpa.entities.Candidate;
import services.interviewApp.jpa.exception.NotFoundException;
import services.interviewApp.util.Context;

public class ApplicationDAO implements Serializable
{
    
	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	private Logger log = null;
	
	public ApplicationDAO()
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
	public Application findApplicationById(int applicationId) throws NotFoundException
	{
	    Application application = getEntityManager().find(Application.class, applicationId);
	    
	    if(application == null)
	    {
		throw new NotFoundException("Could not found ApplicationId with this ID: "+applicationId);
	    }
	    getLog().debug("Candidate Found: "+application);
	    
	    return application;
	}
	
	public void addApplication(Application application) 
	{
	    if(!getEntityManager().getTransaction().isActive())
	    {
	    	getEntityManager().getTransaction().begin();
	    }
	    getEntityManager().persist(application);
	    getEntityManager().getTransaction().commit();
	    getLog().info("New Application added successfully");
	}
	
}

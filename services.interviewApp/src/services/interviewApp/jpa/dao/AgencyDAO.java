package services.interviewApp.jpa.dao;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import libraries.logging.Logger;
import libraries.logging.fact.LogFactory;
import services.interviewApp.jpa.entities.Agency;
import services.interviewApp.jpa.exception.NotFoundException;
import services.interviewApp.util.Context;

public class AgencyDAO implements Serializable 
{

	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	private Logger log = null;
	
	public AgencyDAO()
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
	
	public void addAgency(Agency agency)
	{
	    getEntityManager().persist(agency);
	    getLog().info(" Agency added successfully");
	}
	
	public Agency findAgencyByName(String agencyName) throws NotFoundException
	{
	    Agency agency = null;
	    Query query = getEntityManager().createNamedQuery("Agency.findByName", Agency.class);
	    query.setParameter("NAME", agencyName);
	    
	    try
	    {
		agency = (Agency)query.getSingleResult();

	    }catch (NoResultException e)
	    {
		throw new NotFoundException("Could not find Agency with this Name: "+agencyName);
	    }
	    
	    
	    return agency;
	}
	
	public List<Agency> getAgencyList()
	{
		String sql = "select ag from Agency ag";
		Query query = getEntityManager().createQuery(sql,Agency.class);
		
		if(query != null)
		{
			getLog().info("Agency List pulled successfully from the database");
		}
		return query.getResultList();
	}
}

package services.interviewApp.jpa.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import libraries.logging.Logger;
import libraries.logging.fact.LogFactory;
import services.interviewApp.jpa.entities.Candidate;
import services.interviewApp.jpa.exception.NotFoundException;
import services.interviewApp.util.Context;

public class CandidateDAO
{


	/**
         * 
         */
    	private static final long serialVersionUID = 1L;
	private Logger log = null;
	
	public CandidateDAO()
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
	
	public void addCandidate(Candidate candidate) 
	{
	    if(!getEntityManager().getTransaction().isActive())
	    {
	    	getEntityManager().getTransaction().begin();
	    }
	    getEntityManager().persist(candidate);
	    getEntityManager().getTransaction().commit();
	    getLog().info("New Candidate added to the database ");
	}
	
	public Candidate findCandidateByIdNumber(String idNumber) throws NotFoundException
	{
	    Candidate candidate = null;
	    Query query = getEntityManager().createNamedQuery("Candidate.findByIdNumber", Candidate.class);
	    query.setParameter("IDNO", idNumber);
	    
	    try
	    {
		candidate = (Candidate)query.getSingleResult();
		
	    }catch (NoResultException e)
	    {
		throw new NotFoundException("Could not find Candidate with this ID NO: "+idNumber);
	    }
	    
	    
	    return candidate;
	}
	
	public void updateCandidate(Candidate candidate)
	{
	    if(!getEntityManager().getTransaction().isActive())
	    {
	    	getEntityManager().getTransaction().begin();
	    }
	    getEntityManager().merge(candidate);
	    getEntityManager().getTransaction().commit();
	    getLog().info(" Candidate updated successfully ");
	}
	
	public void removeCandidate(int candidateId) throws NotFoundException
	{
	    Candidate candidate = findCandidateById(candidateId);
	    candidate.setCandidateStatus("deleted");
	    
	    if(!getEntityManager().getTransaction().isActive())
	    {
	    	getEntityManager().getTransaction().begin();
	    }
	    
	    getEntityManager().merge(candidate);
	    getEntityManager().getTransaction().commit();
	    getLog().info("Candidate removed successfully");
	    
	}
	
	public Candidate findCandidateById(int candidateId) throws NotFoundException
	{
	    Candidate candidate = (Candidate)getEntityManager().find(Candidate.class, candidateId);
	    
	    if(candidate == null)
	    {
		throw new NotFoundException("Could not found Candidate with this ID: "+candidateId);
	    }
	    getLog().debug("Candidate Found: "+candidate);
	    
	    return candidate;
	}
	

	public List<Candidate> getCandidates()
	{
		String sql = "select c from Candidate c where c.candidateStatus <> 'deleted' OR c.candidateStatus is Null";
		
		Query query = getEntityManager().createQuery(sql,Candidate.class);
		getLog().info("Candidate List pulled successfully from the database");
		
		
		
		return query.getResultList();
	}
	
	public List<Candidate> searchCandidate(String search)
	{
	    Query query = getEntityManager().createNamedQuery("Candidate.search", Candidate.class);
	    query.setParameter("IDNO", "%"+search+"%");
	    query.setParameter("FIRSTNAME", "%"+search+"%");
	    query.setParameter("SURNAME", "%"+search+"%");
	    
	    List<Candidate> c = (List<Candidate>)query.getResultList();

	    return c;
	}
	
}

package services.interviewApp.jpa.dao;


import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import javax.persistence.NoResultException;

import services.interviewApp.jpa.dao.QualificationDAO;
import services.interviewApp.jpa.entities.Agency;
import services.interviewApp.jpa.entities.Application;
import services.interviewApp.jpa.entities.Candidate;
import services.interviewApp.jpa.entities.Comment;
import services.interviewApp.jpa.entities.Qualification;
import services.interviewApp.jpa.entities.Role;
import services.interviewApp.jpa.entities.User;
import services.interviewApp.jpa.exception.NotFoundException;
import services.interviewApp.jpa.exception.UserNotFoundException;
import services.interviewApp.util.Context;
import libraries.logging.Logger;
import libraries.logging.fact.LogFactory;
import rest.interviewApp.pojo.UserBean;

public class QualificationDAO implements Serializable
{
	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	private Logger log = null;
	
	public QualificationDAO()
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
	
	public List<Qualification> getQualificationList()
	{
		String sql = "select q from Qualification q";
		Query query = getEntityManager().createQuery(sql,Qualification.class);
		
		if(query != null)
		{
			getLog().info("Qualification List pulled successfully from the database");
		}
		return query.getResultList();
	}
	

	


	

	

	
}


package services.interviewApp.jpa.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import libraries.logging.Logger;
import libraries.logging.fact.LogFactory;
import services.interviewApp.jpa.entities.Comment;
import services.interviewApp.util.Context;

public class CommentDAO implements Serializable
{

	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	private Logger log = null;
	
	public CommentDAO()
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
	

	public void addComment(Comment comment)
	{
	    if(!getEntityManager().getTransaction().isActive())
	    {
	    	getEntityManager().getTransaction().begin();
	    }
	    getEntityManager().merge(comment);
	    getEntityManager().getTransaction().commit();
		
	    getLog().info("New Comment added to the database ");
	}
	
	
	public List<Comment> getListOfComments()
	{
		String sql = "select co from Comment co";
		Query query = getEntityManager().createQuery(sql,Comment.class);
		
		if(query != null)
		{
			getLog().info("Comment List pulled successfully from the database");
		}
		return query.getResultList();	
	}
}

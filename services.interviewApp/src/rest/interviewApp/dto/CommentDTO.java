package rest.interviewApp.dto;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import libraries.logging.Logger;
import libraries.logging.fact.LogFactory;
import rest.interviewApp.dto.DTO;
import rest.interviewApp.pojo.ApplicationBean;
import rest.interviewApp.pojo.CommentBean;
import services.interviewApp.jpa.dao.ApplicationDAO;
import services.interviewApp.jpa.dao.CommentDAO;
import services.interviewApp.jpa.dao.QualificationDAO;
import services.interviewApp.jpa.entities.Application;
import services.interviewApp.jpa.entities.Comment;
import services.interviewApp.jpa.exception.NotFoundException;

public class CommentDTO implements DTO 
{
    private CommentDAO commentDAO;
    private ApplicationDAO  applicationDAO;
    private Logger log;
    
    private Logger getLog()
    {
	if(log == null)
	{
	    log = LogFactory.getInstance();
	}
	return log;
    }
    
    @Override
    public List<?> getAll()
    {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Object findById(int id) throws NotFoundException
    {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void update(Object obj)
    {
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
	ApplicationBean applicationBean = null;
	Comment comment = null;
	CommentBean commentBean = null;
	Application application = null;
	
	if(obj != null && obj instanceof CommentBean)
	{
	    commentDAO = new CommentDAO();
	   commentBean = (CommentBean)obj;
	 
	   comment = new Comment();
	    
	    comment.setComment(commentBean.getComment());
	    comment.setCommentedPerson(commentBean.getCommentPerson());
	    comment.setCommentCreatedDate(new Date());
	    
	    applicationBean = commentBean.getApplicationBean();
	    
	    if(applicationBean != null)
	    {
		
		applicationDAO = new ApplicationDAO();
		
		
		try
		{
		    application = applicationDAO.findApplicationById(applicationBean.getApplicationId());
		}catch (NotFoundException e)
		{
		    getLog().debug(e.getMessage());
		}
		
		application.addComment(comment);
		comment.setApplication(application);
	    }
	    else
	    {
		return;
	    }
	    
	    commentDAO.addComment(comment); 
	    
	    
	}
	
    }

    @Override
    public List<?> search(Object obj) {
	// TODO Auto-generated method stub
	return null;
    }

}

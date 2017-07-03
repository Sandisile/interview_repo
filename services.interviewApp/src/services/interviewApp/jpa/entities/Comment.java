package services.interviewApp.jpa.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



@Entity
@Table(name = "IM_COMMENT")
public class Comment implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COMMENT_ID")
	private int commentId;
	
	@ManyToOne
	@JoinColumn(name = "APPLICATION_ID", referencedColumnName = "APPLICATION_ID")
	private Application application;
	
	@Column(name = "COMMENT_CREATED_DATE", insertable = true, nullable = false, updatable = false)
	private Date commentCreatedDate;
	
	@Column( name = "INTERVIEW_COMMENT", nullable = false)
	private String comment;
	
	@Column( name = "COMMENT_PERSON_COMMENTED",nullable = false)
	private String commentedPerson;
	
	
	public Date getCommentCreatedDate()
	{
		return commentCreatedDate;
	}
	public void setCommentCreatedDate(Date commentCreatedDate) 
	{
		this.commentCreatedDate = commentCreatedDate;
	}
	public String getComment() 
	{
		return comment;
	}
	public void setComment(String comment)  
	{
		this.comment = comment;
	}
	public String getCommentedPerson()
	{
		return commentedPerson;
	}
	public void setCommentedPerson(String commentedPerson) 
	{
		this.commentedPerson = commentedPerson;
	}
	
	public int getCommentId() 
	{
		return commentId;
	}
	public void setCommentId(int commentId) 
	{
		this.commentId = commentId;
	}
	public Application getApplication() 
	{
	    return application;
	}
	public void setApplication(Application application) 
	{
	    this.application = application;
	}
	
	
}

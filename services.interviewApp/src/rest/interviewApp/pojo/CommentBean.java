package rest.interviewApp.pojo;

import java.io.Serializable;
import java.util.Date;

import rest.interviewApp.pojo.ApplicationBean;

public class CommentBean implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private ApplicationBean applicationBean;
    private int commentId;
    private Date commentCreatedDate;
    private String comment;
    private String commentPerson;
    
    public ApplicationBean getApplicationBean() 
    {
        return applicationBean;
    }
    public void setApplicationBean(ApplicationBean applicationBean)
    {
        this.applicationBean = applicationBean;
    }
    public int getCommentId()
    {
        return commentId;
    }
    public void setCommentId(int commentId) 
    {
        this.commentId = commentId;
    }
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
    public String getCommentPerson()
    {
        return commentPerson;
    }
    public void setCommentPerson(String commentPerson) 
    {
        this.commentPerson = commentPerson;
    } 
    
}

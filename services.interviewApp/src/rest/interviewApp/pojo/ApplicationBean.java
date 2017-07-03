package rest.interviewApp.pojo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import rest.interviewApp.pojo.AgencyBean;
import rest.interviewApp.pojo.CandidateBean;
import rest.interviewApp.pojo.CommentBean;
import rest.interviewApp.pojo.QualificationBean;

public class ApplicationBean implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private int applicationId;
    private String applicationStatus;
    private Date applicationDate;
    private double expectedSalary;
    private double currentSalary;
    private int experience;
    private int recuiterDecision;
    private CandidateBean candidate;
    private QualificationBean qualification;
    private List<QualificationBean> qualificationList;
    private AgencyBean agency;
    private List<CommentBean> commentList;
    
    public int getApplicationId() 
    {
        return applicationId;
    }
    public void setApplicationId(int applicationId) 
    {
        this.applicationId = applicationId;
    }
    public String getApplicationStatus() 
    {
        return applicationStatus;
    }
    public void setApplicationStatus(String applicationStatus)
    {
        this.applicationStatus = applicationStatus;
    }
    public Date getApplicationDate() 
    {
        return applicationDate;
    }
    public void setApplicationDate(Date applicationDate)
    {
        this.applicationDate = applicationDate;
    }
    public double getExpectedSalary() 
    {
        return expectedSalary;
    }
    public void setExpectedSalary(double expectedSalary) 
    {
        this.expectedSalary = expectedSalary;
    }
    public double getCurrentSalary()
    {
        return currentSalary;
    }
    public void setCurrentSalary(double currentSalary)
    {
        this.currentSalary = currentSalary;
    }
    public int getExperience() 
    {
        return experience;
    }
    public void setExperience(int experience)
    {
        this.experience = experience;
    }
    public int getRecuiterDecision() 
    {
        return recuiterDecision;
    }
    public void setRecuiterDecision(int recuiterDecision) 
    {
        this.recuiterDecision = recuiterDecision;
    }
    public CandidateBean getCandidate() 
    {
        return candidate;
    }
    public void setCandidate(CandidateBean candidate) 
    {
        this.candidate = candidate;
    }
    
    public List<QualificationBean> getQualificationList() 
    {
        return qualificationList;
    }
    public void setQualificationList(List<QualificationBean> qualificationList) 
    {
        this.qualificationList = qualificationList;
    }
    
    public AgencyBean getAgency() 
    {
        return agency;
    }
    public void setAgency(AgencyBean agency) 
    {
        this.agency = agency;
    }
    
    public QualificationBean getQualification()
    {
        return qualification;
    }
    public void setQualification(QualificationBean qualification) 
    {
        this.qualification = qualification;
    }
    public List<CommentBean> getCommentList()
    {
        return commentList;
    }
    public void setCommentList(List<CommentBean> commentList)
    {
        this.commentList = commentList;
    }
    
}

package rest.interviewApp.pojo;

import java.io.Serializable;
import java.util.List;

import rest.interviewApp.pojo.ApplicationBean;



public class CandidateBean implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private int candidateId;
    private String firstname;
    private String surname;
    private String idNo;
    private String eeStatus;
    private List<ApplicationBean> applicationList;
    private ApplicationBean application;
    
    public List<ApplicationBean> getApplicationList()
    {
        return applicationList;
    }
    public void setApplicationList(List<ApplicationBean> applicationList)
    {
        this.applicationList = applicationList;
    }

    public int getCandidateId() 
    {
        return candidateId;
    }
    public void setCandidateId(int candidateId) 
    {
        this.candidateId = candidateId;
    }
    public String getFirstname() 
    {
        return firstname;
    }
    public void setFirstname(String firstname) 
    {
        this.firstname = firstname;
    }
    public String getSurname()
    {
        return surname;
    }
    public void setSurname(String surname) 
    {
        this.surname = surname;
    }
    public String getIdNo()
    {
        return idNo;
    }
    public void setIdNo(String idNo) 
    {
        this.idNo = idNo;
    }

    public String getEeStatus()
    {
        return eeStatus;
    }
    public void setEeStatus(String eeStatus) 
    {
        this.eeStatus = eeStatus;
    }
    public ApplicationBean getApplication() 
    {
        return application;
    }
    public void setApplication(ApplicationBean application)
    {
        this.application = application;
    }

    
       
}

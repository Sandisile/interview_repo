package rest.interviewApp.pojo;

import java.io.Serializable;

import rest.interviewApp.pojo.ApplicationBean;

public class AgencyBean implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private int agencyId;
    private String agencyName;
    private ApplicationBean application;
    
    public int getAgencyId()
    {
        return agencyId;
    }
    
    public void setAgencyId(int agencyId) 
    {
        this.agencyId = agencyId;
    }
    
    public String getAgencyName() 
    {
        return agencyName;
    }
    
    public void setAgencyName(String agencyName) 
    {
        this.agencyName = agencyName;
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

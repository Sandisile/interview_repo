package services.interviewApp.jpa.entities;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity
@Table(name = "IM_QUALIFICATION")
public class Qualification implements Serializable
{
	/**
     * 
     */
    private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "QUALIFICATION_ID")
	private int qualificationId;
	
	
	@ManyToMany(mappedBy = "qualificationList", cascade = CascadeType.ALL)
	private List<Application> applicationList;
	
	@Column(name = "QUALIFICATION_NAME", nullable = false, updatable = false)
	private String qualificationName;
	
	@Column(name = "QUALIFICATION_TYPE")
	private String qualificationType;
	
	public void setQualificationId(int qualificationId) 
	{
	    this.qualificationId = qualificationId;
	}

	public String getQualificationType() 
	{
	    return qualificationType;
	}

	public void setQualificationType(String qualificationType) 
	{
	    this.qualificationType = qualificationType;
	}

	public int getQualificationId() 
	{
		return qualificationId;
	}

	public void setQualificatioId(int qualificationId)
	{
		this.qualificationId = qualificationId;
	}

	public String getQualificationName()
	{
		return qualificationName;
	}

	public void setQualificationName(String qualificationName) 
	{
		this.qualificationName = qualificationName;
	}
	public List<Application> getApplicationList() 
	{
	    return applicationList;
	}

	public void setApplicationList(List<Application> applicationList) 
	{
	    this.applicationList = applicationList;
	}
	/*
	public void addApplication(Application application)
	{
	    if(applicationList == null)
	    {
		applicationList = new ArrayList();
	    }
	    applicationList.add(application);
	    application.addQualification(this);
	    
	}
		*/
}


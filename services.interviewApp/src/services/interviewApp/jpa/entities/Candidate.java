package services.interviewApp.jpa.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import javax.persistence.*;
import javax.validation.constraints.NotNull;


/**
 * Entity implementation class for Entity: Candidate
 *
 */
@Entity
@Table(name = "IM_CANDIDATE")
@NamedQueries({
    @NamedQuery(name = "Candidate.search", query = "select c from Candidate c where c.idNo like :IDNO "
    			+ "or c.firstname like :FIRSTNAME or c.surname like :SURNAME  "
    			+ "and c.candidateStatus <> 'deleted' OR c.candidateStatus is Null"),
    
    @NamedQuery(name = "Candidate.findByIdNumber", query = "select c from Candidate c where c.idNo = :IDNO")
})
public class Candidate implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="CANDIDATE_ID")
	private int candidateId;

	@Column(name = "CANDIDATE_IDNO",unique=true)
	@NotNull
	private String idNo;
	
	@NotNull
	@Column(name = "CANDIDATE_FIRSTNAME")
	private String firstname;
	
	@NotNull
	@Column(name = "CANDIDATE_SURNAME")
	private String surname;
	
	@Column(name = "CANDIDATE_EE_STATUS",nullable = true)
	private String EEStatus;
	
	@Column(name = "CANDIDATE_STATUS", nullable = true)
	private String candidateStatus;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "candidate", orphanRemoval = true)
	private List<Application> applicationList;

	
	public Candidate()
	{
		super();
	}
	
	
	public List<Application> getApplicationList() 
	{
	    return applicationList;
	}

	public void setApplicationList(List<Application> applicationList) 
	{
	    this.applicationList = applicationList;
	}

	public void addApplication(Application application)
	{
	    if(applicationList == null)
	    {
		applicationList = new ArrayList();
	    }
            applicationList.add(application);
            application.setCandidate(this);
	}

	public int getCandidateId()
	{
		return candidateId;
	}

	public void setCandidateId(int candidateId) 
	{
		this.candidateId = candidateId;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getFirstname() {
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

	public String getEEStatus() 
	{
		return EEStatus;
	}

	public void setEEStatus(String eeStatus) 
	{
		this.EEStatus = eeStatus;
	}
	
	public void setCandidateStatus(String candidateStatus)
	{
	    this.candidateStatus = candidateStatus;
	}
	
	public String getCandidateStatus()
	{
	    return candidateStatus;
	}
	
	
	
}

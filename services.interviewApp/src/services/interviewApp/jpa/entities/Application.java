package services.interviewApp.jpa.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Application
 *
 */
@Entity
@Table(name = "IM_APPLICATION")
public class Application implements Serializable 
{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	@Column(name = "APPLICATION_ID")
	private int applicationId;
	
	@Column(name = "APPLICATION_STATUS", nullable = false)
	private String applicationStatus;
	
	@Column(name = "APPLICATION_DATE")
	private Date applicationDate;
	
	@Column( name = "APPLICATION_CURRENT_SALARY")
	private double currentSalary;
	
	@Column(name ="APPLICATION_EXPECTED_SALARY")
	private double expectedSalary;
	
	@Column(name = "APPLICATION_EXPERIENCE")
	private int experience;
	
	@Column(name = "APPLICATION_RECRUITER_DECISION")
	private int recruiterDecision;
	
	@JoinColumn(name = "AGENCY_ID" ,referencedColumnName = "AGENCY_ID")
	@ManyToOne
	private Agency agency;
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "application", orphanRemoval = true )
	private List<Comment> listOfComments;
	
	@ManyToOne
	@JoinColumn(name = "CANDIDATE_ID", referencedColumnName = "CANDIDATE_ID")
	private Candidate candidate;
	
	@JoinTable(name = "IM_APPLICATION_QUALIFICATION", 
		joinColumns ={
			@JoinColumn(name = "APPLICATION_ID", referencedColumnName =  "APPLICATION_ID")
		}, 
		inverseJoinColumns ={
			@JoinColumn(name = "QUALIFICATION_ID", referencedColumnName = "QUALIFICATION_ID")
		}
		)
	@ManyToMany
	private List<Qualification> qualificationList;
	
	
	public Application() 
	{
		super();
	}

	public List<Qualification> getQualificationList() 
	{
	    return qualificationList;
	}

	public void setQualificationList(List<Qualification> qualificationList) 
	{
	    this.qualificationList = qualificationList;
	}

	public void addQualification(Qualification qualification)
	{
	    if(qualificationList == null)
	    {
		qualificationList = new ArrayList();
	    }
	    qualificationList.add(qualification);
	    
	}
	public Agency getAgency()
	{
	    return agency;
	}

	public void setAgency(Agency agency)
	{
	    this.agency = agency;
	}

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


	public double getCurrentSalary()
	{
	    return currentSalary;
	}


	public void setCurrentSalary(double currentSalary) 
	{
	    this.currentSalary = currentSalary;
	}


	public double getExpectedSalary() 
	{
	    return expectedSalary;
	}


	public void setExpectedSalary(double expectedSalary) 
	{
	    this.expectedSalary = expectedSalary;
	}


	public int getExperience() 
	{
	    return experience;
	}


	public void setExperience(int experience) 
	{
	    
	    this.experience = experience;
	}


	public int getRecruiterDecision()
	{
	    return recruiterDecision;
	}


	public void setRecruiterDecision(int recruiterDecision)
	{
	    this.recruiterDecision = recruiterDecision;
	}


	public Candidate getCandidate()
	{
	    return candidate;
	}


	public void setCandidate(Candidate candidate)
	{
	    this.candidate = candidate;
	}
   
	public List<Comment> getListOfComments()
	{
		return listOfComments;
	}

	public void setListOfComments(List<Comment> listOfComments)
	{
		this.listOfComments = listOfComments;
	}
	
	public void addComment(Comment comment)
	{
		if(listOfComments == null)
		{
			listOfComments = new ArrayList<Comment>(); 
		}
		listOfComments.add(comment);
	}
	/*
	public Comment removeCandidateComment(Comment comment) 
	{
		getListOfComments().remove(comment);
		comment.setApplication(null);

		return comment;
	}
*/
}

package services.interviewApp.jpa.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Agency
 *
 */
@Entity
@Table(name = "IM_AGENCY")
@NamedQueries({
    @NamedQuery(name = "Agency.findByName", query = "select ag from Agency ag where ag.agencyName = :NAME")
})
public class Agency implements Serializable 
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "AGENCY_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int agencyId;
	
	@Column(name = "AGENCY_NAME", insertable = true )
	private String agencyName;
	
	
	@OneToMany( cascade = CascadeType.ALL,  mappedBy = "agency", orphanRemoval = true)
	private List<Application> application;
	
	/*
	@PreRemove
	public void preRemove()
	{
	    setApplication(null);
	}
	*/
	public Agency() 
	{
		super();
	}
	
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

	public List<Application> getApplication()
	{
	    return application;
	}

	public void setApplication(List<Application> application) 
	{
	    this.application = application;
	}
	
}

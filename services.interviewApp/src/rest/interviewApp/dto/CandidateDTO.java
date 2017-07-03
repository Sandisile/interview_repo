package rest.interviewApp.dto;


import java.util.ArrayList;
import java.util.List;

import libraries.logging.Logger;
import libraries.logging.fact.LogFactory;
import rest.interviewApp.dto.DTO;
import rest.interviewApp.pojo.AgencyBean;
import rest.interviewApp.pojo.ApplicationBean;
import rest.interviewApp.pojo.CandidateBean;
import rest.interviewApp.pojo.CommentBean;
import rest.interviewApp.pojo.QualificationBean;
import services.interviewApp.jpa.dao.AgencyDAO;
import services.interviewApp.jpa.dao.ApplicationDAO;
import services.interviewApp.jpa.dao.CandidateDAO;
import services.interviewApp.jpa.dao.QualificationDAO;
import services.interviewApp.jpa.entities.Application;
import services.interviewApp.jpa.entities.Candidate;
import services.interviewApp.jpa.entities.Comment;
import services.interviewApp.jpa.entities.Agency;
import services.interviewApp.jpa.entities.Qualification;
import services.interviewApp.jpa.exception.NotFoundException;

public class CandidateDTO implements DTO
{
    private List<Candidate> candidateList = null;
    private CandidateDAO candidateDAO = null;
    private ApplicationDAO applicationDAO = null;
    private AgencyDAO agencyDAO = null;
    private boolean inserted;
    private boolean isUpdated;
    private Logger log;
    
    private Logger getLog()
    {
	if(log == null)
	{
	    log = LogFactory.getInstance();
	}
	return log;
    }
    
    public List<CandidateBean> getAll()
    {
	candidateDAO = new CandidateDAO();
	candidateList = candidateDAO.getCandidates();
	List<CandidateBean> listOfCandidates = null;
	CandidateBean candidateBean = null;
	Agency agency = null;
	List<QualificationBean> qualificationList  = null;
	QualificationBean candidateQualification = null;
	ApplicationBean application = null;
	CommentBean commentBean = null;
	List<ApplicationBean> applicationList = null;
	List<CommentBean> commentList = null;
	AgencyBean agencyBean = null;
	
	if(!candidateList.isEmpty())
	{
	    listOfCandidates = new ArrayList();
	    for(Candidate candidate : candidateList)
	    {
		candidateBean = new CandidateBean();
		candidateBean.setCandidateId(candidate.getCandidateId());
		candidateBean.setFirstname(candidate.getFirstname());
		candidateBean.setSurname(candidate.getSurname());
		candidateBean.setIdNo(candidate.getIdNo());
		candidateBean.setEeStatus(candidate.getEEStatus());
		
		if(candidate.getApplicationList() != null && !candidate.getApplicationList().isEmpty())
		{
		    	List<Application> appList = candidate.getApplicationList();
		    	applicationList = new ArrayList();
		    	for(Application app : appList)
		    	{
		    	    	application = new ApplicationBean();
		    	    	agencyBean = new AgencyBean();
		    	    	agency = app.getAgency();
		    	    	
		    	    	if(agency != null)
		    	    	{
        		    	    	agencyBean.setAgencyId(agency.getAgencyId());
        		    	    	agencyBean.setAgencyName(agency.getAgencyName());
        		    	    	application.setAgency(agencyBean);
		    	    	}
		    	    	
		    	    	application.setApplicationId(app.getApplicationId());
		    	    	application.setApplicationDate(app.getApplicationDate());
		    	    	application.setApplicationStatus(app.getApplicationStatus());
		    	    	application.setCurrentSalary(app.getCurrentSalary());
		    	    	application.setExpectedSalary(app.getExpectedSalary());
		    	    	application.setExperience(app.getExperience());
		    	    	application.setRecuiterDecision(app.getRecruiterDecision());
		    	    	
				if(app.getQualificationList() != null && !app.getQualificationList().isEmpty())
				{
		    		    	List<Qualification> listOfQualification = app.getQualificationList();
		    		    	qualificationList = new ArrayList();
		    		    	for(Qualification qualification : listOfQualification)
		    		    	{
		        			candidateQualification = new QualificationBean();
		        			candidateQualification.setQualificationId(qualification.getQualificationId());
		        			candidateQualification.setQualificationName(qualification.getQualificationName());
		        			candidateQualification.setQualificationType(qualification.getQualificationType());
		        			qualificationList.add(candidateQualification);
		    		    	}
		    		 
		    		    	application.setQualificationList(qualificationList);  
				}
				
				if(app.getListOfComments() != null &&  !app.getListOfComments().isEmpty())
				{
				    List<Comment> cList = app.getListOfComments();
				    commentList = new ArrayList();
				    
				    for(Comment comment : cList)
				    {
					commentBean = new CommentBean();
					
					commentBean.setCommentId(comment.getCommentId());
					commentBean.setComment(comment.getComment());
					commentBean.setCommentCreatedDate(comment.getCommentCreatedDate());
					commentBean.setCommentPerson(comment.getCommentedPerson());
					
					commentList.add(commentBean);
					
				    }
				    
				   application.setCommentList(commentList);
				    
				}
				
				applicationList.add(application);
		    	}
		    	candidateBean.setApplicationList(applicationList);
		}
		
		listOfCandidates.add(candidateBean);
	    }
	}
	
	return listOfCandidates;
    }
    
    @Override
    public void add(Object obj)
    {
	//Pojos
	
	ApplicationBean  applicationBean = null;
	AgencyBean agencyBean = null;
	CandidateBean candidateBean = null;
	//Entities 
	
	Candidate candidate = null;
	Application application = null;
	Agency agency = null;
	Qualification qualification = null;
	
	if(obj != null && obj instanceof CandidateBean )
	{
	    candidateDAO = new CandidateDAO();
	    candidate = new Candidate();
	    candidateBean = (CandidateBean)obj;
	    Candidate c = null;
	    try
	    {
		c = candidateDAO.findCandidateByIdNumber(candidateBean.getIdNo());
	    }catch(NotFoundException e)
	    {
		getLog().debug(e.getMessage());
	    }
	    
	    if(c == null)
	    {
		    candidate.setEEStatus(candidateBean.getEeStatus());
		    candidate.setFirstname(candidateBean.getFirstname());
		    candidate.setSurname(candidateBean.getSurname());
		    candidate.setIdNo(candidateBean.getIdNo());
	    }

	    
	    applicationBean = candidateBean.getApplication();
	    
	    if(applicationBean != null)
	    {
		application = new Application();
		
		application.setApplicationDate(applicationBean.getApplicationDate());
		application.setApplicationStatus(applicationBean.getApplicationStatus());
		application.setExperience(applicationBean.getExperience());
		application.setCurrentSalary(applicationBean.getCurrentSalary());
		application.setExpectedSalary(applicationBean.getExpectedSalary());
		
		agencyBean = applicationBean.getAgency();
		
		if(agencyBean != null && agencyBean.getAgencyName().length() != 0)
		{
		    
		    agency = new Agency();
		    agencyDAO = new AgencyDAO();
		    Agency ag = null;
		    
		    try
		    {
			ag = agencyDAO.findAgencyByName(agencyBean.getAgencyName());
			
		    }catch(NotFoundException e)
		    {
			getLog().debug(e.getMessage());
		    }
		    
		    //agency.setAgencyId(agencyBean.getAgencyId());
		    if(ag == null)
		    {
    		    	agency.setAgencyName(agencyBean.getAgencyName());
    		    	agencyDAO.addAgency(agency);
    		    	application.setAgency(agency);
		    }
		    else
		    {
			application.setAgency(ag);
		    }
		    
		}
		
		List<QualificationBean> listOfQualifications = applicationBean.getQualificationList();
		
		if(listOfQualifications != null && !listOfQualifications.isEmpty() )
		{
		    
		    for(QualificationBean q: listOfQualifications)
		    {
			qualification = new Qualification();
			
			qualification.setQualificatioId(q.getQualificationId());
			qualification.setQualificationName(q.getQualificationName());
			qualification.setQualificationType(q.getQualificationType());
			
			application.addQualification(qualification);
			
		    }
		    
		}
		 
		if(c != null)
		{
		    applicationDAO = new ApplicationDAO();
		    c.setCandidateStatus(null);
		    c.addApplication(application);
		    application.setCandidate(c);
		    applicationDAO.addApplication(application);
		}
		else
		{
		    candidate.addApplication(application);
		    candidateDAO.addCandidate(candidate);
		}
		
	    }
	    
	    setInserted(true);
	    
	}
	
    }

  

    @Override
    public void update(Object obj)
    {
	Candidate candidate = null;
	Qualification qualification = null;
	Application application = null;
	Application app = null;
	Agency agency = null;
	
	if(obj instanceof CandidateBean)
	{
	    CandidateBean candidateBean = (CandidateBean)obj;
	    candidate = new Candidate();
	    candidateDAO = new CandidateDAO();
	    applicationDAO = new ApplicationDAO();
	    
	    candidate.setCandidateId(candidateBean.getCandidateId());
	    candidate.setFirstname(candidateBean.getFirstname());
	    candidate.setSurname(candidateBean.getSurname());
	    candidate.setIdNo(candidateBean.getIdNo());
	    candidate.setEEStatus(candidateBean.getEeStatus());
	    
	    ApplicationBean applicationBean = candidateBean.getApplication();
	    
	    if(applicationBean != null)
	    {
		
		application = new Application();
		
		try
		{
		   app = applicationDAO.findApplicationById(applicationBean.getApplicationId());   
		}catch(NotFoundException e)
		{
		    getLog().error("Error: "+e.getMessage());
	   	    getLog().debug("Could not found Application with id: "+applicationBean.getApplicationId());
		}
		
		
		application.setApplicationId(applicationBean.getApplicationId());
		application.setApplicationDate(applicationBean.getApplicationDate());
		application.setApplicationStatus(applicationBean.getApplicationStatus());
		application.setExpectedSalary(applicationBean.getExpectedSalary());
		application.setCurrentSalary(applicationBean.getCurrentSalary());
		application.setExperience(applicationBean.getExperience());
		
		AgencyBean agencyBean = applicationBean.getAgency();
		if(agencyBean != null && agencyBean.getAgencyId() != 0)
		{
		    agency = new Agency();
		    agency.setAgencyId(agencyBean.getAgencyId());
		    application.setAgency(agency); 
		}
		
		QualificationBean qualificationBean = applicationBean.getQualification();
		
		if(qualificationBean != null && qualificationBean.getQualificationId() !=0)
		{
		    boolean isFound = false;
		    qualification = new Qualification();
		    
		    qualification.setQualificatioId(qualificationBean.getQualificationId());
		    qualification.setQualificationName(qualificationBean.getQualificationName());
		    qualification.setQualificationType(qualificationBean.getQualificationType());
		    
		    if(app != null && app.getQualificationList() != null)
		    {
			for(Qualification q :app.getQualificationList())
			{
			    if(qualificationBean.getQualificationId() == q.getQualificationId())
			    {
				isFound = true;
			    }
			}
			if(!isFound)
			{
			    app.getQualificationList().add(qualification);
			}
			
		    }
		    else
		    {
			application.addQualification(qualification);
		    }
		}
		
		candidate.addApplication(application);
		application.setCandidate(candidate);
	    }
	    
	    candidateDAO.updateCandidate(candidate);
	    setUpdated(true);
	    
	    
	}
	
    }

    @Override
    public void delete(int candidateId) throws NotFoundException
    {
	candidateDAO = new CandidateDAO();
	candidateDAO.removeCandidate(candidateId);
		
    }

    @Override
    public Object findById(int id) throws NotFoundException
    {
	candidateDAO = new CandidateDAO();
	CandidateBean candidate = null;
	AgencyBean agencyBean = null;
	Candidate obj = candidateDAO.findCandidateById(id);
	candidate = new CandidateBean();
	List<ApplicationBean> applicationList = null;
	ApplicationBean applicationBean = null;
	List<QualificationBean> qualificationList = null;
	QualificationBean qualificationBean = null;
	
	candidate.setCandidateId(obj.getCandidateId());
	candidate.setFirstname(obj.getFirstname());
	candidate.setSurname(obj.getSurname());
	candidate.setEeStatus(obj.getEEStatus());
	candidate.setIdNo(obj.getIdNo());
	
	List<Application> listOfApp = obj.getApplicationList();
	
	if(listOfApp != null || !listOfApp.isEmpty())
	{
	   applicationList = new ArrayList();
	   
	   for(Application application : listOfApp)
	   {
	       applicationBean = new ApplicationBean();
	       applicationBean.setApplicationId(application.getApplicationId());
	       applicationBean.setApplicationStatus(application.getApplicationStatus());
	       applicationBean.setCurrentSalary(application.getCurrentSalary());
	       applicationBean.setExpectedSalary(application.getExpectedSalary());
	       applicationBean.setExperience(application.getExperience());
	       applicationBean.setApplicationDate(application.getApplicationDate());
	       
	       Agency agency  = application.getAgency();
	       if(agency != null)
	       {
		   agencyBean = new AgencyBean();
		   agencyBean.setAgencyId(agency.getAgencyId());
		   agencyBean.setAgencyName(agency.getAgencyName());
		   
	       }
	       
	       List<Qualification> listOfQ = application.getQualificationList();
	       
	       if(listOfQ != null && !listOfQ.isEmpty())
	       {
		   qualificationList = new ArrayList();
		   
		   for(Qualification qualification : listOfQ)
		   {
		       qualificationBean = new QualificationBean();
		       
		       qualificationBean.setQualificationId(qualification.getQualificationId());
		       qualificationBean.setQualificationName(qualification.getQualificationName());
		       qualificationBean.setQualificationType(qualification.getQualificationType());
		       
		       qualificationList.add(qualificationBean);
		   }
		   
		   applicationBean.setQualificationList(qualificationList);
	       }
	       
	       applicationList.add(applicationBean);
	   }
	   candidate.setApplicationList(applicationList);
	}
	
	return (Object)candidate;
    }

    public boolean isInserted()
    {
        return inserted;
    }

    public void setInserted(boolean inserted) 
    {
        this.inserted = inserted;
    }

    public boolean isUpdated() 
    {
        return isUpdated;
    }

    public void setUpdated(boolean isUpdated) 
    {
        this.isUpdated = isUpdated;
    }

    @Override
    public List<CandidateBean> search(Object obj) 
    {
	CandidateBean candidateBean = null;
	ApplicationBean applicationBean = null;
	//
	candidateDAO = new CandidateDAO();
	if(obj instanceof String)
	{
	    String search = (String)obj;
	    List<Candidate> candidateList = candidateDAO.searchCandidate(search);
	    
	    if(candidateList != null && !candidateList.isEmpty())
	    {
		
	    }
	    
	    
	}
	
	return null;
    }
    
    
   
}

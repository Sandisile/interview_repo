package rest.interviewApp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import libraries.RESTDocs.SpringWebAppConfig;
import libraries.logging.Logger;
import libraries.logging.fact.LogFactory;
import rest.interviewApp.doc.CustomVersioningSwaggerConfig;
import rest.interviewApp.dto.AgencyDTO;
import rest.interviewApp.dto.CandidateDTO;
import rest.interviewApp.dto.CommentDTO;
import rest.interviewApp.dto.QualificationDTO;
import rest.interviewApp.pojo.AgencyBean;
import rest.interviewApp.pojo.CandidateBean;
import rest.interviewApp.pojo.CommentBean;
import rest.interviewApp.pojo.QualificationBean;
import services.interviewApp.jpa.entities.Candidate;
import services.interviewApp.jpa.exception.NotFoundException;

@RestController
@RequestMapping(value={"/api"})
@Import({SpringWebAppConfig.class, CustomVersioningSwaggerConfig.class})
public class InterviewController 
{
    private Logger log;
        public InterviewController() 
        {
    	
        }
        
        
	/*-----------------------------------------------------------
	* private methods
	*-------------------------------------------------------------*/
	
	private Logger getLog()
	{
		if(log == null)
		{
			log = LogFactory.getInstance();
		}
		return log;
	}
        
	/*************************GET Candidate List ********************************/
        
    	@ResponseBody
	@RequestMapping(value="/v1/getAllCandidates", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('AUTHORISED')")
	public ResponseEntity<List<CandidateBean>> candidate(HttpServletRequest request, HttpServletResponse response) 
	{
	    	
	    	CandidateDTO candidateDTO = new CandidateDTO ();
	    	List<CandidateBean> candidateList  = candidateDTO .getAll();
	    	getLog().debug("Get Candidate List "+candidateList);
		return new ResponseEntity<List<CandidateBean>>(candidateList, HttpStatus.OK);
	}
    	
    	/**********************ADD Candidate ***********************************/
    	
    	@RequestMapping(value="/v1/addCandidate", method = RequestMethod.POST)
    	@PreAuthorize("hasAuthority('AUTHORISED')")
    	public ResponseEntity<Boolean> addCandidate (@RequestBody CandidateBean candidate)
    	{
    	    boolean isInserted = false;
    	    CandidateDTO candidateDTO = new CandidateDTO();
    	    getLog().info("Preparing to add Candidate");
    	    if(candidate == null)
    	    {
    		getLog().info("User input invalid or empty fields submitted");
    		return new ResponseEntity<Boolean>(isInserted ,HttpStatus.BAD_REQUEST);
    	    }
    	    candidateDTO.add(candidate);
    	    isInserted = candidateDTO.isInserted();
    	    
    	    return new ResponseEntity<Boolean>(isInserted ,HttpStatus.CREATED);
    	}
    	
   	/**********************UPDATE Candidate ***********************************/
    	
    	@RequestMapping(value="/v1/updateCandidate", method = RequestMethod.PUT)
    	@PreAuthorize("hasAuthority('AUTHORISED')")
    	public ResponseEntity<Boolean> updateCandidate (@RequestBody CandidateBean candidate)
    	{
    	    boolean isUpdated = false;
    	    CandidateDTO candidateDTO = new CandidateDTO();
    	    getLog().info("Preparing to add Candidate");
    	    if(candidate == null)
    	    {
    		getLog().info("User input invalid or empty fields submitted");
    		return new ResponseEntity<Boolean>(isUpdated ,HttpStatus.BAD_REQUEST);
    	    }
    	    candidateDTO.update(candidate);
    	    isUpdated = candidateDTO.isInserted();
    	    
    	    return new ResponseEntity<Boolean>(isUpdated ,HttpStatus.CREATED);
    	}
    	
    	
   	/**********************Remove Candidate ***********************************/
    	
 	@RequestMapping(value="/v1/deleteCandidate/{candidateId}", method = RequestMethod.DELETE)
    	@PreAuthorize("hasAuthority('AUTHORISED')")
    	public ResponseEntity<?> removeCandidate (@PathVariable int candidateId)
    	{
    	    CandidateDTO candidateDTO = new CandidateDTO();
    	    getLog().info("Fetching and Deleting Candidate with id: "+ candidateId);
    	    try
    	    {
    		 candidateDTO.delete(candidateId);
    		
    	    }catch(NotFoundException e)
    	    {
    		getLog().error("Error: "+e.getMessage());
   		getLog().debug("Unable to delete. Candidate with id: "+candidateId);
    		
    		return new ResponseEntity<Long>(HttpStatus.NOT_FOUND);
    	    }
    	      
    	    return new ResponseEntity<Long>(HttpStatus.ACCEPTED);
    	}
	
   	/**********************Find Candidate ***********************************/
    	
 	@ResponseBody
 	@RequestMapping(value="/v1/findCandidate/{candidateId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    	@PreAuthorize("hasAuthority('AUTHORISED')")
    	public ResponseEntity<CandidateBean> findCandidate (@PathVariable int candidateId)
    	{
 	    CandidateBean candidate = null;
    	    CandidateDTO candidateDTO = new CandidateDTO();
    	    getLog().info("Fetching  Candidate with id: "+ candidateId);
    	    try
    	    {
    		candidate = (CandidateBean)candidateDTO.findById(candidateId);
    		
    	    }catch(NotFoundException e)
    	    {
    		getLog().error("Error: "+e.getMessage());
   		getLog().debug("Could not found Candidate with id: "+candidateId);
    		
    		return new ResponseEntity<CandidateBean>(HttpStatus.NOT_FOUND);
    	    }
    	    
    	    return new ResponseEntity<CandidateBean>(candidate, HttpStatus.OK);
    	}
 	
   	/**********************Search Candidate ***********************************/
    	
 	@ResponseBody
 	@RequestMapping(value="/v1/searchCandidate/{search}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    	@PreAuthorize("hasAuthority('AUTHORISED')")
    	public ResponseEntity<List<Candidate>> searchCandidate (@PathVariable String search)
    	{
 	    List<Candidate> candidateList = null;
    	    CandidateDTO candidateDTO = new CandidateDTO();
    	    getLog().info("Fetching  Candidate with id: "+ search);
    	    try
    	    {
    		//candidateList = candidateDTO.search(search);
    		
    	    }catch(Exception e)
    	    {
    		getLog().error("Error: "+e.getMessage());
   		getLog().debug("Could not found Candidate with id: "+search);
    		
    		return new ResponseEntity<List<Candidate>>(HttpStatus.NOT_FOUND);
    	    }
    	    
    	    return new ResponseEntity<List<Candidate>>((List<Candidate>) candidateList, HttpStatus.OK);
    	}
 	
    	/*************************GET Agency List ********************************/
    	
	@ResponseBody
	@RequestMapping(value="/v1/getAllAgencies", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('AUTHORISED')")
	public ResponseEntity<List<AgencyBean>> agency(HttpServletRequest request, HttpServletResponse response) 
	{
	    	
	    	AgencyDTO agencyDTO = new AgencyDTO ();
	    	List<AgencyBean> agencyList  = agencyDTO .getAll();
	    	getLog().debug("Get Agency List "+agencyList);
		return new ResponseEntity<List<AgencyBean>>(agencyList , HttpStatus.OK);
	}
	
	/*************************GET Qualification List ********************************/
	
	@ResponseBody
	@RequestMapping(value="/v1/getAllQualifications", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('AUTHORISED')")
	public ResponseEntity<List<QualificationBean>> qualification(HttpServletRequest request, HttpServletResponse response) 
	{
	    	
	    	QualificationDTO qualificationDTO = new QualificationDTO ();
	    	List<QualificationBean> qualificationList  = qualificationDTO.getAll();
	    	getLog().debug("Get Qualification List "+qualificationList);
		return new ResponseEntity<List<QualificationBean>>(qualificationList , HttpStatus.OK);
	}
	
	/*************************Add Comment ********************************/
	
	@RequestMapping(value="/v1/addComment", method = RequestMethod.POST)
    	@PreAuthorize("hasAuthority('AUTHORISED')")
	public ResponseEntity<?> addComment (@RequestBody CommentBean comment)
	{
	    CommentDTO commentDTO = new CommentDTO();
	    getLog().info("Preparing to add comment");
	    if(comment == null)
	    {
		getLog().info("User input invalid or empty request");
		return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
	    }
	    
	    commentDTO.add(comment);
	    
	    return new ResponseEntity<Long>(HttpStatus.CREATED);
	    
	}
	

}

package rest.interviewApp.dto;

import java.util.ArrayList;
import java.util.List;

import rest.interviewApp.dto.DTO;
import rest.interviewApp.pojo.AgencyBean;
import services.interviewApp.jpa.dao.QualificationDAO;
import services.interviewApp.jpa.entities.Agency;
import services.interviewApp.jpa.exception.NotFoundException;

public class AgencyDTO implements DTO
{
    private QualificationDAO qualificationDAO = null;
    
    @Override
    public List<AgencyBean> getAll()
    {
	qualificationDAO = new QualificationDAO();
	List<Agency> agencyList = qualificationDAO.getAgencyList(); 
	List<AgencyBean> listOfAgency = null;
	if(!agencyList.isEmpty())
	{
	    AgencyBean agency = null;
	    listOfAgency = new ArrayList(); 
	    for(Agency a : agencyList)
	    {
		agency = new AgencyBean();
		
		agency.setAgencyId(a.getAgencyId());
		agency.setAgencyName(a.getAgencyName());
		listOfAgency.add(agency);
	    }
	}
	
	return listOfAgency;
    }
    @Override
    public Object findById(int id) throws NotFoundException 
    {
	// TODO Auto-generated method stub
	return null;
    }
    @Override
    public void update(Object obj) {
	// TODO Auto-generated method stub
	
    }
 
    @Override
    public void add(Object obj) 
    {
	// TODO Auto-generated method stub
	
    }
    @Override
    public void delete(int obj) throws NotFoundException 
    {
	// TODO Auto-generated method stub
	
    }
    @Override
    public List<?> search(Object obj) {
	// TODO Auto-generated method stub
	return null;
    }
}

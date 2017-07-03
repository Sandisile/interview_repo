package rest.interviewApp.dto;

import java.util.ArrayList;
import java.util.List;

import rest.interviewApp.dto.DTO;
import rest.interviewApp.pojo.QualificationBean;
import services.interviewApp.jpa.dao.QualificationDAO;
import services.interviewApp.jpa.entities.Qualification;
import services.interviewApp.jpa.exception.NotFoundException;

public class QualificationDTO implements DTO
{
    private QualificationDAO qualificationDAO = null;
    
    @Override
    public List<QualificationBean> getAll()
    {
	qualificationDAO = new QualificationDAO();
	List<Qualification> listOfQualification = qualificationDAO.getQualificationList();
	List<QualificationBean> qualificationList = null;
	QualificationBean qualification = null;
	
	if(!listOfQualification.isEmpty())
	{
	    qualificationList = new ArrayList();
	    for(Qualification q : listOfQualification)
	    {
		qualification = new QualificationBean();
		
		qualification.setQualificationId(q.getQualificationId());
		qualification.setQualificationName(q.getQualificationName());
		qualification.setQualificationType(q.getQualificationType());
		
		qualificationList.add(qualification);
	    }
	    
	}
	
	return qualificationList;
    
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

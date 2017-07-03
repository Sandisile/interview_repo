package web.interviewApp.test;


import libraries.testUtils.base.TestConfig;
import libraries.testUtils.base.TestNGUnitTestBase;
import libraries.testUtils.base.UnitTestEnvironmentEnum;
import services.interviewApp.jpa.entities.Qualification;
import services.interviewApp.jpa.entities.Candidate;

import java.util.List;

import org.testng.annotations.Test;

@TestConfig(cannedEnvironment = UnitTestEnvironmentEnum.DEVELOPMENT_WAS)
public class InterviewAppTest 
{/*
    @Test(enabled = true)
    public void testJPAConnector()
    {
	DAO dao = new DAO();
	List<Candidate> c = dao.getCandidates();
	
	
	for(Candidate candidate: c)
	{
	    System.out.println("Candidate :"+candidate.getFirstname());
	    /*
	    if(!candidate.getQualificationList().isEmpty())
	    {
		List<Qualification> q = candidate.getQualificationList();
		for(Qualification qual: q)
		{
		    System.out.println("Qual "+qual.getQualificationName());
		}
	    }
	    
	    
	}
    }*/
}

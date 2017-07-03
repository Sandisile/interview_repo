package test21;

import java.util.ArrayList;
import java.util.List;

import services.interviewApp.jpa.dao.QualificationDAO;
import services.interviewApp.jpa.entities.Candidate;



public class Test 
{
   public static void main(String[] args)
   {
       List<String> s = new ArrayList();
       s.add("say");
       s.add("said");
       
       String s1 ="sa";
       
       for(String a : s)
       {
	   if(s1.contains(a))
	   {
	       System.out.println("Yes");
	   }
       }
   }
}	

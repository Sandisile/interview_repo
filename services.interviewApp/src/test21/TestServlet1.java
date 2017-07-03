package test21;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.interviewApp.jpa.dao.QualificationDAO;
import services.interviewApp.jpa.entities.Candidate;
import services.interviewApp.jpa.entities.Qualification;

/**
 * Servlet implementation class TestServlet
 */
//@WebServlet(name = "TestServlet", urlPatterns = {"/TestServlet"})
public class TestServlet1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     *
    public TestServlet1() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
	    CandidateDAO dao = new CandidateDAO();
	       List<Candidate> c = dao.getCandidates();
	       
	       response.setContentType("text/html;charset=UTF-8");
	        PrintWriter out = response.getWriter();
	        try {
	            /* TODO output your page here. You may use following sample code. *
	            out.println("<!DOCTYPE html>");
	            out.println("<html>");
	            out.println("<head>");
	            out.println("<title>Servlet NewServlet</title>");            
	            out.println("</head>");
	            out.println("<body>");
	            out.println("<h1>Servlet NewServlet at " + request.getContextPath() + "</h1>");
	            out.println("<ul>");
	            for(Candidate candidate: c)
		    {
	        	out.println("<li>Candidate :"+candidate.getFirstname()+": Surname :"+candidate.getSurname()+"</li>");
	        	if(!candidate.getQualificationList().isEmpty())
	     	    	{
	     			List<Qualification> q = candidate.getQualificationList();
	     			out.println("<ul>");
	     			for(Qualification qual: q)
	     			{
	     			out.println("<li>Candidate :"+qual.getQualificationName()+"</li>");
	     			}
	     			out.println("</ul>");
	     	    	}
		    }
	            out.println("</ul>");
	            out.println("</body>");
	            out.println("</html>");
	        } finally {
	            out.close();
	        }
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{

	       //processRequest(request, response);
	      ///*
	      // request.setAttribute("CandidatesList", c);
	      // RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
	      // rd.forward(request, response);
	      // */
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//processRequest(request, response);
	}

}

package rest.interviewApp.pojo;

import java.io.Serializable;

public class QualificationBean implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private int qualificationId;
    private String qualificationName;
    private String qualificationType;
    
    public int getQualificationId()
    {
        return qualificationId;
    }
    public void setQualificationId(int qualificationId)
    {
        this.qualificationId = qualificationId;
    }
    public String getQualificationName()
    {
        return qualificationName;
    }
    public void setQualificationName(String qualificationName) 
    {
        this.qualificationName = qualificationName;
    }
    public String getQualificationType()
    {
        return qualificationType;
    }
    public void setQualificationType(String qualificationType)
    {
        this.qualificationType = qualificationType;
    }
    
    
    
}

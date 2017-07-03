package eafUtil.jpa.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import libraries.logging.fact.LogFactory;

import java.io.File;

public class PropertiesConfig 
{
        private static InputStream in = null;
        private static Properties properties = null;
        private static URL url = null;
        private static File file = null;
        public PropertiesConfig()
        {
    	
    	
        }
        
       static
       {
          try 
          {
        	   url = PropertiesConfig.class.getClassLoader().getResource("files/config.properties");
        	   file = new File(url.getFile());
        	   in = new FileInputStream(file);
        	    
        	   if(in != null)
        	   {
                	   setProperties(Properties.class.newInstance());
                	   properties.load(in);
                	   LogFactory.getInstance().debug("Properties File Loaded");
                	   in.close();
        	   }
        	   else
        	   {
        	       LogFactory.getInstance().debug("Properties File Not Loaded ");
        	   } 	    
          }catch (IOException e) 
          {
              LogFactory.getInstance().error("Exception occurs :"+e.getMessage());;
          }catch (IllegalAccessException e ) 
          {
              LogFactory.getInstance().error("Exception occurs :"+e.getMessage());;
          }catch (InstantiationException e) 
          {
              LogFactory.getInstance().error("Exception occurs :"+e.getMessage());;
    	  }
    }
        
    
    public static Properties getProperties() 
    {
        return properties;
    }


    public static void setProperties(Properties properties)
    {
        PropertiesConfig.properties = properties;
    }
    

}

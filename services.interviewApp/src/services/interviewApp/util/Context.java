package services.interviewApp.util;

import java.util.Properties;

import javax.persistence.EntityManager;
import eafUtil.jpa.configuration.PropertiesConfig;

import eafUtil.jpa.connector.JPAConnector;
import libraries.ejbUtils.ejb.EafContext;
import libraries.logging.fact.LogFactory;

public class Context extends EafContext
{

	private static String MODULE_NAME = "services.interviewApp";
	private Properties properties;
	private JPAConnector connector;
	private static Context context = null;
	
	public Context()
	{
	    properties = PropertiesConfig.getProperties();
	    LogFactory.getInstance().debug("Get DB Properties ");
	    connector = new JPAConnector("interviewDB", properties);
	    LogFactory.getInstance().debug("JPAConnector instance Created ");   
	}
	
	public static synchronized Context getContextInstance()
	{
	    if(context == null)
	    {
		synchronized (Context.class)
		{
		    if(context == null)
		    {
			context = new Context();
			LogFactory.getInstance().debug("Context instance Created ");
		    }
		}
	    }
	    return context;
	}
	
	public String getInitProp(String key) throws Exception
	{
	    String propValue = properties.getProperty(key);
	    if(propValue == null)
	    {
		LogFactory.getInstance().error("Unable to find initialization property '"+ key +"'!");
		throw new Exception("Unable to find initialization property '"+ key +"'!");
	    }
	    return propValue;
	}
	
	public String getInitProp(String key, String defaultValue)
	{
	    String propValue = properties.getProperty(key,defaultValue);
	    LogFactory.getInstance().debug("Property (key & value ) successfully assigned to propVale ");
	    return propValue;
	}
	
	public synchronized EntityManager getEntityManager()
	{
		return connector.getEntityManager();
	}
	
	public void close()
	{
	    connector.close();
	}

	@Override
	public String getModuleName() {
	    // TODO Auto-generated method stub
	    return null;
	}


}

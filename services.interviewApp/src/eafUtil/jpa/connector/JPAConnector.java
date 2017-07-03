package eafUtil.jpa.connector;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;
public class JPAConnector 
{
  
  private static final int CONN_POOL_INITIAL_SIZE = 0;
  private static final int CONN_POOL_MAX_ACTIVE = -1; //no limit
  private static final int CONN_POOL_MAX_IDLE = 5;
  private static final int CONN_POOL_MIN_IDLE = 3;
  private static final String CONN_POOL_VALIDATION_QUERY = "select 1 from dual";
  private static final int CONN_POOL_NUM_TESTS_PER_EVICTION_RUN = 3;
  private static final int CONN_POOL_MIN_EVICTABLE_IDLE_TIME_MILLIS = 3 * 1000 * 60;
  private static final int CONN_POOL_TIME_BETWEEN_EVICTION_RUNS_MILLIS = 5 * 1000 * 60;
  private static final boolean CONN_POOL_ACCESS_UNDERLYING_CONNECTION = true;
  private String               connectorName;
  private Properties           connectorProperties;
  private EntityManager        entityManager;
  private static Map<String, EntityManagerFactory> entityManagerFactoryMap;
  private static Map<String, DataSource> datasourceMap;
  
  public JPAConnector(String connectorName, Properties connectorProperties) {
    this.connectorName       = connectorName;
    this.connectorProperties = connectorProperties;
  }
  
  protected synchronized static Map<String, DataSource> getDatasourceMap() {
    if (datasourceMap == null) {
      datasourceMap = new HashMap<String, DataSource>();
    }
    return datasourceMap;
  }
  
  protected synchronized static DataSource getDatasource(String connectorName) 
  {
    DataSource ds = datasourceMap.get(connectorName);
    if (ds == null) {
      throw new RuntimeException("Failed to retrieve datasource information for connector '" + connectorName + "'");
    }
    return ds;
  }
  
  protected synchronized static Map<String, EntityManagerFactory> getFactoryMap() 
  {
    if (entityManagerFactoryMap == null) {
      entityManagerFactoryMap = new HashMap<String, EntityManagerFactory>();
    }
    return entityManagerFactoryMap;
  }
  
  protected synchronized EntityManagerFactory getFactory() 
  {
    EntityManagerFactory entityManagerFactory = getFactoryMap().get(connectorName);
    if (entityManagerFactory == null) {
      DataSource ds = setupDataSource();        
      getDatasourceMap().put(connectorName, ds);
      //setup EnityManager Factory////////////////////////////////////////////////////////////////////////////////////////////////////
      Map<String,Object> propertyMap = new HashMap<String,Object>();
      //addToplinkProperties(propertyMap);
      addOpenJPAProperties(propertyMap);
      
      for (Object k : connectorProperties.keySet()) {
        String key = (String)k;
        if (key.startsWith(connectorName + ".other.")) {
          propertyMap.put(key.substring((connectorName + ".other.").length()),connectorProperties.getProperty(key));
        }
      }
      entityManagerFactory = Persistence.createEntityManagerFactory(connectorProperties.getProperty(connectorName + ".jpa.persistenceunit"),propertyMap);
      getFactoryMap().put(connectorName, entityManagerFactory);
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }
    return entityManagerFactory;
  }
 /**
  * use the openjpa.RuntimeUnenhancedClasses property to support unenhanced classes. Unenhanced classes degrades performance a bit. 
  * For ibm's openjpa, entities can be enhanced using the wsenhancer command (script -> wsenhancer.sh)
  * possible values for property : 
  * supported -> automatically create subclasses, for unenhanced entities i.e. runtime enhancement
  * unsupported -> no runtime enhancement, 
  *                        creating entity manager logs and error for unenhanced entities, 
  *                        accessing unenhanced entities throws an exception
  * warn -> no runtime enhancement, 
  *             creating entity manager logs a warning message for unenhanced entities, 
  *             accessing unenhanced entities throws an exception
  * connection pool can be set in the map passed to Persistence.createEntityManagerFactory
  */
  private void addOpenJPAProperties(Map<String, Object> propertyMap) 
  {
        propertyMap.put("openjpa.RuntimeUnenhancedClasses", 
                        connectorProperties.getProperty(connectorName + ".openjpa.runtimeUnenhancedClasses", "supported"));
        //possible values for Default Level are TRACE, DEBUG, INFO, WARN, and ERROR. To view sql add SQL=TRACE 
        propertyMap.put("openjpa.Log", connectorProperties.getProperty(connectorName + ".openjpa.Log", "DefaultLevel=INFO, Runtime=INFO, Tool=INFO, SQL=TRACE, SQLDiag=TRACE"));
       // propertyMap.put("openjpa.ConnectionFactoryProperties", "PrintParameters=True");
        //setting the third party datasource. This is to use connection pooling
        propertyMap.put("openjpa.ConnectionFactory", getDatasourceMap().get(connectorName));
       // propertyMap.put("openjpa.DataCache", " value=true");
  }
  /**
   * see EafSessionCustomizer to see where connectionpool gets linked to EntityManagerFactory
   *
  private void addToplinkProperties(Map<String, Object> propertyMap) {
        propertyMap.put("toplink.session-name", connectorName);
//  props.put(TopLinkProperties.LOGGING_LEVEL, connectorProperties.getProperty(connectorName + ".jpa.loglevel","WARNING")); //ALL CONFIG FINE FINER FINEST INFO OFF SEVERE WARNING
    propertyMap.put("toplink.logging.level", connectorProperties.getProperty(connectorName + ".jpa.loglevel","WARNING")); //ALL CONFIG FINE FINER FINEST INFO OFF SEVERE WARNING
      
    //we have to set this to enable external connection pooling
//  props.put(TopLinkProperties.SESSION_CUSTOMIZER, "EAFutil.EafSessionCustomizer");
    propertyMap.put("toplink.session.customizer", "EAFutil.EafSessionCustomizer");
      
    //shared cache is not a good thing - changes to database are not reflected if objects are cached, turn if off by default
//  props.put(TopLinkProperties.CACHE_SHARED_DEFAULT,connectorProperties.getProperty(connectorName + ".other." + TopLinkProperties.CACHE_SHARED_DEFAULT,"false"));
    propertyMap.put("toplink.cache.shared.default", connectorProperties.getProperty(connectorName + ".other." + "toplink.cache.shared.default","false"));
  }
  /**
   * connector.jpa.loglevel
   * connector.jpa.persistenceunit
   * connector.jpa.other.sharedcache
   * connector.jdbc.driver
   * connector.jdbc.url
   * connector.jdbc.username
   * connector.jdbc.password
   * connector.connectionpool.maxActive
   */
        private DataSource setupDataSource() 
        {
          //setup the connection pool for this connector/persistence unit ///////////////////////////////////////////////////////////////////////////
      BasicDataSource ds = new BasicDataSource();
      ds.setValidationQuery(connectorProperties.getProperty(connectorName + ".connectionpool.validationQuery",CONN_POOL_VALIDATION_QUERY));
      ds.setDriverClassName(connectorProperties.getProperty(connectorName + ".jdbc.driver"));
      ds.setUsername       (connectorProperties.getProperty(connectorName + ".jdbc.user"));
      ds.setPassword       (connectorProperties.getProperty(connectorName + ".jdbc.password"));
      
      //jpa won't work if the cocoprop is set in the connection
      String url = connectorProperties.getProperty(connectorName + ".jdbc.url");
      url        = url.replaceAll(";cocoprop=dynamic.querying=true", "");
      url        = url.replaceAll(";cocoprop=use.resultscale=false", "");
      ds.setUrl(url);
      
      String initSizeProp = connectorName + ".connectionpool.initialSize";
      ds.setInitialSize(getProperty(initSizeProp, CONN_POOL_INITIAL_SIZE));
      String maxActiveProp = connectorName + ".connectionpool.maxActive";
      ds.setMaxActive(getProperty(maxActiveProp, CONN_POOL_MAX_ACTIVE));
      
      String maxIdleProp = connectorName + ".connectionpool.maxIdle";
      ds.setMaxIdle(getProperty(maxIdleProp, CONN_POOL_MAX_IDLE));
      
      String minIdleProp = connectorName + ".connectionpool.minIdle";
      ds.setMinIdle(getProperty(minIdleProp, CONN_POOL_MIN_IDLE));
      
      String testEvictProp = connectorName + ".connectionpool.numTestsPerEvictionRun";
      ds.setNumTestsPerEvictionRun(getProperty(testEvictProp, CONN_POOL_NUM_TESTS_PER_EVICTION_RUN));
      
      String evictMinProp = connectorName + ".connectionpool.minEvictableIdleTimeMillis";
      ds.setMinEvictableIdleTimeMillis(getProperty(evictMinProp, CONN_POOL_MIN_EVICTABLE_IDLE_TIME_MILLIS));
      
      String evictRunsProp = connectorName + ".connectionpool.timeBetweenEvictionRunsMillis";
      ds.setTimeBetweenEvictionRunsMillis(getProperty(evictRunsProp, CONN_POOL_TIME_BETWEEN_EVICTION_RUNS_MILLIS));
      
      String allowAccessProp = connectorName + "connectionPool.accessToUnderlyingConnectionAllowed";
      ds.setAccessToUnderlyingConnectionAllowed(getProperty(allowAccessProp, CONN_POOL_ACCESS_UNDERLYING_CONNECTION));
      
      return ds;
        }
        
        private int getProperty(String propertyName, int defaultValue) {
                return Integer.parseInt(connectorProperties.getProperty(propertyName, "" + defaultValue));              
        }
        private boolean getProperty(String propertyName, boolean defaultValue) {
                return new Boolean(connectorProperties.getProperty(propertyName, new Boolean(defaultValue).toString()));                
        }
        
  public synchronized EntityManager getEntityManager() {
    if (entityManager == null) {
      entityManager = getFactory().createEntityManager();
    }
    return entityManager;
  }
  public synchronized void close() {
    if (entityManager != null) {
      entityManager.close();
      entityManager = null;
    }
  }
}
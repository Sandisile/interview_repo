package services.interviewApp.util.authentication;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import security.UnknownEntityException;
import security.admin.account.AccountExtSysPropUpdate;
import security.admin.account.AccountExtSysPropsUpdate;
import security.admin.account.InvalidPropertyUpdateException;
import security.admin.account.SecurityAccountAdmin;
import security.admin.extsys.ExtSysView;
import security.admin.extsys.SecurityExtSysAdmin;
import security.users.LinkUndefinedException;
import security.users.SecurityUser;
import libraries.analytics.AnalyticsRecord;
import libraries.configuration.ConfigurationFactory;
import libraries.configuration.ConfigurationServiceFactory.EnvironmentProps;
import libraries.eaf.serviceLookup.ResolverFactory;
import libraries.logging.Logger;
import libraries.security.UserNameUtil;
import libraries.security.UserNameUtil.DomainUser;
import libraries.security.exception.LDAPError;
import libraries.security.ldap.LDAPAuthentication;
import webFramework.WFActiveUserObject;
import webFramework.WFAuthenticationException;
import webFramework.WFPageConstructException;
import webFramework.WFRequestContext;
import webFramework.WFRoleCreateException;
import webFramework.WFServicePack;
import webFramework.WFSessionStartException;
import webFramework.WFUserRole;
//import webFramework.WFUtils;
import webFramework.exceptions.ValidatorException;
import webFramework.utils.Validator;

/** Handles the authentication for the login of the user */
public class ADAuthenticationHandler implements Serializable {

	private static final long serialVersionUID = 1L;
	private String adAuthenticateStatus;
	private static final String USER_MESSAGE = "user.message";
	
	public final static String AD_ERROR = "error";
	public final static String AD_LOGIN = "Valid login";
	public final static String AD_INVALID = "invalid";
	
	/** Constructor */
	public ADAuthenticationHandler(){
		adAuthenticateStatus = AD_ERROR;	
	}
	
	//--------------------------------------------------------------------------
	// Private Methods
    //--------------------------------------------------------------------------
	
    private LDAPAuthentication authenticateADUser(String username, String password, WFServicePack sp) {
        String ldapURL = ConfigurationFactory.getConfiguration().getProperty("ad.ldap.url", "ldap://MMIMETMOMDC1.metmom.mmih.biz/DC=metmom,DC=mmih,DC=biz");
        LDAPAuthentication auth = new LDAPAuthentication(ldapURL, username, password, sp.getLog());
        return auth;
    }
    
    //--------------------------------------------------------------------------
    // Public Methods
	//--------------------------------------------------------------------------
    
    /** Returns the status of the authentication for the user - login means that 
     * user is able to login 
     */
	public String getAdAuthenticateStatus() {
		return adAuthenticateStatus;
	}
	
	//--------------------------------------------------------------------------

	/** 
	 * Does AD Authentication for the username and password parsed in. If the username
	 * and password is authenticated then the status of the adAuthenticate will be 
	 * "login" 
	 */
	public void doADAuthenticate(String username, String password) {		
	
		WFServicePack myServicePack = WFRequestContext.getServicePack();
		Logger log = myServicePack.getLog();
        
        try {
			username = Validator.validateADUser(username, password, myServicePack);

			LDAPAuthentication adUser = authenticateADUser(username, password, myServicePack);
			if (!adUser.isAuthenticated()) {
				LDAPError ldapError = adUser.getLdapError();				
				try{
					Validator.handleLDAPException(ldapError);					
				}
				catch(NullPointerException e){
					e.printStackTrace();
					adAuthenticateStatus = AD_ERROR;
				}																				
            }
			else
			{
				adAuthenticateStatus = AD_LOGIN;
			}
            
        } catch (WFAuthenticationException e) {
            //myServicePack.logError("WFAuthenticationException", e);
        	log.error("WFAuthenticationException", e);           
        } catch (ValidatorException ve) {
        	//FacesContext facesContext = FacesContext.getCurrentInstance();
        	if(ve.isIncorrectCredentials()){     
        		adAuthenticateStatus = AD_INVALID;
            	//facesContext.getExternalContext().getRequestMap().put("info.message", " Please Note: your password is your current windows password, as of today.");            	            	
        	}
        	//facesContext.getExternalContext().getRequestMap().put("user.message", ve.getMessage());
		}		
	}	
	
    //--------------------------------------------------------------------------
	// Protected Methods
    //--------------------------------------------------------------------------
	            
    /**
     * After authenticate user <a href="doc-files/controller_events.html">'event
     * hook'</a> method.
     * 
     * Implement this method if you want to be able to cancel the authentication
     * process, or if you want to do some additional processing at this point in
     * the login process. Throw a new WFAuthenticationException and pass the
     * string description of your cancelation to its constructor.
     * 
     * The base class does nothing in this method - it will be called in the
     * correct place.
     * 
     * @param myServicePack
     *            The currently active service pack.
     * @param newUser
     *            The WFActiveUserObject just created for the new session.
     * @throws WFAuthenticationException
     *             when the process needs to be stopped.
     */
    protected void afterAuthenticateUser(WFServicePack myServicePack, WFActiveUserObject newUser) throws WFAuthenticationException {
        
        if (myServicePack.getSession() != null) {
            AnalyticsRecord.addField(myServicePack.getSession(), "userId", newUser.getCrmId());
            String externalDomain = (String) myServicePack.getSession().getAttribute("auth-external-user");
            if (externalDomain != null) {
                if (newUser == null) {
                    throw new WFAuthenticationException("Cannot link user if no active user");
                }
                DomainUser user = UserNameUtil.splitUser(externalDomain);
                if (user.getDomainName() == null) {
                    user.setDomainName("HO");
                }
                final String extSysId = user.getDomainName() + "_ID";
                final String extPropId = user.getDomainName() + "_PROP_ID";
                try {
                    String name = SecurityExtSysAdmin.NS_NAME;
                    SecurityExtSysAdmin sea = ResolverFactory.getInstance().lookup(name, SecurityExtSysAdmin.class);
                    ExtSysView extSysView = sea.getExtSysViewData(extSysId);
                    if (extSysView == null) {
                        throw new WFAuthenticationException("Cannot link orbit users to domain:" + user.getDomainName());
                    }
                } catch (UnknownEntityException x) {
                    throw new WFAuthenticationException("Cannot link orbit users to domain:" + user.getDomainName());
                }
                myServicePack.getSession().removeAttribute("auth-external-user");
                try {
                    String name = SecurityUser.NS_NAME;
                    SecurityUser su = ResolverFactory.getInstance().lookup(name, SecurityUser.class);
                    String propValue = su.getExternalSystemProperty(newUser.getUserId(), extSysId, extPropId);
                    if(propValue != null && user.getUserId().equals(propValue)) {
                        // same user already linked
                        return;
                    } else if (propValue != null) {
                        throw new WFAuthenticationException("External user " + user.getDomainName() + "\\" + propValue + " already linked to:" + newUser.getUserId());
                    }
                    // no user linked proceed to link the user
                } catch (UnknownEntityException e) {
                    // Do nothing we expected this
                } catch (LinkUndefinedException e) {
                    // Do nothing we expected this
                }
                try {
                    String name = SecurityAccountAdmin.NS_NAME;
                    SecurityAccountAdmin sa = ResolverFactory.getInstance().lookup(name, SecurityAccountAdmin.class);
                    AccountExtSysPropUpdate prop = new AccountExtSysPropUpdate(extSysId, extPropId, user.getUserId());
                    AccountExtSysPropsUpdate extSys = new AccountExtSysPropsUpdate(newUser.getUserId(), new AccountExtSysPropUpdate[] { prop });
                    sa.createAccountExtSysAndProps(extSys, newUser.getUserId(), newUser.getUserIdNumber(), newUser.getCrmIdNumber());
                } catch (UnknownEntityException x) {
                    //myServicePack.logError("Exception linking external domain user to orbit user:" + x, x);
                    throw new WFAuthenticationException("Exception linking external domain user to orbit user:" + x);
                } catch (InvalidPropertyUpdateException x) {
                    //myServicePack.logError("Exception linking external domain user to orbit user:" + x, x);
                    throw new WFAuthenticationException("Exception linking external domain user to orbit user:" + x);
                }
            }
        }
    } // afterAuthenticate
    
    //--------------------------------------------------------------------------
    
    /**
     * Start a new session.
     * 
     * Any existing session will be invalidated before a new session is started.
     * The makeNewRoleObject method will be called for all roles played by this
     * user.
     * 
     * @param myServicePack
     *            The currently active service pack.
     * @param newUser
     *            The WFActiveUserObject just authenticated.
     * @throws WFSessionStartException
     *             when the session could not start.
     * @see WFUserRole
     */
    protected void startNewUserSession(WFServicePack myServicePack, WFActiveUserObject newUser) throws WFSessionStartException {
        Logger log = myServicePack.getLog();
        try {
            HttpSession theSession = myServicePack.getSession();
            Map<String, Object> preserved = new HashMap<String, Object>();            
            if (theSession != null) {
                String authExternalUser = (String) theSession.getAttribute("auth-external-user");
                if(authExternalUser != null) {
                    preserved.put("auth-external-user", authExternalUser);
                }
                Enumeration<String> names = theSession.getAttributeNames();
                while(names.hasMoreElements()) { 
                    String name = names.nextElement();
                    if(name.startsWith("web.functions.analytics")) {
                        preserved.put(name, theSession.getAttribute(name));
                    }
                }
                theSession.invalidate();
                theSession = null;
            }

            if (theSession == null) {
                theSession = myServicePack.createNewSession();
            }
            if (theSession != null) {
                for(Map.Entry<String, Object> entry : preserved.entrySet()) {
                    theSession.setAttribute(entry.getKey(), entry.getValue());                    
                }
                String moduleName = ""; //getServletContext().getInitParameter("moduleName");
                if(moduleName != null) {
                    AnalyticsRecord.addField(theSession, "moduleName", moduleName);
                }
                EnvironmentProps envProps = libraries.configuration.ConfigurationServiceFactory.getEnvironmentProps(moduleName);
                AnalyticsRecord.addField(theSession, "environmentName", envProps.getEnvironmentName());
                // before we add to the session, we have to see what we must do
                // with the
                // active roles
                String allowManyActive = myServicePack.getGlobalParam("application.roles.active.allowMany", "true");
                newUser.setAllowMultipleActiveRoles(allowManyActive.equalsIgnoreCase("true"));
                newUser.initialize();
                myServicePack.setActiveUser(newUser);
                //refreshRoleObjects(myServicePack, newUser);
            }// if theSession not null
        }// try
        catch (WFRoleCreateException rce) {        	
            //myServicePack.logError("Exception in startNewUserSession:" + rce.getMessage(), rce);
            throw new WFSessionStartException("Could not start session. Role creation exception:" + rce.getMessage());
        }// catch
    }
    
    //--------------------------------------------------------------------------
    
    /**
     * Default method called when no active user could be found during
     * controller functions.
     * 
     * The default implementation will try to redirect to the login page.
     * 
     * @param myServicePack
     *            The currently active service pack.
     * 
     */
    protected void doNoActiveUserError(WFServicePack myServicePack, String userName) {
        try {
            if(userName != null) {
                myServicePack.getSession().setAttribute(USER_MESSAGE, "User [" + userName + "] not linked. Please provide Orbit credentials");
            }
            myServicePack.goToLoginPage();
        } catch (WFPageConstructException pce) {
            //myServicePack.logError("Could not go to login page.", pce);            
        }
    }
    
    //--------------------------------------------------------------------------
}

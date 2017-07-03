package services.interviewApp.util;

import libraries.ejbUtils.ejb.EafContext;
import libraries.ejbUtils.ejb.EafContextFactory;
import services.interviewApp.util.Context;


public class ContextFactory extends EafContextFactory {

	public ContextFactory() {
	}

	@Override
	public EafContext createContext() {
		return new Context();
	}

}

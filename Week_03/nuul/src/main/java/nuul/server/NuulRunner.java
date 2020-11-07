package nuul.server;

import nuul.FilterProcessor;
import nuul.exception.NuulException;

public class NuulRunner {

	
    public void init() {}

    public void postRoute() throws NuulException {
        FilterProcessor.getInstance().postRoute();
    }

    public void route() throws NuulException {
        FilterProcessor.getInstance().route();
    }

    public void preRoute() throws NuulException {
        FilterProcessor.getInstance().preRoute();
    }

    public void error() {
        FilterProcessor.getInstance().error();
    }
	
}

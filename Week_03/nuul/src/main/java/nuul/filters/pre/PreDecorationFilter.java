package nuul.filters.pre;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.handler.codec.http.HttpRequest;
import nuul.context.RequestContext;
import nuul.enums.FilterType;
import nuul.exception.NuulException;
import nuul.filters.NuulFilter;
import nuul.filters.NuulProperties;
import nuul.filters.NuulProperties.NuulRoute;

public class PreDecorationFilter extends NuulFilter {

	private static Logger logger = LoggerFactory.getLogger(PreDecorationFilter.class);
	private NuulProperties nuulProperties;
	
	public PreDecorationFilter(NuulProperties nuulProperties) {
		this.nuulProperties = nuulProperties;
	}
	
	@Override
	public FilterType filterType() {
		return FilterType.PRE;
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	protected Object run() throws NuulException {
		HttpRequest req = RequestContext.getCurrentContext().getRequest();
		try {
			RequestContext.getCurrentContext().setRouteHost(getRouteHost(req));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new NuulException(e, 500, e.getMessage());
		}
		return null;
	}
	
	private URL getRouteHost(HttpRequest req) throws MalformedURLException {
		Collection<NuulRoute> nuulRoutes = nuulProperties.getRoutes().values();
		String originalUrl = req.uri();
		for (NuulRoute zuulRoute : nuulRoutes) {
			String path = zuulRoute.getPath();
			if (originalUrl.startsWith(path)) {
				String urlStr = zuulRoute.getUrl();
				if (originalUrl.length() > path.length()) {
					urlStr += originalUrl.substring(path.length());
				}
				return new URL(urlStr);
			}
		}
		return null;
	}
}

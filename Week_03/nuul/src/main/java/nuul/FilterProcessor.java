package nuul;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nuul.enums.ExecutionStatus;
import nuul.enums.FilterType;
import nuul.exception.NuulException;
import nuul.filters.NuulFilter;
import nuul.server.HttpInboundHandler;

public class FilterProcessor {

	private static final Logger logger = LoggerFactory.getLogger(HttpInboundHandler.class);

	private static final FilterProcessor INSTANCE = new FilterProcessor();

	public static FilterProcessor getInstance() {
		return INSTANCE;
	}

	public void postRoute() throws NuulException {
		try {
			runFilters(FilterType.POST);
		} catch (NuulException e) {
			throw e;
		} catch (Throwable e) {
			throw new NuulException(e, 500, "UNCAUGHT_EXCEPTION_IN_POST_FILTER_" + e.getClass().getName());
		}
	}

	public void error() {
		try {
			runFilters(FilterType.ERROR);
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void route() throws NuulException {
		try {
			runFilters(FilterType.ROUTE);
		} catch (NuulException e) {
			throw e;
		} catch (Throwable e) {
			throw new NuulException(e, 500, "UNCAUGHT_EXCEPTION_IN_ROUTE_FILTER_" + e.getClass().getName());
		}
	}

	public void preRoute() throws NuulException {
		try {
			runFilters(FilterType.PRE);
		} catch (NuulException e) {
			throw e;
		} catch (Throwable e) {
			throw new NuulException(e, 500, "UNCAUGHT_EXCEPTION_IN_PRE_FILTER_" + e.getClass().getName());
		}
	}

	public Object runFilters(FilterType type) throws Throwable {
		boolean bResult = false;
		List<NuulFilter> list = FilterLoader.getInstance().getFiltersByType(type);
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				NuulFilter zuulFilter = list.get(i);
				Object result = processZuulFilter(zuulFilter);
				if (result != null && result instanceof Boolean) {
					bResult |= ((Boolean) result);
				}
			}
		}
		return bResult;
	}

	public Object processZuulFilter(NuulFilter filter) throws NuulException {
		String filterName = "";
		try {
			long ltime = System.currentTimeMillis();
			filterName = filter.getClass().getSimpleName();
			Object o = null;
			Throwable t = null;
			NuulFilterResult result = filter.runFilter();
			ExecutionStatus s = result.getStatus();
			if (logger.isDebugEnabled())
				logger.debug("nuul.filter-execTime[" + filterName + " ]: " + (System.currentTimeMillis() - ltime));
			switch (s) {
			case FAILED:
				t = result.getException();
				break;
			case SUCCESS:
				o = result.getResult();
				break;
			default:
				break;
			}
			if (t != null)
				throw t;
			return o;

		} catch (Throwable e) {
			if (e instanceof NuulException) {
				throw (NuulException) e;
			} else {
				NuulException ex = new NuulException(e, "Filter threw Exception", 500,
						filter.filterType() + ":" + filterName);
				throw ex;
			}
		}
	}

}

package nuul.filters;

import nuul.NuulFilterResult;
import nuul.enums.ExecutionStatus;
import nuul.enums.FilterType;
import nuul.exception.NuulException;

public abstract class NuulFilter implements Comparable<NuulFilter> {

	public abstract FilterType filterType();

	public abstract int filterOrder();

	public abstract boolean shouldFilter();

	protected abstract Object run() throws NuulException;

	
	public NuulFilterResult runFilter() {
		NuulFilterResult zr = new NuulFilterResult();
        if (!isFilterDisabled()) {
            if (shouldFilter()) {
                try {
                    Object res = run();
                    zr = new NuulFilterResult(res, ExecutionStatus.SUCCESS);
                } catch (Throwable e) {
                    zr = new NuulFilterResult(ExecutionStatus.FAILED);
                    zr.setException(e);
                } 
            } else {
                zr = new NuulFilterResult(ExecutionStatus.SKIPPED);
            }
        }
        return zr;
    }
	

    /**
     * If true, the filter has been disabled by archaius and will not be run
     *
     * @return
     */
    public boolean isFilterDisabled() {
    	return false;
    }

	
	@Override
	public int compareTo(NuulFilter filter) {
		return Integer.compare(this.filterOrder(), filter.filterOrder());
	}

}

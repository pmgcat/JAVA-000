package nuul;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import nuul.enums.FilterType;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import nuul.filters.NuulFilter;
import nuul.filters.NuulProperties;
import nuul.filters.post.SendResponseFilter;
import nuul.filters.pre.PreDecorationFilter;
import nuul.filters.route.SimpleHostRoutingFilter;

public class FilterLoader {

//	private static final Logger LOG = LoggerFactory.getLogger(FilterLoader.class);

	private final static FilterLoader INSTANCE = new FilterLoader();

	private final ConcurrentHashMap<FilterType, List<NuulFilter>> hashFiltersByType = new ConcurrentHashMap<>();
	private final FilterRegistry filterRegistry = FilterRegistry.instance();

	public FilterLoader() {
		load();
	}
	
	public static FilterLoader getInstance() {
		return INSTANCE;
	}
	
	
    public List<NuulFilter> getFiltersByType(FilterType filterType) {

        List<NuulFilter> list = hashFiltersByType.get(filterType);
        if (list != null) return list;

        list = new ArrayList<>();

        Collection<NuulFilter> filters = filterRegistry.getAllFilters();
        for (Iterator<NuulFilter> iterator = filters.iterator(); iterator.hasNext(); ) {
        	NuulFilter filter = iterator.next();
            if (filter.filterType() == filterType) {
                list.add(filter);
            }
        }
        Collections.sort(list); // sort by priority

        hashFiltersByType.putIfAbsent(filterType, list);
        return list;
    }
    //TODO Just Test
    protected void load() {
    	NuulProperties nuulProperties = new NuulProperties();
    	Map<String, NuulProperties.NuulRoute> map = new HashMap<>();
    	NuulProperties.NuulRoute route = new NuulProperties.NuulRoute();
    	route.setPath("/");
    	route.setUrl("https://www.baidu.com");
    	map.put("", route);
    	nuulProperties.setRoutes(map);
    	filterRegistry.put("1", new PreDecorationFilter(nuulProperties));
    	filterRegistry.put("2", new SimpleHostRoutingFilter());
    	filterRegistry.put("3", new SendResponseFilter());
    }
}

package nuul;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import nuul.filters.NuulFilter;

public class FilterRegistry {

	private static final FilterRegistry INSTANCE = new FilterRegistry();

	public static final FilterRegistry instance() {
		return INSTANCE;
	}

	private final ConcurrentHashMap<String, NuulFilter> filters = new ConcurrentHashMap<>();

	private FilterRegistry() {
	}

	public NuulFilter remove(String key) {
		return this.filters.remove(key);
	}

	public NuulFilter get(String key) {
		return this.filters.get(key);
	}

	public void put(String key, NuulFilter filter) {
		this.filters.putIfAbsent(key, filter);
	}

	public int size() {
		return this.filters.size();
	}

	public Collection<NuulFilter> getAllFilters() {
		return this.filters.values();
	}

}

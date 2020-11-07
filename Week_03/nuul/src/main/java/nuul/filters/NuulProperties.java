package nuul.filters;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties("nuul")
public class NuulProperties {

	private Map<String, NuulRoute> routes = new LinkedHashMap<>();
	
	public Map<String, NuulRoute> getRoutes() {
		return routes;
	}

	public void setRoutes(Map<String, NuulRoute> routes) {
		this.routes = routes;
	}

	public static class NuulRoute {
		
		private String path;

		private String url;

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}
		
		

	}
	
}

package httpdemo;


import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.ConnectionPool;
import okhttp3.CookieJar;
import okhttp3.OkHttpClient;

/**
 * 
 * @author spike
 *
 */
public class OkHttpClientFactory {

	
	private static final Logger log = LoggerFactory.getLogger(OkHttpClientFactory.class);

	private final Timer managerTimer = new Timer("DefaultOkHttpClientFactory.mnagerTimer", true);
	
	private final ClientPropertie clientPropertie;
	private AtomicReference<HttpClientInfo> httpClientInfoRef;
	
	public OkHttpClientFactory(ClientPropertie clientPropertie) {
		this.clientPropertie = clientPropertie;
		initialize();
	}
	
	private void initialize() {
		httpClientInfoRef = new AtomicReference<>(newClient());
	}
	
	public final void loadClient() {
		final HttpClientInfo oldClientInfo = httpClientInfoRef.get();
		httpClientInfoRef.set(newClient());
		if (oldClientInfo != null) {
			managerTimer.schedule(new TimerTask() {
						@Override
						public void run() {
							try {
								oldClientInfo.close();
							} catch (Throwable t) {
								log.error("error shutting down old connection manager", t);
							}
						}
					}, 30000);
		}

	}

	protected HttpClientInfo newClient() {
		ConnectionPool pool = new ConnectionPool(this.clientPropertie.getMaxTotalConnections(),
				this.clientPropertie.getTimeToLive(), this.clientPropertie.getTimeUnit());
		OkHttpClient okHttpClient = new OkHttpClient.Builder().connectionPool(pool)
				.connectTimeout(this.clientPropertie.getConnectTimeoutMillis(), TimeUnit.MILLISECONDS)
				.readTimeout(this.clientPropertie.getConnectionRequestTimeoutMillis(), TimeUnit.MILLISECONDS)
				.writeTimeout(this.clientPropertie.getSocketTimeoutMillis(), TimeUnit.MILLISECONDS)
				.retryOnConnectionFailure(false)
				.cookieJar(CookieJar.NO_COOKIES)
				.build();
		return new HttpClientInfo(okHttpClient, pool);
	}
	
	public OkHttpClient create() {
		return httpClientInfoRef.get().httpClient;
	}
	
	public void stop() {
		HttpClientInfo httpClientInfo = httpClientInfoRef.get();
		if (null != httpClientInfo) httpClientInfo.close();
		this.managerTimer.cancel();
	}
	
	protected static class HttpClientInfo {
		
		final OkHttpClient httpClient;
		final ConnectionPool pool;
		
		public HttpClientInfo(OkHttpClient httpClient, ConnectionPool pool) {
			this.httpClient = httpClient;
			this.pool = pool;
		}
		
		public void close() {
			if (null != pool) {
				log.debug("HttpClientInfo close...");
				pool.evictAll();
				httpClient.dispatcher().executorService().shutdown();
				log.debug("HttpClientInfo close over");
			}
		}
		
	} 
	

}

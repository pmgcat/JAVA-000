package httpdemo;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public  class ClientPropertie {
	/**
	 * The maximum number of total connections the proxy can hold open to backends.
	 */
	private int maxTotalConnections = 200;
	/**
	 * The maximum number of connections that can be used by a single route.
	 */
	private int maxPerRouteConnections = 20;
	/**
	 * The socket timeout in millis. Defaults to 10000.
	 */
	private int socketTimeoutMillis = 10000;
	/**
	 * The connection timeout in millis. Defaults to 2000.
	 */
	private int connectTimeoutMillis = 2000;
	/**
	 * The timeout in milliseconds used when requesting a connection
	 * from the connection manager. Defaults to -1, undefined use the system default.
	 */
	private int connectionRequestTimeoutMillis = 1500;
	/**
	 * The lifetime for the connection pool.
	 */
	private long timeToLive = 30 * 60 * 1000;
	/**
	 * The time unit for timeToLive.
	 */
	private TimeUnit timeUnit = TimeUnit.MILLISECONDS;

	public ClientPropertie() {
	}

	public ClientPropertie(int maxTotalConnections, int maxPerRouteConnections,
			int socketTimeoutMillis, int connectTimeoutMillis, long timeToLive,
			TimeUnit timeUnit) {
		this.maxTotalConnections = maxTotalConnections;
		this.maxPerRouteConnections = maxPerRouteConnections;
		this.socketTimeoutMillis = socketTimeoutMillis;
		this.connectTimeoutMillis = connectTimeoutMillis;
		this.timeToLive = timeToLive;
		this.timeUnit = timeUnit;
	}

	public int getMaxTotalConnections() {
		return maxTotalConnections;
	}

	public void setMaxTotalConnections(int maxTotalConnections) {
		this.maxTotalConnections = maxTotalConnections;
	}

	public int getMaxPerRouteConnections() {
		return maxPerRouteConnections;
	}

	public void setMaxPerRouteConnections(int maxPerRouteConnections) {
		this.maxPerRouteConnections = maxPerRouteConnections;
	}

	public int getSocketTimeoutMillis() {
		return socketTimeoutMillis;
	}

	public void setSocketTimeoutMillis(int socketTimeoutMillis) {
		this.socketTimeoutMillis = socketTimeoutMillis;
	}

	public int getConnectTimeoutMillis() {
		return connectTimeoutMillis;
	}

	public void setConnectTimeoutMillis(int connectTimeoutMillis) {
		this.connectTimeoutMillis = connectTimeoutMillis;
	}

	public int getConnectionRequestTimeoutMillis() {
		return connectionRequestTimeoutMillis;
	}

	public void setConnectionRequestTimeoutMillis(int connectionRequestTimeoutMillis) {
		this.connectionRequestTimeoutMillis = connectionRequestTimeoutMillis;
	}

	public long getTimeToLive() {
		return timeToLive;
	}

	public void setTimeToLive(long timeToLive) {
		this.timeToLive = timeToLive;
	}

	public TimeUnit getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(TimeUnit timeUnit) {
		this.timeUnit = timeUnit;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ClientPropertie host = (ClientPropertie) o;
		return maxTotalConnections == host.maxTotalConnections &&
				maxPerRouteConnections == host.maxPerRouteConnections &&
				socketTimeoutMillis == host.socketTimeoutMillis &&
				connectTimeoutMillis == host.connectTimeoutMillis &&
				connectionRequestTimeoutMillis == host.connectionRequestTimeoutMillis &&
				timeToLive == host.timeToLive &&
				timeUnit == host.timeUnit;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maxTotalConnections, maxPerRouteConnections, socketTimeoutMillis, connectTimeoutMillis,
				connectionRequestTimeoutMillis, timeToLive, timeUnit);
	}

	@Override
	public String toString() {
		return "Host [maxTotalConnections=" + maxTotalConnections + ", maxPerRouteConnections=" + maxPerRouteConnections
				+ ", socketTimeoutMillis=" + socketTimeoutMillis + ", connectTimeoutMillis=" + connectTimeoutMillis
				+ ", connectionRequestTimeoutMillis=" + connectionRequestTimeoutMillis + ", timeToLive=" + timeToLive
				+ ", timeUnit=" + timeUnit + "]";
	}

}
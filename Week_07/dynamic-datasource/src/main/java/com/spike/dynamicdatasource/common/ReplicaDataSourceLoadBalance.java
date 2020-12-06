package com.spike.dynamicdatasource.common;

/**
 * Slave数据源负载均衡器
 * @author spike
 *
 */
public interface ReplicaDataSourceLoadBalance {

	String route(String group);
	
}

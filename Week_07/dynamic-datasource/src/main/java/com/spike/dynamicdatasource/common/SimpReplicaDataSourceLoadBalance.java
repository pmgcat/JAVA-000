package com.spike.dynamicdatasource.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.spike.dynamicdatasource.common.constant.RoutingConstant;

public class SimpReplicaDataSourceLoadBalance implements ReplicaDataSourceLoadBalance {

	public final Map<String, Integer> slaveCountInfo;
	public final Map<String, Long> slaveRouteState = new ConcurrentHashMap<String, Long>();

	public SimpReplicaDataSourceLoadBalance(Map<String, Integer> slaveCountInfo) {
		this.slaveCountInfo = slaveCountInfo;
		slaveCountInfo.forEach((k, v) -> slaveRouteState.put(k, 0L));
	}

	@Override
	public String route(String group) {
		final int slaveCount = slaveCountInfo.get(group);
		final long routeState = slaveRouteState.computeIfPresent(group, (k, v) -> v + 1);
		return group + "_" + RoutingConstant.REPLICA + (routeState % slaveCount);
	}

}

package com.spike.dynamicdatasource.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.spike.dynamicdatasource.common.DynamicRoutingDataSource;
import com.spike.dynamicdatasource.common.SimpReplicaDataSourceLoadBalance;
import com.spike.dynamicdatasource.common.ReplicaDataSourceLoadBalance;
import com.spike.dynamicdatasource.common.constant.RoutingConstant;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties("spring")
@MapperScan("com.spike.dynamicdatasource.mapper")
public class DataSourceConfig {

	private Map<String, Map<String, HikariConfig>> datasource = new LinkedHashMap<>();
	
	@Bean 
	public ReplicaDataSourceLoadBalance getSlaveDataSourceLoadBalance() {
		return new SimpReplicaDataSourceLoadBalance(buildSlaveCountInfo());
	}
	
	private  Map<String, Integer> buildSlaveCountInfo() {
		Map<String, Integer> slaveCountInfo = new HashMap<>();
        for (Map.Entry<String, Map<String, HikariConfig>> entry : datasource.entrySet()) {
        	String key = entry.getKey();
        	int count = 0;
        	Map<String, HikariConfig> subMap = entry.getValue();
        	for (String subKey : subMap.keySet()) {
        		if (subKey.startsWith(RoutingConstant.REPLICA)) count++;
        	}
        	slaveCountInfo.put(key, count);
        }
        return slaveCountInfo;
	}
	
	@Bean
	public DataSource getRoutingDataSourceConfig() {
        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
        dynamicRoutingDataSource.setTargetDataSources(buildTargetDataSources());
		return dynamicRoutingDataSource;
	}

	private  Map<Object, Object> buildTargetDataSources() {
		Map<Object, Object> targetDataSources = new HashMap<>();
        for (Map.Entry<String, Map<String, HikariConfig>> entry : datasource.entrySet()) {
        	Map<String, HikariConfig> subMap = entry.getValue();
        	for (Map.Entry<String, HikariConfig> subEntry : subMap.entrySet()) {
        		String key = entry.getKey() + "_" + subEntry.getKey();
        		targetDataSources.put(key, new HikariDataSource(subEntry.getValue()));
        	}
        }
        return targetDataSources;
	}

}

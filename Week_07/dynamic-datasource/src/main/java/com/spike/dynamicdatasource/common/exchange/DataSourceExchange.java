package com.spike.dynamicdatasource.common.exchange;

import java.lang.reflect.Method;

import javax.annotation.Resource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.spike.dynamicdatasource.common.DataSourceHolder;
import com.spike.dynamicdatasource.common.ReplicaDataSourceLoadBalance;
import com.spike.dynamicdatasource.common.annotation.TargetDataSource;
import com.spike.dynamicdatasource.common.constant.RoutingConstant;

@Aspect
@Order(1)
@Component
public class DataSourceExchange {

	@Resource
	private ReplicaDataSourceLoadBalance replicaDataSourceLoadBalance;

	@After("@annotation(com.spike.dynamicdatasource.common.annotation.TargetDataSource)")
	public void afterReturning(JoinPoint point) {
		DataSourceHolder.clearDataSource();
	}

	@Before(value = "@annotation(com.spike.dynamicdatasource.common.annotation.TargetDataSource)")
	public void before(JoinPoint joinPoint) {
		TargetDataSource targetDataSource = getTargetDataSource(joinPoint);
		String key = null;
		if (RoutingConstant.REPLICA.equals(targetDataSource.name())) {
			key = replicaDataSourceLoadBalance.route(targetDataSource.group());
		} else {
			key = targetDataSource.group() + "_" + targetDataSource.name();
		}
		DataSourceHolder.setDataSource(key);
	}
	
	private TargetDataSource getTargetDataSource(JoinPoint joinPoint) {
		MethodSignature ms = (MethodSignature) joinPoint.getSignature();
		Method method = ms.getMethod();
		TargetDataSource targetDataSource = method.getAnnotation(TargetDataSource.class);
		return targetDataSource;
	}
}
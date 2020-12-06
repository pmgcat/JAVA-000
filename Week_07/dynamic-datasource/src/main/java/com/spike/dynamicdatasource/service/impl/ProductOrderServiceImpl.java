package com.spike.dynamicdatasource.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spike.dynamicdatasource.common.annotation.TargetDataSource;
import com.spike.dynamicdatasource.entity.ProductOrder;
import com.spike.dynamicdatasource.mapper.ProductOrderMapper;
import com.spike.dynamicdatasource.service.ProductOrderService;

@Service
public class ProductOrderServiceImpl extends ServiceImpl<ProductOrderMapper, ProductOrder> implements ProductOrderService {


	@Override
	@TargetDataSource(name=TargetDataSource.PRIMARY, group="product-order")
	public boolean add(ProductOrder order) {
		return saveOrUpdate(order);
	}

	@Override
	@TargetDataSource(name=TargetDataSource.REPLICA, group="product-order")
	public ProductOrder get(long id) {
		return getById(id);
	}
	
	
}

package com.spike.dynamicdatasource.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spike.dynamicdatasource.entity.ProductOrder;
import com.spike.dynamicdatasource.mapper.ProductOrderMapper;
import com.spike.dynamicdatasource.service.ProductOrderService;

@Service
public class ProductOrderServiceImpl extends ServiceImpl<ProductOrderMapper, ProductOrder> implements ProductOrderService {


	@Override
	public boolean add(ProductOrder order) {
		return saveOrUpdate(order);
	}

	@Override
	public ProductOrder get(long id) {
		return getById(id);
	}
	
	
}

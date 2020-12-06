package com.spike.dynamicdatasource.service;

import com.spike.dynamicdatasource.entity.ProductOrder;

public interface ProductOrderService {
	
	boolean add(ProductOrder order);
	
	ProductOrder get(long id);
	
}

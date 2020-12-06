package com.spike.dynamicdatasource;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.spike.dynamicdatasource.entity.ProductOrder;
import com.spike.dynamicdatasource.service.ProductOrderService;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SampleTest {

	@Resource
	private ProductOrderService productOrderService;

	@Test
	public void testGet() {
		for (int i = 0; i < 10; i++) {
			ProductOrder productOrder = productOrderService.get(1);
			Assert.assertEquals(1, productOrder.getId().longValue());
			log.info(productOrder.toString());
		}

	}

	@Test
	public void testSave() {
		ProductOrder order = new ProductOrder().setId(2L).setProductId(1L).setDeliveryInfoSnapId(1L).setLogisticsId(1L)
				.setStatus(1).setUserId(1L).setOrderTime(System.currentTimeMillis());
		boolean result = productOrderService.add(order);
		Assert.assertEquals(true, result);
	}
}

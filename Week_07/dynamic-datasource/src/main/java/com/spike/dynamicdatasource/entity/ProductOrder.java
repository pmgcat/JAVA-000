package com.spike.dynamicdatasource.entity;

import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 商品订单
 * @author spike
 *
 */
@Data
@ToString
@Accessors(chain=true)
public class ProductOrder {
	
	@TableId
	private Long id;
	private Long userId;
	private Long productId;
	private Long deliveryInfoSnapId;
	private Long logisticsId;
	private Integer status;
	private Long orderTime;
	
}

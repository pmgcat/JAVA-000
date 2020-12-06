package com.spike.dynamicdatasource.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@MapperScan("com.spike.dynamicdatasource.mapper")
public class DataSourceConfig {

	
}

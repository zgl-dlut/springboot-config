package com.zgl.springboot.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

/**
 * @author zgl
 * @date 2019/4/1 上午10:34
 */
@Configuration
@Data
@ConfigurationProperties(prefix = "spring.datasource.druid")
@Slf4j
public class DataSourceConfiguration {

	private String driverClassName;

	private String userName;

	private String password;

	private String url;

	private Integer maxActive;

	private Integer initialSize;

	private Long maxWait;

	private Integer minIdle;

	private Long timeBetweenEvictionRunsMillis;

	private Long minEvictableIdleTimeMillis;

	private String validationQuery;

	private Boolean testWhileIdle;

	private Boolean testOnBorrow;

	private Boolean testOnReturn;

	private Boolean poolPreparedStatements;

	private Integer maxOpenPreparedStatements;

	private Boolean useGlobalDataSourceStat;

	private String filters;


	/**
	 * SpringBoot已经自动配置好了,可以不用写
	 */
	@Bean
	public DruidDataSource druidDataSource() {
		DruidDataSource druidDataSource=new DruidDataSource();
		druidDataSource.setMaxActive(maxActive);
		druidDataSource.setInitialSize(initialSize);
		druidDataSource.setMaxWait(maxWait);
		druidDataSource.setMinIdle(minIdle);
		druidDataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		druidDataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		druidDataSource.setValidationQuery(validationQuery);
		druidDataSource.setTestWhileIdle(testWhileIdle);
		druidDataSource.setTestOnBorrow(testOnBorrow);
		druidDataSource.setTestOnReturn(testOnReturn);
		druidDataSource.setPoolPreparedStatements(poolPreparedStatements);
		druidDataSource.setMaxOpenPreparedStatements(maxOpenPreparedStatements);
		druidDataSource.setUseGlobalDataSourceStat(useGlobalDataSourceStat);
		try {
			druidDataSource.setFilters(filters);
		} catch (SQLException e) {
			log.error("druid configuration initialization filter", e);
		}
		return druidDataSource;
	}
}
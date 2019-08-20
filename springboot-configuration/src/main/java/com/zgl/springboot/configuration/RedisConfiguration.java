package com.zgl.springboot.configuration;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.CollectionUtils;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zgl
 * @date 2019/4/1 上午10:11
 */
@Data
@Slf4j
@Configuration
@EnableCaching
@ConfigurationProperties(prefix = "spring.redis")
public class RedisConfiguration
{
	private String keyPrefix;
	private Duration defaultTTL;
	private Map<String, Duration> ttl;

	/**
	 * redisTemplate 序列化使用的jdkSerializeable, 存储二进制字节码, 所以自定义序列化类
	 * @param redisConnectionFactory
	 * @return
	 */
	@Bean
	@SuppressWarnings("unchecked")
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		/**
		 * 使用Jackson2JsonRedisSerialize 替换默认序列化
		 */
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
		/**
		 * 设置value的序列化规则和 key的序列化规则
		 */
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}

	/**
	 * StringRedisTemplate配置
	 * @param factory
	 * @return
	 */
	@Bean
	public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) {
		StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
		stringRedisTemplate.setConnectionFactory(factory);
		return stringRedisTemplate;
	}

	@Bean
	public KeyGenerator keyGenerator() {
		return (target, method, params) -> {
			StringBuilder paramsStr = new StringBuilder();
			paramsStr.append(target.getClass().getName());
			for (Object obj : params) {
				if (obj != null) {
					paramsStr.append(JSON.toJSONString(obj));
				}
			}
			HashFunction hashFunction = Hashing.murmur3_128();
			return hashFunction.hashString(target.getClass().getSimpleName() + method.getName()
					+ paramsStr.toString(), StandardCharsets.UTF_8);
		};
	}

	@Bean
	public RedisCacheConfiguration cacheConfig() {
		return RedisCacheConfiguration.defaultCacheConfig()
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(fastJsonRedisSerializer))
				.prefixKeysWith(keyPrefix)
				.entryTtl(defaultTTL)   //默认超时时间 5分钟
				.disableCachingNullValues()
				;
	}

	@Bean
	public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory,
	                                      RedisCacheConfiguration redisCacheConfiguration) {
		Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
		if (!CollectionUtils.isEmpty(ttl)) {
			for (Map.Entry<String, Duration> entry : ttl.entrySet()) {
				configMap.put(entry.getKey(), RedisCacheConfiguration.defaultCacheConfig().entryTtl(entry.getValue()));
				log.info("redis时延配置成功 - " + entry.getKey() + " ： " + entry.getValue());
			}
		}
		return RedisCacheManager.builder(connectionFactory)
				.cacheDefaults(redisCacheConfiguration)
				.withInitialCacheConfigurations(configMap)
				.build();
	}

}
package com.nttdata.nova.bookStore.configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.context.annotation.Configuration;

import redis.embedded.RedisServer;

@Configuration
public class RedisEmbeddedConfiguration {


	private RedisServer redisServer;

	public RedisEmbeddedConfiguration(RedisProperties redisProperties) {
		this.redisServer = RedisServer.builder().setting("maxheap 512Mb").port(redisProperties.getRedisPort()).build();
	}

	@PostConstruct
	public void startRedis() {
		redisServer.start();
	}

	@PreDestroy
	public void stopRedis() {
		redisServer.stop();
	}

}

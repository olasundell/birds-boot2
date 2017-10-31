package se.atrosys.birds.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheFactoryBean;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * TODO write documentation
 */
@Configuration
public class CacheConfig {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Bean
	public SimpleCacheManager cacheManager(){
		logger.info("Setting up cache-manager");
		SimpleCacheManager cacheManager = new SimpleCacheManager();

		List<Cache> caches = Arrays.asList(new ConcurrentMapCache("birds"),
			new ConcurrentMapCache("flickr"));

		cacheManager.setCaches(caches);
		return cacheManager;
	}
}

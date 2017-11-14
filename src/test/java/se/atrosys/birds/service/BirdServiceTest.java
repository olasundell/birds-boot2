package se.atrosys.birds.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import se.atrosys.birds.model.Bird;

import java.util.Collections;

/**
 * TODO write documentation
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@EnableCaching
//@ComponentScan({"se.atrosys.birds.service", "se.atrosys.birds.taxonomy", "se.atrosys.birds.flickr", "se.atrosys.birds.media", ""})
@ComponentScan({"se.atrosys.birds"})
public class BirdServiceTest {
	@Autowired
	private BirdService birdService;

	@Test
	public void shouldFoo() {
		Bird bird = birdService.save(Bird.builder()
			.scientificName("foo")
			.build());

		Bird result = birdService.findByScientificName(bird.getScientificName());

		Assert.assertEquals(bird, result);
	}

	public static class TestConfiguration {
		@Bean
		public CacheManager cacheManager() {
//			return new ConcurrentMapCacheManager("birds");
			final SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
			simpleCacheManager.setCaches(Collections.singletonList(new ConcurrentMapCache("birds")));
			return simpleCacheManager;
		}
	}
}
package se.atrosys.birds.service;

import org.junit.Assert;
import org.junit.Ignore;
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
import se.atrosys.birds.taxonomy.avibase.AviBaseResult;
import se.atrosys.birds.model.Region;
import se.atrosys.birds.model.RegionalScarcity;
import se.atrosys.birds.repository.BirdRepository;
import se.atrosys.birds.repository.RegionRepository;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * TODO write documentation
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@EnableCaching
@ComponentScan({"se.atrosys.birds"})
public class RegionScarcityServiceTest {
	@Autowired
	private BirdRepository birdRepository;

	@Autowired
	private RegionRepository regionRepository;

	@Autowired
	private PopulateService populateService;

	@Autowired
	private RegionScarcityService regionScarcityService;

	@Autowired
	private CacheManager cacheManager;

	@Test
	@Ignore
	public void readFileShouldWork() {
		populateService.populateRegions();
		Region region = regionRepository.findAll().iterator().next();
		AviBaseResult result = regionScarcityService.readRegionFile(region);
		Assert.assertNotNull(result);
	}

	@Test
	@Ignore("Too extensive a test")
	public void test() {
//		populateService.populateRegions();
//		populateService.populateBirds();
		populateService.populate();
		Map<Region, List<RegionalScarcity>> result = regionScarcityService.readRegionalScarcities();
		Assert.assertNotNull(result);
	}

//	@Configuration
//	@EnableCaching
//	@ComponentScan({"se.atrosys.birds.service", "se.atrosys.birds.xml", "se.atrosys.birds.avibase", "se.atrosys.birds.flickr", "se.atrosys.birds.xenocanto" })
	public static class TestConfiguration{
		@Bean
		public CacheManager cacheManager() {
//			return new ConcurrentMapCacheManager("birds");
			final SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
			simpleCacheManager.setCaches(Collections.singletonList(new ConcurrentMapCache("birds")));
			return simpleCacheManager;
		}

//		@Bean
//		public SimpleCacheManager cacheManager(){
//			SimpleCacheManager cacheManager = new SimpleCacheManager();
//
//			List<Cache> caches = new ArrayList<>();
//			final ConcurrentMapCacheFactoryBean cache = cacheBean();
//			caches.add();
//			cacheManager.setCaches(caches);
//
//			return cacheManager;
//		}
//
//		@Bean
//		public ConcurrentMapCacheFactoryBean cacheBean(){
//			ConcurrentMapCacheFactoryBean cacheFactoryBean = new ConcurrentMapCacheFactoryBean();
//
//			cacheFactoryBean.setName("default");
//
//			return cacheFactoryBean;
//		}
	}

}
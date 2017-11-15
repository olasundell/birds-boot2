package se.atrosys.birds.taxonomy.inaturalist;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;
import se.atrosys.birds.model.TaxonomyRank;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * TODO write documentation
 */
public class INaturalistServiceTest {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private RestTemplate restTemplate;
	private ObjectMapper objectMapper;
	private INaturalistService service;

	@Before
	public void setup() {
		restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().forEach(conv -> {
			if (conv.getSupportedMediaTypes().contains(MediaType.APPLICATION_JSON)) {
				MappingJackson2HttpMessageConverter jacksonConv = (MappingJackson2HttpMessageConverter) conv;
				final ObjectMapper mapper = jacksonConv.getObjectMapper()
					.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
				mapper.setConfig(mapper.getDeserializationConfig().with(DeserializationFeature.READ_ENUMS_USING_TO_STRING));
				this.objectMapper = mapper;
				jacksonConv.setObjectMapper(mapper);
			}
		});
		service = new INaturalistService(restTemplate);
	}
	@Test
	public void shouldDeserialize() throws IOException {
		final ClassPathResource resource = new ClassPathResource("inaturalist");
		Assert.assertTrue(resource.getFile().isDirectory());

		ClassPathResource cpr = new ClassPathResource("inaturalist/4.json");

		INResponse result = objectMapper.readValue(cpr.getFile(), INResponse.class);
		assertResult(result);

		testOne(473);
		testOne(12160);
	}

	private void testOne(int id) throws IOException {
		ClassPathResource cpr;
		INResponse result;
		cpr = new ClassPathResource("inaturalist/" + id + ".json");
		result = objectMapper.readValue(cpr.getFile(), INResponse.class);
		assertResult(result);
	}

	private void assertResult(INResponse result) {
		final List<Taxon> results = result.getResults();
		Assert.assertNotNull(results);
		Assert.assertEquals(1, results.size());
		final Taxon taxon = results.get(0);
		if (taxon.getRank().ordinal() < TaxonomyRank.SPECIES.ordinal()) {
			Assert.assertNotNull(taxon.getChildren());
			Assert.assertFalse(taxon.getChildren().isEmpty());
		}
	}

	@Test
	public void shouldGetResult() throws IOException {
		Map<Integer, INResponse> map = new HashMap<>();
		final int key = 472;
		INResponse result = service.read(key);

		assertResult(result);

		map.put(key, result);

		Assert.assertNotEquals(1, map.size());
	}

	@Test
	public void readChildrenTest() {
		StopWatch stopWatch = new StopWatch();
		
		stopWatch.start();
		Map<Integer, INResponse> map = service.readMap();
		stopWatch.stop();

		logger.debug("Reading the map took {} ms", stopWatch.getLastTaskTimeMillis());

		List<Taxon> taxons = map.values().parallelStream()
			.map(INResponse::getResults)
			.map(l -> l.get(0))
			.filter(t -> Objects.nonNull(t.getPreferredCommonName()))
			.filter(t -> t.getPreferredCommonName().contains("Frilled Monarch"))
			.collect(Collectors.toList());

		Assert.assertNotNull(taxons);

		Map<String, List<Taxon>> bySciName = new HashMap<>();

		map.values().stream()
			.map(INResponse::getResults)
			.map(l -> l.get(0))
			.filter(e -> e.getRank().equals(TaxonomyRank.SPECIES))
			.filter(t -> Objects.nonNull(t.getName()))
			.peek(t -> logger.trace(t.getName()))
			.forEach(e -> {
				bySciName.computeIfAbsent(e.getName(), k -> new ArrayList<>()).add(e);
			});

		Map<String, Taxon> sciName = bySciName.entrySet()
			.parallelStream()
			.collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().get(0)));


		Assert.assertNotNull(map);
	}
}
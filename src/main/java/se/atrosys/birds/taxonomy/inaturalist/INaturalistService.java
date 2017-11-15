package se.atrosys.birds.taxonomy.inaturalist;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import se.atrosys.birds.model.TaxonomyRank;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * TODO write documentation
 */
@Component
public class INaturalistService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final RestTemplate restTemplate;
	private final ObjectMapper objectMapper;
	private final AtomicInteger count = new AtomicInteger(10_000);
	private final INResponse STOP = INResponse.builder().build();
	private Map<Integer, INResponse> map;

	public INaturalistService(RestTemplate snakeCaseRestTemplate) {
		this.restTemplate = snakeCaseRestTemplate;

		Optional<HttpMessageConverter<?>> first = restTemplate.getMessageConverters().stream()
			.filter(conv -> conv.getSupportedMediaTypes().contains(MediaType.APPLICATION_JSON))
			.findFirst();

		final Optional<MappingJackson2HttpMessageConverter> converter = first.map(conv -> (MappingJackson2HttpMessageConverter) conv);

		if (!converter.isPresent()) {
			throw new IllegalStateException("Could not find object mapper");
		}

		this.objectMapper = converter.get().getObjectMapper();
	}

	public INResponse read(int id) {
		final Resource resource = new FileSystemResource("src/main/resources/inaturalist/" + id + ".json");
		if (resource.exists()) {
			try {
				logger.trace("File exists, id {}", id);
				return objectMapper.readValue(resource.getFile(), INResponse.class);
			} catch (IOException e) {
				logger.warn("Could not read {}", resource.getFilename(), e);
			}
		}

		try {
			if (count.decrementAndGet() >= 0) {
				logger.debug("Reading from API, id {}", id);
				return write(restTemplate.getForObject("http://api.inaturalist.org/v1/taxa/" + id, INResponse.class));
			} else {
				logger.debug("End of run");
				return STOP;
			}
		} catch (IOException e) {
			throw new IllegalStateException("Could not read from the iNaturalist API", e);
		}
	}

	public Map<Integer, INResponse> readMap() {
		if (map == null) {
			map = new HashMap<>();
		}

		if (map.size() < 1_000) {
			final INResponse read = read(3);
			map.put(3, read);

			readChildren(read, map);
		}

		return map;
	}

	public Map<String, Taxon> bySciName() {
		Map<String, List<Taxon>> bySciName = new HashMap<>();

		readMap().values().stream()
			.map(INResponse::getResults)
			.map(l -> l.get(0))
			.filter(e -> e.getRank().equals(TaxonomyRank.SPECIES))
			.filter(t -> Objects.nonNull(t.getName()))
//			.peek(t -> logger.debug(t.getName()))
			.forEach(e -> bySciName.computeIfAbsent(e.getName(), k -> new ArrayList<>()).add(e));

		Map<String, Taxon> sciName = bySciName.entrySet()
			.parallelStream()
			.collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().get(0)));

		return sciName;
	}

	private void readChildren(INResponse result, Map<Integer, INResponse> map) {
		final Taxon taxon = result.getResults().get(0);
		logger.trace("Reading children of {} - {}", taxon.getRank(), taxon.getName());

		if (taxon.getChildren() == null) {
			return;
		}

		taxon.getChildren().stream()
			.map(Taxon::getId)
			.forEach(id -> {
				logger.trace("Reading child with id {}", id);
				final INResponse read = read(id);

				if (read == STOP) {
					return;
				}

				map.put(id, read);

//				if (!TaxonomyRank.SPECIES.equals(read.getResults().get(0).getRank())) {
				readChildren(read, map);
//				}
			});
	}

	private INResponse write(INResponse inResponse) throws IOException {
		objectMapper.writerWithDefaultPrettyPrinter()
			.writeValue(new File("src/main/resources/inaturalist/" + inResponse.getResults().get(0).getId() + ".json"), inResponse);
		return inResponse;
	}
}

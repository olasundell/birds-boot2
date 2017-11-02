package se.atrosys.birds.taxonomy.avibase;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import se.atrosys.birds.model.Region;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO write documentation
 */
@Component
public class AviBaseRegionService {
	public List<Region> readRegions() {
		try {
			final File file = new ClassPathResource("regions/regions.json").getFile();
			final List<AviBaseRegion> regions = new ObjectMapper().readValue(file, new RegionListTypeReference());

			return regions.stream()
				.map(this::mapToRegion)
				.collect(Collectors.toList());

		} catch (IOException e) {
			throw new IllegalStateException("Could not read regions", e);
		}
	}

	private Region mapToRegion(AviBaseRegion aviBaseRegion) {
		return Region.builder()
			.name(aviBaseRegion.getRegionName())
			.code(aviBaseRegion.getRegion())
			.build();
	}

	@Data
	@Builder
	public static class AviBaseRegion {
		private String regionName;
		private String region;
		private String regionType2;
		private int regionType;
	}

	public static class RegionListTypeReference extends TypeReference<List<AviBaseRegion>> {}
}

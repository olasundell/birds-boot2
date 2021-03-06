package se.atrosys.birds.taxonomy.avibase;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;
import se.atrosys.birds.model.Region;

import java.util.List;

/**
 * TODO write documentation
 */
@Builder
@Data
@JsonDeserialize(builder = AviBaseResult.AviBaseResultBuilder.class)
public class AviBaseResult {
	private Region region;
	private List<AviBaseRegionalScarcity> regionalScarcities;

	@JsonPOJOBuilder(withPrefix = "")
	public static class AviBaseResultBuilder {}
}

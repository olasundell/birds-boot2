package se.atrosys.birds.taxonomy.inaturalist;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * TODO write documentation
 */
@Data
@Builder
@JsonDeserialize(builder = INResponse.INResponseBuilder.class)
public class INResponse {
	private String name;
	private Integer perPage;
	private Integer page;
	private Integer totalResults;
	private List<Taxon> results;

	@JsonPOJOBuilder(withPrefix = "")
	public static class INResponseBuilder { }
}

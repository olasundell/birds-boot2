package se.atrosys.birds.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * TODO write documentation
 */
@Builder
@Data
public class Response {
	private List<ResponseBird> genusBirds;
	private Media media;
	private ResponseBird actualBird;

	@Builder
	@Data
	public static class ResponseBird {
		private String name;
		private String scientificName;
		private String genusName;
	}
}

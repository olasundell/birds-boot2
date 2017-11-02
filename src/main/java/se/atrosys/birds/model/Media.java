package se.atrosys.birds.model;

import lombok.Builder;
import lombok.Data;

/**
 * TODO write documentation
 */
@Data
@Builder
public class Media {
	private MediaType mediaType;
	private String url;
}

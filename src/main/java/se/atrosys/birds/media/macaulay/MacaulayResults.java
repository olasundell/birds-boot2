package se.atrosys.birds.media.macaulay;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * TODO write documentation
 */
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class MacaulayResults {
	private List<MacaulayContent> content;
}

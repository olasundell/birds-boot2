package se.atrosys.birds.xenocanto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * TODO write documentation
 */
@Data
@Builder
public class XenoCantoResult {
	private int numPages;
	private int numRecordings;
	private int numSpecies;
	private int page;

	private List<XenoCantoRecording> recordings;
}

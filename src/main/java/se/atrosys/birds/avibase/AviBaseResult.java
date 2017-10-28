package se.atrosys.birds.avibase;

import lombok.Builder;
import lombok.Data;
import se.atrosys.birds.model.Bird;
import se.atrosys.birds.model.Region;
import se.atrosys.birds.model.RegionalScarcity;

import java.util.List;

/**
 * TODO write documentation
 */
@Builder
@Data
public class AviBaseResult {
	private Region region;
	private List<AviBaseRegionalScarcity> regionalScarcities;
}

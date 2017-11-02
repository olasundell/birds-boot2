package se.atrosys.birds.taxonomy.avibase;

import lombok.Builder;
import lombok.Data;
import se.atrosys.birds.model.Scarcity;

import java.util.Set;

/**
 * TODO write documentation
 */
@Data
@Builder
public class AviBaseRegionalScarcity {
	private String scientificName;
	private Set<Scarcity> scarcities;
}

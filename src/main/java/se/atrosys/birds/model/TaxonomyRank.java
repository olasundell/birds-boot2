package se.atrosys.birds.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * TODO write documentation
 */
//@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TaxonomyRank {
	KINGDOM,
	PHYLUM,
	SUBPHYLUM,
	CLASS,
//	@JsonProperty("order")
	ORDER,
//	@JsonProperty("family")
	FAMILY,
	SUBFAMILY,
//	@JsonProperty("genus")
	GENUS,
//	@JsonProperty("species")
	SPECIES,
	SUBSPECIES,
	INFRAHYBRID,
	GENUSHYBRID,
	HYBRID,
	TRIBE,
	FORM,
	VARIETY;

	@Override
	public String toString() {
		return name().toLowerCase();
	}
}

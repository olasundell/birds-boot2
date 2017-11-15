package se.atrosys.birds.taxonomy.inaturalist;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Builder;
import lombok.Data;
import se.atrosys.birds.model.TaxonomyRank;

import java.util.List;

/**
 * TODO write documentation
 */
@Data
@Builder
@JsonDeserialize(builder = Taxon.TaxonBuilder.class)
public class Taxon {
	private String minSpeciesAncestry;
	private String iconicTaxonName;
	private String createdAt;
	private Integer iconicTaxonId;
	private Integer atlasId;
	private String name;
	private Integer completeSpeciesCount;
	private TaxonPhoto.Photo defaultPhoto;
	@JsonSerialize(using = ToStringSerializer.class)
	private TaxonomyRank rank;
	private String ancestry;
	private Integer rankLevel;
	private Integer taxonSchemesCount;
	private List<Integer> ancestorIds;
	private Integer id;
	private Integer parentId;
	private Integer taxonChangesCount;
	private String preferredCommonName;
	private Boolean extinct;
	private Boolean isActive;
	private Integer observationsCount;
	private List<TaxonPhoto> taxonPhotos;
	private List<ConservationStatus> conservationStatuses;
	private List<Taxon> children;
	private List<Taxon> ancestors;
	private String wikipediaSummary;

	@JsonProperty("rank")
	public void setRank(String rank) {
		this.rank = TaxonomyRank.valueOf(rank.toUpperCase());
	}

	@JsonPOJOBuilder(withPrefix = "")
	public static class TaxonBuilder {
	}

	@Override
	public String toString() {
		return String.format("%d %s", id, preferredCommonName);
	}
}

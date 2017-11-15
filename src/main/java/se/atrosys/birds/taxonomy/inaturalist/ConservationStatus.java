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
@JsonDeserialize(builder = ConservationStatus.ConservationStatusBuilder.class)
public class ConservationStatus {
//               "description" : "",
	private String description;
//               "iucn" : 40,
	private Integer iucn;
//               "url" : "http://www.natureserve.org/explorer/servlet/NatureServe?searchName=Fulica+americana",
	private String url;
//               "authority" : "NatureServe",
	private String authority;
//               "taxon_id" : 473,
	private Integer taxonId;
	private Place place;
//               "place" : {
//                  "ancestor_place_ids" : [
//                     9853,
//                     1,
//                     19
//                  ],
//                  "display_name" : "Alabama, US",
//                  "name" : "Alabama",
//                  "id" : 19,
//                  "admin_level" : 1
//               },
//               "status" : "S2B,S5N"
	private String status;

	@JsonPOJOBuilder(withPrefix = "")
	public static class ConservationStatusBuilder { }

	@Builder
	@Data
	@JsonDeserialize(builder = Place.PlaceBuilder.class)
	public static class Place {
		private List<Integer> ancestorPlaceIds;
		private String displayName;
		private String name;
		private Integer id;
		private Integer adminLevel;

		@JsonPOJOBuilder(withPrefix = "")
		public static class PlaceBuilder {}
	}
}

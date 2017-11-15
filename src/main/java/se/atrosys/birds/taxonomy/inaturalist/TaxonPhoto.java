package se.atrosys.birds.taxonomy.inaturalist;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * TODO write documentation
 }
 */
@Data
@Builder
@JsonDeserialize(builder = TaxonPhoto.TaxonPhotoBuilder.class)
public class TaxonPhoto {
	private Photo photo;
	private Taxon taxon;

	@JsonPOJOBuilder(withPrefix = "")
	public static class TaxonPhotoBuilder {
	}

	/*

                 "photo" : {
 "url" : "https://farm1.staticflickr.com/205/499334547_68fab65c70_s.jpg",
 "license_code" : "cc-by-nc",
 "square_url" : "https://farm1.staticflickr.com/205/499334547_68fab65c70_s.jpg",
 "type" : "FlickrPhoto",
 "native_page_url" : "http://www.flickr.com/photos/23084352@N00/499334547",
 "attribution" : "(c) Kenny P., some rights reserved (CC BY-NC)",
 "large_url" : "https://farm1.staticflickr.com/205/499334547_68fab65c70_b.jpg",
 "native_photo_id" : "499334547",
 "medium_url" : "https://farm1.staticflickr.com/205/499334547_68fab65c70.jpg",
 "id" : 222,
 "original_url" : "https://farm1.staticflickr.com/205/499334547_c2b9244a27_o.jpg",
 "flags" : [],
 "original_dimensions" : null,
 "small_url" : "https://farm1.staticflickr.com/205/499334547_68fab65c70_m.jpg"
 },
	 */
	@Data
	@Builder
	@JsonDeserialize(builder = Photo.PhotoBuilder.class)
	public static class Photo {
		private String url;
		private String licenseCode;
		private String type;
		private String attribution;
		private String nativePhotoId;
		private String smallUrl;
		private String mediumUrl;
		private String largeUrl;
		private String squareUrl;
		private String originalUrl;
		private String nativePageUrl;
		private Integer id;
		private List<Map<String, String>> flags;
		private Object originalDimensions;

		@JsonPOJOBuilder(withPrefix = "")
		public static class PhotoBuilder {}
	}

	/*

 "taxon" : {
 "min_species_ancestry" : "48460,1,2,355675,3",
 "iconic_taxon_name" : "Aves",
 "created_at" : "2008-03-13T02:35:09+00:00",
 "iconic_taxon_id" : 3,
 "atlas_id" : null,
 "name" : "Aves",
 "complete_species_count" : null,
 "default_photo" : {
 "license_code" : "cc-by-nc",
 "square_url" : "https://farm1.staticflickr.com/205/499334547_68fab65c70_s.jpg",
 "url" : "https://farm1.staticflickr.com/205/499334547_68fab65c70_s.jpg",
 "medium_url" : "https://farm1.staticflickr.com/205/499334547_68fab65c70.jpg",
 "id" : 222,
 "flags" : [],
 "original_dimensions" : null,
 "attribution" : "(c) Kenny P., some rights reserved (CC BY-NC)"
 },
 "rank" : "class",
 "ancestry" : "48460/1/2/355675",
 "rank_level" : 50,
 "taxon_schemes_count" : 2,
 "ancestor_ids" : [ 48460, 1, 2, 355675, 3 ],
 "id" : 3,
 "parent_id" : 355675,
 "taxon_changes_count" : 1,
 "preferred_common_name" : "Birds",
 "extinct" : false,
 "is_active" : true,
 "observations_count" : 1574830
	 */

}

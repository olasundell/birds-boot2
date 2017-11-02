package se.atrosys.birds.taxonomy.ebird;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * TODO write documentation
 *
 */
@Data
@Builder
public class EbirdSpecies {
	//      "sciName" : "Struthio camelus",
	private String sciName;

	//      "bandingCodes" : [],
	private List<Object> bandingCodes;
	//      "taxonOrder" : 3,
	private Integer taxonOrder;
	//      "taxonID" : "TC000002",
	private String taxonID;
	//      "comName" : "Common Ostrich",
	private String comName;
	//      "speciesCode" : "ostric2",
	private String speciesCode;
	//      "sciNameCodes" : [
	//         "STCA"
	//      ],
	private List<String> sciNameCodes;
	//      "category" : "species",
	private String category;
	//      "comNameCodes" : [
	//         "COOS"
	//      ]
	private List<String> comNameCodes;
}

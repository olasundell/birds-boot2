package se.atrosys.birds.media.macaulay;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

/**
 * TODO write documentation
 */
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class MacaulaySubjectData {
	private String speciesCode;
	private String sciName;
	private Object subjectTags;
//	private List<String> ageSexCounts;
	private String comName;
//                  "speciesCode" : "marori2",
//                  "sciName" : "Oriolus traillii",
//                  "subjectTags" : {},
//                  "ageSexCounts" : [],
//                  "comName" : "Maroon Oriole"
}

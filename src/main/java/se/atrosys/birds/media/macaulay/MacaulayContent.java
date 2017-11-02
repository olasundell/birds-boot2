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
public class MacaulayContent {
//            "accessories" : null,
//            "comments" : null,
//            "commonName" : "Maroon Oriole",
	private String commonName;
//            "licenseType" : "anylabuse_v1",
//            "mediaUrl" : "https://download.ams.birds.cornell.edu/api/v1/asset/71732221/large",
	private String mediaUrl;
//            "subjectData" : [
//               {
//                  "speciesCode" : "marori2",
//                  "sciName" : "Oriolus traillii",
//                  "subjectTags" : {},
//                  "ageSexCounts" : [],
//                  "comName" : "Maroon Oriole"
//               }
//            ],
	private List<MacaulaySubjectData> subjectData;
//            "thumbnailUrl" : "https://search.macaulaylibrary.org/static/blank-placeholder.png",
	private String thumbnailUrl;
//            "obsDttm" : "5 Oct 2017",
//            "ratingCount" : "0",
//            "sex" : null,
//            "rating" : "0.0",
//            "microphone" : null,
//            "eBirdChecklistUrl" : "https://ebird.org/ebird/view/checklist/S39576838",
//            "sciName" : "Oriolus traillii",
	private String sciName;
//            "collected" : false,
//            "behaviors" : null,
//            "userDisplayName" : "坤慧 林",
//            "eBirdChecklistId" : "S39576838",
//            "locationLine2" : "Pingtung County, Taiwan",
//            "valid" : "true",
	private Boolean valid;
//            "catalogId" : "71732221",
//            "userId" : "USER896030",
//            "specimenUrl" : "https://macaulaylibrary.org/asset/71732221",
//            "assetId" : "71732221",
//            "source" : "ebird",
//            "locationLine1" : "屏東--屏東科技大學(Pingtung--Pingtung University of Science and Technology)",
//            "previewUrl" : "https://download.ams.birds.cornell.edu/api/v1/asset/71732221/",
//            "mediaType" : "Photo",
	private String mediaType;
//            "age" : null,
//            "specimenIds" : null,
//            "obsComments" : null,
//            "userProfileUrl" : "https://ebird.org/ebird/profile/ODk2MDMw",
//            "recorder" : null,
//            "location" : "屏東--屏東科技大學(Pingtung--Pingtung University of Science and Technology), Pingtung County, Taiwan",
//            "homeArchive" : null,
//            "speciesCode" : "marori2",
	private String speciesCode;
//            "stimulus" : false,
//            "largeUrl" : "https://download.ams.birds.cornell.edu/api/v1/asset/71732221/large"
	private String largeUrl;
}

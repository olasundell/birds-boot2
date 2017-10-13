package se.atrosys.birds.xenocanto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

/**
 * TODO write documentation
 */
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = XenoCantoRecording.XenoCantoRecordingBuilder.class)
public class XenoCantoRecording {
	@JsonProperty("loc")
	private String location;

	private Date date;

	private String type;
	@JsonProperty("gen")
	private String genus;

	private String id;
	@JsonProperty("rec")
	private String recorder;

	@JsonProperty("sp")
	private String species;

	private String file;
	private String url;
	@JsonProperty("cnt")
	private String country;

	@JsonPOJOBuilder(withPrefix = "")
	public static class XenoCantoRecordingBuilder {}
	/*
		 "loc" : "augsburg, siebentischwald - 48.317184, 10.941688",
         "date" : "2016-10-27",
         "type" : "call",
         "gen" : "Troglodytes",
         "id" : "342929",
         "rec" : "johannes buhl",
         "sp" : "troglodytes",
         "lic" : "http://creativecommons.org/licenses/by-nc-sa/4.0/",
         "ssp" : "",
         "q" : "B",
         "file" : "http://www.xeno-canto.org/342929/download",
         "url" : "http://www.xeno-canto.org/342929",
         "cnt" : "Germany",
         "en" : "Eurasian Wren",
         "lng" : "10.9417",
         "time" : "08:30",
         "lat" : "48.3172"
	 */
}

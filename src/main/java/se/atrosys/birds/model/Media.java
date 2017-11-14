package se.atrosys.birds.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Builder;
import lombok.Data;

import java.io.IOException;

/**
 * TODO write documentation
 * TODO verification of MediaHash
 */
@Data
@Builder
public class Media {
	private MediaType mediaType;
	private String url;
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonDeserialize(using = MediaHashDeserializer.class)
	private MediaHash hash;

	@Data
	@Builder
	public static class MediaHash {
		public static final String SEPARATOR = "-";
		private String source;
		private MediaType mediaType;
		private Integer birdId;
		private Integer index;

		@Override
		public String toString() {
			return source + SEPARATOR + mediaType.name() + SEPARATOR + birdId + SEPARATOR + index;
		}
	}

	static class MediaHashDeserializer extends JsonDeserializer<MediaHash> {

		@Override
		public MediaHash deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
			JsonNode node = jp.getCodec().readTree(jp);
			String[] parts = node.textValue().split(MediaHash.SEPARATOR);

			return MediaHash.builder()
				.source(parts[0])
				.mediaType(MediaType.valueOf(parts[1]))
				.birdId(Integer.valueOf(parts[2]))
				.index(Integer.valueOf(parts[3]))
				.build();
		}
	}
}

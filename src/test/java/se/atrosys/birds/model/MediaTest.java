package se.atrosys.birds.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * TODO write documentation
 */
public class MediaTest {
	@Test
	public void shouldDeserializeMediaHash() throws IOException {
		String s = "{\n" +
			"      \"mediaType\" : \"PHOTO\",\n" +
			"      \"url\" : \"https://download.ams.birds.cornell.edu/api/v1/asset/64830491/large\",\n" +
			"      \"hash\" : \"MACAULAY-PHOTO-1-5\"\n" +
			"   }";

		Media media = new ObjectMapper().readValue(s, Media.class);

		Assert.assertNotNull(media);
		Assert.assertNotNull(media.getHash());
		Assert.assertEquals(5, media.getHash().getIndex().intValue());
		Assert.assertEquals(MediaType.PHOTO, media.getHash().getMediaType());
		Assert.assertEquals("MACAULAY", media.getHash().getSource());
	}
}
package se.atrosys.birds.flickr;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import se.atrosys.birds.exception.BirdFlickrException;
import se.atrosys.birds.media.flickr.FileFetcher;
import se.atrosys.birds.media.flickr.FlickrService;
import se.atrosys.birds.model.Bird;
import se.atrosys.birds.model.BirdPhoto;

import java.util.List;

/**
 * TODO write documentation
 */
public class FlickrServiceTest {
	@Test
	@Ignore("integration test. Anyway, Flickr is deprecated.")
	public void shouldDoStuff() throws BirdFlickrException {
		FlickrService service = new FlickrService(new FileFetcher());
		List<BirdPhoto> result = service.getPictures(Bird.builder()
			.scientificName("Pica pica")
			.build());
		Assert.assertNotNull(result);
	}
}
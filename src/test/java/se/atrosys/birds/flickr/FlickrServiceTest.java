package se.atrosys.birds.flickr;

import org.junit.Assert;
import org.junit.Test;
import se.atrosys.birds.exception.BirdFlickrException;
import se.atrosys.birds.model.Bird;
import se.atrosys.birds.model.BirdPhoto;

import javax.xml.bind.JAXBException;

import java.util.List;

import static org.junit.Assert.*;

/**
 * TODO write documentation
 */
public class FlickrServiceTest {
	@Test
	public void shouldDoStuff() throws BirdFlickrException {
		FlickrService service = new FlickrService(new FileFetcher());
		List<BirdPhoto> result = service.getPictures(Bird.builder()
			.scientificName("Pica pica")
			.build());
		Assert.assertNotNull(result);
	}

}
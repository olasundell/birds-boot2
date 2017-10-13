package se.atrosys.birds.flickr;

import org.junit.Test;
import se.atrosys.birds.model.Bird;

import javax.xml.bind.JAXBException;

import static org.junit.Assert.*;

/**
 * TODO write documentation
 */
public class FlickrServiceTest {
	@Test
	public void shouldDoStuff() throws JAXBException {
		FlickrService service = new FlickrService(new FileFetcher());
//		FlickrPhotoList result = service.getPictures(Bird.builder()
//			.scientificName("Pica pica")
//			.build());
	}

}
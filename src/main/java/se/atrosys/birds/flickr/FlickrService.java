package se.atrosys.birds.flickr;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photos.PhotoList;
import com.flickr4java.flickr.photos.SearchParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import se.atrosys.birds.model.Bird;
import se.atrosys.birds.model.BirdPhoto;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO write documentation
 */
@Component
public class FlickrService {
	private static final String API_KEY = "b94821e60fb20b35d9bd8e950a692aaf";
	private static final String FLICKR_URL = "http://api.flickr.com/services/rest/";
	private final String FILE_URL = String.format("file://%s/flickr/{flickrmethod}-tags={tags}", new File(".").getAbsoluteFile().getParent());
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private final FileFetcher fileFetcher;

	public FlickrService(FileFetcher fileFetcher) {
		this.fileFetcher = fileFetcher;
	}

	public List<BirdPhoto> getPictures(Bird model) throws JAXBException {
		PhotoList<Photo> result = search(model.getScientificName());
//		String.format("http://farm%s.staticflickr.com/%s/%s_%s.jpg", farm, server, id, secret);
		return result.stream()
			.map(p -> BirdPhoto.builder()
				.url(p.getUrl())
				.niftyUrl(String.format("http://farm%s.staticflickr.com/%s/%s_%s.jpg",
					p.getFarm(),
					p.getServer(),
					p.getId(),
					p.getSecret()))
				.build())
			.collect(Collectors.toList());
	}

	private PhotoList<Photo> search(String tag) {
		String apiKey = "b94821e60fb20b35d9bd8e950a692aaf";
		String sharedSecret = "40edbd76352c0ce1";

		Flickr f = null;
		try {
			f = new Flickr(apiKey, sharedSecret, new REST());
			final SearchParameters params = new SearchParameters();
			params.setTags(new String[] { tag });

			return f.getPhotosInterface().search(params, 10, 0);
		} catch (FlickrException e) {
			logger.error("Error when flickring", e);
		}
		return null;

	}
}

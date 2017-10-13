package se.atrosys.birds.flickr;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.photos.PhotoList;
import com.flickr4java.flickr.photos.SearchParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * TODO write documentation
 */
@Component
public class FileFetcher {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public File fetchFile(Map<String, String> map, String fileUrl, String flickrUrl) {

		return null;
	}
}

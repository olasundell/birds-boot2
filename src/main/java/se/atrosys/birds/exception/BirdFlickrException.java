package se.atrosys.birds.exception;

import com.flickr4java.flickr.FlickrException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * TODO write documentation
 */
@ResponseStatus(code = HttpStatus.BAD_GATEWAY, reason = "Something went wrong while communicating with Flickr")
public class BirdFlickrException extends Throwable {
	public BirdFlickrException(FlickrException e) {
		super(e);
	}
}

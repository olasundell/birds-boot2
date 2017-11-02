package se.atrosys.birds.exception;

import se.atrosys.birds.model.Bird;

/**
 * TODO write documentation
 */
public class CountNotFindMediaException extends Throwable {
	public CountNotFindMediaException(Bird bird) {
		super("Could not find media for " + bird.getScientificName());
	}
}

package se.atrosys.birds.avibase;

import org.junit.Test;

import java.io.IOException;

/**
 * TODO write documentation
 */
public class AviBaseServiceTest {
	@Test
	public void test() throws IOException {
		new AviBaseService(birdRepository, regionRepository).getAviBaseStuff();
	}

}
package se.atrosys.birds.taxonomy.ioc;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * TODO write documentation
 */
public class IocTranslationServiceTest {
	@Test
	public void shouldTranslate() {
		Map<String, String> result = new IocTranslationService().read();
		Assert.assertNotNull(result);
		Assert.assertFalse(result.isEmpty());
		Assert.assertEquals("oressochen melanopterus", result.get("chloephaga melanoptera"));
	}

}
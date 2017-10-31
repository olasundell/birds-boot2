package se.atrosys.birds.avibase;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import se.atrosys.birds.model.Region;
import se.atrosys.birds.model.Scarcity;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * TODO write documentation
 */
@Component
public class AviBaseService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public AviBaseResult getAviBaseStuff(Region region) throws IOException {
		Document document = Jsoup.connect("http://avibase.bsc-eoc.org/checklist.jsp?region=" + region.getCode() + "&list=ioc").get();

		return createResult(region, document);
	}

	public void writeToFile(String filename, AviBaseResult result) throws IOException {
		new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(new File(filename), result);
	}

	protected AviBaseResult createResult(Region region, Document document) {
		Elements sel = document.select(".highlight1");

		return AviBaseResult.builder()
			.region(region)
			.regionalScarcities(sel.stream()
				.map(e -> calculateRegionalScarcity(e, region))
				.collect(Collectors.toList()))
			.build();
	}

	private AviBaseRegionalScarcity calculateRegionalScarcity(Element element, Region region) {
		return AviBaseRegionalScarcity.builder()
			.scarcities(findScarcities(element.child(2).text()))
			.scientificName(element.child(1).text())
			.build();
	}

	private Set<Scarcity> findScarcities(String text) {
		Set<Scarcity> scarcities = new HashSet<>();

		if (text == null) {
			scarcities.add(Scarcity.COMMON_OR_BREEDING);
			return scarcities;
		}

		Map<String, Scarcity> map = setupMapping();

		String t = filterIgnores(text);

		if (t.isEmpty()) {
			scarcities.add(Scarcity.COMMON_OR_BREEDING);
		} else {

			for (Map.Entry<String, Scarcity> entry: map.entrySet()) {
				if (t.contains(entry.getKey())) {
					scarcities.add(entry.getValue());
					t = t.replace(entry.getKey(), "").trim();
				}
			}

			if (!t.isEmpty()) {
				logger.error("Missing mapping for scarcity {}", t);
				throw new IllegalStateException("Missing mapping for scarcity " + t);
			}
		}

		return scarcities;
	}

	private String filterIgnores(String text) {
		Set<String> ignore = new HashSet<>();
		ignore.add("(possibly extinct)");
		ignore.add("(non established)");
		ignore.add("Data deficient");
		ignore.add("in the wild");

		String t = text;

		for (String i: ignore) {
			t = t.replace(i, "").trim();
		}
		return t;
	}

	private Map<String, Scarcity> setupMapping() {
		Map<String, Scarcity> map = new HashMap<>();

		map.put("Rare/Accidental", Scarcity.RARE_ACCIDENTAL);
		map.put("Vulnerable", Scarcity.VULNERABLE);
		map.put("Introduced species", Scarcity.INTRODUCED);
		map.put("Near-threatened", Scarcity.NEAR_THREATENED);
		map.put("Extirpated", Scarcity.EXTIRPATED);
		map.put("Endangered", Scarcity.ENDANGERED);
		map.put("Critically endangered", Scarcity.CRITICALLY_ENDANGERED);
		map.put("Extinct", Scarcity.EXTINCT);
		map.put("Endemic (country/region)", Scarcity.ENDEMIC);
		map.put("Endemic", Scarcity.ENDEMIC);
		map.put("Breeding endemic", Scarcity.COMMON_OR_BREEDING);
		map.put("Outlying islands only", Scarcity.COMMON_OR_BREEDING);

		return map;
	}
}

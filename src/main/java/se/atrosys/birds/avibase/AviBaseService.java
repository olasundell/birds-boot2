package se.atrosys.birds.avibase;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import se.atrosys.birds.model.Region;
import se.atrosys.birds.model.RegionalScarcity;
import se.atrosys.birds.repository.BirdRepository;
import se.atrosys.birds.repository.RegionRepository;

import java.io.IOException;
import java.util.stream.Collectors;

/**
 * TODO write documentation
 */
@Component
public class AviBaseService {
	private final BirdRepository birdRepository;

	public AviBaseService(BirdRepository birdRepository,
	                      RegionRepository regionRepository) {
		this.birdRepository = birdRepository;
	}

	public AviBaseResult getAviBaseStuff(Region region) throws IOException {
		// http://avibase.bsc-eoc.org/checklist.jsp?region=EUR&list=clements
		Document document = Jsoup.connect("http://avibase.bsc-eoc.org/checklist.jsp?region=EUR&list=clements").get();
		Elements sel = document.select(".highlight1");

		return AviBaseResult.builder()
			.birds(sel.stream()
				.map(e -> calculateRegionalScarcity(e, region))
				.collect(Collectors.toList()))
			.build();
	}

	private RegionalScarcity calculateRegionalScarcity(Element element, Region region) {
		return RegionalScarcity.builder()
			.bird(birdRepository.findByScientificName(element.child(1).text()))
			.regionModel(region)
			.build();
	}
}

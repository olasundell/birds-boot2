package se.atrosys.birds.media.macaulay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import se.atrosys.birds.model.Bird;

import java.time.Instant;

/**
 * TODO write documentation
 * https://search.macaulaylibrary.org/catalog.json?searchField=species&q=oriolus+traillii
 * &action=new_search
 * &taxonCode=
&hotspotCode=
&regionCode=
&userId=
&_mediaType=on
&mediaType=all
&species=
&region=
&hotspot=
&mr=M1TO12
&bmo=1
&emo=12
&yr=YALL
&by=1900
&ey=2017
&user=
&view=Gallery
&sort=upload_date_desc
&_tag=on
&_tag=on
&_req=on
&subId=
&catId=
&_spec=on
&specId=
&start=0
&_=1509451949455
 */
@Component
public class MacaulayService {
	// read from https://www.macaulaylibrary.org
	private final RestTemplate restTemplate;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public MacaulayService() {
		this.restTemplate = new RestTemplate();
	}

	// https://search.macaulaylibrary.org/catalog.json?searchField=species&q=Eurasian+Bullfinch+-+Pyrrhula+pyrrhula
	//	 &taxonCode=eurbul&hotspotCode=&regionCode=&userId=&_mediaType=on&mediaType=a&species=&region=&hotspot=
	//	 &mr=M1TO12&bmo=1&emo=12&yr=YALL&by=1900&ey=2017&user=&view=Gallery&sort=upload_date_desc&_tag=on
	//	 &_tag=on&_req=on&subId=&catId=&_spec=on&specId=&start=0
	//	 &_=1509456795027

	public MacaulayResponse search(Bird bird) {
		UriComponentsBuilder builder = buildBuilder(bird);

		final String url = builder.toUriString();
		logger.debug("Getting Macaulay results from {}", url);

		return restTemplate.getForObject(url, MacaulayResponse.class);
	}

	private UriComponentsBuilder buildBuilder(Bird bird) {
		return UriComponentsBuilder.fromUriString("https://search.macaulaylibrary.org/catalog.json")
			.queryParam("searchField", "species")
			.queryParam("q", bird.namesByLanguage().get("english") + " - " + bird.getScientificName())
			.queryParam("_", Instant.now().toEpochMilli())
			.queryParam("taxonCode", bird.getEbirdTaxonId())
//			.queryParam("_", "1509456795027")
			;
	}

//	private UriComponentsBuilder buildBuilder(Bird bird) {
//		return UriComponentsBuilder.fromUriString("https://search.macaulaylibrary.org/catalog.json")
//				.queryParam("searchField", "species")
//				.queryParam("q", bird.getScientificName());
//	}
}

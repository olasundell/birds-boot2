package se.atrosys.birds.media.macaulay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import se.atrosys.birds.exception.CountNotFindMediaException;
import se.atrosys.birds.media.MediaService;
import se.atrosys.birds.model.Bird;
import se.atrosys.birds.model.Media;
import se.atrosys.birds.model.MediaType;
import se.atrosys.birds.service.BirdService;

import java.time.Instant;
import java.util.List;
import java.util.Random;

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
public class MacaulayService implements MediaService {
	// read from https://www.macaulaylibrary.org
	private final RestTemplate restTemplate;
	private final BirdService birdService;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final Random random;

	public MacaulayService(RestTemplate restTemplate, BirdService birdService) {
		this.restTemplate = restTemplate;
		this.birdService = birdService;
		random = new Random(0);
	}

	@Override
	public Media getSpecificMedia(Media.MediaHash hash) {
		final List<MacaulayContent> content = search(birdService.findById(hash.getBirdId()), hash.getMediaType())
			.getResults()
			.getContent();

		return buildMedia(hash.getMediaType(), hash.getIndex(), content);
	}

	@Override
	public Media getMedia(Bird bird, MediaType mediaType) throws CountNotFindMediaException {
		final List<MacaulayContent> content = search(bird, mediaType).getResults().getContent();
		if (content.isEmpty()) {
			throw new CountNotFindMediaException(bird);
		}
		final int index = random.nextInt(content.size());

		return buildMedia(mediaType, index, content);
	}

	private Media buildMedia(MediaType mediaType, int index, List<MacaulayContent> content) {
		final MacaulayContent macaulayContent = content.get(index);

		return Media.builder()
			.url(macaulayContent.getMediaUrl())
			.hash(Media.MediaHash.builder().source("MACAULAY").mediaType(mediaType).index(index).build())
			.mediaType(mediaType)
			.build();
	}
	// https://search.macaulaylibrary.org/catalog.json?searchField=species&q=Eurasian+Bullfinch+-+Pyrrhula+pyrrhula
	//	 &taxonCode=eurbul&hotspotCode=&regionCode=&userId=&_mediaType=on&mediaType=a&species=&region=&hotspot=
	//	 &mr=M1TO12&bmo=1&emo=12&yr=YALL&by=1900&ey=2017&user=&view=Gallery&sort=upload_date_desc&_tag=on
	//	 &_tag=on&_req=on&subId=&catId=&_spec=on&specId=&start=0
	//	 &_=1509456795027

	public MacaulayResponse search(Bird bird, MediaType mediaType) {
		UriComponentsBuilder builder = buildBuilder(bird, mediaType);

		final String url = builder.toUriString();
		logger.debug("Getting Macaulay results from {}", url);

		return restTemplate.getForObject(url, MacaulayResponse.class);
	}

	private UriComponentsBuilder buildBuilder(Bird bird, MediaType mediaType) {
		return UriComponentsBuilder.fromUriString("https://search.macaulaylibrary.org/catalog.json")
			.queryParam("searchField", "species")
			.queryParam("mediaType", mediaType.name().toLowerCase())
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

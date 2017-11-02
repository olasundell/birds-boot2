package se.atrosys.birds.taxonomy.ebird;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import se.atrosys.birds.model.Bird;
import se.atrosys.birds.model.Order;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * TODO write documentation
 */
@Component
public class EbirdService {
	private List<EbirdSpecies> ebirdSpecies = null;

	List<EbirdSpecies> readSpecies() {
		if (ebirdSpecies != null) {
			return ebirdSpecies;
		}

		try {
			ClassPathResource cpr = new ClassPathResource("ebird-species.json");
			ebirdSpecies = new ObjectMapper().readValue(cpr.getFile(), new EbirdSpeciesListTypeRef());
			return ebirdSpecies;
		} catch (IOException e) {
			throw new IllegalStateException("Could not read ebird species list", e);
		}
	}

	Map<String, EbirdSpecies> sciNameMap() {
		return readSpecies()
			.stream()
			.collect(Collectors.toMap(s -> s.getSciName().toLowerCase(), Function.identity()));
	}

	Map<String, EbirdSpecies> engNameMap() {
		return readSpecies()
			.stream()
			.collect(Collectors.toMap(s -> s.getComName().toLowerCase(), Function.identity()));
	}

	public List<Order> addEbirdInformation(List<Order> orders, Map<String, String> translation) {
		Map<String, EbirdSpecies> sciNameMap = sciNameMap();

		orders.stream()
			.flatMap(o -> o.getFamilies().stream())
			.flatMap(f -> f.getGenus().stream())
			.flatMap(g -> g.getBirds().stream())
			.forEach(b -> addToBird(b, sciNameMap, translation));

		return orders;
	}


	private void addToBird(Bird bird, Map<String, EbirdSpecies> sciNameMap, Map<String, String> translation) {
//		String scientificName = translateScientificName(bird);

//		if (!sciNameMap.containsKey(scientificName.toLowerCase())) {
//			if (!engNameMap.containsKey(bird.namesByLanguage().get("english"))) {
//				throw new IllegalStateException("Ebird species does not contain " + bird);
//			} else {
//				bird.setEbirdTaxonId(sciNameMap.get(bird.namesByLanguage().get("english").toLowerCase()).getTaxonID());
//			}
//		} else {

		String key = bird.getScientificName().toLowerCase();

		if (!sciNameMap.containsKey(key)) {

			if (!translation.containsKey(key)) {
				throw new IllegalStateException("Translation list does not contain " + bird);
			}

			String tra = translation.get(key);

			if (!sciNameMap.containsKey(tra)) {
				throw new IllegalStateException("Sci name map does not contain " + tra + ", translation for " +
					bird.getScientificName() + " (" + bird.namesByLanguage().get("english") + ")");
			} else {
				key = tra;
			}
		}

		final EbirdSpecies ebirdSpecies = sciNameMap.get(key);

		bird.setEbirdTaxonId(ebirdSpecies.getSpeciesCode());
//		}
	}

	private String translateScientificName(Bird bird) {
		switch (bird.getScientificName()) {
			case "sarkidiornis sylvicola":
				return "sarkidiornis melanotos";
			case "neochen jubata":
				return "oressochen jubatus";
			case "chloephaga melanoptera":
				return "oressochen melanopterus";
			case "anas diazi":
				return "anas platyrhynchos";
			case "anas carolinensis":
				return "anas crecca";
			case "melanitta deglandi":
				return "melanitta fusca";
			case "oxyura ferruginea":
				return "oxyura jamaicensis";
			case "ortalis ruficeps":
				return "ortalis motmot";
			case "colinus leucopogon":
				return "colinus cristatus";
			case "lyrurus tetrix":
				return "tetrao tetrix";
			case "lyrurus mlokosiewiczi":
				return "tetrao mlokosiewiczi";
			case "pternistis atrifrons":
				return "pternistis castaneicollis";
		}

		return bird.getScientificName();
	}

	private static class EbirdSpeciesListTypeRef extends TypeReference<List<EbirdSpecies>> {}
}

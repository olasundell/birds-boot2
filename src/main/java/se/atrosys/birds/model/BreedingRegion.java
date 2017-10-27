package se.atrosys.birds.model;

import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO write documentation
 *
 North  America (NA)–includes the Caribbean
 Middle  America (MA)—Mexico through Panama
 South  America (SA)
 Latin  America (LA)—Middle & South America: Replaced with MA, SA 6.3
 Africa  (AF)—entire continent rather than south of Sahara
 Eurasia  (EU)–Europe, Asia from the Middle East through central Asia north of the  Himalayas, Siberia and northern China to Japan.
 Oriental  Region (OR)–South Asia from Pakistan to Taiwan, plus southeast Asia, the Philippines  and Greater Sundas.
 Beaman 1994 (Palearctic Birds: A Checklist of the Birds of Europe, North Africa and Asia north of the foothills of the Himalayas), provides a working definition of the boundary between the Palearctic and Oriental regions, which can be summarised as follows, courtesy of Richard Klim: “In southern Asia the boundary runs from the Makran coast of Pakistan at Ras Ormara northwards to the Harboi Hills and the mountains of Quetta, and along the Sulaiman Range. The line then runs along  the mountainous borderlands of Pakistan and Afghanistan, the main range of the Himalayas, the mountains at the extreme northernmost tip of Myanmar, the Lijiang Range in northern Yunnan and the mountainous western margins of the Red Basin of Sichuan [species included are limited to those known to occur at or above: 2000m in northwestern Pakistan and adjacent Afghanistan, the northwestern Himalayas (Pakistan/Kashmir) and central/northern Sichuan; 2500m in the western Himalayas (Himachal Pradesh), northern Yunnan and southern Sichuan; and 2800m in the eastern Himalayas (Nepal to Arunachal Pradesh) and northernmost Myanmar].  In eastern China, the boundary follows the Yangtze/Huang Ho watershed in southern Gansu, the Qinlang Shan in Shaanxi, and then the 34°N line to the Yellow Sea coast.  On the Pacific fringe, the Nansei, Daito, Kazan and Ogasawara Islands are included.“
 Australasia  (AU)–Wallacea (Indonesian islands east of Wallace’s  Line), New Guinea and its islands, Australia, New Zealand and its subantarctic islands, the Solomons,  New Caledonia and Vanuatu.
 Atlantic,  Pacific, Indian, Tropical, Temperate, Northern & Southern Oceans (AO, PO, IO, TrO, TO, NO, SO)
 Antarctica  (AN)
 Southern  Cone (So. Cone) includes Argentina and Chile south of the Tropic of Capricorn,  also Falkland Islands.

 */
public enum BreedingRegion {
	NORTH_AMERICA("NA"),
	MIDDLE_AMERICA("MA"),
	SOUTH_AMERICA("SA"),
	AFRICA("AF"),
	EURASIA("EU"),
	ORIENTAL_REGION("OR"),
	AUSTRALASIA("AU"),
	ATLANTIC_OCEAN("AO"),
	PACIFIC_OCEAN("PO"),
	INDIAN_OCEAN("IO"),
	TROPICAL_OCEANS("TrO"),
	TEMPERATE_OCEANS("TO"),
	SOUTHERN_OCEANS("SO"),
	NORTHERN_OCEANS("NO"),
	WORLDWIDE("Worldwide"),
	ANTARCTICA("AN");

	String code;
	private static final Map<String, BreedingRegion> lookup = new HashMap<>();

	static {
		for (BreedingRegion br: BreedingRegion.values()) {
			lookup.put(br.code, br);
		}
	}

	BreedingRegion(String code) {
		this.code = code;
	}

	public static BreedingRegion findByCode(String code) {
		if (!BreedingRegion.lookup.containsKey(code.trim())) {
			LoggerFactory.getLogger(BreedingRegion.class).error("Could not find a breeding region for `{}'", code.trim());
		}

		return BreedingRegion.lookup.get(code.trim());
	}
}

package se.atrosys.birds.taxonomy.xml.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO write documentation
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class XmlSpecies {
	@XmlElement(name = "latin_name")
	@Builder.Default
	private String latinName = "";

	@Builder.Default
	private String authority = "";

	@XmlElement(name = "english_name")
	@Builder.Default
	private String englishName = "";

	@XmlElement(name = "breeding_regions")
	@Builder.Default
	private String breedingRegions = "";

	@XmlElement(name = "breeding_subregions")
	@Builder.Default
	private String breedingSubregions = "";

	@XmlAttribute
	@Builder.Default
	private String extinct = "no";

	@XmlElement
	@Builder.Default
	private List<XmlSpecies> subspecies = new ArrayList<>();

	@XmlTransient
	public Map<String, String> names = new HashMap<>();
}

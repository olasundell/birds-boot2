package se.atrosys.birds.taxonomy.xml.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * TODO write documentation
 */
@XmlRootElement(name = "ioclist")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IocList {
	@XmlElementWrapper(name = "list")
	@XmlElement(name = "order")
	@Builder.Default
	private List<XmlOrder> orders = new ArrayList<>();

	private transient Map<String, XmlSpecies> birdsByName;

	public Map<String, XmlSpecies> allBirdsByName() {
		if (birdsByName == null) {
			birdsByName = orders.stream()
				.flatMap(order -> order.getFamilies().stream())
				.flatMap(family -> family.getGenus().stream())
				.flatMap(genus -> genus.getSpecies()
					.stream()
					.map(s -> Tuple.builder()
						.genus(genus.getLatinName())
						.species(s)
						.build()))
				.collect(Collectors.toMap(Tuple::getFullName, Tuple::getSpecies));
		}

		return birdsByName;
	}

	@Builder
	@Data
	private static class Tuple {
		String genus;
		XmlSpecies species;

		String getFullName() {
			return genus + " " + species.getLatinName();
		}
	}
}

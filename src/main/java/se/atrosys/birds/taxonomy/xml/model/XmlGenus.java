package se.atrosys.birds.taxonomy.xml.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO write documentation
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class XmlGenus {
	@XmlElement(name = "latin_name")
	@Builder.Default
	private String latinName = "";

	@XmlElement
	@Builder.Default
	private String authority = "";

	@XmlElement
	@Builder.Default
	private List<XmlSpecies> species = new ArrayList<>();
}

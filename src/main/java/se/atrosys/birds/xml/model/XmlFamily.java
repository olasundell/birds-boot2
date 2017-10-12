package se.atrosys.birds.xml.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * TODO write documentation
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class XmlFamily {
	@XmlElement(name = "latin_name")
	@Builder.Default
	private String latinName = "";

	@XmlElement(name = "english_name")
	@Builder.Default
	private String englishName = "";

	@XmlElement
	@Builder.Default
	private List<XmlGenus> genus = new ArrayList<>();
}

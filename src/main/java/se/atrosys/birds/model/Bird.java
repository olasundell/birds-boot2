package se.atrosys.birds.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * TODO write documentation
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Bird {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIdentityReference
	private Integer id;

	private String scientificName;

	private String href;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonManagedReference
	private List<BirdName> birdNames;

	@ManyToOne
	@JsonBackReference
	private Genus genus;

	@Transient
	@JsonProperty("genusName")
	public String getGenusName() {
		return genus.getName();
	}

	@Transient
	@JsonProperty("familyName")
	public String getFamilyName() {
		return genus.getFamily().getName();
	}

	@OneToMany(cascade = CascadeType.ALL)
	private List<RegionalScarcity> regionalScarcity;

	@Transient
	@JsonIgnore
	public Map<String, String> namesByLanguage() {
		return birdNames.stream().collect(Collectors.toMap(b -> b.getLanguage().getName(), BirdName::getName));
	}
}

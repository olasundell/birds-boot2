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
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.HashMap;
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
@Table(name = "birds", indexes = { @Index(name = "BIRDS_SCINAME_IDX", columnList = "scientificName") })
public class Bird {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIdentityReference
	private Integer id;

	private String scientificName;

	private String href;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "bird_id")
	@JsonManagedReference
	private List<BirdName> birdNames;

	private String ebirdTaxonId;

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
	@JoinTable(indexes = { @Index(name = "birds_regional_scarcity_bird_id_idx", columnList = "bird_id")})
	private List<RegionalScarcity> regionalScarcity;

	@ElementCollection(targetClass = BreedingRegion.class)
	@Enumerated(EnumType.STRING)
	@Builder.Default
	@JoinTable(indexes = { @Index(name = "bird_breeding_regions_bird_id_idx", columnList = "bird_id")})
	private List<BreedingRegion> breedingRegions = new ArrayList<>();

	@Transient
	@JsonIgnore
	public Map<String, String> namesByLanguage() {
		Map<String, String> map = new HashMap<>();

		for (BirdName name: birdNames) {
			map.put(name.getLang().toLowerCase(), name.getName());
		}

		return map;

		// it is beyond me why the below doesn't work
//		return birdNames.stream()
//			.collect(Collectors.toMap(BirdName::getLang, BirdName::getName));
	}
}

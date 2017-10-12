package se.atrosys.birds.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
//	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String scientificName;

	private String href;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonManagedReference
	private List<BirdName> birdNames;

	@ManyToOne
	@JsonBackReference
	private Genus genus;
//	@ManyToMany(cascade = CascadeType.ALL)
//	@JoinColumn(name = "BIRD_ID")
//	@ForeignKey(name = "FK_PHOTOS")
//    @JoinTable(name = "BIRDS_PHOTOS_JT")
//	private List<Photo> birdPhotos;

	@OneToMany(cascade = CascadeType.ALL)
//	@ForeignKey(name = "FK_SCARCITY")
	private List<RegionalScarcity> regionalScarcity;

//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@ForeignKey(name = "FK_SOUNDS")
//	private List<Sound> sounds;

	@Transient
	private Map<Locale, String> nameByLocale;
	@Transient
	private Map<String, String> nameByString;
}

package se.atrosys.birds.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.IndexColumn;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Locale;

/**
 * TODO write documentation
 */
@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(indexes = { @Index(name = "bird_name_bird_id_idx", columnList = "bird_id")})
public class BirdName {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	private Bird bird;

	private String name;

	@ManyToOne
	@JoinColumn
	private Language language;

	@JsonProperty("lang")
	public String getLang() {
		return language.getLocale().getDisplayLanguage();
	}
}

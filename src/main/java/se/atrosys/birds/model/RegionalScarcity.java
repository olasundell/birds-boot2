package se.atrosys.birds.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.Set;

/**
 * TODO write documentation
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegionalScarcity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne
	private Bird bird;
	@OneToOne
	private Region region;

	@ElementCollection(targetClass = Scarcity.class)
	@Enumerated(EnumType.STRING)
	private Set<Scarcity> scarcity;
}

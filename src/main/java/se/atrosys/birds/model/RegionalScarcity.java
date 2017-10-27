package se.atrosys.birds.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * TODO write documentation
 */
@Entity
@Data
@Builder
public class RegionalScarcity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	private Bird bird;
	@OneToOne
	private Region regionModel;
	@Enumerated(EnumType.STRING)
	private Scarcity scarcity;
}

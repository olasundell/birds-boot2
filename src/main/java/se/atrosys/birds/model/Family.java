package se.atrosys.birds.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * TODO write documentation
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Family {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	@OneToMany(mappedBy = "family", fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<Genus> genus;
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	private Order order;
}

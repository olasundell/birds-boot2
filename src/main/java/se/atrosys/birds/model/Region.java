package se.atrosys.birds.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * TODO write documentation
 */
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonDeserialize(builder = Region.RegionBuilder.class)
public class Region {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String code;
	private String name;

	@JsonPOJOBuilder(withPrefix = "")
	public static class RegionBuilder {}
}

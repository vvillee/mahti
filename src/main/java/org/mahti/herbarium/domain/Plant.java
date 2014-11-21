package org.mahti.herbarium.domain;

import javax.persistence.Entity;
import javax.persistence.Lob;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Plant extends AbstractPersistable<Long> {

	@NotBlank
	private String PictureName;  // overal picture name, example "Roses in Finland."
	@NotBlank
	private String PlantName;  // name in common language for quiz

	// optionals
	// private String LatinName;  // name in latin language for quiz
	// private String description;

	@Lob  // tells database to reserve big blocks, optional, runs faster
	private byte[] picture;

	public String getPictureName() {
		return PictureName;
	}

	public void setPictureName(String PictureName) {
		this.PictureName = PictureName;
	}

	public String getPlantName() {
		return PlantName;
	}

	public void setPlantName(String PlantName) {
		this.PlantName = PlantName;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}
}

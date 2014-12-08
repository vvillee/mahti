package org.mahti.herbarium.domain;

import javax.persistence.Entity;
import javax.persistence.Lob;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Plant extends AbstractPersistable<Long> {

    // fields: date, location (GPS: lat&long)

    private boolean identified;

    // names
    private String name;  // kansankielellä
    private String family;  //  heimo
    private String genus;  // suku
    private String species;  // laji
    // binomial nomenclature: genus + species

	@Lob  // tells database to reserve big blocks, optional, runs faster
	private byte[] content;
        
    //link to right table
    private String username;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}
        
    public void setUser(String user){
        this.username=user;
    }
    public String getUser(){
        return this.username;
    }
}

package org.mahti.herbarium.domain;

import javax.persistence.Entity;
import javax.persistence.Lob;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Plant extends AbstractPersistable<Long> {

    // optional:
    // private String kingdom;  // kunta?
    // private String order;  //  lahko
    // private String tribe;  //  heimo

    // requested
    // private String family;  //  heimo
    // private String genus;  // suku
    // private String species;  // laji

	@Lob  // tells database to reserve big blocks, optional, runs faster
	private byte[] content;
        
        //link to right table
        private String username;

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

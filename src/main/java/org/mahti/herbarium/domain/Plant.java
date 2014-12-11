package org.mahti.herbarium.domain;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Plant extends AbstractPersistable<Long> {

	// TODO
    // fields: date, location (GPS: lat&long)

	private long correct;
	private long incorrect;
	// see who have voted and prevent them to vote again
	List<User> usersVoted;  // or should be String username?

	private BigInteger likes;
	List<User> usersLiked;  // or should be String username?

    private boolean identified;

    // names
    private String name;  // kansankielellä
    private String family;  //  heimo
    private String genus;  // suku
    private String species;  // laji
    // binomial nomenclature: genus + species

	@Lob  // tells database to reserve big blocks, optional, runs faster
	private byte[] content;
        
    // TODO link to the right table
    private String username;

    @Temporal(TemporalType.DATE)
    private Date date;


	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigInteger getLikes() {
		return likes;
	}

	public void setLikes(BigInteger likes) {
		this.likes = likes;
	}

    public boolean isIdentified() {
        return identified;
    }

    public void setIdentified(boolean identified) {
        this.identified = identified;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getGenus() {
        return genus;
    }

    public void setGenus(String genus) {
        this.genus = genus;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

}

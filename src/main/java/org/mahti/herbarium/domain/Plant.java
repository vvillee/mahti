package org.mahti.herbarium.domain;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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

    private String username;

    @Temporal(TemporalType.DATE)
    private Date date;

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setUser(String user) {
        this.username = user;
    }

    public String getUser() {
        return this.username;
    }
}

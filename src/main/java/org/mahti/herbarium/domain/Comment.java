package org.mahti.herbarium.domain;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Comment extends AbstractPersistable<Long> {
    
    @NotBlank
    @Length(min = 1, max = 255)
    private String comment;
    
    @Temporal(TemporalType.DATE)
    private Date dateAdded;
    
    private long userId;
    
    private long plantId;

    public String getComment() {
        return comment;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public long getUserId() {
        return userId;
    }

    public long getPlantId() {
        return plantId;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setPlantId(long plantId) {
        this.plantId = plantId;
    }
    
}

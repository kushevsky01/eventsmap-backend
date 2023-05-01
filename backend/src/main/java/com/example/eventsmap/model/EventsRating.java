package com.example.eventsmap.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "eventsrating")
public class EventsRating {

    @EmbeddedId
    EventsRatingPK id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    @JsonIgnore
    User user;

    @ManyToOne
    @MapsId("eventsId")
    @JoinColumn(name = "events_id")
    @ToString.Exclude
    @JsonIgnore
    Events events;

    @Column(name = "rating")
    long rating;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EventsRating other = (EventsRating) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}

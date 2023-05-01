package com.example.eventsmap.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Embeddable
public class EventsRatingPK implements Serializable {

    @Column(name ="user_id")
    private Long userId;

    @Column(name = "event_id")
    private Long eventsId;


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        result = prime * result + ((eventsId == null) ? 0 : eventsId.hashCode());
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
        EventsRatingPK other = (EventsRatingPK) obj;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        if (eventsId == null) {
            if (other.eventsId != null)
                return false;
        } else if (!eventsId.equals(other.eventsId))
            return false;
        return true;
    }


}

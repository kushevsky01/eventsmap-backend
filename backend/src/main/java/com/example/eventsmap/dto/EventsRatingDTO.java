package com.example.eventsmap.dto;

import com.example.eventsmap.model.Events;
import com.example.eventsmap.model.EventsRatingPK;
import com.example.eventsmap.model.User;
import lombok.*;

import javax.persistence.*;

@Data
public class EventsRatingDTO {

    long userId;

    long eventsId;

    long rating;
}
